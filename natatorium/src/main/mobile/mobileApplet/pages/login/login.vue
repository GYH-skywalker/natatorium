<template>
	<view class="login">
		<span class="rippleFilter">
		  </span>
		<view class="content">
			<u-form
				labelPosition="left"
				:model="Form"
				
				ref="form1">
				<u-form-item
					prop="userInfo.name"
					borderBottom
					ref="item1">
					<u-input
						v-model="Form.phone"
						border="bottom"
						placeholderStyle="color:rgba(141, 141, 141, 0.8);font-size:14px;"
						placeholder="手机号"
						adjustPosition
						class="uinput"
					></u-input>
				</u-form-item>
				<u-form-item
					prop="userInfo.name"
					borderBottom
					ref="item1">
					<u-input
						placeholder="密码"
						class="/deep/uinput"
						password
						adjustPosition
						placeholderStyle="color:rgba(141, 141, 141, 0.8);font-size:14px;"
						v-model="Form.password"
						border="bottom"
					></u-input>
				</u-form-item>
			</u-form>
			<u-button 
			text="登录" 
			shape="circle" 
			:plain="true"
			:customStyle="{backgroundColor:'rgba(255,255,255,0)',boxShadow:'inset 0px 0px 5px 2px #fff',width:'90%',marginTop:'20px'}"
			color="#fff"
			@click="consumerLogin"
			hairline></u-button>
		</view>	
	</view>
</template>

<script>
	import { login } from "@/network/api.js"
	export default {
		data() {
			return {
				Form:{}
			}
		},
		methods: {
			consumerLogin(){
				console.log('test')
				login(this.Form).then(res => {
					uni.setStorage({
						key: 'token',
						data: res.token,
						success: function () {
							uni.reLaunch({
								url: "/pages/index/index"
							})
						}
					});
					
				}).catch(err => {
					console.log(err)
				})
				
				console.log("test2")
			}
		}
	}
</script>

<style lang="scss" scoped>
.login{
	width:100vw;
	height:100vh;
	background: linear-gradient(45deg,rgba(76,180,231,0.6),rgba(255,192,159,0.6));
	display:flex;
	justify-content:center;
	align-items:center;
	.rippleFilter{
	    z-index: 999;
	    position: absolute;
	    width: 100vw;
	    height: 100vh;
	    background-image: url("https://s2.loli.net/2022/05/05/dbJFP6X4x7qtDpB.png");
			background-size: cover;
	    opacity: 0.6;
	  }
	.content{
		z-index: 1000;
		margin-top:-30px;
		width: 85vw;
		// height: 50vw;
		// background-color:red;
		display:flex;
		flex-direction:column;
		justify-content:space-around;
		align-items:center;
		&::v-deep .u-line{
			display: none;
		}
	}
}
</style>
<style>
	.u-form{
		width: 82%;
	}
	.u-border-bottom{
		border-color:#f0f0f0 !important;
	}
</style>
