package omenta.ant.android;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Execute;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.taskdefs.UpToDate;

import java.util.ArrayList;
import java.io.File;

public class D8 extends Task {
  private String source;
  private boolean release;
  private boolean includelibs;
  private String lib;
  private String output;
  private String classpath;
  private Path libpath;


  public void setSource(String s) {
    this.source = s;
  }

  public void setRelease(boolean b) {
    this.release = b;
  }

  public void setLib(String l) {
    this.lib = l;
  }

  public void setClasspath(String path) {
    this.classpath = path;
  }

  public void setOutput(String path) {
    this.output = path;
  }
  public void setIncludelibs(boolean b){
    this.includelibs = b;
  }
  public Path createLibpath(){
      this.libpath = new Path(getProject());
      return this.libpath;
  }

  private boolean isIvyDependenciesUpToDate(){
    UpToDate up = new UpToDate();
    File ivy = new File("ivy.xml");
    File lib = new File(this.output+"/libdex.jar");

    up.setSrcfile(ivy);
    up.setTargetFile(lib);

    boolean uptodate = up.eval();

    return uptodate;
  }

  private boolean isSourceUpToDate(String source, String output){
    File jar = new File(source);
    File dex = new File(output+"/classes.dex");

    UpToDate up = new UpToDate();
    up.setSrcfile(jar);
    up.setTargetFile(dex);

    return up.eval();
  }

  public void execute() {
    if (source == null) {
      throw new BuildException("Source not provided");
    }

    if (output == null) {
      throw new BuildException("Destination output not provided");
    }

    boolean libUpToDate = isIvyDependenciesUpToDate();
    boolean sourceUpToDate = isSourceUpToDate(source, output);

    if(!libUpToDate){
      // recompile libraries
      intermediate();
    }

    if(!libUpToDate || !sourceUpToDate)
       d8(source, this.output+"/libdex.jar", "--output", output);
    else
      log("Dex is up to date");

  }

  private void intermediate(){
    ArrayList<String> commands = new ArrayList<String>();
    commands.add("d8");
    if(libpath != null){
       for(String lib: libpath.list()){
         commands.add(lib);
       }
    }

    log("Compiling libraries to dex for intermediate builds");
    commands.add("--intermediate");
    commands.add("--min-api");
    commands.add("25");
    commands.add("--output");
    commands.add(this.output+"/libdex.jar");

    StringBuilder builder = new StringBuilder();

    Execute.runCommand(this, commands.toArray(new String[commands.size()]));
  }

  public void d8(String... args) {

    ArrayList<String> commands = new ArrayList<String>();
    commands.add("d8");

    for (String s : args) {
      commands.add(s);
    }

    if (release) {
      commands.add("--release");
    }

    if (classpath != null) {
      commands.add("--classpath");
      commands.add(classpath);
    }

    if (lib != null) {
      commands.add("--lib ");
      commands.add(lib);
    }

    commands.add("--min-api");
    commands.add("25");
    //commands.add("--no-desugaring");


    Execute.runCommand(this, commands.toArray(new String[commands.size()]));

  }
}
