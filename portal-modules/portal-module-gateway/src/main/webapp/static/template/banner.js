jc.data.setup(function (data) {
    var html = '';

    var id = "banner_" + new Date().getTime() + parseInt(Math.random() * 10);
    var list = data.list;

    html += '<div id="' + id + '" class="carousel slide">';
    html += '<ol class="carousel-indicators">';

    for (var i = 0, l = list.length; i < l; i++) {
        html += '<li data-target="#' + id + '" data-slide-to="' + i + '" class="' + (i == 0 ? 'active' : '') + '"></li>';
    }

    html += '</ol>';
    html += '<!-- Carousel items -->';
    html += '<div class="carousel-inner">';

    for (var i = 0, l = list.length; i < l; i++) {

        html += '<div class="item ' + (i == 0 ? 'active' : '') + '">';
        var coverImageUrl = list[i].coverImageUrl;
        html += '<a href="javascript:;"><img src="' + (coverImageUrl && coverImageUrl.startsWith('http') ? coverImageUrl : window.serverUploadPath + coverImageUrl) + '" /></a>';
        html += ' <div class="carousel-caption"><h3>' + list[i].title + '</h3></div>'
        html += '</div>';
    }

    html += '</div>';
    html += '<!-- Carousel nav -->';
    html += '<a class="left carousel-control" href="#' + id + '" data-slide="prev">';
    html += '<span class="glyphicon glyphicon-chevron-left"></span>';
    html += '</a>';
    html += '<a class="right carousel-control" href="#' + id + '" data-slide="next">';
    html += '<span class="glyphicon glyphicon-chevron-right"></span>';
    html += '</a>';
    html += '</div>';

    return html;

});