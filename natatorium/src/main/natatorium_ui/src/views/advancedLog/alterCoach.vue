<template>
  <div>
    <operate-panel @search="search" >
      <template #button>
        <el-button>
          变更教练
        </el-button>
      </template>
    </operate-panel>
    <nata-table
        :table-data="tableData"
        :header-setting="fields"
        :current-page="currentPage"
        :page-size="pageSize"
        :total="total"
        ref="menuTable"
        @selectOne="handleSelectionChange"
        is-radio
        @onCurrentChange="currentChange" />
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
    operatePanel
  },
  data(){
    return{
      fields: [
        {prop:'userCardNumber',label:'会员卡号',width:120},
        {prop:'userCardName',label:'会员名称',width:120},
        {prop:'resetTime',label:'重置时间',width:120},
        {prop:'remarks',label:'备注',width:null},
      ],
      tableData:[],
      currentPage:1,
      pageSize:10,
      total:0,
      isVisible:false,
      Form:{},
      selection:[],
      isSelect:true,
      // 网络请求地址前缀
      urlPrefix:"al",
      // 查询条件
      sCondition:{},
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
.el-table{
  .el-button{
    padding: 5px;
    font-size: 12px;
  }
}

</style>