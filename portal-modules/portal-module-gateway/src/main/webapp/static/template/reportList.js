/// <reference path="../js/common.js" />

jc.data.setup(function (data) {
    var html = '';

    if (!data) return html;

    var list = data.list;

    for (var i = 0, l = list.length; i < l; i++) {

        var firstClass = i == 0 ? "first" : "";

        var curList = list[i];

        html += '<div onclick="window.open(\'' + window.serverUploadPath + '/reports/' + curList.id + '.pdf\')" class="l_item ' + (firstClass) + '">';
        html += '<a class="i_link" href="javascript:;">' + (this.getString('[' + curList.no + ']' + curList.object)) + '</a>';
        html += '<img src="' + (curList.images && curList.images.length > 0 ? window.serverUploadPath + curList.images[0].filePath : window.notImgUrl) + '">';
        html += '<span class="i_time">' + (jc.tools.formatDate(curList.updateDate)) + '</span>';
        html += '<p class="i_intro">' + (this.getString(curList.comments)) + '</p>';
        html += '</div>';
    }


    /*
    html += '<div class="l_item">';
    html += '<a class="i_link" href="javascript:;">“践行绿色教学、构建高效课堂”教学开放日圆满成功</a>';
    html += '<span class="i_time">1月31日</span>';
    html += '<p class="i_intro">3月31日下午，清华大学2017年宣传工作会议在文科图书馆未央厅召开。会议全面总结了学校2016年宣传工作，并对2017年宣传工作进行了部署。校党委副书记邓卫出席会议并作总结讲话。学校各单位党委（直属总支）分管宣传工作的副书记或书记、新闻宣传负责人、宣传委员等100余人参加了会议。</p>';
    html += '</div>';
    */



    return html;

});