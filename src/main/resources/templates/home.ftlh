<#import "master.ftlh" as m/>
<@m.master>
<div class="container">
    <div class="row">
        <div class="col-xs-12">
            <h1 class="text-danger">Steam Statistics</h1>
        </div>
    </div>
    <div class="row">
        <div id="privacystate" class="hide">
            <dd><span class="text-danger">Warning</span>: Your account is not set to public, some functions on
                this
                website may not work!
            </dd>
        </div>
        <div class="col-md-10 col-md-offset-1" style="padding-right: auto;">
            <div class="profileHeaderBackground">
                <div class="profileHeader">
                    <div class="profileHeaderContent">
                        <div class="profileHeaderCenteredPersona" style="text-align: left;">
                            <div id="steamhref" class="hide">
                                <a href="/login">
                                    <img src="/css/img/sits_02.png" alt="Steam Login">
                                </a>
                            </div>
                            <div id="loader"></div>
                            <div id="profile" class="hide">
                                <div class="personaName">
                                    <span id="personaname" class="actualPersonaName"
                                          style="font-size: 24px;"></span>
                                </div>
                                <span>Friends Gained (this month): <span id="month"></span></span>
                                <br>
                                <span>Friends Gained (this week): <span id="week"></span></span>
                                <br>
                                <span>Friends Lost (this month): <span id="lmonth"></span></span>
                                <br>
                                <span>Friends Lost (this week): <span id="lweek"></span></span>
                            </div>
                        </div>
                        <div id="state" class="profileAvatar profileHeaderSize offline">
                            <div class="playerAvatarAutoSizeInner">
                                <img id="avatar"
                                     src="css/img/defaultImage.jpg">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-offset-2" style="padding-top: 10px;">
            <div class="col-xs-3" style="text-align: left;">
                Top 5 oldest friends:
                <div id="oldest"></div>
            </div>
            <div class="col-xs-3 col-xs-offset-4" style="text-align: left;">
                Top 5 newest friends:
                <div id="newest"></div>
            </div>
        </div>
        <div class="col-xs-12">
            Removed friends:
            <div id="removed"></div>
        </div>
        <div class="col-xs-12">
            Friends from countries:
            <div id="country"></div>
        </div>
        <div class="col-xs-12">
            <div class="col-xs-12">
                <div id="wrapper">
                    <div class="chart">
                        <div id="figure">
                            <h4>Graph:</h4>
                            <div class="graph">
                                <ul id="countries" class="x-axis"></ul>
                                <ul id="counter" class="y-axis"></ul>
                                <div id="bars" class="bars"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="js/steamstatistics.js"></script>
</@m.master>