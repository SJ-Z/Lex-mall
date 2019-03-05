let _ = window._ ? window._:{};// 找到工具类 _
const MS = 86400000;// 一天的毫秒数
//设置cookie
_.Cookie= {
	setCookie : (cname, cvalue, exdays) => {
		var d = new Date();
		d.setTime(d.getTime() + (exdays * MS));
		var expires = "expires=" + d.toUTCString();
		document.cookie = encodeURIComponent(cname) + "=" + encodeURIComponent(cvalue) + "; " + expires;
	},
	//清除cookie，清空值并直接过期  
	clearCookie : (name) => {
		setCookie(name, "", -1);
	},
	// 提取所有的cookie
	getCookie : () => {
		var cookie = {};
		var all = document.cookie;
		if (all == '') {
				return null;
		}
		var list = all.split('; ');
		for (var i = 0; i < list.length; i++) {
				var item = list[i];
				var p = item.indexOf('=');
				var name = item.substring(0, p);
				var value = item.substring(p + 1);
				value = decodeURIComponent(value);
				cookie[name] = value;
		}
		return cookie;
	}
};
