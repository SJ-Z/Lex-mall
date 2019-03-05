import {
    setRemFontSize
} from './init.js';

$(document).ready(function () {
    setRemFontSize();
    // 根据登录状态显示自己相应的功能区
    // 缓存三个功能区
    let $person = $('.person-info'),
        $login = $('.login'),
        $sign = $('.signin');

    if (localStorage.getItem('lexian-user-u_id')) {
        $person.show();
        $('.p-username>span').html(localStorage.getItem('lexian-user-name'));
    } else {
        $login.show();
    }

    // 登录
    $('.login-btn').click((event) => {
        event.preventDefault();
        if ($('#u_name').val() && $('#u_pwd').val()) {
            let uname = $('#u_name').val();
            localStorage.setItem('lexian-user-name',$('#u_name').val());
            console.log(uname)
            $.ajax({
                type: "POST",
                url: "/lexian-mall/UserLoginServlet",
                data: {
                    u_name: $('#u_name').val(),
                    u_pwd: $('#u_pwd').val()
                }
            })
                .done((data) => {
                    data = JSON.parse(data);
                    if (data.u_id) {
                        // 登录成功 在localStorage保存u_id，和收藏列表likegoodslist,likesellerList
                        localStorage.setItem('lexian-user-u_id', JSON.stringify(data.u_id))
                        localStorage.setItem('lexian-user-like-goods', JSON.stringify(data.likegoodsList))
                        localStorage.setItem('lexian-user-like-seller', JSON.stringify(data.likesellerList))
                        // 创建一个购物车用来保存商品
                        localStorage.setItem('lexian-user-cart', []);
                        window.location.href = '/lexian-mall/user/index.html'
                    } else if (data.msg) {
                        document.querySelectorAll('.tip.alert')[0].innerHTML = '请正确输入账号密码';
                    }
                })
        } else {
            // 账号或者密码为空
            document.querySelectorAll('.tip.alert')[0].innerHTML = '请正确输入账号密码';
        }
    })

    // 打开注册功能
    $('.login>.tip').click(() => {
        $login.hide();
        $sign.show();
    })

    // 注册用户名检查
    $('#u_name_sign').blur((event) => {
        event.preventDefault();
        $.ajax({
            type: "POST",
            url: "/lexian-mall/CheckUserNameServlet",
            data: {
                u_name: $('#u_name_sign').val()
            }
        }).then((data) => {
            console.log(data)
            $('.signin>.tip').text(JSON.parse(data).msg != "用户名可用！" ? JSON.parse(data).msg : '');
        })
    })
    // 确认密码检测，以及注册
    $('.sign-form>button').click((event) => {
        event.preventDefault();
        if ($('#u_pwd_sign').val() == $('#u_pwd_confirm').val()) {
            $.ajax({
                url: "/lexian-mall/UserRegistServlet",
                type: "POST",
                data: {
                    u_name: $('#u_name_sign').val(),
                    u_pwd: $('#u_pwd_sign').val()
                }
            })
                .then((data) => {
                    data = JSON.parse(data);
                    if (data.msg == 1) {
                        window.location.href = './person.html'
                    } else {
                        document.querySelectorAll('.tip.alert')[1].innerHTML = data.msg
                    }
                })
        } else {
            document.querySelectorAll('.tip.alert')[1].innerHTML = '两次输入密码不一致'
        }
    })


    // 放弃注册
    $('.signin>.cancel').click(() => {
        $login.show();
        $sign.hide();
    })


    // 退出登录,删除本地的记录
    $(".p-control").click((event) => {
        event.preventDefault();
        localStorage.clear();
        // localStorage.removeItem('lexian-user-u_id');
        window.location.href = '../index.html'
    })
})