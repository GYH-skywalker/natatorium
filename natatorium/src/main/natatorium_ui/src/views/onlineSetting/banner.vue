<template>
  <div>
    <operate-panel @search="search">
    </operate-panel>
    <div class="contents">
      <el-row :gutter="15">
        <el-col :span="8">
          <el-card>
            <el-upload
                class="avatar-uploader"
                action="#"
                drag
                :show-file-list="false"
                :http-request="addFile"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload">
<!--              <img v-if="imageUrl" :src="imageUrl" class="avatar">-->
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            </el-upload>
          </el-card>
        </el-col>
        <el-col :span="16">
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
                  label="预览">
                <template slot-scope="scope">
                  <el-image
                      style="width: 200px; height: 100px"
                      :src="scope.row.banner"
                      :preview-src-list="srcList">
                  </el-image>
                </template>
              </el-table-column>
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
  getByPage,
  updateOneData
} from "network/requestAPI/commonAPI/general";
import {uploadFile} from "network/requestAPI/files/files";
import {addBanner, delBanner} from "network/requestAPI/banner/banner";

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
        {prop:'createBy',label:'添加人',width:80},
        {prop:'createTime',label:'添加时间',width:120},
        {prop:'remarks',label:'备注',width:150},
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
      urlPrefix:"banner",
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
      addBanner(this.Form).then(res => {
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
    // 添加文件请求
    addFile(uploadBody){
      let files = new FormData()
      files.append("mFile",uploadBody.file)
      uploadFile(files).then(res => {
        console.log(res);
        this.Form.banner = res.fileUrl
        this.Form.bannerUuid = res.uuid
        this.$message({
          message:"上传成功",
          type:"success"
        })
        this.addData()
      }).catch(err => {
        console.log('file.vue=> addFile=> err:')
        this.$message({
          message:err.msg,
          type:"error"
        })
        console.log(err);
      })
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
      delBanner(id).then(res => {
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