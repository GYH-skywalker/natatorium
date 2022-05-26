export function checkToken(){
	//判断当前token是否过期
	uni.getStorage({
		key:'token',
		success:(res) => {
			let strings = res.data.split("."); //截取token，获取载体
			var userinfo = JSON.parse(decodeURIComponent(escape(atob(strings[1].replace(/-/g, "+").replace(/_/g, "/")))));
			if(new Date() > parseInt(userinfo.exp)){
				return true;
			}else {
				uni.removeStorage({
					key:'token',
					success: (res) => {
						console.log(res);
					}
				})
				return false
			}
		}
	})
}