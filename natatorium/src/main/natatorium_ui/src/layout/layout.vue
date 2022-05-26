<template>
  <el-container style="height: 100vh; ">
  <nata-aside :isCollapse="isCollapse"></nata-aside>
    <el-container style="display: block">
      <nata-header
          @onCollapse="collapse"
          @signOut="logOut"
          :isCollapse="isCollapse"></nata-header>
      <el-main>
<!--        <el-breadcrumb separator-class="el-icon-arrow-right">-->
<!--          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>-->
<!--          <el-breadcrumb-item>活动管理</el-breadcrumb-item>-->
<!--          <el-breadcrumb-item>活动列表</el-breadcrumb-item>-->
<!--          <el-breadcrumb-item>活动详情</el-breadcrumb-item>-->
<!--        </el-breadcrumb>-->
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import nataAside from "./nataAside";
import nataHeader from "./nataHeader";
// import operatePanel from "@/components/operatePanel";
import basicMemberInfo from "../views/memberManage/basicMemberInfo";
export default {
  name:'layout',
  components:{
    nataAside,
    nataHeader,
    basicMemberInfo
  },
  data() {
    const item = {
      date: '2016-05-02',
      name: '王小虎',
      address: '上海市普陀区金沙江路 1518 弄'
    };
    return {
      tableData: Array(20).fill(item),
      isCollapse: false
    }
  },
  methods:{
    collapse(){
      this.isCollapse = !this.isCollapse;
    },
    logOut(){
      console.log('signout');
      this.$router.push("/")
      localStorage.removeItem("menus")
      localStorage.removeItem("token")
    }
  }
};
</script>

<style scoped>
.el-main{
  padding: 10px;
}
.el-breadcrumb{
  margin-bottom: 10px;
}
</style>