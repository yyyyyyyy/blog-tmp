var express = require('express');
var router = express.Router();
var http = require('http')

var options = {
    hostname: '127.0.0.1',
    port: 8762,
    path: '/findAllBlogs',
    method: 'GET'
}

/* GET home page. */
router.get('/', function (req, res) {
    http.get(options, function (response) {
        console.log('STATUS: ' + response.statusCode);
        var buffer = [];
        response.on('data', function (chunk) {
            buffer.push(chunk);
        });
        response.on('error', function (e) {
            console.log('problem with request: ' + e.message);
            res.render('error');
        });
        response.on('end', function (chuck) {
            var wholeData = Buffer.concat(buffer);
            res.render('index', {title: '三土同学', topics: JSON.parse(wholeData)});
        });
    })
});

module.exports = router;

