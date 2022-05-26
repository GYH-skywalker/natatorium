<template>
<div>
  <el-card class="box-card">

    <el-avatar shape="square" :size="100" src="#" style="background-color: rgba(255,255,255,0)">
      <img :src="Form.qrCode" />
    </el-avatar>
    <div class="infoBody">
      <el-descriptions class="margin-top" label-class-name="label-class"  title="门店信息" :column="1" border>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-tickets"></i>
            门店名称
          </template>
          <el-tag size="small">{{ Form.shopPrincipal }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            门店负责人
          </template>
          {{ Form.shopPrincipal }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-mobile-phone"></i>
            负责人电话
          </template>
          {{ Form.principalPhone }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-mobile-phone"></i>
            门店电话
          </template>
          {{ Form.shopPhone}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-location-outline"></i>
            地址
          </template>
          {{ Form.address}}
        </el-descriptions-item>
<!--        <el-descriptions-item>-->
<!--          <template slot="label">-->
<!--            <i class="el-icon-office-building"></i>-->
<!--            联系地址-->
<!--          </template>-->
<!--          江苏省苏州市吴中区吴中大道 1188 号-->
<!--        </el-descriptions-item>-->
      </el-descriptions>
    </div>
    <div class="bottom clearfix">
      <el-button type="primary" class="button" @click="openFormDialog">操作按钮</el-button>
    </div>
  </el-card>
  <nata-dialog @confirm="confirm" width="35%" title="店铺信息">
    <el-form label-position="right" label-width="90px" :model="Form">
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
                    @click="handleRemove(Form.qrUuid)">
                  <i class="el-icon-delete"></i>
                </span>
              </span>
          </div>
        </el-upload>
        <el-dialog :visible.sync="dialogVisible">
          <img width="100%" :src="dialogImageUrl" alt="">
        </el-dialog>
      </el-form-item>
      <el-form-item label="门店名称:">
        <el-input v-model="Form.shopName"></el-input>
      </el-form-item>
      <el-form-item label="门店负责人:">
        <el-input v-model="Form.shopPrincipal"></el-input>
      </el-form-item>
      <el-form-item label="负责人电话:">
        <el-input v-model="Form.principalPhone"></el-input>
      </el-form-item>
      <el-form-item label="门店电话:">
        <el-input v-model="Form.shopPhone"></el-input>
      </el-form-item>
      <el-form-item label="地址:">
        <el-input v-model="Form.address"></el-input>
      </el-form-item>
    </el-form>
  </nata-dialog>
</div>
</template>

<script>
import NataDialog from "components/nataDialog";
import {uploadFile} from "network/requestAPI/files/files";
import {addOneData} from "network/requestAPI/commonAPI/general";
import {getById} from "../../network/requestAPI/commonAPI/general";
import {delFile} from "../../network/requestAPI/files/files";
export default {
  name: "shopInfo",
  components: {NataDialog},
  data(){
    return {
      shopInfo:{},
      Form:{},
      dialogImageUrl: '',
      dialogVisible: false,
      urlPrefix: 'si'
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
    openFormDialog(){
      this.$store.commit("changeVisibleState",true)
    },
    addFile(uploadBody){
      let files = new FormData()
      files.append("mFile",uploadBody.file)
      uploadFile(files).then(res => {
        console.log(res);
        this.Form.qrCode = res.fileUrl
        this.Form.qrUuid = res.uuid
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
    getInfo(){
      getById(this.urlPrefix,"2").then(res => {
        this.Form = res
      }).catch(err => {
        console.log(err)
      })
    },
    confirm(){
      delete this.Form.id
      addOneData(this.urlPrefix,this.Form).then(res => {
        this.$message({
          message:res.msg,
          type:"success"
        })
        this.getInfo()
      }).catch(err => {
        console.log(err)
      })
    }
  },
  created() {
    this.getInfo()
  }
}
</script>

<style lang="scss" scoped>
.box-card{
  //justify-content: left;
  //align-items: start;
  .infoBody{
    width: 100%;
    /deep/ .label-class{
      width: 15em !important;
    }
  }
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  /*padding: 0;*/
  float: right;
  margin-bottom: 1em;
}

.image {
  width: 100%;
  display: block;
}

::v-deep .el-upload{
  width: 100px;
  height: 100px;
  line-height: 100px;
}
::v-deep .el-upload-list__item{
  //width: 100px !important;
  height: 100px !important;
  border:none;
  margin-left: 10px !important;
  margin-bottom: 0px !important;
}
::v-deep .upload-form-item{
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