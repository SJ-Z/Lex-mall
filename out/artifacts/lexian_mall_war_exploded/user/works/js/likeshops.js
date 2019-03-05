import {
  setRemFontSize
} from './init.js';
import {ShopCard} from '../../component/ShopCard/shopCard.js';

$(function () {
  // 设置rem
  setRemFontSize();
  // 基本功能跳转
  function buildShops(shopsBox,shops){
    for (let i = 0; i < shops.length; i++) {
      const item = shops[i];
      let shopitem = new ShopCard({
        faEle:shopsBox,
        data:item
      });
    }
  }

  let shopsBox = $('.shop-list')[0];
  let shops = JSON.parse(localStorage.getItem('lexian-user-like-seller'));
  console.log(shops);
  buildShops(shopsBox,shops);
})