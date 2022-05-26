<template>
  <div>
    <operate-panel @search="search">
      <template #input>
        <el-input
            placeholder="请输入内容"
            v-model="sCondition.menuDesc"
            class="input-width m-left-10"
            clearable>
        </el-input>
      </template>
      <template #button>
        <el-button @click="buttonAdd">
          添加菜单
        </el-button>
        <el-button @click="alterMenu">
          修改
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
    <nata-dialog @confirm="confirm">
      <el-form label-position="left" label-width="80px" :model="Form">
        <el-form-item label="菜单名称">
          <el-input v-model="Form.name"></el-input>
        </el-form-item>
        <el-form-item label="路由">
          <el-input v-model="Form.path"></el-input>
        </el-form-item>
        <el-form-item label="页面路径">
          <el-input v-model="Form.pageUrl"></el-input>
        </el-form-item>
        <el-form-item label="图标">
          <el-input v-model="Form.icon"></el-input>
        </el-form-item>
        <el-form-item label="父级菜单">
          <el-select v-model="Form.pid" clearable placeholder="请选择" style="width: 100%">
            <el-option
                v-for="item in options"
                :key="item.id"
                :label="item.name"
                :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="菜单描述">
          <el-input v-model="Form.menuDesc"></el-input>
        </el-form-item>
        </el-form>
    </nata-dialog>
  </div>
</template>

<script>
import nataTable from "components/nataTable";
import operatePanel from "components/operatePanel";
import nataDialog from "components/nataDialog";
import {getAllPmenus, insertMenu, modifyMenu} from "network/requestAPI/menus/menus";
import {getByPage,delOneData} from "network/requestAPI/commonAPI/general";

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
        {prop:'name',label:'菜单名称',width:120},
        {prop:'path',label:'路由',width:150},
        {prop:'pageUrl',label:'路径',width:250},
        {prop:'icon',label:'icon',width:120},
        {prop:'pid',label:'pid',width:80},
        {prop:'menuDesc',label:'菜单描述',width:null},
      ],
      input:'',
      tableData:[],
      currentPage:1,
      pageSize:10,
      total:0,
      isVisible:false,
      Form:{},
      options:[],
      selection:[],
      isAdd:true,
      urlPrefix:'menus',
      sCondition:{},
    }
  },
  methods:{
    // 翻页请求
    currentChange(page){
      this.currentPage = page
      this.getMenus()
    },
    // 获取选中的行数据
    handleSelectionChange(selection){
      console.log("============");
      console.log(selection);
      console.log("============");
      this.selection = selection
    },
    //打开弹窗
    openFormDialog(){
      this.findPmenus()
      this.$store.commit("changeVisibleState",true)
    },
    //获取目录列表请求
    getMenus(mParams){
      getByPage(this.urlPrefix,{
        currentPage:this.currentPage,
        pageSize:this.pageSize,
        multiParams:mParams
      }).then(res => {
        this.tableData = res.records
        // this.options = this.tableData.filter(item => item.pid === null || item.pid === 0)
        this.total = res.total
      }).catch(err => {
        console.log(err)
      })
    },
    // 添加目录请求
     addMenu(){
      if(!this.Form){
        this.$alert({
          message:"表单不能为空",
          type:"warning"
        })
        return;
      }
      insertMenu(this.Form).then(res => {
        this.$message({
          message:'添加成功',
          type:"success"
        })
      }).catch(err => {
        this.$message({
          message:err,
          type:"error"
        })
      })
    },
    //提交表单
    confirm(){
      if(this.isAdd){
        this.addMenu()
      }else{
        this.updateMenuItem()
      }
    },
    // 表单修改按钮响应事件
    alterMenu(){
      if(this.selection.length > 0){
        // console.log("++++++++");
        // console.log(this.selection);
        // console.log("++++++++");
        // 再打开修改表单前置空表单
        this.Form = this.selection[0]
        this.isAdd = false
        this.openFormDialog();
      }else{
        this.$message({
          message:"请先选择要修改的的项!",
          type:"warning"
        })
      }
    },
    //表单添加按钮响应事件
    buttonAdd(){
      this.isAdd = true
      this.Form = {}
      this.openFormDialog()
    },
    //更新表单请求
    updateMenuItem(){
      // this.Form.id = this.selection.id
      modifyMenu(this.Form).then(res => {
        this.getMenus()
        this.$message({
          message:res.msg,
          type:"success"
        })
      }).catch(err => {
        this.$message({
          message:res.msg,
          type:"error"
        })
      })
    },
    //获取父级菜单列表
    findPmenus(){
      getAllPmenus().then(res => {
        this.options = res
      }).catch(err => {
        console.log(err);
      })
    },
    search(menuName){
      const mParams ={
        name:menuName,
        menus_desc:this.sCondition.menuDesc,
      }
      console.log(mParams);
      this.getMenus(mParams)
    },
    del(id){
      this.$confirm('此操作将删除该菜单条目, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delOneData(this.urlPrefix,id).then(res => {
          this.getMenus()
          this.$message({
            message:res.msg,
            type:"success"
          })
          this.getTableData()
        }).catch(err => {
          console.log(err);
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });

    },
  },
  created() {
    this.getMenus()
  }
}
</script>

<style scoped>

</style>