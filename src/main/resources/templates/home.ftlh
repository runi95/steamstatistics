<#import "master.ftlh" as m/>
<@m.master>
<div class="container">
    <div class="row">
        <h1 class="text-danger">Steam Statistics</h1>
    </div>
    <div class="row">
        <div id="privacystate" class="hide">
            <dd><span class="text-danger">Warning</span>: Your account is not set to public, some functions on
                this
                website may not work!
            </dd>
        </div>
        <div class="row">
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
        <div class="row col-md-offset-2" style="padding-top: 10px;">
            <div class="col-md-3" style="text-align: left;">
                Top 3 oldest friends:
                <div id="oldest"></div>
            </div>
            <div class="col-md-3 col-md-offset-4" style="text-align: left;">
                Top 3 newest friends:
                <div id="newest"></div>
            </div>
        </div>
        <div class="row">
            Removed friends:
            <div id="removed"></div>
        </div>
    </div>
</div>

<script>
    function getProfile(data) {
        switch (data.status) {
            case "200":
                getProfileSuccess(data.message);
                break;
            case "400":

                break;
        }
    }

    function getProfileSuccess(message) {
        document.getElementById("steamhref").setAttribute("class", "hide");
        document.getElementById("profile").setAttribute("class", "show");

        document.getElementById("personaname").innerHTML = message.steamProfile.personaname;
        var month = document.getElementById("month");
        var week = document.getElementById("week");
        month.setAttribute("class", message.monthIndex);
        week.setAttribute("class", message.weekIndex);
        month.innerHTML = message.friendsGainedLastMonth;
        week.innerHTML = message.friendsGainedLastWeek;
        document.getElementById("state").setAttribute("class", "profileAvatar profileHeaderSize " + message.steamProfile.profilestate);
        document.getElementById("avatar").setAttribute("src", message.steamProfile.avatarfull);
        if (message.steamProfile.communityvisibilitystate != "3") {
            document.getElementById("privacystate").setAttribute("class", "show");
        }

        for(var i = 0; i < message.newest.length; i++) {
            addFriend(message.newest[i], "newest");
        }

        for(var i = 0; i < message.oldest.length; i++) {
            addFriend(message.oldest[i], "oldest");
        }
    }

    function addFriend(profile, parentdiv) {
        var personastate = "offline";
        switch (profile.personastate) {
            case "offline":
            case "snooze":
                personastate = "offline";
                break;
            case "online":
            case "busy":
            case "away":
            case "looking to trade":
            case "looking to play":
                /*
                if(profile.gameid != null) {
                    newestpersonastate = "ingame";
                } else {
                    newestpersonastate = "online";
                }
                */
                personastate = "online";
                break;
        }

        var div = document.createElement("div");
        div.setAttribute("class", "profile persona " + personastate);
        div.setAttribute("href", profile.profileurl);
        var a = document.createElement("a");
        a.setAttribute("class", "profileLink");
        a.setAttribute("href", profile.profileurl);
        div.appendChild(a);
        var avatar = document.createElement("div");
        avatar.setAttribute("class", "profileAvatar " + personastate);
        var img = document.createElement("img");
        img.setAttribute("src", profile.avatar);
        avatar.appendChild(img);
        div.appendChild(avatar);
        var name = document.createElement("div");
        name.innerHTML = profile.personaname;
        var namebr = document.createElement("br");
        name.appendChild(namebr);
        var span = document.createElement("span");
        span.innerHTML = personastate;
        name.appendChild(span);
        div.appendChild(name);
        document.getElementById(parentdiv).appendChild(div);
    }

    function requestlogin() {
        document.getElementById("profile").setAttribute("class", "hide");
        document.getElementById("steamhref").setAttribute("class", "show");
    }

    function getRemovedSuccessful(message) {
        for(var i = 0; i < message.length; i++) {
            addFriend(message[i], "removed");
        }
    }

    function getRemoved(data) {
        switch (data.status) {
            case "200":
                getRemovedSuccessful(data.message);
                break;
            case "400":

                break;
        }
    }

    function processStatus(data) {
        switch (data.request) {
            case "getprofile":
                getProfile(data);
                break;
            case "login":
                requestlogin();
                break;
            case "getremoved":
                getRemoved(data);
                break;
        }
    }

    function requestProfile() {
        document.getElementById("loader").setAttribute("class", "loader");
        $.ajax({
            type: "GET",
            dataType: "json",
            url: "/getprofile",
            success: function (data) {
                processStatus(data);
                requestRemovedFriends();
            }
        });
        document.getElementById("loader").setAttribute("class", "");
    }

    function requestRemovedFriends() {
        $.ajax({
            type: "GET",
            dataType: "json",
            url: "/getremoved",
            success: function (data) {
                processStatus(data);
            }
        });
    }

    requestProfile();
</script>

</@m.master>