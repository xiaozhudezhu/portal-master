/// <reference path="../js/common.js" />

jc.data.setup(function (data) {
    var html = '';

    if (!data) return html;

    var list = data.list;

    for (var i = 0, l = list.length; i < l; i++) {

        var firstClass = i == 0 ? "first" : "";

        var curList = list[i];

        html += '<div onclick="window.open(\'' + window.server + 'pdfviewer#' + curList.id + '\')" class="l_item ' + (firstClass) + '">';
        html += '<span class="i_time">' + (jc.tools.formatDate(curList.updateDate)) + '</span>';
        html += '<img src="' + (curList.images && curList.images.length > 0 ? window.serverUploadPath + curList.images[0].filePath : window.notImgUrl) + '">';
        html += '<a class="i_link" href="javascript:;">' + (this.getString('[' + curList.no + ']' + curList.object)) + '</a>';
        html += '<p class="i_intro">' + (this.getString(curList.comments)) + '</p>';
        html += '</div>';
    }
    return html;

});