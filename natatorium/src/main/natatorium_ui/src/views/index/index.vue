<template>
<el-card>
  <el-descriptions title="项目使用的相关工具" direction="vertical" :column="6" border>
    <el-descriptions-item label="前后端开发工具">idea</el-descriptions-item>
    <el-descriptions-item label="移动端开发工具">HbuilderX</el-descriptions-item>
    <el-descriptions-item label="数据库" >navicat</el-descriptions-item>
    <el-descriptions-item label="redis缓存">another redis</el-descriptions-item>
    <el-descriptions-item label="服务器控制">Xshell，XFTP</el-descriptions-item>
    <el-descriptions-item label="移动端调试">微信开发工具</el-descriptions-item>
  </el-descriptions>
  <br/>
  <el-descriptions title="后端使用的相关技术" direction="vertical" :column="4" border>
    <el-descriptions-item label="基础语言">java</el-descriptions-item>
    <el-descriptions-item label="数据库" >mysql</el-descriptions-item>
    <el-descriptions-item label="缓存工具">redis</el-descriptions-item>
    <el-descriptions-item label="框架" >springboot，springmvc，mybatis-plus,jwt</el-descriptions-item>
  </el-descriptions>
  <br/>
  <el-descriptions title="运维部署" direction="vertical" :column="4" border>
    <el-descriptions-item label="部署平台">linux(centos)</el-descriptions-item>
    <el-descriptions-item label="容器管理工具">docker</el-descriptions-item>
  </el-descriptions>
  <br/>
  <el-descriptions title="前端使用到的技术" direction="vertical" :column="4" border>
    <el-descriptions-item label="基础语言">javascript,html,css</el-descriptions-item>
    <el-descriptions-item label="框架">vue,axios,echarts,scss</el-descriptions-item>
    <el-descriptions-item label="UI模板">elementUI,UView,uniapp</el-descriptions-item>
  </el-descriptions>
</el-card>
</template>

<script>
export default {
  name: "index"
}
</script>

<style lang="scss" scoped>
 .el-descriptions-item::v-deep{
  font-size: 30px;
  font-weight: bolder;
}
</style>