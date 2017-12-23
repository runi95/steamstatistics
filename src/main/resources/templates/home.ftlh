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
                <a class="navbar-brand" href="/">Steam Statistics</a>
            </div>
            <div class="collapse navbar-collapse" id="mainNav">
                <ul class="nav navbar-nav">
                    <li><a href="/home">Frontpage</a></li>
                    <li class="active"><a href="/">Profile</a></li>
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
        <div id="privacystate" class="hide">
            <dd><span class="text-danger">Warning</span>: Your account is not set to public, some functions on
                this
                website may not work!
            </dd>
        </div>
        <div class="col-xs-12" style="padding-right: auto;">
            <div class="profileHeaderBackground">
                <div class="profileHeader">
                    <div class="profileHeaderContent">
                        <div class="row">
                            <div class="col-xs-12 col-sm-7" style="max-width: 184px;">
                                <div id="state" class="profileAvatar profileHeaderSize offline">
                                    <div class="playerAvatarAutoSizeInner">
                                        <img id="avatar"
                                             src="css/img/defaultImageLarge.jpg">
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-5">
                                <div class="profileHeaderCenteredPersona" style="text-align: left;">
                                    <div class="personaName">
                                    <span id="personaname" class="actualPersonaName"
                                          style="font-size: 24px;"></span>
                                    </div>
                                    <div id="loader" class="loader"></div>
                                    Joined Steam Statistics on <span id="jdate"></span>
                                    <br>
                                    <span><span id="lmonth"></span> friendships ended</span>
                                    <br>
                                    <span><span id="gmonth"></span> friendships started</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-12" style="padding-right: auto; margin-top: 10px; margin-bottom: 10px;">
            <div class="friend-container">
                <h4 class="text-danger">Friendships Ended</h4>
                <div id="removed"></div>
                <div><button class="btn btn-steamstats" onclick="seemorebtn('ended')">See more</button></div>
            </div>
        </div>
        <div class="col-xs-12" style="padding-right: auto; margin-top: 10px;">
            <div class="friend-container container">
                <h4 class="text-success">Friendships Started</h4>
                <div id="added"></div>
                <div class="col-xs-12"><button class="btn btn-steamstats" onclick="seemorebtn('started')">See more</button></div>
            </div>
        </div>
    </div>
</div>
<div class="footer" style="color: rgb(100,100,100)">
    *All data is cached and updates once every hour
</div>
<script src="js/steamstatistics.js"></script>
<script src="js/home.js"></script>
</@m.master>