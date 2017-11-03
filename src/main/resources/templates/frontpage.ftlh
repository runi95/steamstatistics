<#import "master.ftlh" as m/>
<@m.master>

<style>
    .longestfriend {
        background: repeating-linear-gradient(
                -55deg,
                #222,
                #222 10px,
                #282828 10px,
                #282828 20px
        );
        padding: 20px;
        margin: 0 auto;
        min-height: 32px;
    }

    .roundedAvatar img {
        border-radius: 50%;
        border-color: #1e1f26;
    }

    .monthly {
        border: 3px solid #1e1f26;
        background: #222222;
        padding: 10px;
    }

    .monthly .monthlytitle {
        color: #e1f1e1;
        font-size: 2em;
        text-shadow: 2px 3px rgba(100, 85, 100, .5);
    }

    .monthly .monthlystat {
        color: darkgrey;
    }

    .monthlynumb {
        color: teal;
    }

    .donatorHeader {
        background: #222;
        color: #e1f1e1;
        font-size: 2em;
        text-shadow: 2px 3px rgba(100, 85, 100, .5);
    }

    .donatorHeader .donatorContent {
        padding: 10px;
        min-height: 50px;
    }

    .longestfriendship {
        background: #222222;
        padding: 10px;
    }

    .longestfriendship .longestfriendshipHeader {
        color: #a94442;
    }

    .footer {
        position: absolute;
        right: 0;
        bottom: 0;
        left: 0;
        padding: 1rem;
        text-align: center;
    }

    .paypal {
    }
</style>
<div style="padding-bottom: 50px;">
    <nav class="navbar navbar-inverse navbar-fixed-top">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#mainNav">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/">Steam Statistics</a>
            </div>
            <div class="collapse navbar-collapse" id="mainNav">
                <ul class="nav navbar-nav">
                    <li><a href="/">Profile</a></li>
                    <li class="active"><a href="/home">Frontpage</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <div style="padding: 7px 5px;">
                        <div id="steamhref" class="hide">
                            <a href="/login">
                                <img src="/css/img/sits_01.png" alt="Steam Login">
                            </a>
                        </div>
                    </div>
                </ul>
            </div>
        </div>
    </nav>
</div>
<div class="container">
    <div class="row">
        <div class="col-xs-12">
            <div class="longestfriend">
                <div class="row">
                    <div class="col-xs-12 col-md-4">
                        <div class="roundedAvatar">
                            <div id="longestfrienda">
                                <img id="longestfriendaimg" src="css/img/defaultImageMedium.jpg">
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-12 col-md-4">
                        <div class="longestfriendship" style="margin: 10px 10px">
                            <h4 class="longestfriendshipHeader">
                                Longest Friendship
                            </h4>
                            <div class="plusstest">
                                <span id="longestfriendshipdate"></span>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-12 col-md-4">
                        <div class="roundedAvatar">
                            <div id="longestfriendb">
                                <img id="longestfriendbimg" src="css/img/defaultImageMedium.jpg">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-12 col-md-4">
            <div class="monthly">
                <div class="monthlytitle">
                    Monthly Stat
                </div>
                <div class="monthlystat">
                    <span id="registeredaccounts" class="monthlynumb"></span> new accounts
                </div>
                <div class="monthlystat">
                    <span id="friendgain" class="monthlynumb"></span> friendships bonded
                </div>
                <div class="monthlystat">
                    <span id="friendloss" class="monthlynumb"></span> friendships ruined
                </div>
            </div>
        </div>
        <div class="col-xs-12 col-md-4 col-md-offset-4">
            <div class="donatorHeader">
                Latest donators:
                <div class="donatorContent">
                    <div id="donatordiv"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--
<div class="footer">
    <div class="paypal">
        <form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_top">
            <input type="hidden" name="cmd" value="_donations">
            <#if steamid ??>
                <input type="hidden" name="custom" value="${steamid}"/>
            </#if>
            <input type="image" src="https://www.paypalobjects.com/en_US/NO/i/btn/btn_donateCC_LG.gif" border="0"
                   name="submit" alt="PayPal - The safer, easier way to pay online!">
            <img alt="" border="0" src="https://www.paypalobjects.com/en_US/i/scr/pixel.gif" width="1" height="1">
        </form>
        <div>
            <#if !steamid ??>
                <small>You need to be logged in to get donator rank</small>
            </#if>
        </div>
    </div>
</div>
-->
<script src="js/frontpage.js"></script>
</@m.master>