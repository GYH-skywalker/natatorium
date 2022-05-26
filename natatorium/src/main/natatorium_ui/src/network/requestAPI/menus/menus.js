import {createAPI} from "network/index";
import {methods} from "utils/Constants";

export const findMenus = () => createAPI('menus/getAll','GET')

export const menuList = data => createAPI('menus/page','POST',data)

export const insertMenu = data => createAPI('menus/addOne',"POST",data)

export const modifyMenu = data => createAPI('menus/modifyOne',"put",data)

export const findRoleMenu = data => createAPI('menus/getMenuByRole/'+data,'get')

export const findMenusTree = () => createAPI('menus/menusTree','get')

export const getAllPmenus = () => createAPI('menus/Pmenus',methods.GET)