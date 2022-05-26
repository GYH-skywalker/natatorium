<template>
  <div>
    <operate-panel @search="search">
      <template #input>
        <el-input
            placeholder="输入文件类型"
            v-model="fileType"
            class="input-width m-left-10"
            clearable>
        </el-input>
      </template>
      <template #button>
        <el-upload
            class="upload-demo m-left-10"
            action="#"
            :on-progress="uploading"
            :http-request="addFile"
            :multiple="false"
            :show-file-list="false"
            :limit="1">
          <el-button type="primary">
            上传文件
          </el-button>
        </el-upload>
        <el-button @click="alterFile" class="m-left-10" type="primary">
          修改文件名
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
            width="100">
          <template slot-scope="scope">
            <el-switch
                v-model="scope.row.enable"
                active-color="#13ce66"
                @change="switchChange"
                :active-value="1"
                :inactive-value="0">
            </el-switch>
          </template>
        </el-table-column>
          <el-table-column
              v-if="false"
              fixed="right"
              label="操作"
              width="100">
            <template slot-scope="scope">
              <el-button @click="preview(scope.row.uuid)" size="mini" >预览</el-button>
            </template>
        </el-table-column>
        <el-table-column
            fixed="right"
            label="操作"
            width="100">
          <template slot-scope="scope">
            <el-button @click="download(scope.row.uuid)" size="mini">下载</el-button>
          </template>
        </el-table-column>
        <el-table-column
            fixed="right"
            label="操作"
            width="100">
          <template slot-scope="scope">
            <el-button @click="del(scope.row.uuid)" size="mini">删除</el-button>
          </template>
        </el-table-column>
      </template>
    </nata-table>
    <nata-dialog @confirm="confirm">
      <el-form 
          label-position="left" 
          label-width="80px" 
          title="文件名修改"
          :model="Form">
        <el-form-item label="文件名称">
          <el-input v-model="Form.fullName"></el-input>
        </el-form-item>        
      </el-form>
    </nata-dialog>
  </div>
</template>

<script>
import nataTable from "components/nataTable";
import operatePanel from "components/operatePanel";
import nataDialog from "components/nataDialog";
import {insertMenu, menuList, modifyMenu} from "network/requestAPI/menus/menus";
import {findFiles, getPathByUUID, uploadFile} from "network/requestAPI/files/files";
import {delFile, downloadFile} from "network/requestAPI/files/files";

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
        {prop:'fullName',label:'文件名称',width:300},
        {prop:'type',label:'文件类型',width:150},
        {prop:'size',label:'大小(/kb)',width:120},
        {prop:'createTime',label:'创建时间'},
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
      isSelect:true
    }
  },
  methods:{
    // 翻页请求
    currentChange(page){
      this.currentPage = page
      this.getFiles()
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
    getFiles(){
      findFiles({
        currentPage:this.currentPage,
        pageSize:this.pageSize
      }).then(res => {
        console.log('filevue:');
        console.log(res.records);
        this.tableData = res.records
        this.total = res.total
      }).catch(err => {
        console.log(err);
      })
    },
    // 添加文件请求
    addFile(uploadBody){
      let files = new FormData()
      files.append("mFile",uploadBody.file)
      uploadFile(files).then(res => {
        console.log(res);
        this.getFiles()
        this.$message({
          message:res,
          type:"success"
        })
      }).catch(err => {
        console.log('file.vue=> addFile=> err:')
        this.$message({
          message:err.msg,
          type:"error"
        })
        console.log(err);
      })
    },
    //提交表单
    confirm(){
      if(this.isAdd){
        this.addFile()
      }else{
        this.updateFileItem()
      }
    },
    // 表单修改按钮响应事件
    alterFile(){
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
    // uploadBth(){
    //   this.isAdd = true
    //   this.openFormDialog()
    // },
    //更新表单请求
    updateFileItem(){
      // this.Form.id = this.selection.id
      modifyMenu(this.Form).then(res => {

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
    // 文件正在上传时的过场动画
    uploading(){
      console.log('loading');
      this.$loading({
        lock: true,
        text: '上传中',
        spinner: 'el-icon-upload',
        background: 'rgba(0, 0, 0, 0.7)'
      });
    },
    // switch按钮切换时
    switchChange(value){
      if(value === 0){
        this.isSelect = false
      }else{
        this.isSelect = true
      }
    },
    selectable(row,index){
      return false
      // if(row.enable === 0){
      //   return false
      // }else{
      //   return true
      // }
    },
    //文件预览
    preview(id){
      getPathByUUID(id).then(res => {
        window.open("https://view.officeapps.live.com/op/view.aspx?src="+res)
      }).catch(err => {
        console.log(err);
      })
    },
    download(uuid){
      console.log(uuid);
      downloadFile(uuid).then(res => {
        console.log(res.data);
        const blob = new Blob([res.data], {type: res.headers['content-type']});
        const downloadElement = document.createElement('a');
        const href = window.URL.createObjectURL(blob);
        let contentDisposition = res.headers['content-disposition'];  //从response的headers中获取filename, 后端response.setHeader("Content-disposition", "attachment; filename=xxxx.docx") 设置的文件名;
        let patt = new RegExp("filename=([^;]+\\.[^\\.;]+);*");
        let result = patt.exec(contentDisposition);
        console.log('result');
        console.log(result);
        let filename = decodeURI(result[1]);
        downloadElement.style.display = 'none';
        downloadElement.href = href;
        downloadElement.download = filename ; //下载后文件名
        // document.body.appendChild(downloadElement);
        downloadElement.click(); //点击下载
        // document.body.removeChild(downloadElement); //下载完成移除元素
        window.URL.revokeObjectURL(href); //释放掉blob对象
      }).catch(err => {
        console.log(err);
      })
    },
    del(uuid){
      delFile(uuid).then(res => {
        this.getFiles()
        this.$message({
          message:res.msg,
          type:"success"
        })
      }).catch(err => {
        console.log(err);
      })
    },
    search(searchInput){
      console.log(searchInput);
      findFiles({
        currentPage:this.currentPage,
        pageSize:this.pageSize,
        multiParams:{
          full_name:searchInput,
          type:this.fileType
        }
      }).then(res =>{
        this.tableData = res.records
      }).catch(err => {
        console.log(err);
      })
    }
  },
  created() {
    this.getFiles()
  }
}
</script>

<style scoped>

</style>