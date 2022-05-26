import {createAPI} from "network/index";
import {methods} from "utils/Constants";
import {createFileApi} from "../../index";

export const uploadFile = data => createAPI("files/upload",methods.POST,data)

export const findFiles = data => createAPI("files/page",methods.POST,data)

export const delFile = data => createAPI("files/delFile/"+data,methods.DELETE)

export const modifyFile = (data) => createAPI("files/",methods.DELETE,data)

export const getPathById = data => createAPI("fileIdentity/getById/"+data,methods.GET)

export const getPathByUUID = data => createAPI("files/filePath/"+data,methods.GET)

export const downloadFile = data => createFileApi("files/download/"+data,methods.GET)