//dependencies
var express = require("express");
var mongoose = require("mongoose");
var bodyParser = require("body-parser");
const port = process.env.PORT || 3000;

//connect to mongoDB:
mongoose.connect('mongodb://admin:admintypeup@ds113799.mlab.com:13799/typeup');

//express:
var app = express();
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

//route refs:
var users = require('./routes/users');
//routes:
app.use('/api',users);

//start server:
var server = app.listen(port, () => {
    console.log("TypeUp server started on port:" + port);
});
