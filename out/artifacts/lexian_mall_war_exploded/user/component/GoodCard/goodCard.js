// 商品列表的单个卡片,维护一个商品的所有信息
// 依赖myzepto.js
class GoodCard {
  constructor(option) {
    this.faEle = option.faEle; // 所在的容器,这个
    this.ele = null;
    this.data = option.data;
    this.seller = option.data.g_seller; // 部分商家信息

    this.create(); // 创造卡片
    // 通过模板字符串结合 $() 构建好详情元素
    this.detail = $(`<div><img src="${this.data.g_image}" alt="good">
    <div class=" good-msg-details">
      <h5 class="good-name">${this.data.g_name}</h5>
      <p>
        <span class="good-price">￥${this.data.g_price}</span>
        <span class="good-like">${this.data.g_likeCount}收藏</span>
      </p>
      <p class="good-shop">${this.seller.s_storeName}</p>
      <p class="distance">${this.distance?('和你相距大约'+this.distance+"m"):"暂时没有定位，下次试试~"}</p>
      <p class="describe">${this.data.g_desc}</p>
    </div>
    <button class="tolike"></button>
    <button class="tocart"></button>
    <button class="leave">再逛逛乀(ˉεˉ乀)...</button></div>`)[0];
    this.tolike = this.detail.getElementsByClassName("tolike")[0];
    this.tocart = this.detail.getElementsByClassName("tocart")[0];
    this.leave = this.detail.getElementsByClassName("leave")[0];

    this.init(); // 初始化事件
  }
  // 根据列表项创建商品卡片
  create() {
    let template = `<li class="good-area">
        <img src="${this.data.g_image}" alt="good">
        <div class=" good-msg-base">
          <h5 class="good-name">${this.data.g_name}</h5>
          <p><span class="good-price">￥${this.data.g_price}</span><span class="good-like">${this.data.g_likeCount}收藏</span></p>
          <p class="good-shop">${this.seller.s_storeName}</p>
        </div>
      </li>`
    // 字符串转化为dom元素
    this.ele = $(template)[0];
    // 计算距离,为筛选做准备
    if (localStorage.getItem('user_location')) {
      $.ajax({
        type: "GET",
        url: `https://restapi.amap.com/v3/direction/walking?`,
        data: {
          origin: localStorage.getItem('user_location'),
          destination: this.seller.s_addr,
          key: '648c34239775a77fe9637615026d9f9b'
        }
      }).then((data) => {
          this.distance = data.route.paths[0].distance;
      }).fail(() => {
        this.distance = '???'
      })
    }
    // 将元素加入页面的 faEle
    this.faEle.appendChild(this.ele);
  }
  // 收藏
  like(likeList) {
    // 发请求加入收藏
    $.ajax({
        type: "POST",
        url: "/lexian-mall/LikeGoodsServlet",
        data: {
          u_id: localStorage.getItem('lexian-user-u_id'),
          g_id: this.data.g_id
        }
      })
      .then((data) => {
        // 加入本地收藏列表中
        likeList.push(this.data);
        // 改变样式和事件
        this.data.g_likeCount += 1;
        $(".good-like").innerText = this.data.g_likeCount;
        localStorage.setItem('lexian-user-like-goods', JSON.stringify(likeList));
        this.tolike.innerHTML = '已收藏，点击丢出～';
        this.tolike.onclick = ()=>{
          this.removeLike(likeList)
        };
      }).fail(() => {
        alert("收藏失败");
      })
  }
  // 不收藏
  removeLike(likeList) {
    // 发请求移除收藏
     $.ajax({
        type: "POST",
        url: "/lexian-mall/RemoveLikeGoodsServlet",
        data: {
          u_id: localStorage.getItem('lexian-user-u_id'),
          g_id: this.data.g_id
        }
      })
      .then((data) => {
        let index = likeList.findIndex(item => {
          return item.g_id == this.data.g_id;
        });
        // 删除本地收藏夹
        likeList.splice(index, 1);
        // 变更样式和事件
        this.data.g_likeCount -= 1;
        $(".good-like").innerText = this.data.g_likeCount;
        localStorage.setItem('lexian-user-like-goods', JSON.stringify(likeList));
        this.tolike.innerHTML = `加入收藏夹('▽'ʃ♡ƪ)`;
        window.location.reload();
        // 注册新事件
        this.tolike.onclick = ()=>{
            this.like(likeList)
        };
      })
  }

  likeDeal() {
    // 获取本地的收藏
    let likeList = JSON.parse(localStorage.getItem('lexian-user-like-goods'));
    // 判断目前是否在收藏中,采用es6语法避免for
    let likeitem = likeList.find((item) => {
      if (item.g_id == this.data.g_id) {
          return item;
      }
    });
    // 如果在收藏中
    if (likeitem) {
      // 如果在，收藏按钮无效，显示已收藏
      this.tolike.innerHTML = '已收藏，点击丢出～';
      this.tolike.onclick = () => {
        this.promise = this.removeLike(likeList);
      }
    } else {
      // 如果不在收藏中
      this.tolike.innerHTML = `加入收藏夹('▽'ʃ♡ƪ)`;
      this.tolike.onclick = () => {
        this.like(likeList);
      }
    }
  }

