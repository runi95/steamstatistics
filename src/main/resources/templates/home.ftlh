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
        <div class="row" style="padding-top: 10px;">
            <div class="col-md-6">
                Top 3 oldest friends:
                <div id="oldest"></div>
            </div>
            <div class="col-md-6">
                Top 3 newest friends:
                <div id="newest"></div>
            </div>
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
    }

    function requestlogin() {
        document.getElementById("profile").setAttribute("class", "hide");
        document.getElementById("steamhref").setAttribute("class", "show");
    }

    function processStatus(data) {
        switch (data.request) {
            case "getprofile":
                getProfile(data);
                break;
            case "login":
                requestlogin();
                break;
        }
    }

    $.ajax({
        type: "GET",
        dataType: "json",
        url: "/getprofile",
        success: function (data) {
            processStatus(data);
        }
    });
</script>

</@m.master>