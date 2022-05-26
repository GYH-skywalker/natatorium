import {createAPI} from "network/index";

export const pageBl = data => createAPI("bl/page","post",data)