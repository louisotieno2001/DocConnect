package com.healthcare.docconnect;

import com.parse.Parse;
import android.app.Application;

public class App extends Application {
  String applicationId = "myappid";
  String clientId = "clientkey";

  @Override
  public void onCreate() {
    super.onCreate();
    Parse.enableLocalDatastore(this);
    Parse.initialize(new Parse.Configuration.Builder(this)
        .applicationId(applicationId)
        .clientKey(clientId)
        .server("http://localhost:1337/parse/")
        .build());

  }
}
