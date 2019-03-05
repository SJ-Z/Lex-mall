window.onload=function(){
    var f=document.getElementById('finish');
    var nof=document.getElementById('nofinish');
    var all=document.getElementById('all');
    var order=document.getElementById('byOrder')
    var s=document.getElementById('searchbtn');

    f.addEventListener('click',function(){
        f.style.backgroundColor='rgb(106,193,233)';
        nof.style.backgroundColor='#6699CC';
        all.style.backgroundColor='#6699CC';
        order.style.backgroundColor='#6699cc;'
    })

    nof.addEventListener('click',function(){
        nof.style.backgroundColor='rgb(106,193,233)';
        f.style.backgroundColor='#6699CC';
        all.style.backgroundColor='#6699CC';
        order.style.backgroundColor='#6699cc;'
    })

    all.addEventListener('click',function(){
        all.style.backgroundColor='rgb(106,193,233)';
        f.style.backgroundColor='#6699CC';
        nof.style.backgroundColor='#6699CC';
        order.style.backgroundColor='#6699cc;'
    })
    s.addEventListener('click',function(){
        all.style.backgroundColor='#6699CC';
        f.style.backgroundColor='#6699CC';
        nof.style.backgroundColor='#6699CC';
        order.style.backgroundColor='#6699cc;'
    })
    order.addEventListener('click',function (){
        order.style.backgroundColor='rgb(106,193,233)';
        all.style.backgroundColor='#6699CC';
        f.style.backgroundColor='#6699CC';
        nof.style.backgroundColor='#6699CC';
    })

    all.click();
}