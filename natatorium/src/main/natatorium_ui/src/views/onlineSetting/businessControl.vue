<template>
  <div>
    <operate-panel @search="initial" :showDefault="false" buttonName="初始化卡种业务总控"/>
    <nata-table
        :table-data="tableData"
        :header-setting="fields"
        :current-page="currentPage"
        :page-size="pageSize"
        :total="total"
        ref="menuTable"
        :handleSelectable="selectable"
        @selectOne="handleSelectionChange"
        is-radio
        @onCurrentChange="currentChange">
      <template #customColumn>
        <el-table-column
            fixed="right"
            label="状态"
            width="50">
          <template slot-scope="scope">
            <el-tag :type="scope.row.enable === 1 ? 'success':'danger'" size="mini">
              {{scope.row.enable === 1 ? '启用' : '停用'}}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column
            fixed="right"
            label="是否启用"
            width="80">
          <template slot-scope="scope">
            <el-switch
                v-model="scope.row.enable"
                active-color="#13ce66"
                @change="switchChange(scope.row.id,scope.row.enable)"
                :width="40"
                :active-value="1"
                :inactive-value="0">
              {{scope.row.enable === 1 ? 'ON' : 'OFF'}}
            </el-switch>
          </template>
        </el-table-column>
      </template>
    </nata-table>
  </div>
</template>

<script>
import nataTable from "components/nataTable";
import operatePanel from "components/operatePanel";
import {
  getByPage,
  updateState
} from "network/requestAPI/commonAPI/general";

export default {
  name: "businessControl",
  components:{
    nataTable,
    operatePanel
  },
  data(){
    return{
      fields: [
        {prop:'id',label:'索引',width:120},
        {prop:'businessName',label:'业务名称',width:120},
        {prop:'remarks',label:'备注',width:null},
      ],
      tableData:[],
      currentPage:1,
      pageSize:10,
      total:0,
      isVisible:false,
      Form:{},
      selection:[],
      switchValue:1,
      isSelect:true,
      // 网络请求地址前缀
      urlPrefix:"bc",
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
        this.tableData.forEach(item => {
          item.userGender === 0 ? item.userGender = '男' : item.userGender = '女'
        })
      }).catch(err => {
        console.log(err);
      })
    },
    // switch按钮切换时
    switchChange(id,value){
      this.updateStatus(id,value)
      if(value === 0){
        this.isSelect = false
      }else{
        this.isSelect = true
      }
    },
    updateStatus(id,enable){
      updateState(this.urlPrefix,{id, enable}).then(res => {
        this.$message({
          message:res.msg,
          type:"success"
        })
      }).catch(err => {
        console.log(err);
      })
    },
    selectable(row,index){
      if(row.enable === 1){
        return true
      }else{
        return false
      }

    },
    resetPWD(id){
      restorePwd(id).then(res => {
        this.$message({
          message:'还原成功',
          type:"success"
        })
      }).catch(err => {
        console.log(err);
      })
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