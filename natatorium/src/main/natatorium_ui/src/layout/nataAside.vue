<template>
  <el-aside :width="(isCollapse ? 64 : 200) + 'px'">
    <el-menu
        style="min-height: 100%;overflow: hidden;border: none;"
        background-color="#304156"
        text-color="#fff"
        :collapse="isCollapse"
        unique-opened
        :collapse-transition="false"
        >
      <el-header background-color="#304156">
        <img src="@/assets/logo.png" alt="" width="20px" />
        <span style="font-size: 18px; line-height: 40px; color:white" v-if="!isCollapse">sideHeader</span>
      </el-header>
      <div v-for="(Pitem,Pindex) in menuList" :key="Pindex">
        <template v-if="Pitem.path">
          <el-tooltip class="item" effect="dark" :content="Pitem.name" :disabled="!isCollapse" placement="right">
            <el-menu-item :index="Pindex" @click="to(Pitem.path)">
              <i :class="Pitem.icon"></i>
              <span v-if="!isCollapse">{{ Pitem.name }}</span>
            </el-menu-item>
          </el-tooltip>
        </template>
        <template v-else>
          <el-submenu  :index="Pindex" >
            <template slot="title">
              <i :class="Pitem.icon" ></i>
              <span @click="to(Pitem.path)" v-if="!isCollapse">{{ Pitem.name }}</span>
            </template>
            <el-menu-item-group v-if="Pitem.children.length > 0 ">
              <el-menu-item
                  v-for="(Citem,Cindex) in Pitem.children"
                  :key="Pindex+'-'+Cindex"
                  :index="Pindex+'-'+Cindex"
                  @click="to(Citem.path)">
                <span>{{ Citem.name }}</span>
              </el-menu-item>
            </el-menu-item-group>
          </el-submenu>
        </template>
      </div>
    </el-menu>
  </el-aside>
</template>

<script>
import {findMenus} from "../network/requestAPI/menus/menus";

export default {
  name: "nataAside",
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
      menuList:[]
    }
  },
  methods:{
    getMenus(){
      this.menuList = this.$store.getters.getMenus
    },
    //路由跳转
    to(path){
      this.$router.push(path)
    }
  },
  created() {
    this.getMenus()
  }
}
</script>

<style lang="scss" scoped>
.el-aside {
  color: #333;
}
.el-aside::-webkit-scrollbar {
  width: 5px;
  height: 10px;
}
.el-aside::-webkit-scrollbar-thumb {
  background-color: rgba(48, 65, 86, 0.51);
  //border-radius: 3px;
}
.el-submenu{
  .el-menu-item{
    text-align: left;
    text-indent: 3em;
  }
}
.el-icon-setting,
.el-icon-notebook-2{
  font-size: 15px;
  text-align: right;
  display: inline-block;
  margin-right: 0.8em;
}
</style>