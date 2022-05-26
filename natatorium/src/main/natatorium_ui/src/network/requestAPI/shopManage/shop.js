import {createAPI} from "network/index";
import {methods} from "utils/Constants";

export const statistic = data => createAPI("bcl/statistic",methods.GET,data)