export default {
    isVisible(state){
        return state.dialogVisible
    },
    getMenus(state){
        return state.menus
    },
    getRoutes(state){
        return state.DynamicRoutes
    }
}