<%@ page language="java" pageEncoding="utf-8"
	contentType="text/html;charset=utf-8"%>
<html>

<%@ include file="../../common/jstl.jsp"%>

<script type="text/javascript">


function removeImage(ele) {
	$(ele).parent().remove();
}

//表单验证
$(function(){
	
	$('#editForm').validator({
		valid : function(form){
			var laodIdx = layer.load();
			var images = [];
			$('#reportImagesDiv>div').each(function(i, item) {
				images.push({ id: $(item).attr('imageId'), 
					reportId: '${report.id }',
					fileName: $(item).attr('fileName'),  
					filePath: $(item).attr('filePath'),
					orderNo: i})
			});
			$('#imagesStrInput').val(JSON.stringify(images));
			$('#editForm').ajaxSubmit({
				traditional : true,
				success : function(result){
					layer.close(laodIdx);
					if(result.success){
						layer.alert('保存成功', function(){
							//window.location.reload();
						});
					}else{
						layer.alert(result.msg);
					}
				}
			});
		}
	});
	
	$("#uploadImage").uploadify(
			{
				'uploader' : '${ctx}/upload/uploadAttach',
				'cancelImg' : '${ctx}/static/js/uploadify/uploadify-cancel.png',
				'auto' : true,
				'multi' : false,
				'simUploadLimit' : 1,
				'buttonText' : '请点击选择图片',
				'fileObjName' : 'fileData',
				'width' : 70,
				'height' : 20,
				'uploadLimit':5,
				'removeCompleted': true,
				'fileType'  : 'image/*',//只允许图片格式的文件
				'onUploadSuccess' : function(file, data, response) {
					console.log(file);
						if(data != null){
							var attachUrl = '${ctx}/upload/getImage?imagePath=' + data;	
							$('#reportImagesDiv').append('<div class="t_img ml10 fl" fileName="' + file.name + '" filePath="' + data
								+ '"><img src="' + attachUrl + '" width="100px" height="100px" />'
								+ '<a href="javascript:void(0)" onclick="removeImage(this)">X</a></div>')
						}
			 }
		   });
	
});

</script>
<head>

</head>

