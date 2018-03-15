var express = require("express");
var router = express.Router();


router.get('/', (req, res, next) => {
    res.render("../view/index.html");
});

module.exports = router;