<template>
  <div>
    <operate-panel @search="search" hint="项目名称">
      <template #button>
        <el-button type="primary" @click="add">
          刷卡消费
        </el-button>
        <el-button type="primary" @click="">
          结束刷卡
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
              {{scope.row.enable === 1 ? '正常' : '异常'}}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column
            fixed="right"
            label="操作"
            width="50">
          <template slot-scope="scope">
            <el-button @click="del(scope.row.id)" size="mini" :disabled="scope.row.enable === 0">撤单</el-button>
          </template>
        </el-table-column>
      </template>
    </nata-table>
    <nata-dialog @confirm="confirm" title="添加套餐项目" width="45%">
      <el-form
          label-position="left"
          label-width="75px"
          title="用户信息"
          :model="Form">
        <el-form-item label="卡片类型" class="form-margin-left">
          <el-select v-model="Form.cardType" placeholder="请选择">
            <el-option
                v-for="item in options"
                :key="item.id"
                :label="item.businessName"
                :value="item.businessName">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="套餐名称">
          <el-input v-model="Form.packageName"></el-input>
        </el-form-item>
        <el-form-item label="套餐次数" v-if="Form.cardType === '次卡'">
          <el-input v-model="Form.packageCounts"></el-input>
        </el-form-item>
        <el-form-item label="套餐价格">
          <el-input v-model="Form.packagePrice"></el-input>
        </el-form-item>
        <el-form-item label="时间期限">
          <div class="block">
            <el-date-picker
                v-model="dateRange"
                type="daterange"
                align="right"
                unlink-panels
                range-separator="~"
                value-format="yyyy-MM-dd"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                :picker-options="pickerOptions">
            </el-date-picker>
          </div>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="Form.remarks"></el-input>
        </el-form-item>
      </el-form>
    </nata-dialog>
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
  updateOneData,
  getAll,
  updateState
} from "network/requestAPI/commonAPI/general";

export default {
  name: "consumeBusiness",
  components:{
    nataTable,
    operatePanel,
    nataDialog
  },
  data(){
    return{
      fields: [
        {prop:'packageName',label:'会员卡号',width:120},
        {prop:'packageCounts',label:'卡片类型',width:80},
        {prop:'packagePrice',label:'会员名称',width:80},
        {prop:'startTime',label:'教练名称',width:80},
        {prop:'endTime',label:'消费套餐项目',width:120},
        {prop:'warnTimes',label:'消费时间',width:120},
        {prop:'cardType',label:'消费量',width:80},
        {prop:'cardType',label:'消费金额',width:80},
        {prop:'remarks',label:'备注',width:null},
      ],
      fileType:'',
      tableData:[],
      currentPage:1,
      pageSize:10,
      total:0,
      isVisible:false,
      Form:{},
      //角色列表
      options:[],
      selection:[],
      isAdd:true,
      switchValue:1,
      isSelect:true,
      // 网络请求地址前缀
      urlPrefix:"cb",
      // 查询条件
      sCondition:{
        cardType:"次卡"
      },
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            end.setTime(start.getTime() + 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            end.setTime(start.getTime() + 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一年',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            end.setTime(start.getTime() + 3600 * 1000 * 24 * 365);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
      dateRange:null,
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
      this.Form.startTime = this.dateRange[0]
      this.Form.endTime = this.dateRange[1]
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
        if(JSON.stringify(this.Form) == JSON.stringify(this.updateRow)){
          console.log(JSON.stringify(this.Form));
        }else{
          JSON.stringify(this.updateRow)
          this.updateOneItem()
        }

      }
    },
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
        card_type:this.sCondition.cardType,
        package_name:Name
      }
      this.getTableData(mParams)
    },
    selectable(row,index){
      if(row.enable === 1){
        return true
      }else{
        return false
      }

    },
    getCardType(){
      getAll("bc").then(res => {
        this.options = res
      }).catch(err => {
        console.log(err);
      })
    },
    typeChange(){
      this.search()
    }
  },
  created() {
    this.getCardType()
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
.block{
  width: 100%;
  float: left;
  display: flex;
}

</style>