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
<!--        <el-table-column-->
<!--            fixed="right"-->
<!--            label="操作"-->
<!--            width="80">-->
<!--          <template slot-scope="scope">-->
<!--            <el-button @click="viewDetail(scope.row)" size="mini" :disabled="scope.row.enable === 0">查看详情</el-button>-->
<!--          </template>-->
<!--        </el-table-column>-->
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
    <nata-dialog @confirm="confirm" title="添加课程信息" width="45%">
      <el-form
          label-position="left"
          label-width="75px"
          title="课程信息"
          :model="Form">
        <el-form-item class="upload-form-item">
          <el-upload
              action="#"
              list-type="picture-card"
              :multiple="false"
              :limit="1"
              ref="uploadCover"
              :http-request="addFile">
            <i slot="default" class="el-icon-plus"></i>
            <div class="img-list" slot="file" slot-scope="{file}">
              <img
                  class="el-upload-list__item-thumbnail"
                  :src="getThumbnail(file.url)" alt="">
              <span class="el-upload-list__item-actions">
                <span
                    class="el-upload-list__item-preview"
                    @click="handlePictureCardPreview(file)">
                  <i class="el-icon-zoom-in"></i>
                </span>
                <span
                    v-if="!disabled"
                    class="el-upload-list__item-delete"
                    @click="handleRemove(Form.coverUuid)">
                  <i class="el-icon-delete"></i>
                </span>
              </span>
            </div>
          </el-upload>
          <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="">
          </el-dialog>
        </el-form-item>
        <el-form-item label="课程名称" >
          <el-input v-model="Form.courseName" style="width: 48%;float: left"></el-input>
        </el-form-item>
        <el-form-item label="课程教练" class="form-margin-left">
          <el-select v-model="Form.coach" placeholder="请选择教练">
            <el-option
                v-for="item in coach"
                :key="item.id"
                :label="item.coachName"
                :value="item.coachName">
            </el-option>
          </el-select>
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
        <el-form-item label="时间期限">
          <div class="block">
            <el-time-picker
                is-range
                v-model="timeRange"
                range-separator="~"
                format="HH:mm:ss"
                value-format="HH:mm:ss"
                @blur="showTime(timeRange)"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                placeholder="选择时间范围">
            </el-time-picker>
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
  updateOneData
} from "network/requestAPI/commonAPI/general";
import {delFile, uploadFile} from "network/requestAPI/files/files";
import {addCourse,delCourse} from "network/requestAPI/course/course";
import {getCoachName} from "../../network/requestAPI/coach/coach";

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
        {prop:'courseName',label:'课程名称',width:80},
        {prop:'coach',label:'教练名称',width:80},
        {prop:'startDate',label:'开始日期',width:120},
        {prop:'endDate',label:'截至日期',width:120},
        {prop:'classBegins',label:'上课时间',width:80},
        {prop:'classEnds',label:'下课时间',width:null},
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
      urlPrefix:"course",
      // 查询条件
      sCondition:{
        cardNumber:null,
        cardUserPhone:null
      },
      //用于比对修改前后的数据是否有变化
      updateRow:{},
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
      dateRange:[],
      timeRange:[],
      dialogImageUrl: '',
      dialogVisible: false,
      coach:[]
    }
  },
  computed:{
    getThumbnail(){
      return function (url){
        return url
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
      this.coachName()
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
      this.timeRange = null
      this.dateRange = null;
      // this.getThumbnail('')
      this.isAdd = true
      this.openFormDialog()
    },
    edit(row){
      this.updateRow = row
      // 深复制从而让弹窗中的数据修改时而不影响表格中对应的数据
      this.Form = JSON.parse(JSON.stringify(row))
      this.dateRange[0] = this.Form.startDate
      this.dateRange[1] = this.Form.endDate
      this.timeRange[0] = this.Form.classBegins
      this.timeRange[1] = this.Form.classEnds
      console.log(this.Form)
      this.isAdd = false
      this.openFormDialog()
    },
    viewDetail(id){
    },
    addData(){
      this.Form.startDate = this.dateRange[0]
      this.Form.endDate = this.dateRange[1]
      this.Form.classBegins = this.timeRange[0]
      this.Form.classEnds = this.timeRange[1]
      addCourse(this.urlPrefix,this.Form).then(res => {
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
      delCourse(this.urlPrefix,id).then(res => {
        this.$message({
          message:res.msg,
          type:"success"
        })
        this.getTableData()
        this.$refs.uploadCover.clearFiles();
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

    },
    coachName(){
      getCoachName().then(res => {
        this.coach = res
        console.log(this.coach)
      }).catch(err => {
        console.log(err)
      })
    },
    // 添加文件请求
    addFile(uploadBody){
      let files = new FormData()
      files.append("mFile",uploadBody.file)
      uploadFile(files).then(res => {
        console.log(res);
        this.Form.courseCover = res.fileUrl
        this.Form.coverUuid = res.uuid
        this.$message({
          message:"上传成功",
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
    handleRemove(uuid) {
        delFile(uuid).then(res => {
          this.getThumbnail('')
          this.$refs.uploadCover.clearFiles();
          this.$message({
            message:res.msg,
            type:"success"
          })
        }).catch(err => {
          console.log(err);
        })
      console.log(file, fileList);
    },
    handlePictureCardPreview(file) {
      console.log(file)
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    showTime(time){
      console.log(time)
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
.block{
  width: 100%;
  float: left;
  display: flex;
}

</style>

<style lang="scss">
.el-upload{
  width: 100px;
  height: 100px;
  line-height: 100px;
}
.el-upload-list__item{
  //width: 100px !important;
  height: 100px !important;
  border:none;
  margin-left: 10px !important;
  margin-bottom: 0px !important;
}
.upload-form-item{
  .el-form-item__content{
    line-height: 0px;
    div:first-child{
      display: flex;
      flex-flow: row-reverse;
      justify-content: left;
    }
  }
}
</style>