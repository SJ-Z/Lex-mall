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
      window.location.href = "/lexian-mall/user/pages/like.html";
    } else {
      window.location.href = "/lexian-mall/user/pages/person.html";
    }
  });
  $(jmp[1]).click(() => {
    if (localStorage.getItem('lexian-user-u_id')) {
      window.location.href = "/lexian-mall/user/pages/msg.html";
    } else {
      window.location.href = "/lexian-mall/user/pages/person.html";
    }
  });
  $(jmp[2]).click(() => {
    window.location.href = "/lexian-mall/user/index.html";
  });
  $(jmp[3]).click(() => {
    if (localStorage.getItem('lexian-user-u_id')) {
      window.location.href = `/lexian-mall/user/OrderServlet?method=findAll&u_id=${localStorage.getItem('lexian-user-u_id')}`;
    } else {
      window.location.href = "/lexian-mall/user/pages/person.html";
    }
  });

  $(jmp[4]).click(() => {
    if (localStorage.getItem('lexian-user-u_id')) {
      window.location.href = "/lexian-mall/user/pages/cart.html";
    } else {
      window.location.href = "/lexian-mall/user/pages/person.html";
    }
  });




    let map = new Map();
    let addrs = document.getElementsByClassName("bill-addr-val");
    for(let i = 0;i<addrs.length;i++){
      map.set(addrs[i].innerHTML,'');
    }

    console.log(map)
    map.forEach((value,key) =>{
        let realAddr = ''; // 实际地址
        $.ajax({
            url: 'https://restapi.amap.com/v3/geocode/regeo',
            data: {
                key: '648c34239775a77fe9637615026d9f9b',
                location: key
            }
        }).then((data)=>{
            map.set(key,data.regeocode.formatted_address)
            for(let i = 0;i<addrs.length;i++){
                console.log(map.get(addrs[i].innerText))
                addrs[i].innerText = map.get(addrs[i].innerText);
            }
        })
    })
})