<%@ page language="java" pageEncoding="utf-8"
	contentType="text/html;charset=utf-8"%>
<html>

<%@ include file="../../common/jstl.jsp"%>

<script type="text/javascript">


function removeImage(ele) {
	$(ele).parent().remove();
}

function changeReportType(type) {
	if(type == '1') {
		$('#reportType2Table').hide();
		$('#reportType1Table').show();
		$('#reportType1Table :input').removeAttr('disabled');
		$('#reportType2Table :input').attr('disabled', 'disabled');
	}
	else if(type == '2') {
		$('#reportType1Table').hide();
		$('#reportType2Table').show();
		$('#reportType2Table :input').removeAttr('disabled');
		$('#reportType1Table :input').attr('disabled', 'disabled');
	}
	
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
							window.location.reload();
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
	$('#reportNoInput').unbind().focus(function() {
		 readCardTimer = setInterval(Read, 1000);
		 gl_uid = '';
		 readCardInput = this;
	 }).blur(function() {
		 clearInterval(readCardTimer);
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
                                 	<label><input name="type" type="radio" value="1" onclick="changeReportType('1')" <c:if test="${report.type == 1 || report.type == null }">checked="checked"</c:if>  <c:if test="${report.type != null }">disabled</c:if> />宝石</label> 
						 			<label><input name="type" type="radio" value="2" onclick="changeReportType('2')" <c:if test="${report.type == 2 }">checked="checked"</c:if>  <c:if test="${report.type != null }">disabled</c:if>/>钻石</label> 
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
                                 	<input id="reportNoInput" type="text" name="no" data-rule="编号:required;no;" value="${report.no}" />
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
            	</table>
            	<c:if test="${report.type == 1 || report.type == null }">
            	<table id="reportType1Table" style="width: 100%">
					
					 <tr>
						<td class="l_title" width="15%"><b class="cRed">*</b> 重量(CT)</td>
                         <td width="35%">
                             <div class="J_toolsBar fl">
                                 <div class="t_text w200 ml10">
                                     <input type="text" name="weight" data-rule="重量:required;weight;" value="${report.weight }" />
                                 </div>
                             </div>
                         </td>
                         <td class="l_title" width="10%"><b class="cRed">*</b> 尺寸(MM)</td>
                         <td width="40%">
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
                </table>
                </c:if>
                <c:if test="${report.type == 2 || report.type == null }">
				<table id="reportType2Table" style="width: 100%; <c:if test="${report.type == null }">display:none</c:if>">
                     <tr>
						<td class="l_title" width="15%"><b class="cRed">*</b> 形状/切割款式</td>
                         <td width="35%">
                             <div class="J_toolsBar fl">
                                 <div class="t_text w200 ml10">
                                     <input type="text" name="cut" data-rule="形状:required;cut;" value="${report.cut }" />
                                 </div>
                             </div>
                         </td>
                         <td class="l_title" width="10%"><b class="cRed">*</b> 重量(CT)</td>
                         <td width="40%">
                             <div class="J_toolsBar fl">
                                 <div class="t_text w200 ml10">
                                     <input type="text" name="weight" data-rule="重量:required;weight;" value="${report.weight }" />
                                 </div>
                             </div>
                         </td>
                     </tr>
                     <tr>
						<td class="l_title "><b class="cRed">*</b> 颜色</td>
                         <td>
                             <div class="J_toolsBar fl">
                                 <div class="t_text w200 ml10">
                                     <input type="text" name="colorGrade" data-rule="颜色:required;colorGrade;" value="${report.colorGrade }" />
                                 </div>
                             </div>
                         </td>
                         <td class="l_title "><b class="cRed">*</b> 净度</td>
                         <td>
                             <div class="J_toolsBar fl">
                                 <div class="t_text w200 ml10">
                                     <input type="text" name="clarityGrade" data-rule="净度:required;clarityGrade;" value="${report.clarityGrade }" />
                                 </div>
                             </div>
                         </td>
                     </tr>
					 <tr>
						<td class="l_title"><b class="cRed">*</b> 比例</td>
                         <td>
                             <div class="J_toolsBar fl">
                                 <div class="t_text w200 ml10">
                                     <input type="text" name="proportions" data-rule="比例:required;proportions;" value="${report.proportions }" />
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
						<td class="l_title "><b class="cRed">*</b> 抛光度</td>
                         <td>
                             <div class="J_toolsBar fl">
                                 <div class="t_text w200 ml10">
                                     <input type="text" name="polish" data-rule="抛光度:required;polish;" value="${report.polish }" />
                                 </div>
                             </div>
                         </td>
                         <td class="l_title "><b class="cRed">*</b> 对称性</td>
                         <td>
                             <div class="J_toolsBar fl">
                                 <div class="t_text w200 ml10">
                                     <input type="text" name="symmetry" data-rule="对称性:required;symmetry;" value="${report.symmetry }" />
                                 </div>
                             </div>
                         </td>
                     </tr>
                     <tr>
						<td class="l_title "><b class="cRed">*</b> 荧光</td>
                         <td>
                             <div class="J_toolsBar fl">
                                 <div class="t_text w200 ml10">
                                     <input type="text" name="fluorescence" data-rule="荧光:required;fluorescence;" value="${report.fluorescence }" />
                                 </div>
                             </div>
                         </td>
                         <td class="l_title "><b class="cRed">*</b> 评价</td>
                         <td>
                             <div class="J_toolsBar fl">
                                 <div class="t_text w200 ml10">
                                     <input type="text" name="comments" data-rule="评价:required;comments;" value="${report.comments }" />
                                 </div>
                             </div>
                         </td>
                     </tr>
                     <tr>
						<td class="l_title "><b class="cRed">*</b> 净度特征</td>
                        <td colspan="3">
                             <div class="J_toolsBar fl">
                                 <div class="t_textarea w200 ml10">
                                     <textarea name="clarityFeature" data-rule="净度特征:required;clarityFeature;">${report.clarityFeature }</textarea>
                                 </div>
                             </div>
                         </td>
                     </tr>
				</table>
				</c:if>
				<table style="width: 100%">
					<tr>
						<td class="l_title " width="15%"><b class="cRed">*</b> 图片</td>
                         <td colspan="3" width="85%">
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