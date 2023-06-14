package omenta.ant.android;

import org.apache.ivy.Ivy;
import org.apache.ivy.core.report.ResolveReport;

import org.apache.ivy.core.report.ArtifactDownloadReport;
import org.apache.ivy.core.module.descriptor.Artifact;

import org.apache.tools.ant.Task;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.types.resources.FileResource;
import org.apache.tools.ant.types.resources.Resources;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Mkdir;
import org.apache.tools.ant.types.PatternSet;
import org.apache.tools.ant.types.Mapper;
import org.apache.tools.ant.types.Mapper.MapperType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.OutputKeys;

public class IvyAndroid extends Task {
  private String file;
  private String settings;
  private ArrayList<String> packageList = new ArrayList<String>();

  public void setFile(String path) {
    this.file = path;
  }

  public void setSettings(String settings) {
    this.settings = settings;
  }

  public void execute() {

    Ivy ivy = Ivy.newInstance();

    File libDest = new File(getProject().getProperty("lib.path"));
    File libResDest = new File(getProject().getProperty("lib.res.path"));

    try {
      if (settings != null) {
        File f = new File(settings);
        ivy.configure(f);
      }

      if (file != null) {
        ResolveReport report = ivy.resolve(new File(file));

        log("Retrieving files from cache");
        Resources resources = new Resources();

        for (ArtifactDownloadReport a : report.getAllArtifactsReports()) {
          Artifact artifact = a.getArtifact();
          File file = a.getLocalFile();
          String ext = artifact.getExt();

          if (ext.equals("aar")) {
            String aarFileName = artifact.getName() + "-" + artifact.getModuleRevisionId().getRevision();

            if (!(new File(libDest, aarFileName + ".jar").exists())) {

              PatternSet patternSet = new PatternSet();
              patternSet.setIncludes("classes.jar");

              Expand expand = new Expand();
              expand.addPatternset(patternSet);
              expand.setSrc(file);
              expand.setDest(libDest);
              expand.setOverwrite(false);

              Mapper mapper = expand.createMapper();
              mapper.setFrom("classes.jar");
              mapper.setTo(aarFileName + ".jar");
              MapperType mapperType = new MapperType();
              mapperType.setValue("merge");
              mapper.setType(mapperType);

              expand.execute();
            }

            File artifactResFolder = new File(libResDest, aarFileName);
            if (!(artifactResFolder.exists())) {
              // create the res folder and extract content
              Mkdir mkdir = new Mkdir();
              mkdir.setDir(artifactResFolder);
              mkdir.execute();

              PatternSet patternSet = new PatternSet();
              patternSet.setExcludes("classes.jar");

              Expand expand = new Expand();
              expand.addPatternset(patternSet);
              expand.setSrc(file);
              expand.setDest(artifactResFolder);

              expand.execute();
            }

            // read the manifest and update package list
            File manifest = new File(artifactResFolder, "AndroidManifest.xml");
            if (manifest.exists())
              addToPackageList(manifest);

          } else if (ext.equals("jar")) {
            resources.add(new FileResource(file));
          }
        }
        Copy copy = new Copy();
        copy.add(resources);
        copy.setTodir(libDest);
        copy.execute();
      }

      buildPackageListXml(new File(libResDest, "PackageList.xml"));

    } catch (Exception e) {
      throw new BuildException(e);
    }

  }

  private void addToPackageList(File manifest) throws Exception {
    // Read the androidmanifest xml file and extract the package name
    XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
    XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(manifest));
    while (reader.hasNext()) {
      XMLEvent xmlEvent = reader.nextEvent();
      if (xmlEvent.isStartElement()) {
        StartElement startElement = xmlEvent.asStartElement();
        if (startElement.getName().getLocalPart().equals("manifest")) {
          String packageName = startElement.getAttributeByName(new QName("package")).getValue();
          packageList.add(packageName);
          break;
        }
      }
    }
    reader.close();
  }

  private void buildPackageListXml(File packageListFile) throws Exception {
    if (!packageListFile.exists()) {
      packageListFile.createNewFile();
    }

    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = documentBuilderFactory.newDocumentBuilder();
    Document doc = db.newDocument();
    Element rootElement = doc.createElement("packages");
    doc.appendChild(rootElement);

    for (String packageName : packageList) {
      Element p = doc.createElement("package");
      p.setAttribute("name", packageName);
      rootElement.appendChild(p);
    }

    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();

    transformer.setOutputProperty(OutputKeys.INDENT, "yes");

    DOMSource source = new DOMSource(doc);
    StreamResult result = new StreamResult(new FileOutputStream(packageListFile));

    transformer.transform(source, result);
  }
}
