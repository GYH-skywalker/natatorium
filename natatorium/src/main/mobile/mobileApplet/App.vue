<script>
	import {checkToken} from "@/utils/utils.js"
	export default {
		onLaunch: function() {
			console.log('App Launch')
			if(checkToken()){
				uni.getStorage({
					key:'token',
					success: function (res) {
						uni.reLaunch({url:"/pages/index/index"})
							console.log(res.data);
						},
					fail: err => {
						console.log(err)
					}
				})
			}else{
				console.log('token失效')
			}
		},
		onShow: function() {
			console.log('App Show')
			uni.getStorage({
				key:'token',
				success: function (res) {
					uni.reLaunch({url:"/pages/index/index"})
						console.log(res.data);
					},
				fail: err => {
					console.log(err)
				}
			})
		},
		onHide: function() {
			console.log('App Hide')
		},
	}
</script>

<style lang="scss">
	@import "uview-ui/index.scss";
	/*每个页面公共css */
</style>
