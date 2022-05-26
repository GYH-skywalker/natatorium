import {createAPI} from "network/index";
import {methods} from "utils/Constants";


export const getUserByPage = data => createAPI("userRole/page",methods.POST,data)

export const addUser = data => createAPI("user/addUser",methods.POST,data)

export const restorePwd = data => createAPI("user/restorePwd/"+data,methods.PUT)