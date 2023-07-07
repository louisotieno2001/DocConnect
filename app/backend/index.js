const { ParseServer } = require("parse-server");
const express = require("express");

const app = express();

// using postgress for my database since i have it installed
// change to mongodb in production
const api = new ParseServer({
  databaseURI: "postgres://admin:admin@localhost:5432/docconnect",
  appId: "myappid",
  clientKey: "clientkey",
  masterKey: "masterSecretKey",
  serverURL: "http://localhost:1337/parse"
});
(async () => {
  await api.start();

})();

app.use("/parse", api.app);

app.listen(1337, function() {
  console.log("Parse server listening at http://localhost:1337/parse ");

});