<body>	

	<div id="addForm" class="mgt20">
		<form action="${ctx }/gr/ajax/save" id="editForm" method="post">
		<input type="hidden" name="id" value="${report.id }"/>
		<div class="">
			<div class="J_formTable l_form_table">
				<table class="not_hightlight" style="width: 100%">
					<tr>
						<td class="l_title" width="15%"><b class="cRed">*</b> 类型</td>
                         <td width="35%">
                             <div class="J_toolsBar fl">
                                 <div class="t_check ml10">
                                 	<label><input name="type" type="radio" value="1" <c:if test="${report.type == 1 }">checked="checked"</c:if> />宝石</label> 
						 			<label><input name="type" type="radio" value="2" <c:if test="${report.type == 2 }">checked="checked"</c:if>/>钻石</label> 
                                 </div>
                             </div>
                         </td>
                         <td class="l_title" width="10%"></td>
                         <td width="40%">
                         </td>
                     </tr>
					<tr>
						<td class="l_title" width="15%"><b class="cRed">*</b> 编号</td>
                         <td width="35%">
                             <div class="J_toolsBar fl">
                                 <div class="t_text w200 ml10">
                                 	<input type="text" name="no" data-rule="编号:required;no;" value="${report.no}" />
                                 </div>
                             </div>
                         </td>
                         <td class="l_title" width="10%"><b class="cRed">*</b> 日期</td>
                         <td width="40%">
                             <div class="J_toolsBar fl">
                                 <div class="t_text w200 ml10">
                                 	<input type="date" name="reportDate" data-rule="日期:required;reportDate;" value="<fmt:formatDate value="${report.reportDate }" pattern="yyyy-MM-dd"/>" />
                                 </div>
                             </div>
                         </td>
                     </tr>
					 <tr>
						<td class="l_title "><b class="cRed">*</b> 鉴定物品</td>
                         <td>
                             <div class="J_toolsBar fl">
                                 <div class="t_text w200 ml10">
                                     <input type="text" name="object" data-rule="物品:required;object;" value="${report.object }" />
                                 </div>
                             </div>
                         </td>
                         <td class="l_title"><b class="cRed">*</b> 鉴定结果</td>
                         <td>
                             <div class="J_toolsBar fl">
                                 <div class="t_text w200 ml10">
                                     <input type="text" name="identification" data-rule="结果:required;identification;" value="${report.identification }" />
                                 </div>
                             </div>
                         </td>
                     </tr>
					 <tr>
						<td class="l_title "><b class="cRed">*</b> 重量(CT)</td>
                         <td>
                             <div class="J_toolsBar fl">
                                 <div class="t_text w200 ml10">
                                     <input type="text" name="weight" data-rule="重量:required;weight;" value="${report.weight }" />
                                 </div>
                             </div>
                         </td>
                         <td class="l_title"><b class="cRed">*</b> 尺寸(MM)</td>
                         <td>
                             <div class="J_toolsBar fl">
                                 <div class="t_text w40 ml10">
                                     <input type="text" name="dimensionsLength" value="${report.dimensionsLength }" />
                                 </div>
                                 <div class="t_text" style="border:0;padding: 0 5px">X</div>
                                 <div class="t_text w40">
                                     <input type="text" name="dimensionsWidth" value="${report.dimensionsWidth }" />
                                 </div>
                                 <div class="t_text" style="border:0;padding: 0 4px">X</div>
                                 <div class="t_text w40">
                                     <input type="text" name="dimensionsHeight" value="${report.dimensionsHeight }" />
                                 </div>
                             </div>
                         </td>
                     </tr>
                     <tr>
						<td class="l_title "><b class="cRed">*</b> 切割款式</td>
                         <td>
                             <div class="J_toolsBar fl">
                                 <div class="t_text w200 ml10">
                                     <input type="text" name="cut" data-rule="切割:required;cut;" value="${report.cut }" />
                                 </div>
                             </div>
                         </td>
                         <td class="l_title"><b class="cRed">*</b> 形状</td>
                         <td>
                             <div class="J_toolsBar fl">
                                 <div class="t_text w200 ml10">
                                     <input type="text" name="shape" data-rule="形状:required;shape;" value="${report.shape }" />
                                 </div>
                             </div>
                         </td>
                     </tr>
                     <tr>
						<td class="l_title "><b class="cRed">*</b> 颜色</td>
                         <td>
                             <div class="J_toolsBar fl">
                                 <div class="t_text w200 ml10">
                                     <input type="text" name="color" data-rule="颜色:required;color;" value="${report.color }" />
                                 </div>
                             </div>
                         </td>
                         <td class="l_title"><b class="cRed">*</b> 产地</td>
                         <td>
                             <div class="J_toolsBar fl">
                                 <div class="t_text w200 ml10">
                                     <input type="text" name="origin" data-rule="产地:required;origin;" value="${report.origin }" />
                                 </div>
                             </div>
                         </td>
                     </tr>
                     <tr>
						<td class="l_title "><b class="cRed">*</b> 图片</td>
                         <td colspan="3">
                             <div class="J_toolsBar">
                                 <div class="w200 ml10">
									<input type="file" id="uploadImage" /> 
                                 </div>
                             </div>
                         </td>
                     </tr>
                     <tr>
						<td class="l_title "></td>
                         <td colspan="3">
                             <div class="J_toolsBar" id="reportImagesDiv">
                             	<c:if test="${ report.images != null }">
	   								<c:forEach items="${report.images }" var="c">
	   									<div class="t_img ml10 fl" imageId="${c.id}" fileName="${c.fileName }" filePath="${c.filePath}">
	   									<img src="${ctx }/upload/getImage?imagePath=${c.filePath}" width="100px" height="100px" />
		                   				<a href="javascript:void(0)" onclick="removeImage(this)">X</a>
		                   				</div>
		                   			</c:forEach>
   								</c:if>
                             </div>
                             <input type="hidden" name="imagesStr" id="imagesStrInput" /> 
                         </td>
                     </tr>
				</table>
			</div>
		</div>
		</form>
	</div>

</body>
</html>