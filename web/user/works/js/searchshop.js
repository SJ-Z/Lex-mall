import {
  setRemFontSize
} from './init.js';
import {
  ShopCard
} from '../../component/ShopCard/shopCard.js';


$(function () {
  // 设置rem
  setRemFontSize();
  // 构建商品卡片
  function buildShops(shopsBox, shops) {
    for (let i = 0; i < shops.length; i++) {
      let shopitem = new ShopCard({
        faEle: shopsBox,
        data: shops[i]
      });
    }
  }

  $.ajax({
      url: "/lexian-mall/SearchSellerServlet",
      data: {
        keyword: localStorage.getItem('lexian-user-keyword')
      }
    })
    .then((data) => {
      data = JSON.parse(data);
      let shopsBox = $('.shop-list')[0];
      let shops = data.sellerList;
      buildShops(shopsBox, shops);

    })

})