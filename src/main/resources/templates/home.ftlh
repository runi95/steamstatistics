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
        <div class="col-xs-12" style="padding-right: auto;">
            <div class="profileHeaderBackground">
                <div class="profileHeader">
                    <div class="profileHeaderContent">
                        <div class="row">
                            <div class="col-xs-7" style="max-width: 184px;">
                                <div id="state" class="profileAvatar profileHeaderSize offline">
                                    <div class="playerAvatarAutoSizeInner">
                                        <img id="avatar"
                                             src="css/img/defaultImage.jpg">
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-5">
                                <div class="profileHeaderCenteredPersona" style="text-align: left;">
                                    <div id="loader"></div>
                                    <div id="profile" class="hide">
                                        <div class="personaName">
                                    <span id="personaname" class="actualPersonaName"
                                          style="font-size: 24px;"></span>
                                        </div>
                                        <div id="loader" class="loader"></div>
                                        <span>Friends Gained (this month): <span id="month"></span></span>
                                        <br>
                                        <span>Friends Gained (this week): <span id="week"></span></span>
                                        <br>
                                        <span>Friends Lost (this month): <span id="lmonth"></span></span>
                                        <br>
                                        <span>Friends Lost (this week): <span id="lweek"></span></span>
                                    </div>
                                </div>
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