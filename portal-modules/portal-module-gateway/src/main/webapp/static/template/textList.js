/// <reference path="../js/common.js" />

jc.data.setup(function (data) {
    var html = '';

    if (!data) return html;

    var list = data.list;

    var columnListId = window.columnListId || data.id;


    for (var i = 0, l = list.length; i < l; i++) {

        var firstClass = i == 0 ? "first" : "";

        var curList = list[i];

        var $div = $("<div>");
        $div.html(curList.content);
        var filterContent = $div.text();
        html += '<div onclick="window.router(\'menuAndDetail\',{ rootColumnId:\'' + curList.rootColumnInfo.id + '\',columnListId:\'' + curList.columnInfo.id + '\',articleId:\'' + curList.id + '\' });" class="l_item ' + (firstClass) + '">';
        html += '<span class="i_time">' + (jc.tools.formatDate(curList.updateDate)) + '</span>';
        html += '<img src="' + (curList.coverImageUrl ? window.serverUploadPath + curList.coverImageUrl : window.notImgUrl) + '">';
        html += '<a class="i_link" href="javascript:;">' + (this.getString(curList.title)) + '</a>';
        html += '<p class="i_intro">' + (this.getString(filterContent)) + '</p>';
        html += '</div>';
    }

    return html;

});