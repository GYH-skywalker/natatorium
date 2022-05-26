import test from "uview-ui/libs/function/test";

const Myconfig = {
    baseURL: 'http://localhost:9090/',
    header: {},
    dataType: 'json',
    responseType: 'text',
    timeout: 50000,
}

uni.$u.http.setConfig((config) => {
	 config.baseURL = `http://localhost:9090/`;
	 // config.baseURL = `http://124.220.159.87:9090/`;
	return config
})

const whiteList = ['consumer/login']

uni.$u.http.interceptors.request.use((config) => { // 可使用async await 做异步操作

	
	if(!whiteList.includes(config.url)){
		const token = uni.getStorageSync("token")
		console.log(token);
		if(token){
			console.log('rtesdsdfsdsdfsd');
			config.header.token = token
		}else{
			console.log('denglu');
			return new Promise.reject("需要登录使用该功能")
		}
	}
	config.header.platform = 'mobile'
	// config.header = {
	// 	...config.header,
	// 	a: 1 // 演示拦截器header加参
	// }
	// 演示custom 用处
	// if (config.custom.auth) {
	//   config.header.token = 'token'
	// }
	// if (config.custom.loading) {
	//  uni.showLoading()
	// }
	// 演示
	// if (!token) { // 如果token不存在，return Promise.reject(config) 会取消本次请求
	// 	return Promise.reject(config)
	// }
	return config
}, config => { // 可使用async await 做异步操作
	return Promise.reject(config)
})

uni.$u.http.interceptors.response.use((response) => {
	/* 对响应成功做点什么 可使用async await 做异步操作*/
	// if (response.data.code !== 200) { // 服务端返回的状态码不等于200，则reject()
	//    	return Promise.reject(response) // return Promise.reject 可使promise状态进入catch
	// if (response.config.custom.verification) { // 演示自定义参数的作用
	//   	return response.data
	// }
	console.log(response)
	return response.data.data
}, (response) => {
	/*  对响应错误做点什么 （statusCode !== 200）*/
	console.log(response)
	return Promise.reject(response)
})

export default uni.$u.http