<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>

<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="confirm" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button>
                <h4 class="modal-title">-</h4>
            </div>
            <div class="modal-body">
                <div align=center>
                    <h4 class="modal-title" id="showText" ></h4>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-warning" id="determine"> 确定</button>
            </div>
        </div>
    </div>
</div>
