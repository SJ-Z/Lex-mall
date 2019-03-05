// import './banner.css'

class Banner {
	constructor(options) {
		this.container = options.container;
		this.imgs = options.imgs;
		// 内部数据
		this.imgLength = options.imgs.length; //图片数量
		this.index = 0;
		this.timer = null; //自动播放定时器
		// 组件节点
		this.sliders = this.buildSlider(); //ul
		this.cursors = this.buildCursor(); //指示器
		// 初始化动作
		this.setCurrent();
		this.autoPlay();
	}
	// 构建轮播图
	buildSlider() {
		let template = ``;
		let sliders = document.createElement("ul");
		sliders.classList.add('b-silder');
		for (let i = 0; i < this.imgLength; i++) {
			template += `<li class="b-item">
							<img src="${this.imgs[i]}">
					</li>`;
		}
		sliders.innerHTML = template;
		this.container.appendChild(sliders);
		return sliders; //返回并进行 ul 并进行缓存
	}
	// 构建指示器
	buildCursor() {
		let cursor = document.createElement('ul'),
			html = '';
		//根据图片的个数插入li
		for (let i = 0; i < this.imgLength; i++) {
			html += `<li data-index = '${i}'></li>`;
		}
		cursor.innerHTML = html;
		this.container.appendChild(cursor);
		return cursor;
	}
	// 切换到下一张图片
	next() {
		let index = (this.index + 1) % this.imgLength;
		this.last = this.index;
		this.index = index;
		this.setCurrent();
	}
	// 设置当前选中状态
	setCurrent() {
		// 去掉之前选中节点的选中状态
		if (this.last !== undefined) {
			this.cursors.children[this.last].classList.remove('z-active');
			this.sliders.children[this.last].classList.remove('z-active');
		}
		// 添加当前选中节点的选中状态
		this.sliders.children[this.index].classList.add('z-active');
		this.cursors.children[this.index].classList.add('z-active');
	}

	autoPlay() {
		this.timer = setInterval(function () {
			this.next();
		}.bind(this), 2400);
	}
}

export {
	Banner
}