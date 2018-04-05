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
    <#if _csrf??>
        <meta name="csrf_name" content="<#if _csrf.parameterName??>${_csrf.parameterName}</#if>">
        <meta name="csrf_value" content="<#if _csrf.token??>${_csrf.token}</#if>">
    </#if>

    <title>Steam Users.info</title>

    <!-- Bootstrap Core CSS -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="/css/steamstatistics.css" rel="stylesheet">

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

    <script src="/js/steamstatistics.js"></script>
</head>
<body>
<noscript><h2 style="color: #ff0000">Javascript is required to view this website, please enable Javascript and refresh
    the page</h2></noscript>
<div style="position: fixed; left: 0; right: 0; z-index: 100;">
    <div id="infobanner" class="banner"></div>
</div>
<div style="padding-bottom: 60px;">
    <nav class="navbar navbar-inverse navbar-fixed-top">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#mainNav">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/">SteamUsers.info</a>
            </div>
            <div class="collapse navbar-collapse" id="mainNav">
                <ul class="nav navbar-nav">
                    <li id="frontpagenav"><a href="/">Frontpage</a></li>
                    <#if steamid??>
                        <li id="profilenav"><a href="/profile/${steamid}">Profile</a></li>
                    <#else>
                        <li id="profilenav"><a href="/login">Profile</a></li>
                    </#if>
                    <li id="statisticsnav"><a href="/statistics">Statistics</a></li>
                    <div class="navbar-form navbar-left" style="margin-left: 16px; margin-right: 16px;">
                    <form class="input-group" onsubmit="search(); return false;">
                        <input id="srchinput" type="text" class="form-control" placeholder="Search">
                        <div class="input-group-btn">
                            <button class="btn btn-default" type="submit">
                                <i class="glyphicon glyphicon-search"></i>
                            </button>
                        </div>
                    </form>
                    </div>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <#if steamid??>
                        <ul class="nav navbar-nav">
                            <li id="steamlogout"><a href="/logout">Logout</a></li>
                        </ul>
                    <#else>
                        <div id="steamhref">
                            <div style="padding: 7px 5px;">
                                <a href="/login">
                                    <img src="/css/img/sits_01.png" alt="Steam Login">
                                </a>
                            </div>
                        </div>
                    </#if>
                </ul>
            </div>
        </div>
    </nav>
</div>
    <#nested/>
<!-- /container -->

</body>
</html>
</#macro>