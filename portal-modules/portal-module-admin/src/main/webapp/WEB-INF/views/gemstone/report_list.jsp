<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="../common/common.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>${menu_name } - ${title }</title>
<link rel="stylesheet" href="${ctx}/static/js/uploadify/uploadifive.css"> 

<script src="${ctx}/static/js/uploadify/jquery.uploadifive.min.js"></script>

</head>
<link href="${ctx }/static/plugins/chosen_v1.6.2/chosen.css" rel="stylesheet" />
<body <%@ include file="../common/skin.jsp" %>>
	<%@ include file="../common/head.jsp" %>
    <%@ include file="../common/menu.jsp" %>
    <div class="J_content">
		<div class="mt20 plr20">
		  <form action="${ctx }/gr/list" id="queryForm" method="post">
	        <div class="J_toolsBar clearfix">
				<div class="t_label">证书编号</div>
				<div class="t_text ml10">
                	<input placeholder="请输入证书编号" type="text" name="no" id="no" value="${queryDTO.no }"/>
                </div>
              
                <div class="t_label">&nbsp;证书类型</div>
				<div class="t_text ml10">
                	
                </div>
                <div class="t_button mgl30">
               		<a class="abtn red" href="javascript:myQuery();">
               		   <i class="icon"></i>查询
               		</a>
               	</div>
               	<div class="t_button ml10">
               		<a class="abtn blue" href="javascript:myEdit();">
               		   <i class="icon"></i>新增
               		</a>
               	</div>
               	<div class="t_button ml10">
               		<a class="abtn maxblue" href="javascript:myExport();">
               			<i class="icon"></i>导出
               		</a>
               	</div>
               	<div class="t_button ml10">
               		<a class="abtn maxblue" href="javascript:myExport();">
               			<i class="icon"></i>导入
               		</a>
               	</div>
               	<div class="t_label ml10">
					记录数：<label style="color: red;" id="total">${page.totalCount }</label>
				</div>
			</div>
			
			<div class="J_table mt20">
                 <div class="t_table">
                     <table>
                         <thead>
                             <tr>
                                 <td>
                                     <span>序号</span>
                                 </td>
                                 <td>
                                     <span>类型</span>
                                 </td>
                                 <td>
                                     <span>编号</span>
                                 </td>
                                 <td>
                                     <span>日期</span>
                                 </td>
                                 <td>
                                     <span>鉴定物品</span>
                                 </td>
                                 <td>
                                     <span>鉴定结果</span>
                                 </td>
                                 <td>
                                 	 <span>创建时间</span>
                                 </td> 
                                 <td>
                                 	 <span>状态</span>
                                 </td>
                                 <td>
                                     <span>操作</span>
                                 </td>
                             </tr>
                         </thead>
                         <tbody>
                            <c:choose>
                              <c:when test="${page.list != null && page.totalCount > 0 }">
                                 <c:forEach items="${page.list }" var="u" varStatus="status">
		                             <tr>
		                                 <td>
		                                     <div class="t_text tc">
		                                        ${status.index+1 }
		                                     </div>
		                                 </td>
		                                 <td>
		                                 	<div class="t_text tc">
		                                 		<c:choose>
		                                         	<c:when test="${u.type eq '1' }">
		                                         		宝石
		                                         	</c:when>
		                                         	<c:when test="${u.type eq '2' }">
		                                         		钻石
		                                         	</c:when>
		                                         </c:choose>
		                                 	</div>
		                                 </td>
		                                 <td>
		                                     <div class="t_text tc">
		                                        ${u.no }
		                                     </div>
		                                 </td>
		                                 <td>
		                                     <div class="t_text tc">
		                                         <fmt:formatDate value="${u.reportDate }" pattern="yyyy-MM-dd"/>
		                                     </div>
		                                 </td>
		                                 <td>
		                                     <div class="t_text tc">
		                                         ${u.object }
		                                     </div>
		                                 </td>
		                                 <td>
		                                     <div class="t_text tc">
		                                         ${u.identification }
		                                     </div>
		                                 </td>
		                                 <td>
		                                     <div class="t_text tc">
		                                         <fmt:formatDate value="${u.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
		                                     </div>
		                                 </td>
		                                 <td>
		                                 	<div class="t_text tc">
		                                 		<c:choose>
		                                         	<c:when test="${u.deleteFlag eq '0' }">
		                                         		<label class="normal_flag">正常</label>
		                                         	</c:when>
		                                         	<c:otherwise>
		                                         		<label class="delete_flag">删除</label>
		                                         	</c:otherwise>
		                                         </c:choose>
		                                 	</div>
		                                 </td>
		                                 <td>
		                                     <div class="t_link">
		                                         <a href="javascript:myEdit('${u.id }');"><i class="icon"></i>编辑</a>
		                                         <c:choose>
		                                         	<c:when test="${u.deleteFlag eq '0' }">
		                                         		<a href="javascript:updStatus('${u.id }', '1');"><i class="icon"></i>删除</a>
		                                         	</c:when>
		                                         	<c:otherwise>
		                                         		<a href="javascript:updStatus('${u.id }', '0');"><i class="icon"></i>恢复</a>
		                                         	</c:otherwise>
		                                         </c:choose>
		                                         <a href="${ctx}/gr/getReportFile?reportId=${u.id }" target="_blank"><i class="icon"></i>查看证书</a>
		                                     </div>
		                                 </td>
		                             </tr>
	                             </c:forEach>
                              </c:when>
                              <c:otherwise>
                                  <tr>
                                    <td colspan="8">
	                                  <div class="J_null mt40">
								            <img src="${ctx }/static/images/null.png">
								            <p>暂无相关数据</p>
								      </div>
								    </td>
							      </tr>
                              </c:otherwise>
                            </c:choose>
                         </tbody>
                     </table>
                 </div>
                 <%@ include file="../common/pager.jsp"%>
             </div>
            </form>
		</div>
    </div>
<script src="${ctx }/static/plugins/chosen_v1.6.2/chosen.jquery.js"></script>    
<script type="text/javascript">
	function myEdit(id){
		var loadIdx = layer.load();
		var title = '添加证书';
		if(!id){
			id = '';
		}else{
			title = '修改证书';
		}
		$.post('${ctx}/gr/dialog/edit?reportId='+id, {}, function(str){
			
			layer.close(loadIdx);
			
			layer.open({
				title : title,
				type : 1,
				area : ['1000px', '600px'],
				content : str,
				btn : ['确定', '取消'],
				yes : function(index, layero){
					$('#editForm').submit();
				},
				btn2 : function(index, layero){
				    layer.close(index);
				}
			});
		});
	}
	
	function mySubmit(){
		$('#editForm').submit();
	}
	
	function myQuery(){
		$('#queryForm').submit();
	}
	
	function myExport(){
		var userName = $("#userName").val();
		window.location.href="${ctx}/user/export?userName="+userName;
	}
	
	function updStatus(id, status){
		var ids = new Array();
		ids.push(id);
		
		var content = '';
		if(status == '1'){
			content = '确认要恢复数据吗？';
		}else{
			content = '确认要删除数据吗？';
		}
		
		layer.confirm(content, function(index){
			layer.close(index);
			var loadIdx = layer.load();
			$.ajax({
				url : '${ctx}/user/ajax/upd/status',
				type : 'post',
				data : {
					'ids' : ids,
					'status' : status
				},
				traditional : true,
				success : function(result){
					layer.close(loadIdx);
					if(result.success){
						layer.alert('操作成功', function(){
							window.location.reload();
						});
					}else{
						layer.alert('操作失败');
					}
				}
			});
		});
	}
</script>
</body>
</html>