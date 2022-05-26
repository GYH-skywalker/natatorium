<template>
<div>
  <el-table
      :data="tableData"
      stripe
      highlight-current-row
      @select="handleSelect"
      @select-all="selectAll"
      @selection-change="handleSelectChange"
      ref="table"
      :header-cell-style="{'background-color':'#F2F6FC','color':'#303133'}"
      style="width: 100%">
    <el-table-column
        type="selection"
        :selectable="selectable"
        width="55">
    </el-table-column>
    <el-table-column
        v-for="(item,index) in headerSetting"
        :key="index"
        :prop="item.prop"
        :label="item.label"
        :show-overflow-tooltip="true"
        :width="item.width">
    </el-table-column>
    <slot name="customColumn"></slot>
  </el-table>
  <el-pagination
      @current-change="handleCurrentChange"
      :current-page.sync="currentPage"
      :page-size="pageSize"
      background
      hide-on-single-page
      layout="prev, pager, next, jumper"
      :total="total">
  </el-pagination>
</div>
</template>

<script>
export default {
  name: "nataTable",
  props:{
    headerSetting:{
      type:Array,
      default(){
        return []
      }
    },
    tableData:{
      type:Array,
      default(){
        return []
      }
    },
    currentPage:{
      type:Number,
      default(){
        return 1
      }
    },
    pageSize:{
      type:Number,
      default(){
        return 10
      }
    },
    total:{
      type:Number,
      default(){
        return 0
      }
    },
    isRadio:{
      type: Boolean,
      default(){
        return false
      }
    },
    handleSelectable: {
      type: Function,
      default:() => true
    }

  },
  data(){
    return{

    }
  },
  methods:{
    handleCurrentChange(){
      this.$emit("onCurrentChange",this.currentPage)
    },
    //将选中的数据传递出去
    handleSelectChange(selection){
      this.$emit("selectionChange",selection)
    },
    //处理选中选择框时发生的事情(这里主要用于限制多选框功能,使其一次只能选择一个
    handleSelect(selection,row){
      if(this.isRadio){
        if(selection.length > 1){
          this.$refs.table.toggleRowSelection(selection[0])
          selection = [row]
        }
        this.$emit("selectOne",selection)
      }
    },
    selectAll(selection){
      if(this.isRadio){
        this.$refs.table.clearSelection()
        selection = []
      }
    },
    selectable(row,index){
      return this.handleSelectable(row,index)
    }
  }
}
</script>

<style scoped>

</style>