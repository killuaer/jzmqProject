$(function(){
    console.log($("#modelId").val());
    setIframeHeight(document.getElementById('content'));
    document.getElementById('content').src = '../views/app/workArea/system/activiti/modeler.html?modelId='+$("#modelId").val();
});

function setIframeHeight(iframe) {
    console.log("弹出框高度："+$('#iframe').height());
    iframe.height = $('#iframe').height()+'px';
};

window.onresize = function () {
    setIframeHeight(document.getElementById('content'));
};