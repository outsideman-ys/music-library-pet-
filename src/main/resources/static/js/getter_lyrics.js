let num = 0;
let lyrics = '';

function removeSpace(str) {
    return str.split('\n').filter(Boolean).join('\n');
}

function addLine() {
    if (num > lyrics.length) {
        const textElement = document.getElementById('lyrics');
        textElement.textContent = lyrics;
        return
    }
    const textElement = document.getElementById('lyrics');
    textElement.textContent = lyrics.slice(0, num);
    num += 6;
    setTimeout(addLine, 1 + Math.floor(Math.random() * 60));
}

setTimeout(() => {

    const artistElement = document.getElementById('band_name');
    const songElement = document.getElementById('song_name');


    const artistName = artistElement.textContent.split('Band Name: ')[1];
    const songName = songElement.textContent.split('Song Name: ')[1];

    const url = `https://api.lyrics.ovh/v1/${artistName}/${songName}`;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            lyrics = removeSpace(data.lyrics);
            addLine();
        })
        .catch(error => {
            lyrics = '\nТекст песни не найдён\n'
            addLine();
            console.error(error);
        });
}, 10)
