<template>
  <div>
    <operate-panel @search="search">
      <template #input>
        <el-input
            placeholder="救生员编号"
            v-model="sCondition.cardNumber"
            class="input-width m-left-10"
            clearable>
        </el-input>
        <el-input
            placeholder="救生员资格证号"
            v-model="sCondition.cardUserPhone"
            class="input-width m-left-10"
            clearable>
        </el-input>
      </template>
      <template #button>
        <el-button type="primary" @click="add">
          添加教练
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
        @onCurrentChange="currentChange">
      <template #customColumn>
        <el-table-column
            fixed="right"
            label="操作"
            width="70">
          <template slot-scope="scope">
            <el-button @click="edit(scope.row)" size="mini" :disabled="scope.row.enable === 0">上传照片</el-button>
          </template>
        </el-table-column>
        <el-table-column
            fixed="right"
            label="操作"
            width="70">
          <template slot-scope="scope">
            <el-button @click="edit(scope.row)" size="mini" :disabled="scope.row.enable === 0">上传签章</el-button>
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
            <el-button @click="del(scope.row.id)" size="mini" :disabled="scope.row.enable === 0" type="danger">删除</el-button>
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
        <el-form-item label="救生员编号" label-width="110px">
          <el-input v-model="Form.lifeguardNumber"></el-input>
        </el-form-item>
        <el-form-item label="救生员资格证号" label-width="110px" >
          <el-input v-model="Form.lifeguardCertificationNumber"></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="Form.coachName"></el-input>
        </el-form-item>
        <el-form-item label="性别" class="form-margin-left">
          <el-radio-group v-model="Form.gender" size="small">
            <el-radio label="0" border>男🚹</el-radio>
            <el-radio label="1" border>女🚺</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model="Form.age"></el-input>
        </el-form-item>
        <el-form-item label="教龄">
        <el-input v-model="Form.seniority"></el-input>
      </el-form-item>
        <el-form-item label="电话号码">
          <el-input v-model="Form.phone"></el-input>
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
        {prop:'lifeguardNumber',label:'救生员编号',width:120},
        {prop:'lifeguardCertificationNumber',label:'救生员资格证号',width:120},
        {prop:'coachName',label:'教练名称',width:80},
        {prop:'gender',label:'性别',width:80},
        {prop:'age',label:'年龄',width:80},
        {prop:'phone',label:'电话',width:100},
        {prop:'seniority',label:'教龄',width:80},
        {prop:'coachPhoto',label:'照片',width:80},
        {prop:'signaturePic',label:'签章',width:80},
        {prop:'remarks',label:'备注',width:null}
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
      urlPrefix:"coach",
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
        this.tableData.forEach(item => {
          item.gender === 0 ? item.gender = '男' : item.gender = '女'
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
      this.Form.gender = this.Form.gender === '男' ?  '0' : '1'
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
        this.updateRow.gender = this.updateRow.gender === '男' ? '0' : '1'
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
    search(cardUserName){
      const mParams ={
        card_number:this.sCondition.cardNumber,
        card_user_phone:this.sCondition.cardUserPhone,
        card_user_name:cardUserName
      }
      this.getTableData(mParams)
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