var table;
var form;
var laydate;
sjlrTemplate();
    layui.use(['laydate'], function() {
        laydate = layui.laydate;
        
//        form.render();

    });
    function sjlrTemplate(){
        layui.use(['form', 'laydate'], function() {
            form = layui.form;
            laydate = layui.laydate;
            var html = "<div class='custom-form-title'>试验信息</div>"
            html += "<table class='table_temple'>";
            for(var initial = 0; initial < fsjcData.length; initial++){
                if(fsjcData[initial].templeName == '混凝土抗压'){
                    for(var coefficient = 0; coefficient < fsjcData[initial].templeParameters.length; coefficient++){
                        console.log(fsjcData[initial].templeParameters[coefficient]);
                        html += "<thead style='background-color:#D0E8F4;'>";
                        html += "<tr>";
                        html += "<td colspan='50' class='custom-form-second-title'>"+fsjcData[initial].templeParameters[coefficient].templeParametersName+"</td>";
                        html += "</tr>";
                        html += "<tr>"
                        console.log(fsjcData[initial].templeParameters[coefficient].tableThead);
                        if(fsjcData[initial].templeParameters[coefficient].tableThead){
                            for(i = 0; i < fsjcData[initial].templeParameters[coefficient].tableThead.length; i++){
                                html += "<th ";
                                if(fsjcData[initial].templeParameters[coefficient].tableThead[i].colspan){
                                    html += "colspan="+fsjcData[initial].templeParameters[coefficient].tableThead[i].colspan;
                                } else if(fsjcData[initial].templeParameters[coefficient].tableThead[i].rowspan){
                                    html += "rowspan="+fsjcData[initial].templeParameters[coefficient].tableThead[i].rowspan;
                                }
                                html += " style='width:"+fsjcData[initial].templeParameters[coefficient].tableThead[i].width+"'>"+fsjcData[initial].templeParameters[coefficient].tableThead[i].name+"</th>";
                            }
                            html += "</tr>";
                            if(fsjcData[initial].templeParameters[coefficient].tableTheadNum>1){
                                html += "<tr>";
                                for(i = 0; i < fsjcData[initial].templeParameters[coefficient].tableThead.length; i++){
                                    var rowNum = 0;
                                    if(fsjcData[initial].templeParameters[coefficient].tableThead[i].rowChild){
                                        for(j = 0; j < fsjcData[initial].templeParameters[coefficient].tableThead[i].rowChild[0][rowNum].length; j++){
                                            html += "<th>"+fsjcData[initial].templeParameters[coefficient].tableThead[i].rowChild[0][rowNum][j]+"</th>";
                                        }
                                        rowNum ++;
                                    }
                                }
                                html += "</tr>";
                            }
                            html += "</thead>";
                        }
                        if(fsjcData[initial].templeParameters[coefficient].tableTbody){
                            html += "<tbody>";
                            for(i = 0; i < fsjcData[initial].templeParameters[coefficient].tableTbody.length; i++){
                                html += "<tr>";
                                    for(j = 0; j < fsjcData[initial].templeParameters[coefficient].tableTbody[i][i].length; j++){
                                        // 层级监听
                                        var inputList = fsjcData[initial].templeParameters[coefficient].tableTbody[i][i] || [];
                                        var obj = inputList[j];
                                        if (obj.parent) {
                                            levelsLinkage(obj.parent, obj.name, obj.options, obj.type,inputList);
                                        }
                                        html += "<td ";
                                        var templeDate = fsjcData[initial].templeParameters[coefficient].tableTbody[i][i];
                                        if(templeDate[j].colspan){
                                            html += "colspan="+templeDate[j].colspan;
                                        } else if(templeDate[j].rowspan){
                                            html += "rowspan="+templeDate[j].rowspan;
                                        }
                                        html += ">";
                                        if(templeDate[j].type == 'text'){
                                            html += "<input id='"+templeDate[j].id+"' class='"+templeDate[j].class+"' name="+templeDate[j].name+" onchange=calculate("+templeDate[j].parameter+",'"+fsjcData[initial].templeName+"') />";
                                        } else if(templeDate[j].type == 'select'){
                                            html += generateSelect(templeDate);
                                        } else if(templeDate[j].type == 'date'){
                                            html += "<input name='"+templeDate[j].name+"' lay-verify='' autocomplete='off' class='layui-input dateType' ";
                                            if(templeDate[j].default !="" && templeDate[i].default != [] && templeDate[i].default != undefined && templeDate[i].default !=null){
                                                html += "value='"+templeDate[i].default+"' readonly>";
                                            }
                                        }
                                        html += "</td>"
                                    }

                    //            }
                                html += "</tr>";
                            }

                            html += "</tbody>";
                        }
                        
                        
                    }

                }
            }
            html += "</table>";

            $("#test_parameters").html(html);
            $('.dateType').each(function(){
                laydate.render({
                    elem: this,
                    format: 'yyyy-MM-dd'
                });
            });
        });
//        form.render(); 
    }
	function init() {
		var html = $.templates.indoorTest_info.render();
		window.work_area.empty();
		window.work_area.html(html);
		sjlrTemplate();
	}