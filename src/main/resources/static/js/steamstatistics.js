var steamprofilelist = {};

var friendshipsended = [];
var friendshipsendedindex = 0;
var friendshipsstarted = [];
var friendshipsstartedindex = 0;


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

    document.getElementById("personaname").appendChild(document.createTextNode(message[0].personaname));
    document.getElementById("state").setAttribute("class", "profileAvatar profileHeaderSize " + message[0].profilestate);
    document.getElementById("avatar").setAttribute("src", message[0].avatarfull);
    if (message[0].communityvisibilitystate != "3") {
        document.getElementById("privacystate").setAttribute("class", "show");
    }

    document.getElementById("jdate").appendChild(document.createTextNode(message[1]));
}

function seemorebtn(btn) {
    switch (btn) {
        case "started":
            friendshipsstartedindex = loadFriends(friendshipsstarted.length, friendshipsstarted, friendshipsstartedindex, "golden", "added");
            break;
        case "ended":
            friendshipsendedindex = loadFriends(friendshipsended.length, friendshipsended, friendshipsendedindex, "removed", "removed");
            break;
    }
    
}

/*
function getProfileInfo(message) {
    var month = document.getElementById("month");
    var week = document.getElementById("week");
    month.setAttribute("class", message.monthIndex);
    week.setAttribute("class", message.weekIndex);
    month.innerHTML = message.friendsGainedLastMonth;
    week.innerHTML = message.friendsGainedLastWeek;
}

function getTopFive(message) {
    for (var i = 0; i < message.newest.length; i++) {
        addFriend(message.newest[i], message.newest[i].personastate, switchPersonastate(message.newest[i].personastate), "newest");
    }

    for (var i = 0; i < message.oldest.length; i++) {
        addFriend(message.oldest[i], message.oldest[i].personastate, switchPersonastate(message.oldest[i].personastate), "oldest");
    }
}

function getGraph(message) {
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
*/

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
    console.log(profile);
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
    name.appendChild(document.createTextNode(profile.personaname));
    var namebr = document.createElement("br");
    name.appendChild(namebr);
    var span = document.createElement("span");
    span.appendChild(document.createTextNode(message));;
    name.appendChild(span);
    div.appendChild(name);
    document.getElementById(parentdiv).appendChild(div);
}

function requestlogin() {
    document.getElementById("steamhref").setAttribute("class", "show");
}

function getRemovedSuccessful(message) {
    for (var i = 0; i < message[0].length; i++) {
        steamprofilelist[message[0][i].steamFriendEntity.steamid] = message[0][i].steamFriendEntity;
        friendshipsended.push({ "steamid":message[0][i].steamFriendEntity.steamid, "date":message[0][i].localDateTimeString });
    }

    friendshipsendedindex = loadFriends(5, friendshipsended, friendshipsendedindex, "removed", "removed");

    var lmonth = document.getElementById("lmonth");
    lmonth.setAttribute("class", "negative");
    lmonth.appendChild(document.createTextNode(message[1]));
}

function getAddedSuccessful(message) {
    for (var i = 0; i < message[0].length; i++) {
        steamprofilelist[message[0][i].steamFriendEntity.steamid] = message[0][i].steamFriendEntity;
        friendshipsstarted.push({ "steamid":message[0][i].steamFriendEntity.steamid, "date":message[0][i].localDateTimeString });
    }

    friendshipsstartedindex = loadFriends(5, friendshipsstarted, friendshipsstartedindex, "golden", "added");

    var gmonth = document.getElementById("gmonth");
    gmonth.setAttribute("class", "positive");
    gmonth.appendChild(document.createTextNode(message[1]));
}

function loadFriends(n, list, listindex, addfriendcolor, addfrienddiv) {
    for(var i = 0; (i + listindex) < list.length && i < n; i++) {
        addFriend(steamprofilelist[list[i + listindex].steamid], list[i + listindex].date, addfriendcolor, addfrienddiv);
    }

    listindex += i;
    return listindex;
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

function getAdded(data) {
    switch (data.status) {
        case "200":
            getAddedSuccessful(data.message);
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
        case "getadded":
            getAdded(data);
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
            requestAddedFriends();
        },
        complete: function () {
            document.getElementById("loader").setAttribute("class", "");
        }
    });
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

function requestAddedFriends() {
    $.ajax({
        type: "GET",
        dataType: "json",
        url: "/getadded",
        success: function (data) {
            processStatus(data);
        }
    });
}

function getFriends() {
    $.ajax({
        type: "GET",
        dataType: "json",
        url: "/getfriends",
        success: function (data) {
            updateFriends(data.message);
        }
    });
}

function updateFriends(message) {
    for (var key in message.friends) {
        addFriend(message.friends[key].steamFriendEntity, message.friends[key].localDateTimeString, switchPersonastate(message.friends[key].personastate), "friends");
    }

    document.getElementById("friendcount").appendChild(document.createTextNode(message.length));
}