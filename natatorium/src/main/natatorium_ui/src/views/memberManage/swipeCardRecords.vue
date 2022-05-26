<template>
  <div>
    <operate-panel @search="search"></operate-panel>
    <nata-table
        :table-data="tableData"
        :header-setting="fields"
        :current-page="currentPage"
        :page-size="pageSize"
        :total="total"
        ref="menuTable"
        @selectOne="handleSelectionChange"
        is-radio
        @onCurrentChange="currentChange">
    </nata-table>
  </div>
</template>

<script>
import nataTable from "components/nataTable";
import operatePanel from "components/operatePanel";
import nataDialog from "components/nataDialog";
import {
  addOneData,
  getByPage,
  delOneData,
  updateOneData
} from "network/requestAPI/commonAPI/general";

export default {
  name: "coach",
  components:{
    nataTable,
    operatePanel,
    nataDialog
  },
  data(){
    return{
      fields: [
        {prop:'userCardNumber',label:'卡号',width:120},
        {prop:'swipeTime',label:'刷卡时间',width:null},
      ],
      tableData:[],
      currentPage:1,
      pageSize:10,
      total:0,
      isVisible:false,
      Form:{},
      options:[],
      selection:[],
      isAdd:true,
      switchValue:1,
      isSelect:true,
      // 网络请求地址前缀
      urlPrefix:"scr",
      // 查询条件
      sCondition:{
        cardNumber:null,
        cardUserPhone:null
      },
      //用于比对修改前后的数据是否有变化
      updateRow:{}
    }
  },
  methods:{
    // 翻页请求
    currentChange(page){
      this.currentPage = page
      this.getTableData()
    },
    // 获取选中的行数据
    handleSelectionChange(selection){
      this.selection = selection
    },
    //打开弹窗
    openFormDialog(){
      this.$store.commit("changeVisibleState",true)
    },
    //获取目录列表请求
    getTableData(mParams){
      getByPage(this.urlPrefix,{
        currentPage:this.currentPage,
        pageSize:this.pageSize,
        multiParams:mParams
      }).then(res => {
        this.tableData = res.records
      }).catch(err => {
        console.log(err);
      })
    },
    // 添加文件请求
    add(){
      this.Form = {}
      this.isAdd = true
      this.openFormDialog()
    },
    edit(row){
      this.updateRow = row
      // 深复制从而让弹窗中的数据修改时而不影响表格中对应的数据
      this.Form = JSON.parse(JSON.stringify(row))
      this.isAdd = false
      this.openFormDialog()
    },
    addData(){
      addOneData(this.urlPrefix,this.Form).then(res => {
        this.$message({
          message:res.msg,
          type:"success"
        })
        this.getTableData()
      })
    },
    //提交表单
    confirm(){
      if(this.isAdd){
        this.addData()
      }else{
        if(JSON.stringify(this.Form) === JSON.stringify(this.updateRow)){
          console.log(JSON.stringify(this.Form));
        }else{
          JSON.stringify(this.updateRow)
          this.updateOneItem()
        }

      }
    },
    // 表单修改按钮响应事件
    // alterFile(){
    //   if(this.selection.length > 0){
    //     this.Form = this.selection[0]
    //     this.isAdd = false
    //     this.openFormDialog();
    //   }else{
    //     this.$message({
    //       message:"请先选择要修改的的项!",
    //       type:"warning"
    //     })
    //   }
    // },
    //表单添加按钮响应事件
    // uploadBth(){
    //   this.isAdd = true
    //   this.openFormDialog()
    // },
    //更新表单请求
    updateOneItem(id){
      console.log(this.Form);
      updateOneData(this.urlPrefix,this.Form).then(res => {
        this.getTableData()
        this.$message({
          message:res.msg,
          type:"success"
        })
      }).catch(err => {
        console.log(err);
      })
    },
    // switch按钮切换时
    switchChange(value){
      if(value === 0){
        this.isSelect = false
      }else{
        this.isSelect = true
      }
    },
    del(id){
      delOneData(this.urlPrefix,id).then(res => {
        this.$message({
          message:res.msg,
          type:"success"
        })
        this.getTableData()
      }).catch(err => {
        console.log(err);
      })
    },
    search(Name){
      const mParams ={
        item_name:Name
      }
      this.getTableData(mParams)
    },
    selectable(row,index){
      if(row.enable === 1){
        return true
      }else{
        return false
      }

    }
  },
  created() {
    this.getTableData()
  }
}
</script>

<style lang="scss" scoped>
.form-margin-left{
  :nth-child(2){
    margin-left: 0px !important;
  }
  div:first-of-type{
    margin-left: 0px !important;
    display: flex;
  }
}
.el-table{
  .el-button{
    padding: 5px;
    font-size: 12px;
  }
}

</style>