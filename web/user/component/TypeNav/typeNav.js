// 商品列表页面的一级列表，依赖于zepto
class TypeNav {
  // 构造函数，option包括ele(容器),所有种类的tid和名字
  constructor(option) {
    this.ele = option.ele;// 容器
    this.data = option.data;// 数据
    this.$ele = $(this.ele);// zepto缓存
    this.url = option.url;// 数据来源
    this.current_id = null;// 当前有效元素的t_id
    this.currentActive = null;// 当前的有效元素
    this.subTypes = null;// 缓存一级分类下的所有二级分类
    this.allGoods = null;// 用来缓存当前一级分类下的所有二级分类商品
    this.create();// 生成
    this.init();// 事件注册
    this.promise = this.getAll(this.current_id)
  }
  create() {
    // 获取当前的tid,首页点击时候存入的
    this.current_id = localStorage.getItem('lexian-user-current_tid');
    // 创建元素
    let fragment = document.createDocumentFragment();
    for (let i = 0, length = this.data.length; i < length; i++) {
      const item = this.data[i];
      let tempLi = document.createElement('li');
      // 确定当前选中的具体种类加上样式
      if (item.t_id == this.current_id) {
        tempLi.classList.add('z-active');
        // 缓存当前生效节点
        this.currentActive = tempLi;
      }
      // 添加文本和属性
      $(tempLi).text(item.t_name);
      $(tempLi).attr("t_id", item.t_id);
      fragment.appendChild(tempLi);
    }
    // 完整回填
    $(this.ele).append(fragment);
   
  }
  init() {
    // 点击切换一级分类事件绑定
    this.$ele.click((event) => {
      // 在对没有选中的元素进行点击的时候才执行下面代码
      let target = event.target;
      if (!target.classList.contains('z-active')) {
        // 抹去原来的元素样式
        this.currentActive.classList.remove('z-active');
        // 更新t_id
        let newTid = $(event.target).attr('t_id');
        localStorage.setItem('current_tid', newTid);
        this.current_id = newTid;  
        this.promise = this.getAll(this.current_id)

        this.currentActive = event.target;
        this.currentActive.classList.add('z-active');
        
      }else{
        return false;
      }
    })
  }

  getAll(tid){
    return ($.ajax({
			type: "GET",
			url: this.url,
			data: {
				t_id: tid
			}
    }).then((data) => {
      data = JSON.parse(data)
      this.allGoods = data.goodsList;
      this.subTypes = data.subTypeList;
      return [this.current_id,this.subTypes,this.allGoods]
    }))
  }
}

export { TypeNav };