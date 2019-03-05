// import './typeList.css'

// 组件，动态渲染类型列表，依赖zepto.js/jquery.js

class TypeList {
  // 通过一个对象构建种类列表，数组内容为item对象，对象包含链接，图片资源，名称
  constructor(options) {
    this.ele = options.ele;
    this.url = options.url;
    this.link = options.link;
    this.getItem();
  }
  getItem() {
    $.ajax({
        type: "GET",
        url: this.url,
      })
      .then((data) => {
        data = JSON.parse(data)
        this.items = data.result.items;
        this.api = data.result.api;
        this.create();
        this.init();
      }).fail((xhr, errorType, error) => {
        alert("获取类型列表失败")
        console.error(error);
      })
  }
  // 渲染节点
  create() {
    if (this.items) {
      let ele = $(this.ele);
      // 利用文档碎片进行渲染
      let fragment = document.createDocumentFragment();
      for (let i = 0, length = this.items.length; i < length; i++) {
        const element = this.items[i];
        let tempLi = document.createElement("li"),
          tempA = document.createElement("a"),
          tempH3 = document.createElement('h3'),
          tempImg = document.createElement('img');

        $(tempA).attr('api', this.api);
        $(tempA).attr('href', this.link);
        $(tempH3).html(element.t_name);
        $(tempImg).attr('src', element.t_image);

        tempLi.appendChild(tempA);
        tempA.appendChild(tempImg);
        tempA.appendChild(tempH3);

        fragment.appendChild(tempLi);
      }
      this.ele.appendChild(fragment);
    }
  }
  // 事件注册,本地化
  init() {
    if (this.items) {
      // 临时存储一级种类
      localStorage.setItem('lexian-user-types', JSON.stringify(this.items));
      for (let i = 0, length = this.items.length; i < length; i++) {
        // 跳转事件注册
        $(this.ele.getElementsByTagName('li')[i]).click(() => {
          localStorage.setItem('lexian-user-current_tid', this.items[i].t_id);
          localStorage.setItem('lexian-user-typeAPI',this.api);
        })
      }
    }
  }
}

export {
  TypeList
}