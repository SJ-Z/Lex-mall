window.onload = function(){

    var pwd = document.getElementById('password');
    var pwd1=document.getElementById('password-b');
    var un = document.getElementById('username');
    var un1 = document.getElementById('username-b');
    var vtft = document.getElementById('vertification');

    document.getElementById("reset").onclick=function(){
        un1.style.border='1px gray solid';
        pwd1.style.border='1px gray solid';
        vtft.style.border='1px gray solid';
        pwd.setAttribute('type','input');
    }
    // 实现input框点击时边框为蓝色

    pwd.addEventListener('click',function(){
        pwd.value="";
        pwd1.style.border='1px #00aeff solid';
        un1.style.border='1px gray solid';
        vtft.style.border='1px gray solid';
        pwd.setAttribute('type','password');
        if(un.value==""){
            un.value="用户名";
        }
        if(vtft.value==""){
            pwd.setAttribute('type','input');
            vtft.value="密码";
        }
    })

    un.addEventListener('click',function(){
        un.value="";
        un1.style.border='1px #00aeff solid';
        pwd1.style.border='1px gray solid';
        vtft.style.border='1px gray solid';
        if(pwd.value==""){
            pwd.setAttribute('type','input');
            pwd.value="密码";
        }
        if(vtft.value==""){
            vtft.value='验证码';
        }
    })


    vtft.addEventListener('click',function(){
        vtft.value="";
        vtft.style.border='1px #00aeff solid';
        un1.style.border='1px gray solid';
        pwd1.style.border='1px gray solid';
        if(pwd.value==""){
            pwd.setAttribute('type','input');
            pwd.value="密码";
        }
        if(un.value==""){
            un.value="用户名";
        }
    })
}
