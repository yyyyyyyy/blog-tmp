const express = require('express');
const router = express.Router();
const http = require('http');
const md = require('markdown').markdown;

router.get('/', (req, res) => {
    res.render('addBlog');
});


router.get('/list', (req, res) => {
    const options = {
        hostname: '127.0.0.1',
        port: 8762,
        path: '/core/note/ListNoteNameByAccountId2ndCategory?category=' + req.param('id'),
        method: 'GET',
        xhrFields: {
            withCredentials: true
        }
    };
    http.get(options, (response) => {
        console.log('STATUS: ' + response.statusCode);
        const buffer = [];
        response.on('data', (chunk) => {
            buffer.push(chunk);
        });
        response.on('error', (e) => {
            console.log('problem with request: ' + e.message);
            res.render('error');
        });
        response.on('end', (chuck) => {
            const data = Buffer.concat(buffer);
        });
    })
});

/* GET CONTENT BY ID */
router.get('/get', (req, res) => {
    const options = {
        hostname: '127.0.0.1',
        port: 8762,
        path: '/getNote/' + req.param('id'),
        method: 'GET'
    };
    http.get(options, (response) => {
        console.log('STATUS: ' + response.statusCode);
        const buffer = [];
        response.on('data', (chunk) => {
            buffer.push(chunk);
        });
        response.on('error', (e) => {
            console.log('problem with request: ' + e.message);
            res.render('error');
        });
        response.on('end', (chuck) => {
            const data = Buffer.concat(buffer);
            const note = JSON.parse(data);
            const content = md.toHTML(note.content);
            res.render('blog', {title: '三土同学', content: content});
        });
    })
});

router.post('/add', (req, res) => {

    const postData = JSON.stringify(req.body);

    const options = {
        hostname: '127.0.0.1',
        port: 8762,
        path: '/addBlog',
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Content-Length': Buffer.byteLength(postData)
        }
    };

    const buffer = [];
    const reqHttp = http.request(options, (response) => {
        console.log('STATUS: ' + response.statusCode);
        response.on('data', (chunk) => {
            buffer.push(chunk);
        });
        response.on('error', (e) => {
            console.log('problem with request: ' + e.message);
            res.render('error');
        });
        response.on('end', (chuck) => {
            const data = Buffer.concat(buffer);
            const blog = JSON.parse(data);
            const content = md.toHTML(blog.content);
            res.render('blog', {title: '三土同学', content: content});
        });
    });

    reqHttp.on('error', (e) => {
        console.log('problem with request : ' + e.message)
    });

    reqHttp.write(postData);
    reqHttp.end;
});

module.exports = router;