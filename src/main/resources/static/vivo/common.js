/**
 * 加载界面 激活菜单
 * @param pageKey 链接
 */
function  acvtiveMenu(pageKey) {

    console.log('pageKey='+pageKey);

    if("index" == pageKey){
        $("li[pagekey='"+pagekey+"']").addClass("active") ;
    }else {

        var $obj = $("li[pagekey='"+pagekey+"']") ;
        $obj.addClass("active") ;
        $obj.parent("ul").parent("li").addClass("active open")

        // console.log($obj.html());

    }
}