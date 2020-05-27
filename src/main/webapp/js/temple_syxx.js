var fsjcData = [
    {
        templeName: "混凝土抗压"
        , templeParameters: [{
            templeParametersName:"抗压强度试验"
            ,tableThead: [
            {
                "name": "试样序号"
                , "width": "5%"
                , "rowspan": "2"
            },
            {
                "name": "试样尺寸(mm)"
                , "colspan": "3"
                , "width": "45%"
                , "rowChild": [{
                    '0': ['长', '宽', '高']
                }]
            }, {
                "name": "极限荷载(kN)"
                , "width": "10%"
                , "rowspan": "2"
            }, {
                "name": "尺寸转换系数"
                , "width": "10%"
                , "rowspan": "2"
            }, {
                "name": "抗压强度单个值(MPa)"
                , "width": "10%"
                , "rowspan": "2"
            }, {
                "name": "强度代表值"
                , "width": "10%"
                , "rowspan": "2"
            }, {
                "name": "达到设计强度等级%"
                , "width": "10%"
                , "rowspan": "2"
            }
            ]
        , tableTbody: [{
                "0": [{
                        "name": "1"
                        , "type": "number"
            }, {
                        "name": "l1"
                        , "type": "text"
                        , "id": "l1"
                        , "class": "layui-input custom_input"
                        , "parameter": "'$(this)',['ky1','endky','endkp']"
            }, {
                        "name": "b1"
                        , "type": "text"
                        , "id": "b1"
                        , "class": "layui-input custom_input_table"
                        , "parameter": "'$(this)',['ky1','endky','endkp']"
            }, {
                        "name": "h1"
                        , "type": "text"
                        , "id": "h1"
                        , "class": "layui-input custom_input_table"
                        , "event": "'$(this)','kyChild1,endkyChild','kyqd'"
            }, {
                        "name": "kn1"
                        , "class": "layui-input custom_input_table"
                        , "type": "text"
                        , "id": "kn1"
                        , "parameter": "'$(this)',['ky1','endky','endkp']"
            }, {
                        "name": "ccxs"
                        , "class": "layui-input custom_input_table"
                        , "type": "text"
                        , "id": "ccxs"
                        , "rowspan": "3"
                        , "isReadOnly": 'true'
            }, {
                        "name": "ky1"
                        , "class": "layui-input custom_input_table"
                        , "type": "text"
                        , "id": "ky1"
                        , "isReadOnly": 'true'
                        , "event": ['kyEvent','l1,b1,kn1,ccxs']
            }, {
                        "name": "endky"
                        , "class": "layui-input custom_input_table"
                        , "type": "text"
                        , "id": "endky"
                        , "rowspan": "3"
                        , "isReadOnly": 'true'
                        , "event": ['endkyEvent','ky1,ky2,ky3']
            }, {
                        "name": "endkp"
                        , "class": "layui-input custom_input_table"
                        , "type": "text"
                        , "id": "endkp"
                        , "rowspan": "3"
                        , "isReadOnly": 'true'
                        , "event": ['endkpEvent','endky']
            }
        ]
            }, {"1": [{
                        "name": "2"
                        , "type": "number"
            }, {
                        "name": "l2"
                        , "type": "text"
                        , "id": "l2"
                        , "class": "layui-input custom_input"
                        , "parameter": "'$(this)',['ky2','endky','endkp']"
            }, {
                        "name": "b2"
                        , "type": "text"
                        , "id": "b2"
                        , "class": "layui-input custom_input_table"
                        , "parameter": "'$(this)',['ky2','endky','endkp']"
            }, {
                        "name": "h2"
                        , "type": "text"
                        , "id": "h2"
                        , "class": "layui-input custom_input_table"
                        , "parameter": "'$(this)',['ky2','endky','endkp']"
            }, {
                        "name": "kn2"
                        , "class": "layui-input custom_input_table"
                        , "type": "text"
                        , "id": "kn2"
                        , "parameter": "'$(this)',['ky2','endky','endkp']"
            }, {
                        "name": "ky2"
                        , "class": "layui-input custom_input_table"
                        , "type": "text"
                        , "id": "ky2"
                        , "isReadOnly": 'true'
                        , "event": ['kyEvent','l2,b2,kn2,ccxs']
            },
        ]
            }, {"2": [{
                        "name": "3"
                        , "type": "number"
            }, {
                        "name": "l3"
                        , "type": "text"
                        , "id": "l3"
                        , "class": "layui-input custom_input"
                        , "parameter": "'$(this)',['ky3','endky','endkp']"
            }, {
                        "name": "b3"
                        , "type": "text"
                        , "id": "b3"
                        , "class": "layui-input custom_input_table"
                        , "parameter": "'$(this)',['ky3','endky','endkp']"
            }, {
                        "name": "h3"
                        , "type": "text"
                        , "id": "h3"
                        , "class": "layui-input custom_input_table"
                        , "parameter": "'$(this)',['ky3','endky','endkp']"
            }, {
                        "name": "kn3"
                        , "class": "layui-input custom_input_table"
                        , "type": "text"
                        , "id": "kn3"
                        , "parameter": "'$(this)',['ky3','endky','endkp']"
            },{
                        "name": "ky3"
                        , "class": "layui-input custom_input_table"
                        , "type": "text"
                        , "id": "ky3"
                        , "isReadOnly": 'true'
                        , "event": ['kyEvent','l3,b3,kn3,ccxs']
            }
        ]
            }
            ]
        , tableTheadNum: 2
        , tableTbodyChildNum: 2
        , tableTbodyNum: 3
        }]
    }
]










































