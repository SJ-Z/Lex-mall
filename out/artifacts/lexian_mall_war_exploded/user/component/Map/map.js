// import './map.css';

// 依赖于zepto/jquery的定位组件

class Map {
  constructor(theEle) {
    this.ele = $(theEle);
    this.location = null;
    this.create();
    this.autoPosition();
    this.init();
  }
  init() {
    // 手动定位注册
    this.ele.find("form.map-form>button").click((e) => {
      e.preventDefault();
      this.clickPosition();
    });
  }
  create() {
    let template = `<h2></h2>
      <div class="map-msg" ">
        <p class="map-tip ">获得您的地址，就能帮你找到更近的商家啦～</p>
        <p class="m-position ">还没有定位成功，嘤嘤嘤</p>
      </div>
      <!-- 点击唤起手动输入表单，调用高德地图 web 服务 -->
      <p class="map-tip">定位不准？在这里手动输入地址</p>
      <form class="flex map-form border">
        <input type="text" placeholder="城市名+地名"">
        <button type="button">确定</button>
      </form>`;
    this.ele.html(template);
  }
  // 手动定位点击事件
  clickPosition() {
    // 调用Zepto.ajax，向高德地图请求地理编码服务
    let that = this;
    $.ajax({
      type: "GET",
      url: "https://restapi.amap.com/v3/geocode/geo",
      data: {
        key: "648c34239775a77fe9637615026d9f9b",
        address: this.ele.find("form.map-form>input").val()
      },
      success: function (data, status, xhr) {
        that.location = data.geocodes[0].location;
        that.setLocal(data.geocodes[0]);
      },
      error: function (xhr, errorType, error) {
        alert("定位失败，高德背锅")
        console.error(error);
      }
    });
  }
  // 自动定位
  autoPosition() {
    let that = this;
    console.log(navigator.geolocation)
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        function (pos) {
          that.location = `${parseFloat(pos.coords.longitude).toFixed(6)},${parseFloat(pos.coords.latitude).toFixed(6)}`
          
          // 请求高德地理逆编码服务
          $.ajax({
            type: "GET",
            url: "https://restapi.amap.com/v3/geocode/regeo",
            data: {
              key: "648c34239775a77fe9637615026d9f9b",
              location: that.location,
              radius: 500
            }
          })
          .then((data)=>{
            that.setLocal(data.regeocode);
          })
          .fail(function (xhr, errorType, error) {
            alert("定位失败，开启GPS和WLAN试试？")
            console.error(error);
          })
        },
        function (e) {
          var msg = e.code + "\n" + e.message;
        }
      );
    }
  }
  // 定位成功的回调函数，设置页面内容和 cookie
  setLocal(msg) {
    this.ele.find('p.m-position').text(msg.formatted_address);
    localStorage.setItem("user_location", this.location);
    this.ele.find('.map-form')[0].reset();
  }
}

export {
  Map
}