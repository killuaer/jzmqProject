var table;
var index;
var roleIds;
window.app.grantRole = function() {
    $.ajax({
        type : "POST",
        url : "../user/getUser",
        data :  {id: $('#selectAccountId').val()}, 
        dataType : "json",
        success: function (data) {
            roleIds = data.roleIds ? data.roleIds.split(',') : new Array();
            layui.use(['table'], function() {
                var table   = layui.table,
                    layer   = layui.layer,
                    $       = layui.$,
                    form    = layui.form;
                var mytbl;
                //.存储当前页数据集
                var pageData = [];

                //.渲染完成回调
                var myDone = function(res) {
                    //.假设你的表格指定的 id="maintb"，找到框架渲染的表格
                    var tbl = $('#grantRoleTable').next('.layui-table-view');
                    //.记下当前页数据，Ajax 请求的数据集，对应你后端返回的数据字段
                    pageData = res.data;

                    for (var i=0;i< res.data.length;i++) {
                        if (roleIds && roleIds.length > 0) {
                            for (var j = 0; j < roleIds.length; j++) {
                                //数据id和要勾选的id相同时checkbox选中
                                if (res.data[i].id == roleIds[j]) {
                                    //这里才是真正的有效勾选
                                    res.data[i]["LAY_CHECKED"]='true';
                                    //找到对应数据改变勾选样式，呈现出选中效果
                                    var index= res.data[i]['LAY_TABLE_INDEX'];
                                    $('tr[data-index=' + index + '] input[type="checkbox"]').prop('checked', true);
                                    $('tr[data-index=' + index + '] input[type="checkbox"]').next().addClass('layui-form-checked');
                                }
                            }
                        }
                    }
                    //.PS：table 中点击选择后会记录到 table.cache，没暴露出来，也不能 mytbl.renderForm('checkbox');
                    //.暂时只能这样渲染表单
                    form.render('checkbox');
                };

                //.渲染表格
                mytbl = table.render({
                    elem: '#grantRoleTable',
                    height: 400,
                    url: '../role/listRole',
                    even: true, //开启隔行背景
                    size: 'sm', //小尺寸的表格
                    page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                        layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'], //自定义分页布局
                        //,curr: 5 //设定初始在第 5 页
                        groups: 1, //只显示 1 个连续页码
                        first: false, //不显示首页
                        last: false, //不显示尾页
                        theme: '#008aec'
                    },
                    where: {},
                    done: myDone,
                    cols: [
                            [
                                {type: 'checkbox', width: '4%'},
                                {title:'序号', width: '10%',align: 'center',templet: function (a) {return a.LAY_INDEX}},
                                {field: 'name', width : '86%', title: '角色名称', sort: true}
                            ]
                        ]
                });

                //.监听选择，记录已选择项
                table.on('checkbox(grantRoleTableFilter)', function(obj) {
                    //.全选或单选数据集不一样
                    var data = obj.type == 'one' ? [obj.data] : pageData;
                    //.遍历数据
                    $.each(data, function(k, v) {
                        //.假设你数据中 id 是唯一关键字
                        if (obj.checked) {
                            //.增加已选中项
                            layui.data('checked', {
                                key: v.id, value: v
                            });
                            var jud = true;
                            if (roleIds && roleIds.length > 0) {
                                for (var j = 0; j < roleIds.length; j++) {
                                    if (roleIds[j] == v.id) {
                                        jud =false;
                                    }
                                }                                
                            }
                            if (jud) {
                                roleIds.push(v.id);
                            }
                        } else {
                            //.删除
                            layui.data('checked', {
                                key: v.id, remove: true
                            });
                            if (roleIds && roleIds.length > 0) {
                                for (var j = 0; j < roleIds.length; j++) {
                                    if (roleIds[j] == v.id) {
                                        roleIds.splice(j, 1);
                                    }
                                }
                            }
                        }
                    });
                });

            });
        },
        error: function (msg) {
            alert(msg.responseText);
        }
    });

    this.queryList  = function() {
        var name = $('#roleName_grantRole').val();
        table.reload('grantRoleTable', {
          where: { //设定异步数据接口的额外参数，任意设
            name: name
          },
          page: {
            curr: 1 //重新从第 1 页开始
          }
        });
    }
    
    return this;
}
var grantRole = window.app.grantRole();

function submitGrantRoleForm(i,operM) {
    index = i;
    $.ajax({
        type: "POST",
        url: "../user/grantRoles",
        data: {
            roleIds: roleIds.join(","),
            id: $('#selectAccountId').val()
        },
        dataType: "json",
        success: function(data) {
            if (data.status == "success") {
                layer.close(index);
                layer.open({title: '提示', content: '操作成功!'});
            } else {
                MSG(data.msg);
            }
        },
        error: function(msg) {
            layer.open({title: '提示', content: '错误了!'});
        }
    });
}