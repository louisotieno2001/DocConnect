package omenta.ant.android;

import org.apache.tools.ant.Task;

import org.apache.tools.ant.Project;
import java.io.File;
import java.io.FilenameFilter;

import org.apache.tools.ant.BuildException;

public class Main extends Task {

  private boolean checkForSdk = false;

  public void setCheckForSdk(boolean b) {
    this.checkForSdk = b;
  }

  /* called when the plugin is executed */
  public void execute() throws BuildException {
    Project project = getProject();

    /* First checks */

    if (project == null)
      throw new BuildException("Project not initialized");

    if (checkForSdk) {
      checks();
    }
  }

  public void checks() throws BuildException {
    /* checks */
    /* check for android home */
    log("Checking for android sdk");
    String androidHome = getProject().getProperty("env.ANDROID_HOME");

    if (androidHome == null || androidHome == "") {
      throw new BuildException(
          "Failed to locate Android Sdk. Please ensure that ANDROID_HOME environmental variable exists");
    }

    /* check for aapt2 , d8 , apksigner , etc */
    File path = new File(androidHome);

    if (!path.exists()) {
      throw new BuildException("ANDROID_HOME path: " + androidHome + " is not accessible");
    }

    log("Found: " + path.toString());

    File buildTools = new File(path, "build-tools");

    if (!buildTools.exists())
      throw new BuildException("Failed to locate android sdk build tools. Is it installed? ");

    File[] buildToolsVersions = buildTools.listFiles(new FilenameFilter() {
      public boolean accept(File parent, String name) {
        return name.matches("(\\d+).(\\d+).(\\d+)");
      }
    });

    String buildToolsVersion = "0.0.0";

    /* check for build tools in any of the versions */
    for (File f : buildToolsVersions) {
      
      String name = f.getName();
      log("Build tools: "+name);
      // set the highest version as the current version of build tools
      if (name.compareTo(buildToolsVersion) > 0) {
        buildToolsVersion = name;
      }
    }

    getProject().setProperty("android.buildtools.version", buildToolsVersion);

  }
}
