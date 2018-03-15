var express = require("express");
var bodyParser = require("body-parser");
var path = require("path");
const port = process.env.PORT || 3000;

var app = express();

var index = require("./routes/index");
var users = require("./routes/users");

app.set("views", path.join(__dirname, "view"));
app.set('view engine', "ejs");
app.engine('html', require('ejs').renderFile);

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));

app.use((req, res, next) => {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE");
    res.header("Access-Control-Allow-Headers", "Content-Type");
    next();
});

/*
 *VIEW ENGINE CONFIGURATION
 */

app.set("views", path.join(__dirname, "view"));
app.set('view engine', "ejs");
app.engine('html', require('ejs').renderFile);

app.use('/', index);
app.use('/api', users);

var server = app.listen(port, () => {
    console.log("MACROP SERVER STARTED ON PORT " + port);
});