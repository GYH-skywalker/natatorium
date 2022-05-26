<template>
	<view class="content">
		<u-swiper
				:list="banners"
				previousMargin="30"
				nextMargin="30"
				circular
				@change="change"
				@click="click"
		></u-swiper>
		<view style="margin-top:20px">
			<u-grid
			@click="gridResponse"
				:border="false">
				<u-grid-item
					v-for="(baseListItem,baseListIndex) in baseList"
					:name="baseListItem.path"
					:key="baseListIndex">
						<u-icon
								:customStyle="{paddingTop:20+'rpx',color:baseListItem.color,fontSize:27+'px'}"
								:name="baseListItem.name"
								:size="22"
						></u-icon>
						<text class="grid-text" style="font-size:13px">{{baseListItem.title}}</text>
					</u-grid-item>
				</u-grid>
		</view>
		 
		<view class="feature">
			<!-- <view class="title" style="display:flex">
				功能:
				<view class="verticalGap" />
			</view> -->
			<u-button 
			type="warning" 
			icon="scan" 
			:customStyle="{width:'85vw',height:'2.1em'}"
			plain
			@click="scanQR"
			text="扫一扫"></u-button>
		</view>
		<view class="notice">
			<view class="noticeTitle" style="display:flex">
				通告:
				<view class="verticalGap" />
			</view>
			<view class="noticeBody">
				<uni-card :extra="notification.createTime" note="notification.title" @click="toDetail">
					{{notification.contents}}
				</uni-card>
			</view>
		</view>
		<u-toast ref="utoast">刷卡成功</u-toast>
	</view>
</template>

<script> 
import {scanQR,getNotice,getBanner} from "@/network/api.js"
	export default {

		data() {
			return {
				value6:0,
				banners: [],
				notification:{},
				baseList: [{
							name: 'order',
							title: '约课',
							color:'lightpink',
							path:'reservation'
							},
							{
								name: 'grid',
								title: '二维码',
								color:'lightgreen',
								path: 'MyQRCode'
							},
							{
								name: 'file-text',
								title: '评价',
								color:'skyblue',
								path:'evaluation'
							}
						]
			}
		},
		onLoad() {

		},
		methods: {
			change(pathName){
				uni.$u.route("/pages/"+pathName+"/"+pathName)
			},
			gridResponse(gridItemName){
				console.log("item:"+gridItemName)
				uni.$u.route("/pages/index/"+gridItemName)
			},
			scanQR(){
				uni.scanCode({
					success: (res) => {
						console.log(res)
						scanQR(JSON.parse(res.result)).then(res => {
							console.log(res)
							uni.$u.route({type:"back"})
						}).catch(err => {
							console.log(err)
						})
					}
				})
			},
			Notify(){
				getNotice().then(res => {
					this.notification = res
				}).catch(err => {
					console.log(err);
				})
			},
			banner(){
				getBanner().then(res => {
					this.banners = res.map(item =>{
						return item.banner
					})
				}).catch(err => {
					console.log(err);
				})
			},
			toDetail(){
				uni.$u.route("/pages/index/noticeDetail")
			}
		},
		onShow() {
			this.Notify()
			// this.banner()
		},
		created() {
			this.banner()
		}
	}
</script>

<style lang="scss" scoped>
	/* .content {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		background-color: lightblue;
		height: 100vh;
	} */

	.logo {
		height: 200rpx;
		width: 200rpx;
		margin-top: 200rpx;
		margin-left: auto;
		margin-right: auto;
		margin-bottom: 50rpx;
	}

	.text-area {
		display: flex;
		justify-content: center;
	}

	.title {
		font-size: 36rpx;
		color: #8f8f94;
	}
	.verticalGap{
		width:3px;
		height:1.3em;
		background-color:rgba(76,180,231,0.8);
		margin-left:5px;
	}
	.feature{
		margin-top:30px;
		.title{
			margin-bottom:10px;
		}
	}
	.notice{
		.noticeTitle{
			text-indent:0.5em;
			font-size:13px;
			// color:red;
		}
		margin-top:20px;
	}
</style>
