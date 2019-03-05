// import '../../common/css/base.css';
// import '../../common/css/init.css';
// import '../css/goodList.css'

import {
	setRemFontSize
} from './init.js';
import {
	TypeNav
} from '../../component/TypeNav/typeNav.js';
import {
	SubNav
} from '../../component/SubNav/subNav.js';
import {
	GoodCard
} from '../../component/GoodCard/goodCard.js';



$(function () {
	// 设置rem
	setRemFontSize();
	// 接受参数，构建商品列表（容器，二级类型，商品）
	function buildGoods(goodsBox, subtype, goods) {
		console.log("构建商品二级分类是："+subtype)
		goodsBox.innerHTML = ''
		console.log(goods.length)
		for (let i = 0, length = goods.length; i < length; i++) {
			const goodi = goods[i];
			if (goodi.g_subType.sub_id == subtype) {
				let myGoodCard = new GoodCard({
					faEle: goodsBox,
					data: goodi
				});
			}
		}
	}
	// 根据收藏量排序
    function sortBylike(a, b) {
        let alike = a.g_likeCount;
        let blike = b.g_likeCount;
        if (alike < blike) {
            return 1;
        } else {
            return -1;
        }
    }
    // 根据价格排序
    function sortByPrice(a, b) {
        let alike = a.g_price;
        let blike = b.g_price;
        if (alike < blike) {
            return -1;
        } else {
            return 1;
        }
    }
	// 根据本地缓存的一级分类列表创建二级分类
	if (localStorage.getItem('lexian-user-types')) {
		// 保存在localStorage中的一级分类数据
		let types = JSON.parse(localStorage.getItem('lexian-user-types'));
		// 一级列表容器，二级列表容器，商品列表容器		
		let typenavEle = document.querySelector('ul.type-nav'),
			subNavEle = document.getElementsByClassName('sub-type-nav')[0],
			goodsEle = document.getElementsByClassName('good-list')[0];
		// 一级列表对象，二级列表对象，当前一级分类下所有商品数据
		let myTypeNav = null,
			mySubNav = null,
			goodDatas = null;
		// 构建一个含有 promise 的一级分类列表
		myTypeNav = new TypeNav({
			ele: typenavEle,
			data: types,
			url: localStorage.getItem('lexian-user-typeAPI')
		})
		// 一级列表点击，页面重构
		myTypeNav.promise
			.then((value) => {
				// 由第一个传过来二级分类参数和一级下所有商品信息		
				// 构建二级分类列表
				mySubNav = new SubNav({
					ele: subNavEle,
					data: value[1],
					index: 0
				});
				goodDatas = value[2];
				// 默认按照收藏量排序
                goodDatas.sort(sortBylike);
				// 二级列表点击，商品刷新
				mySubNav.ele.addEventListener('click', () => {
					goodsEle.innerHTML = '';
					buildGoods(goodsEle, mySubNav.current_sub, goodDatas);
				})
				return [mySubNav.current_sub, value[2]]
			})
			.then((value) => {
				// 接受上一级传来的三级列表
				buildGoods(goodsEle, value[0], value[1]);
			});


		myTypeNav.ele.addEventListener('click', () => {
			myTypeNav.promise
				.then((value) => {
					mySubNav.update(value[1]);
					goodDatas = value[2];
                    goodDatas.sort(sortBylike);
					buildGoods(goodsEle, value[1][0].sub_id, goodDatas)
				})
		})


        // 按照收藏量排序
        let screenBtns = document.querySelectorAll('.screen-nav>button');
        screenBtns[0].addEventListener('click', (event) => {
            event.preventDefault();
            $(".screen-nav>.z-active")[0].classList.remove('z-active');
            event.target.classList.add('z-active');
            goodDatas.sort(sortBylike);
            buildGoods(goodsEle, mySubNav.current_sub, goodDatas);
        })

        screenBtns[1].addEventListener('click', (event) => {
            event.preventDefault();
            $(".screen-nav>.z-active")[0].classList.remove('z-active');
            event.target.classList.add('z-active');
            goodDatas.sort(sortByPrice);
            buildGoods(goodsEle, mySubNav.current_sub, goodDatas);
        })
	}
})