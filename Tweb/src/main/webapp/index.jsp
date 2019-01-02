<%@page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Telecom Web Application</title>

    <!-- Bootstrap -->
    <link href="/bootstrap/css/bootstrap.css" rel="stylesheet">

</head>
<body>
<form>
    <div class="form-group">
        <label for="tel">电话号码</label>
        <input type="text" class="form-control" name="tel" id="tel" placeholder="请输入查询的电话号码">
    </div>
    <div class="form-group">
        <label for="calltime">时间</label>
        <input type="text" class="form-control" id="calltime" placeholder="请输入查询时间">
    </div>
    <button type="button" class="btn btn-default" onclick="queryData()">查询</button>
</form>

<script src="/jquery/jquery-2.1.1.min.js"></script>
<script src="/bootstrap/js/bootstrap.js"></script>
<script>

    // 查询数据
    function queryData() {
        // 跳转页面，查询数据（同步，异步）
        window.location.href = "/queryData?tel="+$("#tel").val()+"&calltime="+$("#calltime").val();

        //$.ajax();
    }

</script>
</body>
</html>