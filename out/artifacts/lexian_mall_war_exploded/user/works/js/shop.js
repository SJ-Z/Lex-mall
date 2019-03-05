import {
    setRemFontSize
} from './init.js';

import {
    GoodCard
} from '../../component/GoodCard/goodCard.js';

// 构建商品列表函数
function buildGoods(goodEle, goods) {
    for (let i = 0, length = goods.length; i < length; i++) {
        const element = goods[i];
        let card = new GoodCard({
            faEle: goodEle,
            data: element
        })
    }
}

// 收藏店铺函数，参数，本地所有收藏的商家
function like(sellerLiked) {
    $.ajax({
        url: "/lexian-mall/LikeSellerServlet",
        data: {
            u_id: localStorage.getItem("lexian-user-u_id"),
            s_id: localStorage.getItem('lexian-user-current-shop')
        }
    }).then((data) => {
        // 成功后，改变按钮样式和内容，在本地收藏夹加入商家的部分数据
        $('.like-btn')[0].classList.add('z-active');
        $('.like-btn').text('已收藏');
        sellerLiked.push(currentSeller);
        localStorage.setItem('lexian-user-like-seller', JSON.stringify(sellerLiked))
    })
}

// 取消收藏函数，参数，本地所有收藏的商家
function unlike(sellerLiked) {
    $.ajax({
        url: "/lexian-mall/RemoveLikeSellerServlet",
        data: {
            u_id: localStorage.getItem("lexian-user-u_id"),
            s_id: localStorage.getItem('lexian-user-current-shop')
        }
    }).then((data) => {
        // 成功后，改变按钮的样式内容，删除本地收藏夹的内容
        $('.like-btn')[0].classList.remove('z-active');
        $('.like-btn').text('收藏');
        let index = sellerLiked.findIndex(item => item.s_id == JSON.parse(localStorage.getItem('lexian-user-current-shop')))

        sellerLiked.splice(index, 1);

        localStorage.setItem('lexian-user-like-seller', JSON.stringify(sellerLiked))
    })
}

let currentSeller = {};// 维护数据的一个全局变量
$(function () {
    setRemFontSize();

    let sellerMsg = {}, openTime, closeTime,
        goods = [];
    // 根据　s_id　请求seller 的全部信息
    $.ajax({
        url: "/lexian-mall/LoadSellerServlet",
        type: "GET",
        data: {
            s_id: localStorage.getItem('lexian-user-current-shop')
        }
    }).then((data) => {
        // 拿到数据后构建整个页面
        data = JSON.parse(data);
        currentSeller = sellerMsg = data.seller;
        goods = data.goodsList;
        openTime = data.s_openTime;
        closeTime = data.s_closeTime;
        let realAddr = ''; // 实际地址
        $.ajax({
            url: 'https://restapi.amap.com/v3/geocode/regeo',
            data: {
                key: '648c34239775a77fe9637615026d9f9b',
                location: sellerMsg.s_addr
            }
        }).then((data) => {
            realAddr = data.regeocode.formatted_address;
            // 构建商品基本信息部分
            let template = `<h1 class="name">${sellerMsg.s_storeName}</h1>
                      <div class="shop-msg clearfix">
                        <p>地点: </span><span class="addr">${realAddr}</span></p>
                        <div class="s-detail ">
                          <p>营业时间: ${openTime}~${closeTime}</p>
                          <p>联系电话: ${sellerMsg.s_phone}</p>
                        </div>
                        <button class="like-btn"></button>
                      </div>`;
            $('header').html(template);

            //　构建商品列表
            let goodEle = document.getElementsByClassName('good-list')[0];
            buildGoods(goodEle, goods);
            //  收藏店铺功能
            let sellerLiked = JSON.parse(localStorage.getItem("lexian-user-like-seller"));
            let likeBtn = $('.like-btn')[0];


            if (sellerLiked.length > 0) {
                // 　进入页面判断是否收藏
                let res = sellerLiked.find(item => (item.s_id == JSON.parse(localStorage.getItem('lexian-user-current-shop'))))

                if (res) {
                    likeBtn.innerHTML = '已收藏';
                    likeBtn.classList.add('z-active');
                    likeBtn.onclick = () => {
                        unlike(sellerLiked);
                    }
                } else {
                    likeBtn.innerHTML = '收藏';
                    likeBtn.classList.remove('z-active');
                    likeBtn.onclick = () => {
                        like(sellerLiked);
                    }
                }

            } else {
                likeBtn.innerHTML = '收藏';
                likeBtn.classList.remove('z-active');
                likeBtn.onclick = () => {
                    like(sellerLiked);
                }
            }


        })
    })
})