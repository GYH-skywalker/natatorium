<template>
  <div>
    <operate-panel @search="search" hint="账号">
      <template #button>
        <el-button type="primary" @click="add">
          添加用户
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
            width="80">
          <template slot-scope="scope">
<!--            重置密码将密码重置为默认111111-->
            <el-button @click="resetPWD(scope.row.id)" size="mini" :disabled="scope.row.enable === 0">重置密码</el-button>
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
    <nata-dialog @confirm="confirm" title="添加用户" width="45%">
      <el-form
          label-position="left"
          label-width="75px"
          title="用户信息"
          :model="Form">
        <el-form-item label="账号名称">
          <el-input v-model="Form.userName"></el-input>
        </el-form-item>
        <el-form-item label="账号级别" class="form-margin-left">
          <el-select v-model="Form.roleId" placeholder="请选择">
            <el-option
                v-for="item in options"
                :key="item.id"
                :label="item.roleDesc"
                :value="item.id">
            </el-option>
          </el-select>
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
import {getAllRole} from "../../network/requestAPI/role/role";
import {addUser, getUserByPage, restorePwd} from "../../network/requestAPI/users/users";
import {updateState} from "../../network/requestAPI/commonAPI/general";

export default {
  name: "shopAccount",
  components:{
    nataTable,
    operatePanel,
    nataDialog
  },
  data(){
    return{
      fields: [
        {prop:'userName',label:'账号',width:80},
        {prop:'roleName',label:'账号级别',width:80},
        {prop:'createTime',label:'创建时间',width:null},
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
      urlPrefix:"user",
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
      this.getRoleList()
      this.$store.commit("changeVisibleState",true)
    },
    //获取目录列表请求
    getTableData(account){
      getUserByPage({
        currentPage:this.currentPage,
        pageSize:this.pageSize,
        searchColumn:'user_name',
        searchValue:account
      }).then(res => {
        this.tableData = res.records
        // getOneRole(this.ta)
      }).catch(err => {
        console.log(err);
      })
    },
    getRoleList(){
      getAllRole().then(res => {
        this.options = res
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
      addUser(this.Form).then(res => {
        this.$message({
          message:res.msg,
          type:"success"
        })
        this.getTableData()
      }).catch(err => {
        console.log(err);
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
    search(account){
      this.getTableData(account)
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