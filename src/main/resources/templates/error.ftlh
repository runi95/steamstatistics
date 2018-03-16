<#import "master.ftlh" as m/>
<@m.master>
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
                    <li class="active"><a href="/">Frontpage</a></li>
                    <li><a href="/profile">Profile</a></li>
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
<div style="font-size: 2em;">
    You have encountered an <span style="color: #c9302c;">error</span>!
</div>
<br />
<div style="font-size: 1.25em;">
    Please try to...<br />
    1. Try to <a onclick="refresh()" href="#">refresh</a> the page.<br />
    2. Go back to the <a href="/">frontpage</a>.
</div>
<script>
    function refresh() {
        location.reload();
    }
</script>
</@m.master>