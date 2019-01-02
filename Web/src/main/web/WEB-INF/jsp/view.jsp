<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>CT Web Application</title>

    <!-- Bootstrap -->
    <link href="/bootstrap/css/bootstrap.css" rel="stylesheet">
</head>
<body>
<div id="main" style="width: 600px;height:400px;"></div>
<script src="/jquery/jquery-2.1.1.min.js"></script>
<script src="/bootstrap/js/bootstrap.js"></script>
<script src="/script/echarts.js"></script>
<script>

    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    // {},[]
    // JSON对象
    // JSON字符串 = "{ name:'zhangsan', age:20 }"
    var option = {
        title: {
            text: '通话信息统计结果'
        },
        tooltip: {},
        legend: {
            data:['通话次数']
        },
        xAxis: {
            data: [
                <c:forEach items="${calllogs}" var="calllog"  >
                ${calllog.calltime},
                </c:forEach>
            ]
        },
        yAxis: {},
        series: [{
            name: '通话次数',
            type: 'bar',
            data: [
                <c:forEach items="${calllogs}" var="calllog"  >
                ${calllog.sumcount},
                </c:forEach>
            ]
        }]
    };

    option = {
        title : {
            text: '通话信息统计结果',
            subtext: '${calllog.username}'
        },
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['通话次数','通话时长']
        },
        toolbox: {
            show : true,
            feature : {
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        xAxis : [
            {
                type : 'category',
                data : [
                    <c:forEach items="${calllogs}" var="calllog"  >
                    ${calllog.calltime},
                    </c:forEach>
                ]
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:'通话次数',
                type:'bar',
                data:[
                    <c:forEach items="${calllogs}" var="calllog"  >
                    ${calllog.sumcount},
                    </c:forEach>
                ],
                markPoint : {
                    data : [
                        {type : 'max', name: '最大值'},
                        {type : 'min', name: '最小值'}
                    ]
                },
                markLine : {
                    data : [
                        {type : 'average', name: '平均值'}
                    ]
                }
            },
            {
                name:'通话时长',
                type:'bar',
                data:[
                    <c:forEach items="${calllogs}" var="calllog"  >
                    ${calllog.sumduration},
                    </c:forEach>
                ],
                markPoint : {
                    data : [
                        {name : '年最高', value : 182.2, xAxis: 7, yAxis: 183},
                        {name : '年最低', value : 2.3, xAxis: 11, yAxis: 3}
                    ]
                },
                markLine : {
                    data : [
                        {type : 'average', name : '平均值'}
                    ]
                }
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
</script>
</body>
</html>
