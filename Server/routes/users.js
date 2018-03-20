//dependencies:
var express = require('express');
var router = express.Router();

//get models:
const users = require('../models/users.js').users;

//routes:

router.get("/getAllUsers", (req, res, next) => {
    users.find((err, docs) => {
        if (err)
            res.json(err);
        else
            res.json(docs);
    });
});

router.post("/register", (req, res, next) => {
    let user = req.body;
    console.log(user);
    users.find({username: user.username }, (err, doc) => {
        if (err)
            res.send(err);
        else {
            if (doc.length)
                res.json(doc);
            else {
                
                users.create({ username: user.username, password: user.password, firstName: user.firstName, lastName:user.lastName }, (erro, userdoc) => {
                    if (erro)
                        res.send(err);
                    res.json(userdoc);
                });
            }
        }
    });
});

//export router
module.exports = router;