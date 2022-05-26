import Axios from "axios";
import {methods} from "../utils/Constants";
import Vue from "vue";
import {Message} from "element-ui";

Vue.prototype.$axios=Axios;

const instance = Axios.create({
    // baseURL: 'http://124.220.159.87:9090/',
    baseURL:'http://localhost:9090/',
    timeout: 5000,
    headers:{
        "Access-Control-Allow-Origin":'*'
    }
})

const whiteList = ['/user/login','user/register']

/**
 * 请求拦截
 * */
instance.interceptors.request.use(config =>{
    const token = localStorage.getItem("token")
    config.headers["platform"] = "pc"
    if(token !== null){
        config.headers["token"] = token;
        return config;
    }else{
        if(!whiteList.includes(config.url)){
            return config
        }else{
            return Promise.reject("当前未登录");
        }

    }
},error => {
    this.$message({
        message:error.msg,
        type:"error"
    })
    console.log(error);
    return error;
})
/**
 * 响应拦截
 * */
let _this = this
instance.interceptors.response.use(res => {
    console.log('res:');
    console.log(res);
    console.log('---------------');
    if(res.data.code == null || res.data.code == undefined){
        console.log('index-res');
        return res
    }else if(res.data.code != "200" || res.data.code != 200){
        console.log('index-null');
        console.log(res.data.code);
        Message({
            message: res.data.msg,
            type: "error"
        })
        return Promise.reject(res)
    }else {
        console.log('index-res.data.data');
        if(res.data.data == null){
            return res.data
        }else{
            return res.data.data;
        }
    }
},err => {
    console.log('error');
    console.log(err);
    Message({
        message:err,
        type:"error"
    })
    //通过promise将err返回到catch中，直接返回的话，会到then中而不会到catch中
    return new Promise.reject(err);
})


export const createAPI = (url,method,data) => {
    const config = {}
    if(method == 'get' || method == 'GET'){
        config.params = data
    }else {
        config.data = data
    }
    return instance({
        url,
        method,
        ...config
    })
}

export const createFileApi = (url,method,data) => {
    const config = {}
    config.data = data
    config.headers = {
        'Cache-Control': 'no-cache',
        'Content-Type': 'application/x-www-form-urlencoded'
    }
    config.responseType = 'blob'
    config.transformRequest = [
        function (data) {
            let ret = ''
            for (const it in data) {
                ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
            }
            return ret
        }
    ]
    return instance({
        url,
        method,
        ...config
    })
}