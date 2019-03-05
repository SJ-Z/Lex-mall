// 二级分类列表依赖于zepto.js
class SubNav {
  // 构造函数,接受参数包括ele容器,data数据,tid
  constructor(option) {
    this.ele = option.ele; // 容器
    this.data = option.data; // 数据
    this.index = option.index; // 当前选中的二级类型

    this.current_sub = null; // 当前的二级id
    this.currentActive = null; // 当前的有效元素

    this.createSubNav();
    this.init();
  }
  // 创建二级列表
  createSubNav() {
    let flagment = document.createDocumentFragment();
    this.ele.innerHTML = '';
    // 循环构建
    for (let i = 0, length = this.data.length; i < length; i++) {
      let $tempLi = $(`<li>${this.data[i].sub_name}</li>`);
      $tempLi.attr('sub_id', this.data[i].sub_id);
      if (i == this.index) {
        $tempLi[0].classList.add('z-active');
        this.currentActive = $tempLi[0];
        this.current_sub = $(this.currentActive).attr('sub_id');
      }
      flagment.appendChild($tempLi[0]);
    }
    this.ele.appendChild(flagment);
  }
  init() {
    this.ele.addEventListener('click', (event) => {
      if ($(event.target).attr('sub_id')) {
        // 如果没有属性,说明点击的是父元素,不用下面的操作
        this.currentActive.classList.remove('z-active'); // 删掉旧的样式
        this.currentActive = $(event.target)[0]; // 改变选中元素
        this.currentActive.classList.add('z-active'); // 增加样式 
        this.current_sub = $(event.target).attr('sub_id'); // 改变生效的二级样式
      } else {
        return false;
      }
    })
  }

  update(data) {
    this.data = data;
    // 循环构建
    this.createSubNav();
  }
}
export {
  SubNav
}
