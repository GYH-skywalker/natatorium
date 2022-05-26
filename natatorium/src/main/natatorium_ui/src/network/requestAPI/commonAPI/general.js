import {createAPI} from "network/index";
import {methods} from "utils/Constants";

const pageSuffix = "/page"
const addOne = "/addOne"
const updateOne = "/modifyOne"
const delOne = "/delOne/"
const isEnable = "/isEnable"
const getList = "/getList"
const one = "/getOne"
const cardPackages = "/cardPackages"
const byId = "/getById/"

export const getByPage = (urlPrefix,data) => createAPI(urlPrefix+pageSuffix,methods.POST,data)

export const addOneData = (urlPrefix,data) => createAPI(urlPrefix+addOne,methods.POST,data)

export const updateOneData = (urlPrefix,data) => createAPI(urlPrefix+updateOne,methods.PUT,data)

export const delOneData = (urlPrefix,data) => createAPI(urlPrefix+delOne+data,methods.DELETE)

export const updateState = (urlPrefix,data) => createAPI(urlPrefix+isEnable,methods.GET,data)

export const getOne = (urlPrefix,data) => createAPI(urlPrefix+one,methods.POST,data)

export const getById = (urlPrefix,data) => createAPI(urlPrefix+byId+data,methods.GET)

export const getAll = urlPrefix=> createAPI(urlPrefix+getList,methods.GET)

//查询套餐专用
export const getCardPackages = (urlPrefix) => createAPI(urlPrefix+cardPackages,methods.GET)