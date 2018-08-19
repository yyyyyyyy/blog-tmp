const express = require('express');
const router = express.Router();
const http = require('http');

router.get('/', (req, res) => {
    var keys = req.cookies.SESSION;
    var uid = req.session.key;
    console.info(uid);
    if (typeof(uid) == 'undefined') {
        res.render('index');
        return;
    } else {
        var category = req.query.category;
        if (typeof(category) == 'undefined') {
            category == null;
        }

        const options = {
            hostname: '127.0.0.1',
            port: 8762,
            path: '/core/note/ListNoteNameByAccountId2ndCategory?accountId=' + uid + '&category=' + category,
            method: 'GET',
            xhrFields: {
                withCredentials: true
            }
        }
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
            response.on('end', () => {
                const result = JSON.parse(Buffer.concat(buffer).toString());
                if (result.success) {
                    res.render('note_list', {title: '三土同学', notes: result.response});
                }
            });
        })
    }

});

router.post('/login', (req, res) => {
    const postData = JSON.stringify(req.body);

    const options = {
        hostname: '127.0.0.1',
        port: 8762,
        path: '/user/login',
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Content-Length': Buffer.byteLength(postData),
            xhrFields: {
                withCredentials: true
            }
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
            console.info(response.cookie);
            const data = Buffer.concat(buffer);
            const result = JSON.parse(data);
            if (result.success) {
                console.info(result.uid);
                var key = req.cookies.SESSION;
                req.session.req.cookies.SESSION = result.uid;
                res.render('login_result', {title: '三土同学', notes: result.response});
            } else {
                res.render('index', {errorMsg: result.errorMsg});
            }
        });
    });

    reqHttp.on('error', (e) => {
        console.log('problem with request : ' + e.message)
    });

    reqHttp.write(postData);
    reqHttp.end;
});

router.post('/register', (req, res) => {
    const postData = JSON.stringify(req.body);

    const options = {
        hostname: '127.0.0.1',
        port: 8762,
        path: '/user/register',
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
            const result = JSON.parse(data);
            if (result.success) {
                res.render('register_result', {title: '三土同学', notes: result.response});
            } else {
                res.render('register', {errorMsg: result.errorMsg});
            }
        });
    });

    reqHttp.on('error', (e) => {
        console.log('problem with request : ' + e.message)
    });

    reqHttp.write(postData);
    reqHttp.end;
});


router.get('/register', (req, res) => {
    res.render('register');
});


/* GET home page. */
router.get('/notes', (req, res) => {
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
        response.on('end', () => {
            res.render('index', {title: '三土同学', topics: JSON.parse(Buffer.concat(buffer).toString())});
        });
    })
});

module.exports = router;

