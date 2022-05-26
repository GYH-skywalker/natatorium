<template>
  <el-header border style="text-align: right; font-size: 16px; display: flex; justify-content: space-between">
    <div style="font-size: 25px" @click="collapse()">
      <i :class="isCollapse ? 'el-icon-s-unfold' : 'el-icon-s-fold'"></i>
    </div>
    <div class="headerRight">
      <span>曹政</span>
      <el-dropdown @command="handleCommand">
        <i class="el-icon-more" style="margin-right: 15px; margin-left: 5px;font-size: 20px;display: inline-block"></i>
        <el-dropdown-menu slot="dropdown" >
<!--          <el-dropdown-item>查看</el-dropdown-item>-->
<!--          <el-dropdown-item>新增</el-dropdown-item>-->
          <el-dropdown-item command="signOut">退出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </el-header>
</template>

<script>
export default {
  name: "nataHeader",
  props:{
    isCollapse:{
      type:Boolean,
      default(){
        return false
      }
    }
  },
  data(){
    return{
    }
  },
  methods:{
    collapse(){
      this.$emit("onCollapse")
    },
    handleCommand(command){
      this.$emit(command)
    }
  }
}
</script>

<style scoped>
.el-header {
  /*background-color: #B3C0D1;*/
  color: #333;
  line-height: 60px;
  border-bottom: 1px solid rgb(190, 190, 190);
}
</style>