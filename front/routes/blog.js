var express = require('express');
var router = express.Router();
var http = require('http');
var md = require('markdown').markdown;

router.get('/', function (req, res) {
    res.render('addBlog');
});

/* GET CONTENT BY ID */
router.get('/:id', function (req, res) {
    var options = {
        hostname: '127.0.0.1',
        port: 8762,
        path: '/findBlogById/' + req.param('id'),
        method: 'GET'
    };
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
            var data = Buffer.concat(buffer);
            var blog = JSON.parse(data);
            var content = md.toHTML(blog.content);
            res.render('blog', {title: '三土同学', content: content});
        });
    })
});

router.post('/add', function (req, res) {

    var postData = JSON.stringify(req.body);

    var options = {
        hostname: '127.0.0.1',
        port: 8762,
        path: '/addBlog',
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Content-Length': Buffer.byteLength(postData)
        }
    };

    var buffer = [];
    var reqHttp = http.request(options, function (response) {
        console.log('STATUS: ' + response.statusCode);
        response.on('data', function (chunk) {
            buffer.push(chunk);
        });
        response.on('error', function (e) {
            console.log('problem with request: ' + e.message);
            res.render('error');
        });
        response.on('end', function (chuck) {
            var data = Buffer.concat(buffer);
            var blog = JSON.parse(data);
            var content = md.toHTML(blog.content);
            res.render('blog', {title: '三土同学', content: content});
        });
    });

    reqHttp.on('error', function (e) {
        console.log('problem with request : ' + e.message)
    });

    reqHttp.write(postData);
    reqHttp.end;
});

module.exports = router;