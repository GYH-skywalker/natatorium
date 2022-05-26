<template>
  <div>
    <operate-panel @search="search">
    </operate-panel>
    <div class="contents">
      <el-row :gutter="15">
        <el-col :span="9">
          <el-card>
            <el-form
                label-position="left"
                label-width="40px"
                title="通知"
                :model="Form">
              <el-form-item label="标题">
                <el-input v-model="Form.title" type="text" />
              </el-form-item>
            </el-form>
            <el-input
                type="textarea"
                :autosize="{ minRows: 10, maxRows: 20}"
                placeholder="请输入内容"
                v-model="notifyContents">
            </el-input>
            <div class="bottom clearfix">
              <el-button type="primary" class="button" @click="addData">发布通知</el-button>
            </div>
        </el-card>
        </el-col>
        <el-col :span="15">
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
            <template #customColumn>
              <el-table-column
                  fixed="right"
                  label="操作"
                  width="80">
                <template slot-scope="scope">
                  <el-button @click="del(scope.row.id)" size="mini" :disabled="scope.row.enable === 0" type="danger">删除</el-button>
                </template>
              </el-table-column>
            </template>
          </nata-table>
        </el-col>
      </el-row>
    </div>
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
import {publicNotice} from "../../network/requestAPI/notify/notify";

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
        {prop:'title',label:'标题',width:120},
        // {prop:'contents',label:'内容',width:120},
        {prop:'createTime',label:'创建时间',width:150},
        {prop:'createBy',label:'创建人',width:null},
      ],
      fileType:'',
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
      urlPrefix:"notify",
      // 查询条件
      sCondition:{
        cardNumber:null,
        cardUserPhone:null
      },
      //用于比对修改前后的数据是否有变化
      updateRow:{},
      notifyContents:''
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
    addData(){
      this.Form.contents = this.notifyContents
      publicNotice(this.Form).then(res => {
        this.$message({
          message:res.msg,
          type:"success"
        })
        this.Form = []
        this.notifyContents = ''
        this.getTableData()
      })
    },
    //提交表单
    // confirm(){
    //   if(this.isAdd){
    //     this.addData()
    //   }else{
    //     if(JSON.stringify(this.Form) === JSON.stringify(this.updateRow)){
    //       console.log(JSON.stringify(this.Form));
    //     }else{
    //       JSON.stringify(this.updateRow)
    //       this.updateOneItem()
    //     }
    //
    //   }
    // },
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
.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  //padding: 0;
  float: right;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}

</style>