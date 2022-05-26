export default {
    changeVisibleState(state,visibleState){
        state.dialogVisible = visibleState
    },
    setMenus(state,menuList){
        state.menus = menuList
    },
    setDynamicRoutes(state,routes){
        state.DynamicRoutes = routes
    }
}