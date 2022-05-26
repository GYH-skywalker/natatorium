<template>
	<view class="about-us">
		<view class="contents">
			关于我们
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		methods: {
			
		}
	}
</script>

<style lang="scss" scoped>
.about-us{
	filter:saturate(0.5) brightness(1.1);
	width: 100vw;
	height: 100vh;
	background: url("https://s2.loli.net/2022/05/05/dbJFP6X4x7qtDpB.png") no-repeat center/100%;
	display: flex;
	background-color: #fff;
	justify-content: center;
	align-items: center;
	.contents{
		font-size: 30px;
		color: #363636;
	}
}
</style>
