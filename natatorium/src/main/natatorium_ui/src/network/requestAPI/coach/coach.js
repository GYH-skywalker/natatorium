import {createAPI} from "network/index";
import {methods} from "utils/Constants";

export const getCoachName = () => createAPI("coach/coachName",methods.GET)