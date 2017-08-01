var chartYMax = 400;

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

    for (var i = 0; i < message.newest.length; i++) {
        addFriend(message.newest[i], message.newest[i].personastate, switchPersonastate(message.newest[i].personastate), "newest");
    }

    for (var i = 0; i < message.oldest.length; i++) {
        addFriend(message.oldest[i], message.oldest[i].personastate, switchPersonastate(message.oldest[i].personastate), "oldest");
    }

    var graphdiv = document.getElementById("graphdiv");
    var dl = document.createElement("dl");
    var dt = document.createElement("dt");
    dt.innerHTML = "Your friends countries";
    dl.appendChild(dt);
    var countryKeys = Object.keys(message.countryMap);
    for (var i = 0; i < countryKeys.length; i++) {
        var width = message.countryMap[countryKeys[i]]*10;
        var textY = String(9.5 + i*20);

        var dd = document.createElement("dd");
        var amount = document.createElement("span");
        var desc = document.createElement("span");
        dd.setAttribute("class", "percentage");
        dd.setAttribute("style", "width: " + width + "px");
        amount.setAttribute("class", "amount");
        desc.setAttribute("class", "desc");

        desc.innerHTML = countryKeys[i];
        amount.innerHTML = message.countryMap[countryKeys[i]];
        dd.appendChild(amount);
        dd.appendChild(desc);
        dl.appendChild(dd);
    }
    graphdiv.appendChild(dl);
}

function addCountryMap(alpha2code, count) {
    var country = document.getElementById("country");
    var div = document.createElement("div");
    var img = document.createElement("img");
    var span = document.createElement("span");
    img.setAttribute("src", "/css/img/countries/" + alpha2code.toLowerCase() + ".png");
    span.innerHTML = alpha2code + ": " + count;
    div.appendChild(img);
    div.appendChild(span);
    country.appendChild(div);
}

function switchPersonastate(state) {
    switch (state) {
        case "offline":
        case "snooze":
            return "offline";
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
            return "online";
    }

    return "offline";
}

function addFriend(profile, message, state, parentdiv) {
    var div = document.createElement("div");
    div.setAttribute("class", "profile persona " + state);
    div.setAttribute("href", profile.profileurl);
    var a = document.createElement("a");
    a.setAttribute("class", "profileLink");
    a.setAttribute("href", profile.profileurl);
    div.appendChild(a);
    var avatar = document.createElement("div");
    avatar.setAttribute("class", "profileAvatar " + state);
    var img = document.createElement("img");
    img.setAttribute("src", profile.avatar);
    avatar.appendChild(img);
    div.appendChild(avatar);
    var name = document.createElement("div");
    name.innerHTML = profile.personaname;
    var namebr = document.createElement("br");
    name.appendChild(namebr);
    var span = document.createElement("span");
    span.innerHTML = message;
    name.appendChild(span);
    div.appendChild(name);
    document.getElementById(parentdiv).appendChild(div);
}

function requestlogin() {
    document.getElementById("profile").setAttribute("class", "hide");
    document.getElementById("steamhref").setAttribute("class", "show");
}

function getRemovedSuccessful(message) {
    for (var i = 0; i < message.sortedFriendsSet.length; i++) {
        addFriend(message.sortedFriendsSet[i].steamFriendEntity, message.sortedFriendsSet[i].localDateTimeString, "removed", "removed");
    }

    var lmonth = document.getElementById("lmonth");
    lmonth.setAttribute("class", message.monthIndex);
    lmonth.innerHTML = message.friendsLostLastMonth;

    var lweek = document.getElementById("lweek");
    lweek.setAttribute("class", message.weekIndex);
    lweek.innerHTML = message.friendsLostLastWeek;
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