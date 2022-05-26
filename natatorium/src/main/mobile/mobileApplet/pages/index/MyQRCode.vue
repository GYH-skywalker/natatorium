<template>
	<view class=myQRcode>
		<view class="cardInfo">
			<span>剩余次数:</span>{{cardInfo}}
		</view>
		<view class="VIPCard">
			<view class="QRCode">
				<image :src="QRImg" mode="" v-if="QRImg"></image>
				<text v-else>当前未办理业务</text>
				<!-- <img :src="QRImg" /> -->
			</view>
		</view>
	</view>
</template>

<script>
	import {getQR} from "@/network/api.js"
	export default {
		data() {
			return {
				QRImg:'',
				cardInfo:''
			}
		},
		methods: {
			getMyQR(){
				let _this = this
				getQR().then(res => {
					_this.QRImg = "data:image/jpg;base64,"+res.QR
					_this.cardInfo = res.limit
				}).catch(err => {
					console.log(err)
				})				
			}
		},
		created(){
			this.getMyQR()
		}
	}
</script>

<style lang="scss" scoped>
	.myQRcode{
		display:flex;
		justify-content:center;
		align-items:center;
		flex-direction: column;
		background: linear-gradient(45deg,rgba(76,180,231,0.4),rgba(255,192,159,0.4));
		height: 100vh;
		.cardInfo{
			background-color: rgba(255, 174, 237, 0.5);
			border-radius: 5px;
			margin-top: -25px;
			margin-bottom: 10px;
			width: 55vw;
			text-align: center;
			line-height: 1.4em;
			span{
				font-size: 14px;
			}
		}
		.VIPCard{
			width: 80vw;
			height: 125vw;
			background: url("https://s2.loli.net/2022/04/28/G4zZeRmhxBL5ati.jpg") no-repeat fixed (0 0)/cover;
			border-radius:9px;
			opacity:0.9;
			display:flex;
			justify-content:center;
			
			box-shadow: 2px -2px 10px 5px rgba(255,255,255,1);
			.QRCode{
				margin-top:40px;
				width: 68vw;
				height: 68vw;
				background-color:rgba(255, 174, 237, 0.5);
				display:flex;
				justify-content:center;
				align-items:center;
				image {
					width: 95%;
					height: 95%;
					opacity: 0.7;
					border: 2.3px dotted gainsboro;
				}
			}
		}
	}

</style>
