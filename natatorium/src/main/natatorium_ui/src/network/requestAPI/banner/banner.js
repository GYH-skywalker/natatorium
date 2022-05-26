import {createAPI} from "network/index";
import {methods} from "utils/Constants";

export const addBanner = data => createAPI("banner/addBanner",methods.POST,data)

export const delBanner = data => createAPI("banner/delBanner/"+data,methods.DELETE)
