<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../inc/taglibs.jsp" %>
<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="enterprise1" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button>
                <h4 class="modal-title">选择</h4>
            </div>
            <div class="modal-body">
                <div align=center>
                    <select class="form-control input-sm" id="enterprise">
                        <c:forEach var="v" items="${enterprise}">
                            <option value="${v.id}">${v.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-warning" id="enterpriseSave"> 确定</button>
            </div>
        </div>
    </div>
</div>
