import {createAPI} from "network/index";
import {methods} from "utils/Constants";

export const getRole = (data)=>createAPI("role/page",methods.POST,data)

export const getRoleMenuMap = data => createAPI('roleMenus/getRoleMenuMap/'+data,methods.GET)

export const alterRoleMenuMap = (id,data) => createAPI('roleMenus/assignMenu/'+id,methods.POST,data)

export const insertRole = data => createAPI('role/addOne',methods.POST,data)

export const getAllRole = () => createAPI("role/allRole",methods.GET)

export const getOneRole = data => createAPI('role/getById/'+ data,methods.GET)