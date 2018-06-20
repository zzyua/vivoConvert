/**
 * 加载界面 激活菜单
 * @param pageKey 链接
 */
function  acvtiveMenu(pageKey) {

    // console.log('pageKey='+pageKey);

    if(pageKey == null || "home" == pageKey){
        $("li[pagekey='home']").addClass("active") ;
    }else {

        var $obj = $("li[pagekey='"+pagekey+"']") ;
        $obj.addClass("active") ;
        $obj.parent("ul").parent("li").addClass("active open")

        // console.log($obj.html());

    }
}

function initNestable() {
    $("#sysUserId").attr("data-deptid",initialDeptId) ;
    $("#nestable").find("div[class='dd-handle']").each(function (i,div) {
        if(i == 0 ){
            $(this).addClass("btn-yellow no-hover");
        }
    })
}

function setNestableClass(obj) {
    $("#nestable").find("div[class='dd-handle btn-yellow no-hover']").each(function (i,div) {
        $(this).removeClass("btn-yellow no-hover");
    });
    $(obj).addClass("btn-yellow no-hover");
}


//初始化chosen 插件
function initChosen() {
    $('.chosen-select').chosen({allow_single_deselect:true});

    $(window)
        .off('resize.chosen')
        .on('resize.chosen', function() {
            $('.chosen-select').each(function() {
                var $this = $(this);
                $this.next().css({'width': '200px'});
            })
        }).trigger('resize.chosen');

    $(document).on('settings.ace.chosen', function(e, event_name, event_val) {
        if(event_name != 'sidebar_collapsed') return;
        $('.chosen-select').each(function() {
            var $this = $(this);
            $this.next().css({'width': $this.parent().width()});
        })
    });
}

// 展示提示信息
function showMessage(title, msg, isSuccess) {
    if (!isSuccess) {
        msg = msg || '';
    } else {
        msg = msg || '操作成功'
    }
    $.gritter.add({
        title: title,
        text: msg != '' ? msg : "服务器处理异常, 建议刷新页面来保证数据是最新的",
        time: '',
        class_name: (isSuccess ? 'gritter-success' : 'gritter-warning') + (!$('#gritter-light').get(0).checked ? ' gritter-light' : '')
    });
}