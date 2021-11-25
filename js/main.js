import fetch from 'node-fetch';

const re = /href=\"(https:[^\"]+)\"/g;

function extractLinks(txt) {
    console.log(">extractLinks", txt.length);
    return Array.from(txt.matchAll(re), ar => ar[1]);
}

function load(url) {
    return fetch(url,{redirect:"manual"})
        .then(res => res.text().then(txt => ({ url, txt })));
}

load("https://www.bbc.com/news/world")
    .then(({ txt }) => extractLinks(txt))
    .then(urls => {
        const start = Date.now();
        Promise.all(urls.map(url => load(url)))
            .then(contents => {
                const time= Date.now() - start ;
                const totalLength = contents.reduce((total, { url, txt }) =>  total + txt.length , 0);
                console.log("fetched " + totalLength + " bytes from " + urls.length + " urls in " + time + " ms");
            });
    });
