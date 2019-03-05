// 购物车中的每个店铺的内容，有下单功能
class CartItem {
    // 构造函数，参数，父容器，数据
    constructor(option) {
        this.faEle = option.faEle;// 父容器
        this.data = option.data;  // 商家信息，商品信息

        this.goods = this.data.goods; // 抽取商品信息
        this.totalMoney = 0;

        // 暂存item的三个组成部分
        this.container = null;
        this.title = null;
        this.goodcard = [];
        this.control = null;

        this.create();
        this.init();
    }

    create() {
        // 创建容器
        this.container = document.createElement('li');
        // 标题栏
        this.container.classList.add('bill-item');
        this.title = $(`<p class="seller">${this.data.s_name}</p>`)[0];
        this.container.appendChild(this.title);
        // 三段构建
        let goodsNum = this.goods.length;
        // 构建一个个商品
        for (let i = 0; i < goodsNum; i++) {
            const gooditem = this.goods[i];
            let template = `<div class="good-area clearfix">
          <img src="${gooditem.g_image}">
          <div class="good-msg">
            <p class="good-name">${gooditem.g_name}</p>
            <p class="good-price">￥${gooditem.g_price}</p>
            <div class="clearfix">
                <div class="count border flex">
                  <button class="cut-btn" type="button">-</button>
                  <span class="num">${gooditem.g_num}</span>
                  <button class="add-btn" type="button">+</button>
                </div>
                <button class="delete-btn"></button>
            </div>
            
          </div>
          </div>`;
            this.goodcard.push($(template)[0]);
            this.container.appendChild($(template)[0]);
        }
        this.control = $(`<div class="pay-control clearfix">
          <button class="pay">下单</button>
          <p class="money">￥0</p>
        </div>`)[0];
        // 拼接完整的item
        this.container.appendChild(this.control);
        // 回填到页面中
        this.faEle.appendChild(this.container);
    }

    init() {
        let goodsNum = this.goods.length,
            addBtn = document.getElementsByClassName('add-btn'),
            cutBtn = document.getElementsByClassName('cut-btn'),
            delBtn = document.getElementsByClassName('delete-btn'),
            num = document.getElementsByClassName('num'),
            money = document.getElementsByClassName('money')[0];

        this.getMoney();
        money.innerHTML = `￥${this.totalMoney}`
        // 每一个商品增加减少算价钱
        for (let i = 0; i < goodsNum; i++) {
            const gooditem = this.goods[i];
            cutBtn[i].addEventListener('click', (event) => {
                event.preventDefault();
                // 只有大于 0 才可以
                if (gooditem.g_num > 0) {
                    num[i].innerHTML = --gooditem.g_num;
                    money.innerHTML = `￥${this.getMoney()}`;
                }
            });
            addBtn[i].addEventListener('click', (event) => {
                event.preventDefault();
                num[i].innerHTML = ++gooditem.g_num;
                money.innerHTML = `￥${this.getMoney()}`;
            });
            delBtn[i].addEventListener('click', (event) => {
                event.preventDefault();
                gooditem.g_num = 0;
                money.innerHTML = `￥${this.getMoney()}`;
                let localCart = JSON.parse(localStorage.getItem('lexian-user-cart'));
                // 删除本地数据中的商品
                new Promise(resolve => {
                    let item = localCart.find(item => this.data.s_id == item.s_id);
                    resolve(item.goods)
                }).then((value) => {
                    return new Promise(resolve => {
                        value.find((subitem, index) => {
                            if (subitem.g_id == this.goods[i].g_id) {
                                resolve(value,index)
                            }
                        });
                    })
                }).then((value,index)=>{
                    value.splice(index, 1);
                    this.container.removeChild(event.target.parentNode.parentNode.parentNode);
                    localStorage.setItem('lexian-user-cart', JSON.stringify(localCart));
                    return new Promise(resolve => {
                        // 如果一个订单空了，就删掉购物车中的商家信息
                        localCart.find((item, index) => {
                            if (this.data.s_id == item.s_id) {
                                if (item.goods.length == 0) {
                                    localCart.splice(index, 1);
                                    localStorage.setItem('lexian-user-cart', JSON.stringify(localCart));
                                    // 所有商品都被删掉了,直接把容器删掉
                                    this.container.parentNode.removeChild(this.container)
                                }
                            }
                        });
                    })


                })
            })

        }
        $('.pay').click(() => {
            this.placeOrder();
        })
    }

    // 计算总价
    getMoney() {
        this.totalMoney = 0;
        for (let i = 0; i < this.goods.length; i++) {
            const gooditem = this.goods[i];
            this.totalMoney += gooditem.g_num * gooditem.g_price;
        }
        this.totalMoney = parseFloat(this.totalMoney.toFixed(2))
        return this.totalMoney;
    }

    // 下订单
    placeOrder() {
        let goodSend = [];
        for (let i = 0; i < this.goods.length; i++) {
            const gooditem = this.goods[i];
            if (gooditem.g_num != 0) {
                goodSend.push({
                    g_id: gooditem.g_id,
                    g_num: gooditem.g_num,
                })
            }
        }
        goodSend = JSON.stringify(goodSend)
        if (goodSend != "[]") {
            $.ajax({
                type: "POST",
                url: "/lexian-mall/AddOrderServlet",
                data: {
                    u_id: localStorage.getItem('lexian-user-u_id'),
                    s_id: this.data.s_id,
                    goods: goodSend
                }
            }).then((data) => {
                console.log('xiadan')
                // 直接清除本地购物车中的这一项
                let localCart = JSON.parse(localStorage.getItem('lexian-user-cart'));
                for (let i = 0; i < localCart.length; i++) {
                    const ele = localCart[i];
                    // 通过s_id判断要删除哪一项
                    if (ele.s_id == this.data.s_id) {
                        console.log(i)
                        localCart.splice(i, 1);
                        break;
                    }
                }
                localStorage.setItem('lexian-user-cart', JSON.stringify(localCart));
                // 刷新页面
                window.location.reload();
            })
        } else {
            alert('您还没买东西呢，不能下订单！')
        }
    }
}

export {
    CartItem
}