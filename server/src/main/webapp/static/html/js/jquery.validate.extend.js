$(function () {
    jQuery.validator.addMethod("mobile", function(value, element) {
        var length = value.length;
        var regPhone = /^1([3578]\d|4[57])\d{8}$/;
        return this.optional(element) || ( length == 11 && regPhone.test( value ) );
    }, "请正确填写您的手机号码");

    jQuery.validator.addMethod("number-2", function(value, element) {
        var reg = /^[0-9]+(.[0-9]{2})?$/;
        return this.optional(element) || ( reg.test( value ) );
    }, "请输入正确的数字，不超过2位小数");

    jQuery.validator.addMethod("number-0", function(value, element) {
        var reg = /^[0-9]*$/;
        return this.optional(element) || ( reg.test( value ) );
    }, "请输入正确的数字");
});