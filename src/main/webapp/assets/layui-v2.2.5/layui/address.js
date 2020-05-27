layui.define(["form","jquery"],function(exports){
    var form = layui.form,
    $ = layui.jquery,
    Address = function(){};

    Address.prototype.provinces = function(pname,cname,aname,pval,cval,aval) {
        //加载省数据
        var proHtml = '',that = this;
        $.get("../assets/layui-v2.2.5/layui/address.json", function (data) {
            for (var i = 0; i < data.length; i++) {
            	if (pval != null && pval != '' && pval== data[i].name){
                    proHtml += '<option selected value="' + data[i].name + '">' + data[i].name + '</option>';
                    that.citys(data[i].childs,cname,aname,cval,aval);
            	} else{
                    proHtml += '<option value="' + data[i].name + '">' + data[i].name + '</option>';
            	}
            }
            //初始化省数据
            $("select[name="+pname+"]").append(proHtml);
            form.render();
            form.on('select('+pname+')', function (proData) {
                console.log("##@@!!");
                $("select[name="+aname+"]").html('<option value="">请选择县/区</option>');
                var value = proData.value;
                if (value && value != '') {
                    that.citys(data[$(this).index() - 1].childs,cname,aname);
                } else {
                    $("select[name="+cname+"]").attr("disabled", "disabled");
                }
            });
        })
    }

    //加载市数据
    Address.prototype.citys = function(citys,cname,aname,cval,aval) {
        var cityHtml = '<option value="">请选择市</option>',that = this;
        for (var i = 0; i < citys.length; i++) {
        	if (cval && cval != '' && cval == citys[i].name){
        		cityHtml += '<option selected value="' + citys[i].name + '">' + citys[i].name + '</option>';
        		that.areas(citys[i].childs,aname,aval);
        	} else {
        		cityHtml += '<option value="' + citys[i].name + '">' + citys[i].name + '</option>';
        	}
            
        }
        $("select[name="+cname+"]").html(cityHtml).removeAttr("disabled");
        form.render();
        form.on('select('+cname+')', function (cityData) {
            var value = cityData.value;
            if (value && value != '') {
                that.areas(citys[$(this).index() - 1].childs,aname);
            } else {
                $("select[name="+aname+"]").attr("disabled", "disabled");
            }
        });
    }

    //加载县/区数据
    Address.prototype.areas = function(areas,aname,aval) {
        var areaHtml = '<option value="">请选择县/区</option>';
        for (var i = 0; i < areas.length; i++) {
        	if (aval && aval != '' && aval== areas[i].name){
        		areaHtml += '<option selected value="' + areas[i].name + '">' + areas[i].name + '</option>';
        	} else {
        		areaHtml += '<option value="' + areas[i].name + '">' + areas[i].name + '</option>';
        	}
        }
        $("select[name="+aname+"]").html(areaHtml).removeAttr("disabled");
        form.render();
    }

    var address = new Address();
    exports("address",function(pname,cname,aname,pval,cval,aval){
        address.provinces(pname,cname,aname,pval,cval,aval);
    });
})