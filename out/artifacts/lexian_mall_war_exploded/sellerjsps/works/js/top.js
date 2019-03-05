document.getElementById("exit").onclick=function(e){
    e.preventDefault();
    var r=confirm("确认退出");
    if(r=true){
        alert("安全退出");
        top.location="../index.jsp";
    }
}