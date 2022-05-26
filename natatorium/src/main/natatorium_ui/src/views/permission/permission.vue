<template>
  <div class="permission">
    <el-row>
      <el-col :span="12" :offset="1">
        <el-card >
        <div slot="header" class="clearfix">
          <span>角色列表</span>
          <el-button type="primary" style="float: right;" @click="openFormDialog">添加角色</el-button>
        </div>
        <div>
          <nata-table
              is-radio
              :table-data="tableData"
              :header-setting="fields"
              :current-page="currentPage"
              :page-size="pageSize"
              @selectOne="haveSelect"
              :total="total">
          </nata-table>
        </div>
      </el-card>
      </el-col>
      <el-col :span="9" :offset="1">
        <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>权限列表</span>
          <el-button type="primary" style="float: right;" @click="alterPermission">修改权限</el-button>
        </div>
        <div>
          <el-tree
              :data="treeData"
              :props="defaultProps"
              node-key="id"
              ref="menuTree"
              show-checkbox
              :default-checked-keys="filterKeys(checkedKeys,treeData)"
              @check-change="handleCheckChange">
            <span class="custom-tree-node" slot-scope="{ node, data }">
              <i :class="data.icon"></i>
              <span>{{ node.label }}</span>
            </span>
          </el-tree>
        </div>
      </el-card>
      </el-col>
    </el-row>
    <nata-dialog @confirm="confirm">
      <el-form label-position="left" label-width="80px" :model="Form">
        <el-form-item label="角色名称">
          <el-input v-model="Form.name"></el-input>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="Form.roleDesc"></el-input>
        </el-form-item>
      </el-form>
    </nata-dialog>
  </div>
</template>

<script>
import nataTable from "../../components/nataTable";
import {alterRoleMenuMap, getRole, getRoleMenuMap, insertRole} from "../../network/requestAPI/role/role";
import {findMenusTree, findRoleMenu} from "../../network/requestAPI/menus/menus";
import NataDialog from "../../components/nataDialog";
export default {
  name: "permission",
  components:{
    NataDialog,
    nataTable
  },
  data(){
    return{
      fields: [
        {prop:'name',label:'角色名称'},
        {prop:'roleDesc',label:'描述'},
      ],
      input:'',
      tableData:[],
      currentPage:1,
      pageSize:10,
      total:0,
      treeData: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      checkedKeys:[],
      roleId:0,
      Form:{}
    }
  },
  computed:{

    filterKeys(){
      return function (keys,data){
        if(keys.length > 0 && data.length > 0){
          data.forEach(item => {
            if(item.children.length > 0){
              keys.splice(keys.indexOf(item.id),1)
            }
          })
        }
        return keys
      }
    }
  },
  methods:{
    findRoles(){
      getRole({
        currentPage:this.currentPage,
        pageSize:this.pageSize
      }).then(res => {
        this.tableData = res.records
      }).catch(err => {
        this.$message({
          message:err.msg,
          type:"error"
        })
      })
    },
    //获取角色对应的菜单
    roleMenu(roleId,_treeData){
      getRoleMenuMap(roleId).then(res => {
        this.checkedKeys = res
        // 将有子菜单的父级菜单的key值从checkedkey中移除从而使得tree的多选框的父级节点达到半选的目的
        if(this.checkedKeys.length > 0 && _treeData.length > 0){
          _treeData.forEach(item => {
            if(item.children.length > 0){
              this.checkedKeys.splice(this.checkedKeys.indexOf(item.id),1)
            }
          })
        }
        this.$refs.menuTree.setCheckedKeys(this.checkedKeys,false)
        console.log(this.checkedKeys);
      }).catch(err => {
        this.$message({
          message:err.msg,
          type:"error"
        })
      })
    },
    menusTree(){
      findMenusTree().then(res => {
        this.treeData = res
      }).catch(err => {
        this.$message({
          message:err.msg,
          type:"error"
        })
      })
    },
    haveSelect(selection){
      if (selection.length > 0){
        this.roleId = selection[0].id
        this.roleMenu(selection[0].id,this.treeData)
      }else {
        this.roleId = 0
        this.$refs.menuTree.setCheckedKeys([],false)
      }
    },
    alterPermission(){
      //获取所有全选的key值
      let currentKeys = this.$refs.menuTree.getCheckedKeys(false)
      //将半选的key也放进currentKey中
      currentKeys.push(...this.$refs.menuTree.getHalfCheckedKeys())
      if(this.checkedKeys.sort().toString() != currentKeys.sort().toString()){
        alterRoleMenuMap(this.roleId,currentKeys).then(res => {
          console.log(res);
          this.$message({
            message:res,
            type:"success"
          })
        }).catch(err => {
          this.$message({
            message:err.msg,
            type:"error"
          })
        })
      }
    },
    //打开弹窗
    openFormDialog(){
      this.$store.commit('changeVisibleState',true)
    },
    //添加角色请求
    addRole(){
      insertRole(this.Form).then(res => {
        this.$message({
          message:res,
          type:"success"
        })
      }).catch(err => {
        this.$message({
          message:err,
          type:"error"
        })
      })
    },
    confirm(){
      this.addRole()
    }
  },
  created() {
    this.findRoles();
    this.menusTree()
  }
}
</script>

<style scoped>

</style>