module.exports = {
    lintOnSave: false,
    configureWebpack: {
        resolve: {
            alias: {//配置路径别名//在vue中@默认代表src
                //配置后我们调用assets目录下的某个文件，可以直接通过'assets/该文件名'
                'assets': '@/assets',
                'components': '@/components',
                'network': '@/network',
                'views': '@/views',
                'layout': '@/layout',
                'store': '@/store',
                'router': '@/router',
                'utils':'@/utils'
            }
        }
    },
    css:{
        loaderOptions:{
            sass:{
                prependData:`@import"~assets/variable.scss";`
            }
        }
    }
}