  // 放进购物车
  cart(localCart, flag) {
    // 如果true,创建一个新的商家，加入商品
    // 如果false，直接在所属商家，加入商品
    if (flag) {
      // 这个商品不属于购物车中的任何店铺，创建一个有商品和商家的对象
      let newItem = {
        s_id: this.seller.s_id,
        s_name: this.seller.s_storeName,
        goods: [{
          g_id: this.data.g_id,
          g_name: this.data.g_name,
          g_price: this.data.g_price,
          g_num: 1,
          g_image:this.data.g_image
        }]
      };
      localCart.push(newItem);
    } else {
      // 找到店铺，加入
      let seller = localCart.find(item => {
        if (this.seller.s_id == item.s_id) {
          return item
        }
      })
      seller.goods.push({
        g_id: this.data.g_id,
        g_name: this.data.g_name,
        g_price: this.data.g_price,
        g_num: 1,
        g_image:this.data.g_image
      });
    }
    localStorage.setItem('lexian-user-cart', JSON.stringify(localCart));
    // 将事件更改
    this.tocart.innerHTML = `已加入购物车，丢出去～`;
    this.tocart.onclick = () => {
      this.removeCart(localCart)
    };
  }
  // 移出购物车
  removeCart(localCart) {
    // 函数式编程
    localCart.find(item => {
      if (this.seller.s_id == item.s_id) {
        item.goods.find((subitem, index) => {
          if (subitem.g_id == this.data.g_id) {
            item.goods.splice(index, 1);
          };
        });
      }
    })
    localStorage.setItem('lexian-user-cart', JSON.stringify(localCart));

    this.tocart.innerHTML = `加入购物车✧Duang~`;
    this.tocart.onclick = () => {
      this.cart(localCart, false);
    };
  }


  cartDeal() {
      let localCart;
    // 获取本地的购物车（由于lexian-mall的机制，购物车只在本地有）
      if (localStorage.getItem('lexian-user-cart')) {
          localCart = JSON.parse(localStorage.getItem('lexian-user-cart'));
      }else{
        localCart = []
      }
    // 判断商品所属的商家是否属于购物车
    let seller = localCart.find(item => {
      if (this.seller.s_id == item.s_id) {
        return item
      }
    })
    // 如果商家属于购物车
    if (seller) {
      let good = seller.goods.find(item => {
        if (item.g_id == this.data.g_id) {
          return item;
        };
      });
      // 如果商品属于购物车--商家
      if (good) {
        // 按钮注册事件，可以移出购物车
        this.tocart.innerHTML = `已加入购物车，丢出去～`;
        this.tocart.onclick = () => {
          this.removeCart(localCart)
        };
      } else {
        // 在当前商家下创建商品
        this.tocart.innerHTML = `加入购物车✧Duang~`;
        this.tocart.onclick = () => {
          this.cart(localCart, false);
        };
      }
    } else {
      // 创建购物车--商家--商品
      this.tocart.innerHTML = `加入购物车✧Duang~`;
      this.tocart.onclick = () => {
        this.cart(localCart, true);
      };
    }
  }


  init() {
    //  注册点击卡片唤起详情,详情容器和卡片耦合
    let goodDetail = document.getElementsByClassName('good-details')[0];
    let mask = document.getElementsByClassName('mask')[0];
    // 打开商品详情模块
    $(this.ele).click(() => {
      // 针对某些因为定位问题做的修补
      if(document.getElementById('tempmask')){
          document.getElementById('tempmask').classList.remove('none')
      }
      mask.classList.remove("none");
      goodDetail.classList.remove('none');

      // 装填详情
      goodDetail.innerHTML = '';
      goodDetail.appendChild(this.detail);

      // 未登录状态下进入登录界面
      if (!localStorage.getItem("lexian-user-u_id")) {
        // 登录之后这个事件应该被覆盖，所以不要用addEventListener和jquery
        this.detail.onclick = () => {
          window.location.href = './person.jsp';
        }
        this.detail.onclick = () => {
          window.location.href = './person.jsp';
        }
      } else {
        this.likeDeal();
        this.cartDeal();
      }
      // 离开事件注册
      this.leave.onclick = () => {
        if(document.getElementById('tempmask')){
            document.getElementById('tempmask').classList.add('none')
        }
        goodDetail.innerHTML = '';
        mask.classList.add("none");
        goodDetail.classList.add('none');
      }
    });
  }
}

export {
  GoodCard
}