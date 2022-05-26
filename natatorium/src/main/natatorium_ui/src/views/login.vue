<template>
<div class="login">
  <span class="rippleFilter">
    <svg>
      <filter id="turbulence" x="0" y="0" width="100%" height="100%">
        <feTurbulence id="sea-filter" numOctaves="5" seed="2" baseFrequency="0.02 0.05"></feTurbulence>
        <feDisplacementMap scale="20" in="SourceGraphic"></feDisplacementMap>
        <animate xlink:href="#sea-filter" attributeName="baseFrequency" dur="60"
                 keyTimes="0;0.5;1" values="0.02 0.06;0.04 0.08;0.02 0.06" repeatCount="indefinite"></animate>
      </filter>
    </svg>
  </span>
  <div class="loginForm">
    <span class="drops">
      <span class="drop"></span>
      <span class="drop"></span>
      <span class="drop"></span>
      <span class="drop"></span>
    </span>
    <el-form
        :model="Form"
        status-icon
        :rules="rules"
        ref="ruleForm"
        label-width="60px"
        label-position="right"
        class="demo-ruleForm">
      <el-form-item label="用户名" prop="checkPass">
        <el-input type="text" v-model="Form.userName" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item  label="密码" prop="pass">
        <el-input type="password" v-model="Form.password" autocomplete="off"></el-input>
      </el-form-item>
<!--      <el-form-item label="验证码" prop="age">-->
<!--        <el-input v-model.number="Form.age"></el-input>-->
<!--      </el-form-item>-->
      <div class="btn">
<!--        <el-button type="warning" plain @click="resetForm">注册</el-button>-->
        <el-button type="primary" @click="signIn" class="loginBtn">登录</el-button>
      </div>
    </el-form>
  </div>
</div>
</template>

<script>
import {login} from "../network/requestAPI/login/login";
import {setDynamicRoutes} from "../router";

export default {
  name: "login",
  data(){
    return{
      Form:{},
      rules:[]
    }
  },
  methods:{
    resetForm(){
      console.log("kjk");
    },
    signIn(){
      login(this.Form).then(res => {
        this.$store.commit("setMenus",res.menus)
        this.$store.dispatch("generateRoutes", res.menus)
        setDynamicRoutes()
        localStorage.setItem("token",res.token)
        localStorage.setItem("menus",JSON.stringify(res.menus))
        this.$router.push("/index")
      }).catch(err => {
        console.log(err);
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.login{
  width: 100vw;
  height: 100vh;
  background: linear-gradient(45deg,#4cb4e7,#ffc09f);
  display: flex;
  justify-content: center;
  align-items: center;
  .rippleFilter{
    z-index: 999;
    position: absolute;
    width: 100vw;
    height: 100vh;
    //border: 1px solid lightgreen;
    //border-radius: 0% 100% 100% 0% / 100% 100% 0% 0% ;
    background-image: url("~assets/bgwater.png");
    opacity: 0.6;
    filter: url("#turbulence");
  }
  .loginForm{
    z-index: 1000;
    width: 25vw;
    height: 20vw;
    min-width: 400px;
    min-height: 350px;
    margin-top: -100px;
    //border: 1px solid white;
    border-radius: 5px;
    padding: 10px 8px;
    display: flex;
    justify-content: center;
    //background-color: #fff;
    backdrop-filter: blur(2px);
    align-items: center;
    box-shadow: 0px 0px 7px 2px rgba(255,255,255,0.3);
    .drops{
      position: relative;
      top: -150px;
      right: -280px;
      background-color: red;
      .drop{
        content: '';
        width: 40px;
        height: 40px;
        //background-color: #fff;
        border-radius: 37% 63% 62% 38% / 74% 56% 44% 26% ;
        position: absolute;
        top: -15px;
        box-shadow: inset 10px 10px 10px rgba(0,0,0,0.05),
        4px 10px 10px rgba(0,0,0,0.1),
        4px 8px 20px rgba(0,0,0,0.05),
        inset -3px -3px 9px rgba(255,255,255,0.9);
        background: transparent;
        &::before{
          content:'';
          width: 8px;
          height: 5px;
          background-color: rgba(255,255,255,.7);
          position: absolute;
          left: 10px;
          top: 10px;
          border-radius: 79% 21% 89% 11% / 69% 8% 92% 31% ;
        }
        &::after{
          content:'';
          width: 4px;
          height: 3px;
          background-color: rgba(255, 255, 255, 0.7);
          position: absolute;
          left: 20px;
          top: 7px;
          border-radius: 46% 54% 51% 49% / 54% 56% 44% 46% ;
        }
        &:nth-of-type(2){
          transform: scale(0.6) translate(60px,50px);
          border-radius: 39% 61% 41% 59% / 51% 49% 51% 49% ;
          &:after{
            background-color: transparent;
          }
        }
        &:nth-of-type(3){
          transform: scale(0.7) translate(-55px,55px);
          border-radius: 72% 28% 78% 22% / 64% 49% 51% 36% ;
          &::before{
            width: 5px;
            height: 5px;
            left: 13px;
            border-radius: 41% 59% 50% 50% / 55% 50% 50% 45% ;
          }
        }
        &:nth-of-type(4){
          transform: scale(0.3) translate(-80px,-40px);
          &::before{
            content: none;
          }
        }
      }
    }

    .el-form{
      margin-top: 10px;
      width: 100%;
      .el-form-item{
        display: flex;
        justify-content: space-evenly;
        :nth-child(2){
          margin-left: 0px !important;
        }
        .el-input{
          width: 100%;
          :first-child{
            background-color: rgba(255,255,255,0.4);
            color: black;
            //opacity: 0.4;
          }
        }
      }
      .btn{
        margin-top: 50px;
        display: flex;
        justify-content: space-evenly;
        &::v-deep .loginBtn{
          //background-color: red;
          background: transparent;
          border-radius: 22% 78% 46% 54% / 56% 52% 48% 44%;
          border: none;
          box-shadow: inset 10px 10px 10px rgba(0,0,0,0.05),
          4px 10px 10px rgba(0,0,0,0.1),
          4px 8px 20px rgba(0,0,0,0.05),
          inset -3px -3px 9px rgba(255,255,255,0.9);

        }
        &::v-deep .loginBtn:hover{
          background-color: rgba(173,216,230,0.8);
        }
      }
    }
  }
}
</style>