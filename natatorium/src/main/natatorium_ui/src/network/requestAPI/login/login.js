import {createAPI} from "network/index";
import {methods} from "utils/Constants";


export const login = data => createAPI("user/login",methods.POST,data)

