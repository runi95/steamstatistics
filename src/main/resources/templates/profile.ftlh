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
            <div id="loader" class="loader" style="position: absolute; left: 0; right: 0; top: 0; bottom: 0; margin: auto; height: 68px; width: 68px; z-index: 100;"></div>
            <div class="profileHeaderBackground">
                <div class="profileHeader">
                    <div class="profileHeaderContent">
                        <div style="position: absolute; display: block; right: 0;">
                            <a href="http://steamcommunity.com/profiles/${request_steamid}" style="color: rgba(120, 120, 120, 0.7);">Steam profile</a>
                        </div>
                        <div class="row">
                            <div class="col-xs-12 col-sm-7" style="max-width: 184px;">
                                <div id="state" class="profileAvatar profileHeaderSize offline">
                                    <div class="playerAvatarAutoSizeInner">
                                        <a href="http://steamcommunity.com/profiles/${request_steamid}">
                                            <img id="avatar" src="/css/img/defaultImageLarge.jpg">
                                        </a>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-5">
                                <div class="profileHeaderCenteredPersona" style="text-align: left;">
                                    <div class="personaName">
                                    <span id="personaname" class="actualPersonaName"
                                          style="font-size: 24px;"></span>
                                    </div>
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
<script src="/js/profile.js"></script>
</@m.master>