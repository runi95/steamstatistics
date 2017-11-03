<#import "master.ftlh" as m/>
<@m.master>
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
                            <div class="col-xs-7" style="max-width: 184px;">
                                <div id="state" class="profileAvatar profileHeaderSize offline">
                                    <div class="playerAvatarAutoSizeInner">
                                        <img id="avatar"
                                             src="css/img/defaultImageLarge.jpg">
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-5">
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
        <div class="col-xs-10 col-xs-offset-1">
            <h4 class="text-danger">Steam profiles no longer on your friends list</h4>
            <div id="removed"></div>
        </div>
    </div>
</div>
<div class="footer" style="color: rgb(100,100,100)">
    *All data is cached and updates once every hour.
</div>
<script src="js/steamstatistics.js"></script>
</@m.master>