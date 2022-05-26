<template>
	<view class="course">
		<view class="courseCard" v-for="(item,index) in course" :key="index">
				<uni-card padding="0" @click="toDetail(item)">
					<image :src="item.courseCover" style="height: 38vw;width: 100%;"></image>
					<view class="abstract">
						<u-tag text="简介" plain size="mini" style="height: 30px;width: 18px;"  type="warning" />
						<text class="text">{{item.courseName}}</text>
					</view>
				</uni-card>
		</view>
	</view>
</template>

<script>
	import {coursePage,coverPath} from "@/network/api.js"
	export default {
		data() {
			return {
				course:[],
				checked:[],
				cover:'',
				currentPage:1,
				pageSize:10,
			}
		},
		methods: {
			toDetail(course){
				course.page = "reserve"
				uni.$u.route({url:"pages/index/courseDetail/courseDetail",params:course})
				console.log(course)
			},
			getCourse(){
				coursePage({
					currentPage:this.currentPage,
					pageSize:this.pageSize
				}).then(res => {
					console.log(res.records)
					this.course.push(...res.records)
					// this.course.forEach(item => {
					// 	// coverPath(item.courseCover).then(res => {
					// 	// 	item.courseCover = res
					// 	// 	console.log(item.courseCover)
					// 	// }).catch(err => {
					// 	// 	console.log(err)
					// 	// })
						
					// })
					console.log(this.course)
				}).catch(err => {
					console.log(err)
				})
			}
		},
		onReachBottom() {
					this.getCourse()
				},
		created(){
			this.getCourse()
		}
	}
</script>

<style lang="scss">
	.course{
		display: flex;
		width: 100vw;
		flex-wrap: wrap;
		// background-color:black;
		.courseCard{
			uni-card{
				width: 50vw;
				// height: 63vw;
				// margin-bottom: 15px;
				display: flex;
				.uni-card--border{
					border: none !important;
				}
				.uni-card{
					margin: 10px !important;
					padding: 0px !important;
					box-shadow: 0px 0px 6px 3px rgba(209, 209, 209, 0.7) !important;
					
					.abstract{
						display: flex;
						flex-wrap: wrap;
						padding: 0px 5px 8px;
						// background-color: red;
						.u-tag{
							height: 20px !important;
							padding:0 4px !important;
						}
						.text{
							font-size: 12px;
							line-height: 14px;
							height: 28px;
							margin-top: 5px;
							padding-left: 5px;
							// word-wrap: break-word;
							// word-break: break-all;
							text-overflow: ellipsis;
							overflow: hidden;
						}
					}
				}
				
			}
			
		}
		.courseCard:nth-of-type(odd){
			uni-card{
				.uni-card{
					margin-right: 6px !important;
				}
			}
		}
		.courseCard:nth-of-type(even){
			uni-card{
				.uni-card{
					margin-left: 6px !important;
					
				}
			}
		}
	}

</style>
