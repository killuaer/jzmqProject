var table;
var form;
var laydate;

layui.use(['form', 'laydate'], function() {
    form = layui.form;
    laydate = layui.laydate;
    laydate.render({
        elem: '#wtDate'
    });
    form.render();
});
layui.use(['laydate', "address"], function() {
    laydate = layui.laydate;
    layui.address("province","city","area",'广东省','广州市','天河区');
    layui.address("rProvince","rCity","rTown",'广东省','广州市','天河区');
//        form.render();

});
layui.config({
    base: "../assets/layui-v2.2.5/layui/"
}).use(['layer', 'jquery', "address"], function() {
    layui.address("province","city","area",'广东省','广州市','天河区');
    layui.address("rProvince","rCity","rTown",'广东省','广州市','天河区');
})
function ypxxTemplate(ypname_id){
    layui.use(['form', 'laydate'], function() {
        form = layui.form;
        laydate = layui.laydate;
        var html = "";
        var l = 0;
        for(var initial = 0; initial < ypxxData.length; initial++){
            if(ypxxData[initial].templeName == ypname_id){
                for(j = 0; j < ypxxData[initial].inputList.length; j++){
                    // 层级监听
                    var inputList = ypxxData[initial].inputList || [];
                    var obj = inputList[j];
                    if (obj.parent) {
                        levelsLinkage(obj.parent, obj.name, obj.options, obj.type,inputList);
                    }
                    var k = j;
                    if((k-l)%3 == 0){
                        html += '<tr>';
                    }
                    if(ypxxData[initial].inputList[j].type == 'text'){
                        if(ypxxData[initial].inputList[j].ishidden){
                            l++;
                            html += "<input type='hidden' name='"+ypxxData[initial].inputList[j].name+"' value='"+ypxxData[initial].inputList[j].default+"'/>"
                        } else {
                            html += "<td class='custom-form-label'>"+ypxxData[initial].inputList[j].label+"</td>";
                            html += "<td class='";
                            if(j <= 2){
                                html += "custom-table-td3";
                            }
                            if(ypxxData[initial].inputList[j].isSearch){
                                html += " search_parent";
                            }
                            html += "'>";
                            html += "<input name='"+ypxxData[initial].inputList[j].name+"' lay-verify='' autocomplete='off' class='layui-input custom-input-noborder' value='"+ypxxData[initial].inputList[j].default+"'>";
                            if(ypxxData[initial].inputList[j].isSearch){
                                html += "<i class='fa fa-search input_icon'></i>"   
                            }
                            html += "</td>";
                        } 
                    }
                    if(ypxxData[initial].inputList[j].type == 'select'){
                        html += "<td class='custom-form-label'>"+ypxxData[initial].inputList[j].label+"</td>";
                        html += "<td ";
                        if(j <= 2){
                            html += " class='custom-table-td3'";
                        }
                        html += ">";
                        html += generateSelect(ypxxData[initial].inputList)
                        html += "</td>";
                    } 
                    if(ypxxData[initial].inputList[j].type == 'multi'){
                        l++;
                        html += "<td class='custom-form-label'>"+ypxxData[initial].inputList[j].label+"</td>";
                        html += "<td colspan='5' style='height:30px;padding-left:10px;'>";
                        for(var m = 0; m < ypxxData[initial].inputList[j].value.length; m++){
                            html += "<input type='checkbox' name='"+ypxxData[initial].inputList[j].name+"' lay-skin='primary' title='"+ypxxData[initial].inputList[j].value[m]+"'>"
                        }
                        html += "</td>"; 
                    }
                    if(ypxxData[initial].inputList[j].type == 'date'){
                        html += "<td class='custom-form-label'>"+ypxxData[initial].inputList[j].label+"</td>";
                        html += "<td class='";
                        if(j<=2){
                            html += "custom-table-td3";
                        }
                        html += " date_parent'>";
                        html += "<input name='"+ypxxData[initial].inputList[j].name+"' lay-verify='' autocomplete='off' class='layui-input dateType' ";
                        if(ypxxData[initial].inputList[j].default !="" && ypxxData[initial].inputList[j].default != [] && ypxxData[initial].inputList[j].default != undefined && ypxxData[initial].inputList[j].default !=null){
                            html += "value='"+ypxxData[initial].inputList[j].default+"'" 
                        }
                        html += " readonly><i class='date_icon'></i></td>";
                    }
//                    console.log(k);
                    if((k-l)%3 == 2 || j == ypxxData[initial].inputList.length){
                        html += '</tr>';
                    }
                }
            }
        }
        $("#table_ypxx").html(html);
        $('.dateType').each(function(){
            laydate.render({
                elem: this,
                format: 'yyyy-MM-dd'
            });
        });
        form.render();
    });
//    setTimeout(function(){
//        form.render();
//    },2000)
    
}
function selectXm(){
    var queryParams = {
        type: 'add'
    };
    var btnOptions = {
        sure : {
            name : "suretestItemForm",
            arg : ["sure"]
        }
    };
    generateModel('select_testItem','add', "400px", "550px",'项目信息',queryParams, btnOptions);
}
function selectWtUnitFunc() {
    var queryParams = {};
    selectSystemObj('委托单位', 'select_wtUnit', '70%', '60%', true, queryParams);
}
function selectProjectFunc() {
    var queryParams = {};
    selectSystemObj('工程', 'select_project', '70%', '60%', true, queryParams);
}
function suretestItemForm(winId) {
    ypname_id = $('#test_item_id').val();
    $.when(ypxxTemplate(ypname_id)).done(function(data){
       form.render();
    })
    $("input[name='ypName']").val(ypname_id);
    layer.close(winId);
}
