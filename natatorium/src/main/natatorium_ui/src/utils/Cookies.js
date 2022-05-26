export default {

}

//设置cookie
function setCookies(key,value,exdays){
    let d = new Date();
   //设置过期天数
    d.setTime(d.getTime()+(exdays*24*60*60*1000));
    //toGMTString 转为格林威治时间
    let expires = "expires="+d.toGMTString();
    //通过dom操作原生cookie
    document.cookie = key + "=" + value + "; " + expires;
}

//获取cookie
function getCookies(key){
    // 获取cookie字符串，按照；分割
    let tmp = document.cookie.split(';')
    //  遍历 tmp
    tmp.forEach(function (item) {
        // 再按照=进行分割  得到的数组有两个元素，t[0]为key，t[1]为value、
        let t = item.split('=')
        // 判断key是否相等，相等直接返回。
        if (t[0] == key) {
            return t[1];
        }
    })
    //走到这说明没有key，直接返回空字符串。
    return null
}