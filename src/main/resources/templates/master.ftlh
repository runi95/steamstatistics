<#macro master>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <meta name="steamid" content="<#if steamid??>${steamid}</#if>">
    <meta name="request_steamid" content="<#if request_steamid??>${request_steamid}</#if>">
    <meta name="csrf_name" content="${_csrf.parameterName}">
    <meta name="csrf_value" content="${_csrf.token}">

    <title>Steam Users.info</title>

    <!-- Bootstrap Core CSS -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="/css/steamstatistics.css" rel="stylesheet">
    <link href="/css/frontpage.css" rel="stylesheet">
    <link href="/css/graphs.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- jQuery -->
    <script src="/js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="/js/bootstrap.min.js"></script>
</head>
<body>
<noscript><h2 style="color: #ff0000">Javascript is required to view this website, please enable Javascript and refresh
    the page</h2></noscript>
    <#nested/>
<!-- /container -->

</body>
</html>
</#macro>