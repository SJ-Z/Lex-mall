import {
  setRemFontSize
} from './init.js';

$(function () {
  // 设置rem
  setRemFontSize();
  // 基本功能跳转
  let jmp = $("footer>span");
  $(jmp[0]).click(() => {
    if (localStorage.getItem('lexian-user-u_id')) {
      window.location.href = "./like.html";
    } else {
      window.location.href = "./person.html";
    }
  });
  $(jmp[1]).click(() => {
    if (localStorage.getItem('lexian-user-u_id')) {
      window.location.href = "./msg.html";
    } else {
      window.location.href = "./person.html";
    }
  });
  $(jmp[2]).click(() => {
    window.location.href = "../index.html";
  });
  $(jmp[3]).click(() => {
    if (localStorage.getItem('lexian-user-u_id')) {
      window.location.href = `/lexian-mall/user/OrderServlet?method=findAll&u_id=${localStorage.getItem('lexian-user-u_id')}`;
    } else {
      window.location.href = "./person.html";
    }
  });

  $(jmp[4]).click(() => {
    if (localStorage.getItem('lexian-user-u_id')) {
      window.location.href = "./cart.html";
    } else {
      window.location.href = "./person.html";
    }
  });

  // 左右滑动两个页面
  let switchBtn = document.querySelectorAll('.control>button');
  switchBtn[0].addEventListener('click',(event)=>{
    event.preventDefault();
    if (!switchBtn[0].classList.contains('z-active')) {
      switchBtn[0].classList.add('z-active');
      switchBtn[1].classList.remove('z-active');
      $('.iframe-like2').css('left','18.75rem');
      $('.iframe-like1').css('left','0');
    }else{
      return false
    }
  })
  switchBtn[1].addEventListener('click',(event)=>{
    event.preventDefault();
    if (!switchBtn[1].classList.contains('z-active')) {
      switchBtn[1].classList.add('z-active');
      switchBtn[0].classList.remove('z-active');
      $('.iframe-like1').css('left','-18.75rem');
      $('.iframe-like2').css('left','0');
    }else{
      return false
    }
  })

  
})