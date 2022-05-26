import {createAPI} from "network/index";
import {methods} from "../../../utils/Constants";

export const publicNotice = data => createAPI("notify/addNotice",methods.POST,data)


// export const delNotice = data => createAPI("notify/delNotice/"+data,methods.DELETE)