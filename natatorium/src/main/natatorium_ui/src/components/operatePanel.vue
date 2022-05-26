<template>
<div class="operate-panel">
  <slot name="input" class="m-left-10 input-width"></slot>
  <el-input
      :placeholder="hint"
      v-model="input"
      maxlength="20"
      v-if="showDefault"
      clearable class="m-left-10 input-width">
  </el-input>
  <el-button type="primary" class="m-left-10" @click="search">{{ buttonName }}</el-button>
  <slot name="button"  class="m-left-10"></slot>
</div>
</template>

<script>
export default {
  name: "operatePanel",
  props:{
    hint:{
      type:String,
      default(){
        return "请输入名称"
      }
    },
    showDefault:{
      type:Boolean,
      default() {
        return true
      }
    },
    buttonName:{
      type:String,
      default(){
        return "查询"
      }
    }
  },
  data(){
    return{
      input:''
    }
  },
  methods:{
    search(){
      this.$emit("search",this.input)
    }
  }

}
</script>

<style scoped>
.operate-panel{
  width: 100%;
  background-color: #EBEEF5;
  padding: 5px 10px;
  display: flex;
  justify-content: right;
  margin-bottom: 10px;
}
.m-left-10{
  margin-left: 10px;
}
/*input默认宽度12em*/
.input-width{
  width: 12em;
}
.input-width-14{
  width: 14em;
}
.input-width-16{
  width: 16em;
}
</style>