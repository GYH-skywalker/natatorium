import http from "./index"
// login
export const login = data => http.post('consumer/login',data)

/**
 * QR 
 **/
//getqr
export const getQR = () => http.get('QR/getQR')

//scanQR

export const scanQR = data => http.post("QR/scan",data)


//course
export const coursePage = data => http.post("course/page",data)

//getCoverPath
export const coverPath = data => http.get("files/filePath/"+data)


//reservation预约课程
export const reserve = data => http.post("UCourse/reservation/"+data)


//getMyCourse
export const getMyCourse = data => http.post("course/myCourse",data)


//getNotice
export const getNotice = () => http.get("notify/latestNotice")

//getBanner
export const getBanner = () => http.get("banner/getList")