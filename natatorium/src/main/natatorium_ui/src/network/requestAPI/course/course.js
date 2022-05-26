import {createAPI} from "network/index";
import {methods} from "utils/Constants";

export const addCourse = (prefix,data) => createAPI(prefix+"/addCourse",methods.POST,data)


export const delCourse = (prefix,data) => createAPI(prefix+"/delCourse/"+data,methods.DELETE)