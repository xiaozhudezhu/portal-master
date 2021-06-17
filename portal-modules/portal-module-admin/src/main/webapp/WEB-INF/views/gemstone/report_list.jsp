<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="../common/common.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>${menu_name } - ${title }</title>
<link rel="stylesheet" href="${ctx}/static/js/uploadify/uploadifive.css"> 

<script src="${ctx}/static/js/uploadify/jquery.uploadifive.min.js"></script>
<script src="${ctx}/static/js/rfid/interface.js"></script>

</head>
<link href="${ctx }/static/plugins/ZdCascader/ZdCascader.css" rel="stylesheet" />
<script src="${ctx}/static/plugins/ZdCascader/ZdCascader.js"></script>
<link href="${ctx}/static/plugins/select2/css/select2.min.css" rel="stylesheet" />
<script src="${ctx}/static/plugins/select2/js/select2.full.min.js"></script>

<body <%@ include file="../common/skin.jsp" %>>
	<%@ include file="../common/head.jsp" %>
    <%@ include file="../common/menu.jsp" %>
    <div class="J_content">
		<div class="mt20 plr20">
		  <form action="${ctx }/gr/list" id="queryForm" method="post">
	        <div class="J_toolsBar clearfix">
				<div class="t_label">证书编号</div>
				<div class="t_text ml10">
                	<input placeholder="请输入证书编号" type="text" name="no" id="reportQueryNoInput" value="${queryDTO.no }"/>
                </div>
              
                <div class="t_label">&nbsp;证书类型</div>
				<div class="t_text w200 ml10" style="padding:0">
					<%-- <select name="type" style="width:100%;height:100%;border:0">
						<option value="">请选择</option>
						<option value="1" <c:if test="${queryDTO.type == 1 }">selected="selected"</c:if>>宝石</option>
						<option value="2" <c:if test="${queryDTO.type == 2 }">selected="selected"</c:if>>钻石</option>
					</select> --%>
					<input id="reportQueryTypeNameInput" type="text" name="typeName" />
					<input id="reportQueryTypeValueInput" type="hidden" name="type" value="${queryDTO.type }"/>
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
               	<!-- <div class="t_button ml10">
               		<a class="abtn maxblue" href="javascript:myExport();">
               			<i class="icon"></i>导出
               		</a>
               	</div>
               	<div class="t_button ml10">
               		<a class="abtn maxblue" href="javascript:myExport();">
               			<i class="icon"></i>导入
               		</a>
               	</div> -->
               	<div class="t_button ml10">
               	<a class="abtn gray" href="${ctx}/static/js/rfid/reader_setup.msi">
               		<i class="icon"></i>下载读卡器插件
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
		                                 		${u.type.name }
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
		                                         	<a href="javascript:deleteReport('${u.id }');"><i class="icon"></i>删除</a>
		                                         <a href="${ctx}/gr/getReportFile?reportId=${u.id }&isPrint=false" target="_blank"><i class="icon"></i>查看</a>
		                                         <a href="javascript:printReportFile('${u.id }')"><i class="icon"></i>打印</a>
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
<iframe style="display:none" id="printIframe"></iframe>
<script type="text/javascript">
	function myEdit(id){
		var loadIdx = layer.load();
		var title = '添加证书';
		if(!id){
			id = '';
		}else{
			title = '修改证书';
		}
		$.get('${ctx}/gr/dialog/edit?reportId='+id, {}, function(str){
			
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
		layer.alert('功能开发中..');
		//var userName = $("#userName").val();
		//window.location.href="${ctx}/user/export?userName="+userName;
	}
	
	function deleteReport(id){		
		var content = '确定要删除数据吗？';
		layer.confirm(content, function(index){
			layer.close(index);
			var loadIdx = layer.load();
			$.ajax({
				url : '${ctx}/gr/ajax/delete',
				type : 'post',
				data : {
					'reportId' : id
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
	
	function printReportFile(reportId) {
		$('#printIframe').attr('src', '${ctx}/gr/getReportFile?reportId=' + reportId + '&isPrint=true');
		var laodIdx = layer.load();
		$('#printIframe').load(function() {
			layer.close(laodIdx);
			$("#printIframe")[0].contentWindow.print();
		})
		
	}
	
	function initQueryType() {
		$.get('${ctx}/gr/getTypeSelect', {}, function(data) {
			$('#reportQueryTypeNameInput').zdCascader({
				data: data.data,
				onChange: function(s, data) {
					$('#reportQueryTypeValueInput').val(data.value);
				}
			});
			$('#reportQueryTypeNameInput').val('${queryDTO.typeName }');
		});
	}
	initQueryType();
</script>


<script> 
var msg = document.getElementById('TxtArea');
</script>

<script>
var obj = embed_reader.getOBJ(READER_TYPE._reader_type_contactLess);
obj.onResult(function(rData)
{
	switch(rData.FunctionID)
    {
    case FUNCIDS._fid_adaptReader:
    	Connect();
    break;
		case FUNCIDS._fid_initialcom:
		{
		var opst;
		var rel = rData.RePara_Int;
		if(0 == rel)
		{
			var hdev = parseInt(rData.RePara_Str);
			if(hdev != -1)
			{
				icdev= hdev;
				//obj.beep(icdev,10);   //do a beep
				layer.msg('读卡器连接成功！');
				isComOpen=true;             //Set reader link status
				obj.config_card(icdev, 0x31);//config to iso15693
				$('#reportQueryNoInput').unbind().focus(function() {
					 readCardTimer = setInterval(Read, 1000);
					 gl_uid = '';
					 readCardInput = this;
				 }).blur(function() {
					 clearInterval(readCardTimer);
				 });
			}
			else
			{
				layer.msg('读卡器连接失败！');
						isComOpen=false;           //Set reader link failed status
			}
		}
		else
			layer.msg('读卡器连接失败！');
		}
		break;
		case FUNCIDS._fid_exit:
			layer.msg('连接已断开！');
			break;
		case FUNCIDS._fid_beep:
			obj.config_card(icdev, 0x31);//config to iso15693
			break;
		case FUNCIDS._fid_icode_inventory:
		{
			var strcard= rData.RePara_Str;
			if(strcard!="")
			{	if(strcard != gl_uid) {
					hasCard =true;
					layer.msg('读取到卡号：' + strcard);
        			gl_uid = strcard;
        			obj.icode_select_uid(icdev, gl_flags, gl_uid);
				}
			}
			else
			{
				//layer.msg('未发现卡！');
				hasCard =false;        //Set no card status
			}
		}
		break;
		case FUNCIDS._fid_icode_select_uid:
		{
      var rel = rData.RePara_Int;
      if(0 == rel)
      {
        
        switch(gl_wantFunc)
        {
          case GFUNC.readICode:
          obj.icode_readblock(icdev, gl_flags, gl_BinBlock, gl_rwBlockNum, gl_uid);
          break;
        }
      }
		}
		break;
		case FUNCIDS._fid_icode_readblockAsStr:
		{
			var data =  rData.RePara_Str;     
			if(data != "")
			{
				readCardInput.value = data;
			}
			else
			{
				layer.msg('卡内容读取失败');
			}
		}
		break;

	}
	
}
);


//Link Reader
function Connect()
{
	try{
	if(isComOpen==false)          //if reader link failed
	{
		//alert("initialcom");
	  obj.initialcom(100,115200);
	}
	}catch(e){alert(e.message);}

	return;
}

//Read card
function Read()
{
     var findMode = 0x36;//0x16:multi-card mode, 0x36:single-card mode
     var afi = 0;
     var maskLen = 0;
     
	obj.icode_inventory(icdev,findMode, afi, maskLen);
	gl_wantFunc = GFUNC.readICode;
}


//Disconnect with reader
function Disconnect()
{
	iRet = obj.exit(icdev);
    isComOpen=false; //Set unlink status
}


<!--

var GFUNC = {
	readICode:1,
	writeICode:2,
	lockICode:3,
};

 var nRead =0;     //The count one card repeat find
 var hasCard =false;
 var isComOpen=false;
 var icdev= -1;
 var gl_sector = 2;
 var gl_BinBlock = 8;
 var gl_wantFunc =  1;
 var gl_rwBlockNum = 1;
 var gl_flags = 0x22;
 var gl_uid = "";
var readCardTimer = 0;
 var readCardInput;

</script>

</body>
</html>