//dependencies:
var mongoose = require('mongoose');
mongoose.Promise = global.Promise;

//Schema
var userSchema = mongoose.Schema({
    id:String,
    username:String,
    password:String,
    firstName:String,
    lastName:String
});

//return models:
exports.users = mongoose.model('users',userSchema);