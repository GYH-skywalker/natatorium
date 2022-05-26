<template>
  <div class="business">
    <el-row :gutter="15" >
      <el-col :span="4" v-for="(item,index) in business" :key="index" >
        <div @click="processBusiness(item.name,item.label)">
          <el-card :body-style="{ padding: '10px 10px 0px' }" >
            <img :src="item.img" class="image">
            <div style="padding: 14px;">
              <span>{{item.label}}业务</span>
            </div>
          </el-card>
        </div>
      </el-col>
    </el-row>
    <nata-dialog :title="packageTitle"  width="45%" @confirm="addBusiness">
      <el-form
          label-position="left"
          label-width="75px"
          title="会员信息"
          :model="Form">
        <el-form-item label="卡号姓名" >
          <el-input :value="Form.cardNumber" disabled style="width: 48%;float: left"></el-input>
          <el-input :value="Form.userName" disabled style="width: 48%;float: right"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-input :value="Form.userGender == 0 ? '男':'女'" disabled></el-input>
        </el-form-item>
        <el-form-item label="年龄:生日" class="form-margin-left">
          <div>
            <div style="width:50px;display:inline-block;text-align: center;margin-right: 10px;background-color:#ddeaf6;border-radius: 4px">{{getAge(Form.userBirthday)}}</div>
            <el-date-picker
                v-model="Form.userBirthday"
                type="date"
                disabled=""
                placeholder="选择日期">
            </el-date-picker>
          </div>
        </el-form-item>
        <el-form-item label="电话">
          <el-input :value="Form.userPhone" disabled></el-input>
        </el-form-item>
        <el-form-item label="课程教练" v-if="cardType === 'Trc'" class="form-margin-left">
          <el-select v-model="Form.package" placeholder="请选择教练">
            <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="套餐列表" class="form-margin-left">
          <el-select v-model="Form.packageName" placeholder="请选择套餐">
            <el-option
                v-for="(item,index) in packages"
                :key="index"
                :label="item.packageName"
                :value="item.packageName">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="Form.remarks"></el-input>
        </el-form-item>
      </el-form>
    </nata-dialog>
  </div>
</template>

<script>
import NataDialog from "components/nataDialog";
import {getOne,getCardPackages,addOneData} from "network/requestAPI/commonAPI/general";
export default {
  name: "businessFor",
  components: {NataDialog},
  data(){
    return {
      business:[
        {name:'tc',label:"次卡",img:"https://s2.loli.net/2022/04/23/7ulRWpwUYSLCnId.png"},
        {name:'tc',label:"月卡",img:"https://s2.loli.net/2022/04/23/sibMStCcXqnpfYF.png"},
        {name:'tc',label:"年卡",img:"https://s2.loli.net/2022/04/23/pMGPTm4LobKtrWF.png"},
        {name:'rcc',label:"充值",img:"https://s2.loli.net/2022/04/23/GP8jytwUeOkWu7d.png"},
        {name:'oc',label:"业主卡",img:"https://s2.loli.net/2022/04/23/jKWenw6vtzpZbEa.png"},
        {name:'Trc',label:"培训卡",img:"https://s2.loli.net/2022/04/23/JlASaWGwYqMmT1o.png"}
      ],
      userCardNumber:'',
      packageTitle:'',
      cardType:'',
      urlPrefix:'',
      packages:[],
      Form:{}
    }
  },
  computed: {
    getAge() {
      return function (birthday) {
        if (birthday !== null || birthday !== undefined) {
          const currentYear = new Date().getFullYear()
          const userBirthday = new Date(birthday).getFullYear()
          return currentYear - userBirthday
        } else {
          return new Number(0)
        }
      }
    },
  },
  methods:{
    openFormDialog(){
      this.$store.commit("changeVisibleState",true)
    },
    addBusiness(){
      delete this.Form.id
      console.log(this.Form)
      addOneData("bl",this.Form).then(res => {
        this.$message({
          message:res.msg,
          type:"success"
        })
      }).catch(err => console.log(err))
    },
    processBusiness(name,label){
      let that = this
      this.$prompt('请输入会员卡号', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        //inputPattern: /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
        //inputErrorMessage: '卡号不正确'
      }).then(({ value }) => {
        that.$axios.all([
            getOne("/bmi",{params:{"card_number": value}}),
            getCardPackages(name)])
            .then(that.$axios.spread((memberInfo,packages)=>{
              that.Form = memberInfo
              that.packages = packages.filter(item => {
                if(name == 'tc'){
                  if(item.cardType == label){
                    return item.packageName
                  }
                }else{
                  return item.packageName
                }
              })
              that.openFormDialog()
              that.packageTitle = label
              that.cardType = name
            })).catch(that.$axios.spread((err1,err2)=>{
              console.log(err1)
              console.log(err2)
        }))
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.image {
  width: 100%;
  display: block;
}
.form-margin-left{
  :nth-child(2){
    margin-left: 0px !important;
  }
  div:first-of-type{
    margin-left: 0px !important;
    display: flex;
  }
}
.form-flex{
  display: flex;
}
</style>