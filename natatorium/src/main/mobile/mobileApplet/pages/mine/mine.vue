<template>
	<view class="mine">
		<view class="myInfo">
			<u-avatar :src="src" :size="60"></u-avatar>
			<view class="userName">
				用户名称
			</view>
		</view>
		<view class="operation">
			<u-cell 
			:icon="item.icon" 
			v-for="(item,index) in opItem" 
			:title="item.title" 
			:iconStyle="{color:'rgba(76,180,231,1)'}"
			:key="index"
			isLink="true" 
			:border="false"
			@click="clickRespond(item.path)"
			:customStyle="{backgroundColor:'#f0f0f0',width:95+'vw',borderRadius:10+'px',boxShadow:'inset 0px 0px 2px 3px #fafafa',border:'1px solid rgba(20, 20, 20, 0.1)'}"
			arrow-direction="right"></u-cell>
		</view>
		
	</view>
</template>

<script>
	export default {
		components:{
		},
		data() {
			return {
				src:'https://s2.loli.net/2022/04/23/JlASaWGwYqMmT1o.png',
				opItem:[
					{
						icon:'calendar-fill',
						title:'我的课程',
						path:'myCourse/myCourse'
					},
					{
						icon:'close-circle-fill',
						title:'退出',
						path:'logout'
					},
					{
						icon:'question-circle-fill',
						title:'关于我们',
						path:'aboutUs/aboutUs'
					}
				]
				
			}
		},
		methods: {
			clickRespond(path){
				if(path !== 'logout'){
					uni.$u.route("pages/mine/"+path)
				}else{
					this.logout()
				}
			},
			logout(){
				uni.removeStorage({
					key:"token",
					success: (res) => {
						
						uni.reLaunch({
							url:"pages/login/login" 
						})
						console.log(res);
					}
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.mine{
		background-color:#fcfcfc;
		// background-color:pink;
		// background: linear-gradient(45deg,rgba(76,180,231,0.4),rgba(255,192,159,0.4));
		height:100vh;
		.myInfo{
			background: linear-gradient(45deg,rgba(76,180,231,0.4),rgba(255,192,159,0.4));
			width: 100vw;
			height: 55vw;
			border-bottom-left-radius:5px;
			border-bottom-right-radius:30%;
			display:flex;
			flex-direction:column;
			justify-content:center;
			align-items:center;
			// backdrop-filter:blur(15px);
			// box-shadow:inset 0px -10px 15px 2px rgba(255,255,255,0.5)
		}
		.operation{
			width:100vw;
			height:45vw;
			// background-color:green;
			margin-top:30px;
			display:flex;
			flex-direction:column;
			justify-content:space-around;
			align-items:center;
		}
	}

</style>
