<template>
	<view class="detail">
		<view class="detail-content">
			<view class="cover">
				<image :src="course.courseCover" mode=""></image>
			</view>
			<view class="contents">
				<!-- <u-tag  text="优惠" size="mini" plain /> -->
				<view class="detail-item">
					<u-tag  text="教练" size="mini" style="margin-right: 20px;" plain />
					{{course.coach}}
				</view>
				<view class="detail-item">
					<u-tag  text="开课日期" size="mini" plain />
					{{course.startDate}}
				</view>
				<view class="detail-item">
					<u-tag  text="结课日期" size="mini" plain />
					{{course.endDate}}
				</view>
				<view class="detail-item">
					<u-tag  text="上课时间" size="mini" plain />
					{{course.classBegins}}~{{course.classEnds}}
				</view>
				<view class="noticeTitle" style="display:flex">
					详情:
					<view class="verticalGap" />
				</view>
				<view class="introduction">
					{{course.remarks}}
				</view>
			</view>
		</view>
		<view v-if="course.page == 'reserve'" class="action" @click="mekeOrder(course.id)">
			<view class="content">
				预约
			</view>
		</view>
		<u-toast ref="uToast" style="z-index: 1000;">添加成功</u-toast>
	</view>
</template>

<script>
	import {reserve} from '@/network/api.js'
	export default {
		data() {
			return {
				activeBtn:false,
				course:{}
			}
		},
		methods: {
			mekeOrder(courseId){
				reserve(courseId).then(res => {
							this.$refs.uToast.show("添加成功")
				}).catch(err => {
						this.$refs.uToast.show(err.msg)
					console.log(err)
				})
			},
		},
		onLoad: function (option) { //option为object类型，会序列化上个页面传递的参数
			this.course = option
			}
	}
</script>

<style lang="scss" scoped>
.detail{
	// width: 100vw;
.detail-content{
	// background-color: red;
	height: 88vh;
	overflow-y: scroll;
	padding-bottom: 10px;
	.contents{
		// background-color: red;
		padding: 5px 10px 0px;
		.detail-item{
			margin-bottom: 10px;
			font-size: 14px;
			display: flex;
			&:first-of-type{
				/deep/ .u-transition{
					.u-tag-wrapper{
						.u-tag{
							width: 3.5em;
							text-align: center;
							display: flex;
							justify-content: center;
						}
					}
				}
			}
			/deep/ .u-transition{
				margin-right: 10px;
				.u-tag-wrapper{
					.u-tag{
						
						// width: 3em;
						// text-align: center;
						// display: flex;
						// justify-content: center;
					}
				}
			}
		}
	}
	.cover{
		image{
			width: 100vw;
		}
	}
	.verticalGap{
		width:3px;
		height:1.3em;
		background-color:rgba(76,180,231,0.8);
		margin-left:5px;
	}
		.noticeTitle{
			text-indent:0.5em;
			font-size:13px;
			// color:red;
			margin-top:20px;
			margin-bottom: 10px;
		}
		.introduction{
			text-indent: 3em;font-size: 13px;
		}
}
	.action{
		width: 100vw;
		height: 12vh;
		display: flex;
		justify-content: center;
		background-color: #fff;
		align-items: center;
		.content{
			width: 98%;
			height: 80%;
			background-color: rgba(255, 219, 75, 0.7);
			box-shadow: inset 0px 0px 10px 5px rgba(255, 219, 75, 1);
			border-radius: 10px;
		}
		position: fixed;
		bottom: 0px;
		line-height: 49px;
		text-align: center;
		// border-top: 1px solid rgba(217, 217, 217, 1.0);
		color: #4d4d4d;
		font-family:"Microsoft YaHei";
	}
	.action .content:active{
		background-color: #fac800;
		color: #fff;
	}
}
</style>
