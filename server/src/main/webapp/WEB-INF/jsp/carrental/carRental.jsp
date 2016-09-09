<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../inc/taglibs.jsp" %>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width: 800px;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;</button>
                <h4 class="modal-title">选择派遣车辆</h4>
            </div>
            <div class="modal-body row" style="margin-bottom: -30px;">
                <div class="form-group col-sm-2">
                    <input type="text" id="carNo" name="carNo" class="form-control" placeholder="车牌号">
                </div>
                <div class="form-group col-sm-2" style="margin-left: 5px">
                    <input type="text" id="driverName" name="driverName" class="form-control" placeholder="司机姓名">
                </div>
                <div class="form-group col-sm-2" style="margin-left: 5px">
                    <%--<input type="text" id="carType1" name="carType1" class="form-control" placeholder="用车类型">--%>
                    <select id="carType1" name="carType1" class="form-control input-sm" required >
                        <option value="">用车类型</option>
                        <c:forEach items="${carType}" var="v">
                            <option value="${v.id}">${v.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <button id="c_search" class="btn btn-info"><i class="fa fa-search"></i> 搜索</button>
                <button id="c_clear" class="btn btn-info"><i class="fa fa-recycle"></i> 清空</button>
            </div>
            <div class="modal-body row">
                <div class="adv-table">
                    <table class="display table table-bordered table-striped" id="dataTables" width="100%">
                        <thead>
                        <tr>
                            <th><input type="checkbox" class="list-parent-check"
                                       onclick="$leoman.checkAll(this);"/></th>
                            <th>车牌号</th>
                            <th>司机姓名</th>
                            <th>车型</th>
                            <th>用车类型</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
