<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../inc/taglibs.jsp" %>

<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="detail" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button>
                <h4 class="modal-title">备注详情</h4>
            </div>
            <div class="modal-body">
                <div align=center>
                    <textarea id="showText" name="detail" class="form-control overflow" style="height: 400px;resize:none;" readonly="readonly"></textarea>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-warning" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>
