window.onload=function(){
    var s=document.getElementById('searchbtn');
    var fotd=document.getElementById('fordetails');
    var f=document.getElementById('finish');
    var nof=document.getElementById('nofinish');

    fotd.addEventListener('click',function(){

        if(fotd.value=="查看详情")
        {
           document.getElementById('details').style.display="table-row";
           fotd.value="关闭";
        }
        else{
            document.getElementById('details').style.display="none";
            fotd.value="查看详情";
        }
    })

    f.addEventListener('click',function(){
        f.style.backgroundColor='rgb(106,193,233)';
        nof.style.backgroundColor='#6699CC';
    })

    nof.addEventListener('click',function(){
        nof.style.backgroundColor='rgb(106,193,233)';
        f.style.backgroundColor='#6699CC';
    })

    s.addEventListener('click',function(){
        f.style.backgroundColor='#6699CC';
        nof.style.backgroundColor='#6699CC';
    })
}