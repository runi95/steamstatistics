<!--
<div style="margin-top: 20px;font-size: 2em; color: #3eb63e;">
    Authentication successful! Please wait...
</div>
-->
<!DOCTYPE html>
<html lang="en">
<head>
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
<script>
    initializeProfile();
    function initializeProfile() {
        $.ajax({
            type: "GET",
            dataType: "json",
            url: "/getprofile",
            complete: function () {
                $(location).attr('href', '/')
            }
        });
    }
</script>
</body>
</html>