var ypxxData = [
    {
        templeName: "混凝土抗压"
        , inputList: [
            {
                "type": "text"
                , "label": "样品编号"
                , "name": "syNum"
                , "default": ""
            }, {
                "type": "text"
                , "label": "样品名称"
                , "name": "ypName"
                , "default": ""
            }, {
                "type": "select"
                , "label": "执行标准"
                , "name": "ky_gb_std"
                , "options": ["","GB 50081-2002", "GB 50299-1999"]
                , "value": ["","GB 50081-2002", "GB 50299-1999"]
                , "default": ""
            },{
                "type": "multi"
                , "label": "检测参数"
                , "name": "fac_date"
                , "value": ["抗压强度"]
                , "default": ""
            }, {
                "type": "text"
                , "label": "代表批量(m³)"
                , "name": "db_num"
                , "default": ""
            }, {
                "type": "select"
                , "label": "混凝土结构工程施工质量验收规范"
                , "name": "ky_gb_std"
                , "options": ["","GB 50204-2002", "GB 50204-2015"]
                , "value": ["","GB 50204-2002", "GB 50204-2015"]
                , "default": ""
            }, {
                "type": "text"
                , "label": "样品数量"
                , "name": "yp_num"
                , "default": ""
            }, {
                "type": "select"
                , "label": "设计强度等级"
                , "name": "dengji"
                , "options": ["","C10", "C20", "C30", "C40", "C50"]
                , "value": ["","C10", "C20", "C30", "C40", "C50"]
                , "default": "C30"
            }, {
                "type": "select"
                , "label": "规格尺寸(mm)"
                , "name": "guige"
                , "options": ["","100×100×100", "150×150×150", "200×200×200"]
                , "value": ["","100×100×100", "150×150×150", "200×200×200"]
                , "default": "100×100×100"
            }, {
                "type": "text"
                , "label": "长11"
                , "name": "l1"
                , "ishidden": true
                , "textValue": [
                    {
                        "parentVal": ["100×100×100"],
                        "childVal": "100",
                        "default": ""
                    },{
                        "parentVal": ["150×150×150"],
                        "childVal": "150",
                        "default": ""
                    },{
                        "parentVal": ["200×200×200"],
                        "childVal": "200",
                        "default": ""
                    }
                ]
                , "parent": "guige"
                , "default": "100"
            }, {
                "type": "text"
                , "label": "宽1"
                , "name": "b1"
                , "ishidden": true
                , "textValue": [
                    {
                        "parentVal": ["100×100×100"],
                        "childVal": "100",
                        "default": ""
                    },{
                        "parentVal": ["150×150×150"],
                        "childVal": "150",
                        "default": ""
                    },{
                        "parentVal": ["200×200×200"],
                        "childVal": "200",
                        "default": ""
                    }
                ]
                , "parent": "guige"
                , "default": "100"
            }, {
                "type": "text"
                , "label": "高1"
                , "name": "h1"
                , "ishidden": true
                , "textValue": [
                    {
                        "parentVal": ["100×100×100"],
                        "childVal": "100",
                        "default": ""
                    },{
                        "parentVal": ["150×150×150"],
                        "childVal": "150",
                        "default": ""
                    },{
                        "parentVal": ["200×200×200"],
                        "childVal": "200",
                        "default": ""
                    }
                ]
                , "parent": "guige"
                , "default": "100"
            }, {
                "type": "text"
                , "label": "长2"
                , "name": "l2"
                , "ishidden": true
                , "textValue": [
                    {
                        "parentVal": ["100×100×100"],
                        "childVal": "100",
                        "default": ""
                    },{
                        "parentVal": ["150×150×150"],
                        "childVal": "150",
                        "default": ""
                    },{
                        "parentVal": ["200×200×200"],
                        "childVal": "200",
                        "default": ""
                    }
                ]
                , "parent": "guige"
                , "default": "100"
            }, {
                "type": "text"
                , "label": "宽2"
                , "name": "b2"
                , "ishidden": true
                , "textValue": [
                    {
                        "parentVal": ["100×100×100"],
                        "childVal": "100",
                        "default": ""
                    },{
                        "parentVal": ["150×150×150"],
                        "childVal": "150",
                        "default": ""
                    },{
                        "parentVal": ["200×200×200"],
                        "childVal": "200",
                        "default": ""
                    }
                ]
                , "parent": "guige"
                , "default": "100"
            }, {
                "type": "text"
                , "label": "高2"
                , "name": "h2"
                , "ishidden": true
                , "textValue": [
                    {
                        "parentVal": ["100×100×100"],
                        "childVal": "100",
                        "default": ""
                    },{
                        "parentVal": ["150×150×150"],
                        "childVal": "150",
                        "default": ""
                    },{
                        "parentVal": ["200×200×200"],
                        "childVal": "200",
                        "default": ""
                    }
                ]
                , "parent": "guige"
                , "default": "100"
            }, {
                "type": "text"
                , "label": "长3"
                , "name": "l3"
                , "ishidden": true
                , "textValue": [
                    {
                        "parentVal": ["100×100×100"],
                        "childVal": "100",
                        "default": ""
                    },{
                        "parentVal": ["150×150×150"],
                        "childVal": "150",
                        "default": ""
                    },{
                        "parentVal": ["200×200×200"],
                        "childVal": "200",
                        "default": ""
                    }
                ]
                , "parent": "guige"
                , "default": "100"
            }, {
                "type": "text"
                , "label": "宽3"
                , "name": "b3"
                , "ishidden": true
                , "textValue": [
                    {
                        "parentVal": ["100×100×100"],
                        "childVal": "100",
                        "default": ""
                    },{
                        "parentVal": ["150×150×150"],
                        "childVal": "150",
                        "default": ""
                    },{
                        "parentVal": ["200×200×200"],
                        "childVal": "200",
                        "default": ""
                    }
                ]
                , "parent": "guige"
                , "default": "100"
            }, {
                "type": "text"
                , "label": "高3"
                , "name": "h3"
                , "ishidden": true
                , "textValue": [
                    {
                        "parentVal": ["100×100×100"],
                        "childVal": "100",
                        "default": ""
                    },{
                        "parentVal": ["150×150×150"],
                        "childVal": "150",
                        "default": ""
                    },{
                        "parentVal": ["200×200×200"],
                        "childVal": "200",
                        "default": ""
                    }
                ]
                , "parent": "guige"
                , "default": "100"
            }, {
                "type": "text"
                , "label": "尺寸系数"
                , "name": "cc_xishu"
                , "ishidden": true
                , "textValue": [
                    {
                        "parentVal": ["100×100×100"],
                        "childVal": "0.95",
                        "default": ""
                    },{
                        "parentVal": ["150×150×150"],
                        "childVal": "1.0",
                        "default": ""
                    },{
                        "parentVal": ["200×200×200"],
                        "childVal": "1.05",
                        "default": ""
                    }
                ]
                , "parent": "guige"
                , "default": "0.95"
            }, {
                "type": "select"
                , "label": "养护条件"
                , "name": "yanghu"
                , "options": ["","标准养护", "同条件养护", "自然养护"]
                , "value": ["","标准养护", "同条件养护", "自然养护"]
                , "default": "标准养护"
            }, {
                "type": "select"
                , "label": "是否拆模"
                , "name": "sfcm"
                , "options": ["","是", "否"]
                , "value": ["","是", "否"]
                , "default": "否"
            }, {
                "type": "select"
                , "label": "是否自拌"
                , "name": "sfzb"
                , "options": ["","是", "否"]
                , "value": ["","是", "否"]
                , "default": ""
            }, {
                "type": "select"
                , "label": "是否泵送"
                , "name": "sfbs"
                , "options": ["","是", "否"]
                , "value": ["","是", "否"]
                , "default": ""
            }, {
                "type": "date"
                , "label": "成型日期"
                , "name": "syNum"
                , "default": ""
            }, {
                "type": "text"
                , "label": "龄期(d)"
                , "name": "lingqi_sy"
                , "default": ""
            }, {
                "type": "date"
                , "label": "要求试验日期"
                , "name": "yq_ch_date"
                , "default": ""
            }, {
                "type": "text"
                , "label": "等效养护龄期"
                , "name": "lingqi_equ"
                , "default": ""
            }, {
                "type": "text"
                , "label": "同条件养护折算系数"
                , "name": "yh_xishu"
                , "textValue": [
                    {
                        "parentVal": ["标准养护"],
                        "childVal": "1.0",
                        "default": ""
                    },{
                        "parentVal": ["同条件养护"],
                        "childVal": "0.88",
                        "default": ""
                    },{
                        "parentVal": ["自然养护"],
                        "childVal": "1.0",
                        "default": ""
                    }
                ]
                , "parent": "yanghu"
                , "default": "1.0"
            }, {
                "type": "text"
                , "label": "混凝土流水号"
                , "name": "hnt_seqid"
                , "default": ""
            }, {
                "type": "text"
                , "label": "累计养护温度(℃)"
                , "name": "yh_wd"
                , "default": ""
            }, {
                "type": "select"
                , "label": "抗渗等级(℃)"
                , "name": "dengks"
                , "options": ["","P4", "P6", "P8", "P10", "P12", "P14", "P16"]
                , "value": ["","P4", "P6", "P8", "P10", "P12", "P14", "P16"]
                , "default": ""
            }, {
                "type": "text"
                , "label": "受压面积(mm²)"
                , "name": "mianji"
                , "textValue": [
                    {
                        "parentVal": ["100×100×100"],
                        "childVal": "10000",
                        "default": ""
                    },{
                        "parentVal": ["150×150×150"],
                        "childVal": "22500",
                        "default": ""
                    },{
                        "parentVal": ["200×200×200"],
                        "childVal": "40000",
                        "default": ""
                    }
                ]
                , "parent": "guige"
                , "default": "10000"
            },{
                "type": "text"
                , "label": "厂家编号"
                , "name": "fac_num"
                , "default": ""
            }, {
                "type": "text"
                , "label": "商品砼生产厂家"
                , "name": "hnt_factory"
                , "isSearch" : true
                , "default": ""
            }, {
                "type": "date"
                , "label": "生产日期"
                , "name": "fac_date"
                , "default": ""
            }
//            , 
//            
//            
//            
//            
//            
//            {
//                "type": "multi"
//                , "label": "检测项目"
//                , "name": "xm"
//                , "default": ""
//                , "options": [
//             		"风量", "功率", "供冷量", "供热量", "噪声"
//             	]
//                , "required": "required"
//                , "msg": "请选择检测项目"
//            }, {
//                "type": "text"
//                , "label": "试样编号"
//                , "name": "syNum"
//                , "default": ""
//            }, {
//                "type": "select"
//                , "label": "类型"
//                , "name": "type"
//                , "options": ["","井盖", "井箅"]
//                , "value": ["","井盖", "井箅"]
//                , "default": ""
//            }, {
//                "type": "text"
//                , "label": "试样编号"
//                , "name": "syNum"
//
//                , "textValue": [
//                    {
//                        "parentVal": ["井盖"],
//                        "childVal": "jingai",
//                        "default": ""
//                    },{
//                        "parentVal": ["井箅"],
//                        "childVal": "jinli",
//                        "default": ""
//                    }
//                ]
//                , "parent": "type"
//            }, {
//                "type": "select"
//                , "label": "类型参数"
//                , "name": "leixing"
//                , "options": [
//                    {
//                        "parentVal": ["井盖"],
//                        "childVal": ["A15", "B125", "C250", "D400", "E600", "F900"],
//                        "default": ""
//                    },
//                    {
//                        "parentVal": ["井箅"],
//                        "childVal": ["A150", "B250", "C400"],
//                        "default": ""
//                    }
//                ]
//                , "parent": "type"
//            }
        ]
    }
]