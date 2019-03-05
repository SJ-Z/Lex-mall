window.onload=function(){

var sb=document.getElementById('submit');
var rb=document.getElementById('reset');

var un=document.getElementById('s_name');
var mun=document.getElementById('manager-username');
var pwd=document.getElementById('s_pwd');
var mpwd=document.getElementById('manager-password');
var pwd2=document.getElementById('_pwd');
var mpwd2=document.getElementById('manager-password2');
var em=document.getElementById('s_email');
var mem=document.getElementById('manager-email');
var vtfc=document.getElementById('vertification');

var inputs=document.getElementsByTagName('input');
var divs=new Array(mun,mpwd,mpwd2,mem,vtfc);
var infos=document.getElementsByTagName('span');

 un.addEventListener('click',function(){
    for(var x=0;x<5;x++){
         divs[x].style.border='1px gray solid';
     }
     mun.style.border='1px #00aeff solid';
 })

pwd.addEventListener('click',function(){
    for(var x=0;x<5;x++){
        divs[x].style.border='1px gray solid';
    }
    mpwd.style.border='1px #00aeff solid';
})
pwd2.addEventListener('click',function(){
    for(var x=0;x<5;x++){
        divs[x].style.border='1px gray solid';
    }
    mpwd2.style.border='1px #00aeff solid';
})
em.addEventListener('click',function(){
    for(var x=0;x<5;x++){
        divs[x].style.border='1px gray solid';
    }
    mem.style.border='1px #00aeff solid';
})

vtfc.addEventListener('click',function(){
    for(var x=0;x<5;x++){
        divs[x].style.border='1px gray solid';
    }
    vtfc.style.border='1px #00aeff solid';
})

rb.addEventListener('click',function(){
    for(var x=0;x<5;x++){
        divs[x].style.border='1px gray solid';
    }
})
}