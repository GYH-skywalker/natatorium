<template>
<div>
  <el-dialog
      custom-class="dialog"
      :title="title"
      :visible.sync="$store.getters.isVisible"
      :width="width"
      :destroy-on-close="true"
      @close="close"
      :before-close="handleClose">
    <slot></slot>
    <span slot="footer" class="dialog-footer">
    <el-button @click="close">取 消</el-button>
    <el-button type="primary" @click="nataConfirm">确 定</el-button>
  </span>
  </el-dialog>
</div>
</template>

<script>
export default {
  name: "nataDialog",
  props:{
    title:{
      type:String,
      default(){
        return "提示"
      }
    },
    width:{
      type:String,
      default() {
        return "30%";
      }
    }
  },
  data(){
    return{

    }
  },
  methods:{
    handleClose(done){
      done();
      // this.$confirm('确认关闭？')
      //     .then(_ => {
      //       done();
      //     })
      //     .catch(_ => {});
    },
    // 关闭弹窗
    close(){
      this.$emit("close")
      this.$store.commit("changeVisibleState",false)
    },
    //确认按钮事件
    nataConfirm(){
      this.$emit("confirm")
      this.close()
    }
  }
}
</script>

<style lang="scss" scoped>

</style>