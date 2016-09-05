$(function () {

    //menu
    $('#side-menu').find("li").each(function () {
        var menu_a = $(this).find("a").eq(0);
        var page_title = $("#page-wrapper .page-header").text();
        if (menu_a.text() == page_title) {
            menu_a.addClass("active");
            var ul = $(this).parent("ul .nav-second-level")
            if (ul.length > 0) {
                ul.addClass("in")
                ul.parent("li").addClass("active")
            }
        }
    })

    //小提示框
    $('.img_tooltip').tooltip({
        selector: "[data-toggle=tooltip]",
        container: "body"
    })

    $.fn.dataTableExt.sErrMode = 'throw';

})


var $leoman = {
    v: {
        ajaxOption: {method: 'POST', dataType: 'json', async: true},
        notifyMethod: null,
        dataTableL: {
            "fnDrawCallback": function () {

            },
            "oLanguage": {
                "sLengthMenu": "每页显示 _MENU_条",
                "sZeroRecords": "没有找到符合条件的数据",
                "sProcessing": "正在处理...",
                "sInfo": "当前第 _START_ - _END_ 条　共计 _TOTAL_ 条",
                "sInfoEmpty": "木有记录",
                "sInfoFiltered": "(从 _MAX_ 条记录中过滤)",
                "sSearch": "搜索：",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "前一页",
                    "sNext": "后一页",
                    "sLast": "尾页"
                }
            },
        }
    },
    notify: function (msg, status) {
        var option = {
            position: "top center",
            autoHideDelay: 2000,
            className: status,
            arrowSize: 10
        }
        $.notify(msg, option);
    },
    optNotify: function (method, title, button) {

        if ($("#notifyjs-foo-alert-option").length > 0) {
            return false;
        }

        if (title === undefined) {
            title = '确认删除么？删除后不可恢复！';
        }
        if (button === undefined) {
            button = '删除';
        }

        $.notify({
            title: title,
            button: button
        }, {
            style: 'foo',
            autoHide: false,
            clickToHide: false,
            position: "top center"
        });
        if (method != undefined) {
            $leoman.v.notifyMethod = method;
        }
    },
    uiform: function () {
        jQuery('tbody input:checkbox').click(function () {
            if (jQuery(this).is(':checked')) {
                jQuery(this).parent().addClass('checked');
                jQuery(this).parents('tr').addClass('warning');
            } else {
                jQuery(this).parent().removeClass('checked');
                jQuery(this).parents('tr').removeClass('warning');
            }
        });
    },
    cutText: function (sub, length, less) {
        var str = "";
        if (sub && sub.length > length) {
            str = sub.substr(0, length);
            if (less) {
                str = str + less;
                str = '<p title="' + sub + '">' + str + '</p>';
            }
        } else {
            str = sub;
        }

        return str;
    },
    checkAll: function (obj) {
        var parentTable = jQuery(obj).parents('table');
        var ch = parentTable.find('tbody input[type=checkbox]');
        if (jQuery(obj).is(':checked')) {
            ch.each(function () {
                jQuery(this).prop('checked', true);
                jQuery(this).parent().addClass('checked');
                jQuery(this).parents('tr').addClass('warning');
            });
        } else {
            ch.each(function () {
                jQuery(this).removeAttr('checked')
                jQuery(this).parent().removeClass('checked');
                jQuery(this).parents('tr').removeClass('warning');
            });
        }
    },

    //子复选框的事件
    subSelect: function (obj) {
        var parentTable = jQuery(obj).parents('table');
        //当没有选中某个子复选框时，SelectAll取消选中
        if (!$(this).checked) {
            $(".list-parent-check").attr("checked", false);
        }
        var chsub      =  parentTable.find('tbody input[type=checkbox]').length; //获取subcheck的个数
        var checkedsub = parentTable.find('tbody input[type=checkbox]:checked').length; //获取选中的subcheck的个数
        if (checkedsub == chsub) {
            $(".list-parent-check").attr("checked", true);
        }
    },

    ajax: function (url, data, callbackFun, option) {
        if (option == null || option == undefined) {
            option = $leoman.v.ajaxOption;
        } else {
            if (option.method == null || option.method == undefined) {
                option.method = $leoman.v.ajaxOption.method;
            }
            if (option.dataType == null || option.dataType == undefined) {
                option.dataType = $leoman.v.ajaxOption.dataType;
            }
            if (option.async == null || option.async == undefined) {
                option.async = $leoman.v.ajaxOption.async;
            }
        }
        jQuery.ajax({
            dataType: option.dataType,
            url: url,
            data: data,
            async: option.async,
            success: function (data) {
                callbackFun(data);
            },
            statusCode: {
                401: function () {
                },
                403: function () {
                },
                500: function () {
                }
            }
        });
    },
    turnArray: function (arr) {
        var newArr = [];
        for (var i = arr.length - 1; i >= 0; i--) {
            newArr.push(arr[i])
        }
        return newArr;
    },
    /**
     * 清理表单参数
     * @param form
     * @param option boolean类型，为true清理select插件
     */
    clearForm: function (form) {
        form.find("textarea").val("");
        form.find("input").val("");
        form.find("input[type=checkbox]").removeAttr("checked");
        form.validator("cleanUp");
    },
    dataTable: function (obj, option) {
        return obj.DataTable($.extend($leoman.v.dataTableL, option))
    },
    // 根据类型获取性别
    getSex: function (type) {
        if (type == 0) {
            return "";
        }
        if (type == 1) {
            return "男";
        }
        if (type == 2) {
            return "女";
        }
        return "";
    },
    // 根据类型获取性别
    getUserType: function (type) {
        if (type == 0) {
            return "业主";
        }
        if (type == 1) {
            return "家属";
        }
        if (type == 2) {
            return "租客";
        }
        return "";
    }

}


//$.notify.addStyle('foo', {
//    html: "<div id='notifyjs-foo-alert-option'>" +
//    "<div class='clearfix'>" +
//    "<div class='title' data-notify-html='title'/>" +
//    "<div class='buttons'>" +
//    "<button class='no'>取消</button>" +
//    "<button class='yes' data-notify-text='button'></button>" +
//    "</div>" +
//    "</div>" +
//    "</div>"
//});

$(document).on('click', '.notifyjs-foo-base .no', function () {
    $(this).trigger('notify-hide');
});
$(document).on('click', '.notifyjs-foo-base .yes', function () {
    if ($leoman.v.notifyMethod != null) {
        eval("$leoman.v.notifyMethod()");
    }
    $(this).trigger('notify-hide');
});


;
(function ($) {
    $.fn.getInputId = function (sigle) {
        var checkIds = [];
        $(this).each(function () {
            checkIds.push($(this).val())
        });
        if (sigle) {
            if (checkIds.length > 1) {
                alert("只能选择一条记录！");
                return false;
            }
            else if (checkIds.length == 0) {
                alert("请选择一条记录操作！");
                return false;
            }
            else {
                return checkIds[0];
            }
        } else {
            if (checkIds.length == 0) {
                alert("请选择至少一条记录操作！");
                return false;
            } else {
                return checkIds;
            }
        }
    };
})(jQuery);

(function ($) {
    $.fn.getInputId2 = function (sigle) {
        var checkIds = [];
        $(this).each(function () {
            if($(this).is(":checked")) {
                checkIds.push($(this).val())
            }
        });
        if (checkIds.length > 1) {
            alert("只能选择一条记录！");
            return false;
        }
        else {
            return checkIds[0];
        }
    };
})(jQuery);

