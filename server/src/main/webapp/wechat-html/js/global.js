
//弹出提示消息
function alertMsg(msg,func){
    if(func == '' || func == undefined){
        layer.open({
            content: msg
            ,btn: '确定'
        });
    }else{
        layer.open({
            content: msg
            ,btn: ['确定']
            ,yes: function(index){
                layer.close(index);
                func();
            }
        });
    }
}

//弹出提示消息
function alertConfirm(msg,func){
    if(func == '' || func == undefined){
        layer.open({
            content: msg
            ,btn: '确定'
        });
    }else{
        layer.open({
            content: msg
            ,btn: ['确定','取消']
            ,yes: function(index){
                layer.close(index);
                func();
            }
        });
    }
}

