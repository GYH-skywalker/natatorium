<template>
  <div>
    <operate-panel @search="search">
      <template #input>
        <el-input
            placeholder="会员卡号"
            v-model="sCondition.cardNumber"
            class="input-width m-left-10"
            clearable>
        </el-input>
        <el-input
            placeholder="电话号码"
            v-model="sCondition.cardUserPhone"
            class="input-width m-left-10"
            clearable>
        </el-input>
      </template>
      <template #button>
          <el-button type="primary" @click="add">
            添加
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
            label="操作"
            width="50">
          <template slot-scope="scope">
            <el-switch
                v-model="scope.row.enable"
                active-color="#13ce66"
                @change="switchChange(scope.row.id,scope.row.enable)"
                width="40"
                :active-value="1"
                :inactive-value="0">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column
            fixed="right"
            label="操作"
            width="50">
          <template slot-scope="scope">
            <el-button @click="edit(scope.row)" size="mini" :disabled="scope.row.enable === 0">编辑</el-button>
          </template>
        </el-table-column>
        <el-table-column
            fixed="right"
            label="操作"
            width="50">
          <template slot-scope="scope">
            <el-button @click="del(scope.row.id)" size="mini" :disabled="scope.row.enable === 0">删除</el-button>
          </template>
        </el-table-column>
      </template>
    </nata-table>
    <nata-dialog @confirm="confirm" title="添加会员信息" width="45%">
      <el-form
          label-position="left"
          label-width="75px"
          title="会员信息"
          :model="Form">
        <el-form-item label="会员卡号">
          <el-input v-model="Form.cardNumber"></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="Form.userName"></el-input>
        </el-form-item>
        <el-form-item label="性别" class="form-margin-left">
          <el-radio-group v-model="Form.userGender" size="small">
            <el-radio label="0" border>男🚹</el-radio>
            <el-radio label="1" border>女🚺</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="年龄:生日" class="form-margin-left">
          <div>
            <div style="width:50px;display:inline-block;text-align: center;margin-right: 10px;background-color:#ddeaf6;border-radius: 4px">{{getAge(Form.userBirthday)}}</div>
          <el-date-picker
              v-model="Form.userBirthday"
              type="date"
              placeholder="选择日期">
          </el-date-picker>
          </div>
        </el-form-item>
        <el-form-item label="身高/cm" class="form-margin-left">
          <div>
          <el-input-number v-model="Form.userHeight" :precision="1" :step="1" :max="400" style="margin-right: 16px;"></el-input-number>
          <label >臂长/cm</label>
          <el-input-number v-model="Form.armLength" :precision="1" :step="1" style="margin-right: 16px;"></el-input-number>
          <label>体重/kg</label>
          <el-input-number v-model="Form.userWeight" :precision="1" :step="1"></el-input-number>
          </div>
        </el-form-item>
        <el-form-item label="电话号码">
          <el-input v-model="Form.userPhone"></el-input>
        </el-form-item>
      </el-form>
    </nata-dialog>
  </div>
</template>

<script>
import nataTable from "components/nataTable";
import operatePanel from "components/operatePanel";
import nataDialog from "components/nataDialog";
import {addOneData, getByPage} from "network/requestAPI/commonAPI/general";
import {delOneData, updateOneData, updateState} from "../../network/requestAPI/commonAPI/general";

export default {
  name: "businessList",
  components:{
    nataTable,
    operatePanel,
    nataDialog
  },
  data(){
    return{
      fields: [
        {prop:'cardNumber',label:'卡号',width:120},
        {prop:'userName',label:'姓名',width:120},
        {prop:'userGender',label:'性别',width:80},
        {prop:'userAge',label:'年龄',width: 80},
        {prop:'userPhone',label:'电话',width:120},
        {prop:'userBirthday',label:'生日',width:150},
        {prop:'createTime',label:'注册时间',width:150},
        {prop:'userHeight',label:'身高/cm',width:80},
        {prop:'userWeight',label:'体重/kg',width:80},
        {prop:'armLength',label:'臂长cm'},
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
      urlPrefix:"bmi",
      // 查询条件
      sCondition:{
        cardNumber:null,
        cardUserPhone:null
      },
      //用于比对修改前后的数据是否有变化
      updateRow:{}
    }
  },
  computed:{
    getAge(){
      return function (birthday){
        if(birthday !== null || birthday !== undefined){
          const currentYear = new Date().getFullYear()
          const userBirthday = new Date(birthday).getFullYear()
          return currentYear - userBirthday
        }else{
         return new Number(0)
        }
      }
    },
    getGender(){
      return function (sex){
        return sex === 0 ? '男' : '女'
      }
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
        this.total = res.total
        this.tableData.forEach(item => {
          item.userGender === 0 ? item.userGender = '男' : item.userGender = '女'
        })
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
      this.Form.userGender = this.Form.userGender === '男' ?  '0' : '1'
      this.isAdd = false
      this.openFormDialog()
    },
    addData(){
      this.Form.userAge = this.getAge(this.Form.userBirthday)
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
        this.updateRow.userGender = this.updateRow.userGender === '男' ? '0' : '1'
        if(JSON.stringify(this.Form) == JSON.stringify(this.updateRow)){
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
    search(cardUserName){
      const mParams ={
        card_number:this.sCondition.cardNumber,
        card_user_phone:this.sCondition.cardUserPhone,
        card_user_name:cardUserName
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