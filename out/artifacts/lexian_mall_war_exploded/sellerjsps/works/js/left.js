window.onload=function () {

    var spans = document.getElementsByTagName('span');
    var uls = document.getElementsByClassName('tab2');
    var as = document.getElementsByTagName('a');
    var imgs = document.getElementsByTagName('img');
    imgs[0].style.display = 'block';

    spans[0].addEventListener('click', function () {
        if (uls[0].style.display != 'none') {
            uls[0].style.display = 'none';
        }
        else {
            uls[0].style.display = 'block';
        }
    })
    spans[1].addEventListener('click', function () {
        if (uls[1].style.display != 'none') {
            uls[1].style.display = 'none';
        }
        else {
            uls[1].style.display = 'block';
        }
    })
    spans[2].addEventListener('click', function () {
        if (uls[2].style.display != 'none') {
            uls[2].style.display = 'none';
        }
        else {
            uls[2].style.display = 'block';
        }
    })

    as [0].addEventListener('click', function () {
        for (var i = 0; i < 6; i++) {
            imgs[i].style.display = 'none';
        }
        imgs[0].style.display = 'block';
    })
    as [1].addEventListener('click', function () {
        for (var i = 0; i < 6; i++) {
            imgs[i].style.display = 'none';
        }
        imgs[1].style.display = 'block';
    })
    as [2].addEventListener('click', function () {
        for (var i = 0; i < 6; i++) {
            imgs[i].style.display = 'none';
        }
        imgs[2].style.display = 'block';
    })
    as [3].addEventListener('click', function () {
        for (var i = 0; i < 6; i++) {
            imgs[i].style.display = 'none';
        }
        imgs[3].style.display = 'block';
    })
    as [4].addEventListener('click', function () {
        for (var i = 0; i < 6; i++) {
            imgs[i].style.display = 'none';
        }
        imgs[4].style.display = 'block';
    })
    as [5].addEventListener('click', function () {
        for (var i = 0; i < 6; i++) {
            imgs[i].style.display = 'none';
        }
        imgs[5].style.display = 'block';
    })
}
