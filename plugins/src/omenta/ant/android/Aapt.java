package omenta.ant.android;

import org.apache.tools.ant.types.Mapper;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Execute;
import org.apache.tools.ant.types.DirSet;
import org.apache.tools.ant.types.selectors.DepthSelector;
import org.apache.tools.ant.types.selectors.FileSelector;
import org.apache.tools.ant.types.selectors.MappingSelector;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class Aapt extends Task {
  private String compile;
  private String manifest;
  private String outfile;
  private String java;
  private String link;
  private String android;
  private String libresources;

  public void setCompile(String path) {
    this.compile = path;
  }

  public void setManifest(String path) {
    this.manifest = path;
  }

  public void setOutfile(String path) {
    this.outfile = path;
  }

  public void setJava(String path) {
    this.java = path;
  }

  public void setAndroid(String path) {
    this.android = path;
  }

  public void setLink(String path) {
    this.link = path;
  }

  public void setLibresources(String path) {
    this.libresources = path;
  }

  public void execute() {
    if (this.compile != null)
      compile();

    if (this.link != null)
      link();

  }

  public String[] findLibraryResources() {
    log("Finding resourcses");
    File libres = new File(this.libresources);

    DepthSelector selector = new DepthSelector();
    selector.setMax(1);

    DirectoryScanner scanner = new DirectoryScanner();
    scanner.setBasedir(libres);
    scanner.setIncludes(new String[] { "*/res/" });
    scanner.setSelectors(new FileSelector[] { selector });
    scanner.scan();

    String[] libs = scanner.getIncludedDirectories();
    for (int i = 0; i < libs.length; i++) {
      String s = libs[i];

      int resIndex = s.lastIndexOf("/res");
      if (resIndex > 0)
        libs[i] = s.substring(0, resIndex);
    }
    return libs;
  }

  public void compile() {

    Project project = getProject();

    if (compile == null)
      throw new BuildException("No input source for compilation");

    File source = new File(project.getBaseDir(), this.compile);

    if (!source.exists())
      throw new BuildException("Input source " + source.toString() + " does not exist");

    if (this.outfile == null || this.outfile == "")
      throw new BuildException("Output path not set");

    File dest = new File(project.getBaseDir(), this.outfile);

    if (!dest.exists())
      throw new BuildException("Cannot find path " + dest.toString() + " ");

    aapt("compile", "--dir", source.toString(), "-o", new File(dest, "resources.zip").toString());

    if (libresources != null) {
      String[] libs = findLibraryResources();
      for (String lib : libs) {

        File input = new File(libresources, lib + "/res");
        File output = new File(dest, lib + ".zip");

        if (!output.exists()) {
          aapt("compile", "--dir", input.toString(), "-o", output.toString());
        }
      }
    }
  }

  public void link() {

    Project project = getProject();

    if (link == null)
      throw new BuildException("No input targets specified to link");

    File root = project.getBaseDir();
    // TODO to change this linker to support more files
    File source = new File(root, this.link);
    File android = new File(this.android);
    File manifest = new File(root, this.manifest);
    File java = new File(root, this.java);
    File outfile = new File(root, this.outfile);

    if (!android.exists())
      throw new BuildException("Unable to locate android.jar");

    if (!manifest.exists()) {
      throw new BuildException("Unable to load AndroidManifest.xml");
    }
    File compiledRes = new File(root, project.getProperty("app.res.compiled"));

    DirectoryScanner scanner = new DirectoryScanner();
    scanner.setBasedir(compiledRes);
    scanner.setIncludes(new String[] { "*.zip" });
    scanner.scan();

    String[] files = scanner.getIncludedFiles();

    for (int i = 0; i < files.length; i++) {
      files[i] = project.getProperty("app.res.compiled") + "/" + files[i];
    }
    String extraPackages = null;
    try {
       extraPackages = loadExtraPackages();
    }catch(Exception e){
        log(e.toString());
    }
    apptLink(files, android, java, manifest, outfile, extraPackages);
  }

  private void apptLink(String[] sources, File androidLib, File javaGen, File manifest, File output, String extraPackages) {
    ArrayList<String> commands = new ArrayList<String>();
    commands.add("aapt2");
    commands.add("link");
    for (String source : sources) {
      commands.add(source);
    }
    commands.add("-I");
    commands.add(androidLib.toString());
    commands.add("--auto-add-overlay");
    commands.add("--debug-mode");
    if(extraPackages != ""){
      commands.add("--extra-packages");
      commands.add(extraPackages);
    }
    commands.add("--manifest");
    commands.add(manifest.toString());
    commands.add("--java");
    commands.add(javaGen.toString());
    commands.add("-o");
    commands.add(output.toString());

    String[] args = commands.toArray(new String[commands.size()]);
    Execute.runCommand(this, args);
  }

  private void aapt(String... args) throws BuildException {

    String version = getProject().getProperty("android.buildtools.version");
    File androidHome = new File(getProject().getProperty("env.ANDROID_HOME"));
    File buildtools = new File(androidHome, "build-tools/" + version);
    File aapt2 = new File(buildtools, "aapt2");

    if (!aapt2.exists())
      throw new BuildException("Unable to locate aapt2 in android build tools " + buildtools.toString());

    ArrayList<String> commands = new ArrayList<String>();
    commands.add("aapt2");

    StringBuilder sb = new StringBuilder();
    sb.append("aapt2 ");

    for (String s : args) {
      commands.add(s);
      sb.append(s + " ");
    }

    log("Executing: " + sb.toString());
    Execute.runCommand(this, commands.toArray(new String[commands.size()]));

  }

  private String loadExtraPackages() throws Exception {
    StringBuilder sb = new StringBuilder();
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = dbFactory.newDocumentBuilder();

    File libresDir = new File(getProject().getBaseDir(), "lib-res");
    if(!libresDir.exists())
      return "";

    File packageListFile = new File(libresDir, "PackageList.xml");
    if(!packageListFile.exists())
      return "";

    Document doc = db.parse(new FileInputStream(packageListFile));

    NodeList nodeList = doc.getElementsByTagName("package");

    for(int i = 0; i < nodeList.getLength(); i++){
      Node node = nodeList.item(i);
      if(node instanceof Element){
         String name = ((Element) node).getAttribute("name");

         sb.append(name+":");
      }
    }
    String extras = sb.toString();
    extras = extras.replaceAll(":$", "");

    return extras;
  }
}
