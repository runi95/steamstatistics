var steamprofilelist = {};

var friendshipsended = [];
var friendshipsendedindex = 0;
var friendshipsstarted = [];
var friendshipsstartedindex = 0;

var navs = ["frontpagenav", "profilenav"];

var csrf_name = $('meta[name="csrf_name"]').attr('content');
var csrf_value = $('meta[name="csrf_value"]').attr('content');
var steamid = $('meta[name="steamid"]').attr('content');
var request_steamid = $('meta[name="request_steamid"]').attr('content');

/*
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
    document.getElementById("personaname").appendChild(document.createTextNode(message[0].personaname));
    document.getElementById("state").setAttribute("class", "profileAvatar profileHeaderSize " + message[0].profilestate);
    document.getElementById("avatar").setAttribute("src", message[0].avatarfull);
    if (message[0].communityvisibilitystate != "3") {
        document.getElementById("privacystate").setAttribute("class", "show");
    }

    document.getElementById("jdate").appendChild(document.createTextNode(message[1]));
}
*/

function seemorebtn(btn) {
    switch (btn) {
        case "started":
            friendshipsstartedindex = loadFriends(friendshipsstarted.length, friendshipsstarted, friendshipsstartedindex, "yellow", "added");
            break;
        case "ended":
            friendshipsendedindex = loadFriends(friendshipsended.length, friendshipsended, friendshipsendedindex, "removed", "removed");
            break;
    }
    
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
    var medalcontainer = document.createElement("div");
    var steamrefdiv = document.createElement("div");
    var steamrefa = document.createElement("a");
    var steamrefspan = document.createElement("span");
    var medalavatar = document.createElement("div");
    var avatarcolor = document.createElement("div");
    var avatarouter = document.createElement("div");
    var avatarinner = document.createElement("div");
    var profilea = document.createElement("a");
    var avatarimg = document.createElement("img");
    var medaltitle = document.createElement("a");
    var medaldate = document.createElement("div");

    medalcontainer.setAttribute("class", "medal-container medal-container-small");

    steamrefdiv.setAttribute("class", "medal-steamref");
    steamrefa.setAttribute("href", profile.profileurl);
    steamrefspan.setAttribute("class", "glyphicon glyphicon-share-alt");
    medalavatar.setAttribute("class", "medal-avatar");
    avatarcolor.setAttribute("class", "medal-small " + state);
    avatarouter.setAttribute("class", "medal-outer");
    avatarinner.setAttribute("class", "medal-inner");
    profilea.setAttribute("href", "/profile/" + profile.steamid);
    avatarimg.setAttribute("class", "medal-img");
    avatarimg.setAttribute("src", profile.avatar);

    medaltitle.setAttribute("class", "medal-container-title-small " + state + "-text");
    medaltitle.setAttribute("href", "/profile/" + profile.steamid);
    medaltitle.appendChild(document.createTextNode(profile.personaname));
    medaldate.setAttribute("class", "medal-container-text");
    medaldate.appendChild(document.createTextNode(message));

    steamrefa.appendChild(steamrefspan);
    steamrefdiv.appendChild(steamrefa);
    medalcontainer.appendChild(steamrefdiv);

    profilea.appendChild(avatarimg);
    avatarinner.appendChild(profilea);
    avatarouter.appendChild(avatarinner);
    avatarcolor.appendChild(avatarouter);
    medalavatar.appendChild(avatarcolor);
    medalcontainer.appendChild(medalavatar);

    medalcontainer.appendChild(medaltitle);
    medalcontainer.appendChild(medaldate);

    document.getElementById(parentdiv).appendChild(medalcontainer);
}

function requestlogin() {
    document.getElementById("steamhref").setAttribute("class", "show");
}

function getRemovedSuccessful(message) {
    for (var i = 0; i < message[0].length; i++) {
        steamprofilelist[message[0][i].steamFriendEntity.steamid] = message[0][i].steamFriendEntity;
        friendshipsended.push({ "steamid":message[0][i].steamFriendEntity.steamid, "date":message[0][i].localDateTimeString });
    }

    friendshipsendedindex = loadFriends(5, friendshipsended, friendshipsendedindex, "red", "removed");

    var lmonth = document.getElementById("lmonth");
    lmonth.setAttribute("class", "negative");
    lmonth.appendChild(document.createTextNode(message[1]));
}

function getAddedSuccessful(message) {
    for (var i = 0; i < message[0].length; i++) {
        steamprofilelist[message[0][i].steamFriendEntity.steamid] = message[0][i].steamFriendEntity;
        friendshipsstarted.push({ "steamid":message[0][i].steamFriendEntity.steamid, "date":message[0][i].localDateTimeString });
    }

    friendshipsstartedindex = loadFriends(5, friendshipsstarted, friendshipsstartedindex, "yellow", "added");

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

function loadFrontpage(data) {
    switch (data.status) {
        case "200":
            getFrontpageSuccess(data.message);
            break;
        case "400":

            break;
    }
}

function getFullProfileSuccess(profile, jdate) {
    document.getElementById("personaname").appendChild(document.createTextNode(profile.personaname));
    document.getElementById("state").setAttribute("class", "profileAvatar profileHeaderSize " + profile.profilestate);
    document.getElementById("avatar").setAttribute("src", profile.avatarfull);
    if (profile.communityvisibilitystate != "3") {
        document.getElementById("privacystate").setAttribute("class", "show");
    }

    document.getElementById("jdate").appendChild(document.createTextNode(jdate));
}

function getFullProfile(data) {
    switch (data.status) {
        case "200":
            getFullProfileSuccess(data.message.profile, data.message.jdate);
            getRemovedSuccessful(data.message.removed);
            getAddedSuccessful(data.message.added);
            break;
        case "204":
            banner("#b63e3e", "remove", "This user doesn't have a steamusers.info profile!");
            break;
        case "400":

            break;
    }
}

function processStatus(data) {
    switch (data.request) {
        case "getfullprofile":
            getFullProfile(data);
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
        case "getfrontpage":
            loadFrontpage(data);
            break;
        case "suggestion":
            loadSuggestion(data);
            break;
    }
}

function banner(color, glyphicon, message) {
    var oldbanner = document.getElementById("infobanner");
    var newbanner = oldbanner.cloneNode(false);

    newbanner.setAttribute("class", "banner banner-move");
    newbanner.setAttribute("style", "background: " + color + ";");
    var glyph = document.createElement("span");
    glyph.setAttribute("class", "glyphicon glyphicon-" + glyphicon);
    glyph.setAttribute("style", "margin-right: 5px;");
    newbanner.appendChild(glyph);
    newbanner.appendChild(document.createTextNode(message));

    oldbanner.parentNode.replaceChild(newbanner, oldbanner);
}

function requestProfile() {
    setActiveNav("profilenav");
    document.getElementById("loader").setAttribute("class", "loader");
    $.ajax({
        type: "GET",
        dataType: "json",
        url: "/getfullprofile/" + request_steamid,
        success: function (data) {
            processStatus(data);
            document.getElementById("loader").setAttribute("class", "");
        },
        fail: function () {
            document.getElementById("loader").setAttribute("class", "");
        }
    });
}

function search() {
    var srch = document.getElementById("srchinput").value;
    $(location).attr('href', '/search/' + srch);
}

function setActiveNav(navid) {
    document.getElementById(navid).setAttribute("class", "active");

    for(var i in navs) {
        if(navs[i] != navid) {
            document.getElementById(navs[i]).setAttribute("class", "");
        }
    }
}

function getFrontpage() {
    setActiveNav("frontpagenav");
    $.ajax({
        type: "GET",
        dataType: "json",
        url: "/getfrontpage",
        success: function (data) {
            processStatus(data);
        }
    });
}

function loadSuggestion(data) {
    var oldbanner = document.getElementById("infobanner");
    var newbanner = oldbanner.cloneNode(false);

    switch(data.status) {
        case "200":
            document.getElementById("suggestionText").value = "";
            document.getElementById("suggestionTitle").value = "";

            banner("#3eb63e", "ok", "Suggestion successfully sent out!");
            break;
        case "408":
        default:
            banner("#b63e3e", "remove", "Please try again...");
            break;
    }
    oldbanner.parentNode.replaceChild(newbanner, oldbanner);
}

function getFrontpageSuccess(message) {
    for (var i = 0; i < message.topthreefriendships.length; i++) {
        //var link = message.topthreefriendships[i].steamfriend.profileurl;
        var link = message.topthreefriendships[i].profilelink;
        var imglink = message.topthreefriendships[i].steamfriend.avatarmedium;
        var medal = "";

        switch (i) {
            case 0:
                medal = "gold";
                break;
            case 1:
                medal = "silver";
                break;
            case 2:
                medal = "bronze";
                break;
        }

        populateMedalContainer("topthreefriendships", medal, link, imglink, message.topthreefriendships[i].steamfriend.personaname, "has had a friend for " + message.topthreefriendships[i].friendshipdurationdate);
    }

    for (var i = 0; i < message.topthreehoarders.length; i++) {
        var link = message.topthreehoarders[i].profilelink;
        var imglink = message.topthreehoarders[i].steamfriend.avatarmedium;
        var medal = "";

        switch (i) {
            case 0:
                medal = "gold";
                break;
            case 1:
                medal = "silver";
                break;
            case 2:
                medal = "bronze";
                break;
        }

        populateMedalContainer("topthreehoarders", medal, link, imglink, message.topthreehoarders[i].steamfriend.personaname, "has a total of " + message.topthreehoarders[i].cnt + " friends");
        //topThreeHoardersDiv.appendChild(createMedalContainer(medal, link, imglink, message.topthreehoarders[i].steamfriend.personaname, "has a total of " + message.topthreehoarders[i].cnt + " friends"));
    }

    for (var i = 0; i < message.topthreemonthlyhoarders.length; i++) {
        var link = message.topthreemonthlyhoarders[i].profilelink;
        var imglink = message.topthreemonthlyhoarders[i].steamfriend.avatarmedium;
        var medal = "";

        switch (i) {
            case 0:
                medal = "gold";
                break;
            case 1:
                medal = "silver";
                break;
            case 2:
                medal = "bronze";
                break;
        }

        populateMedalContainer("topthreemonthlyhoarders", medal, link, imglink, message.topthreemonthlyhoarders[i].steamfriend.personaname, "has gained " + message.topthreemonthlyhoarders[i].cnt + " friends this month");
        //topThreeMonthlyHoardersDiv.appendChild(createMedalContainer(medal, link, imglink, message.topthreemonthlyhoarders[i].steamfriend.personaname, "has gained " + message.topthreemonthlyhoarders[i].cnt + " friends this month"));
    }

    populateApprovedSuggestions(message.approvedsuggestions);
}

function populateApprovedSuggestions(suggestions) {
    var sugggestionsdiv = document.getElementById("approvedsuggestions");

    for(var i = 0; i < suggestions.length; i++) {
        var suggestion = document.createElement("div");
        var suggestiontitle = document.createElement("div");
        //var suggestioncategorytitle = document.createElement("div");
        //var suggestioncategory = document.createElement("div");
        //var suggestiondesctitle = document.createElement("div");
        var suggestiondesc = document.createElement("div");

        suggestion.setAttribute("id", suggestions[i].id);
        suggestion.setAttribute("style", "width: 80%; padding: 10px; margin: 15px auto 15px auto; border: 2px solid #444444; border-radius: 10px; font-size: 1.15em;");
        suggestiontitle.setAttribute("style", "font-size: 1.35em; color: #6ca0e0; margin-bottom: 5px;");
        //suggestioncategorytitle.setAttribute("style", "display: block; clear: left; float: left; margin-right: 10px; color: #657090;");
        //suggestioncategory.setAttribute("style", "display: block; text-align: start; clear: right;");
        //suggestiondesctitle.setAttribute("style", "display: block; text-align: start; color: #485590;");
        suggestiondesc.setAttribute("style", "display: block; text-align: start; clear: right;");

        suggestiontitle.appendChild(document.createTextNode(suggestions[i].title));
        //suggestioncategorytitle.appendChild(document.createTextNode("Category:"));
        //suggestioncategory.appendChild(document.createTextNode(suggestions[i].category.substr(0, 1).toUpperCase() + suggestions[i].category.substr(1)));
        //suggestiondesctitle.appendChild(document.createTextNode("Description:"));
        suggestiondesc.appendChild(document.createTextNode(suggestions[i].description));

        suggestion.appendChild(suggestiontitle);
        //suggestion.appendChild(suggestioncategorytitle);
        //suggestion.appendChild(suggestioncategory);
        //suggestion.appendChild(suggestiondesctitle);
        suggestion.appendChild(suggestiondesc);

        sugggestionsdiv.appendChild(suggestion);
    }
}

function populateMedalContainer(medalname, medalcolor, link, imglink, title, text) {
    var medallink = document.getElementById(medalname + "_" + medalcolor + "link");
    medallink.setAttribute("href", link);
    var medalimg = document.getElementById(medalname + "_" + medalcolor + "img");
    medalimg.setAttribute("src", imglink);
    var medaltitle = document.getElementById(medalname + "_" + medalcolor + "title");
    medaltitle.setAttribute("href", link);
    medaltitle.appendChild(document.createTextNode(title));
    var medaltext = document.getElementById(medalname + "_" + medalcolor + "text");
    medaltext.appendChild(document.createTextNode(text));
}

function createMedalContainer(medalcolor, link, imglink, title, description) {
    var medal = createMedal(medalcolor, link, imglink);

    var medalContainer = document.createElement("div");
    medalContainer.setAttribute("class", "medal-container");

    var textDiv = document.createElement("div");

    var floatLeftDiv = document.createElement("div");
    floatLeftDiv.setAttribute("style", "float: left;");
    floatLeftDiv.appendChild(medal);
    textDiv.appendChild(floatLeftDiv);

    if (title != null) {
        var medalTitleDiv = document.createElement("a");
        medalTitleDiv.setAttribute("class", medalcolor + "-text medal-container-title");
        medalTitleDiv.setAttribute("href", link);
        medalTitleDiv.appendChild(document.createTextNode(title));

        textDiv.appendChild(medalTitleDiv);
    }

    if (description != null) {
        var medalTextDiv = document.createElement("div");
        medalTextDiv.setAttribute("class", "medal-container-text");
        medalTextDiv.appendChild(document.createTextNode(description));

        textDiv.appendChild(medalTextDiv);
    }

    medalContainer.appendChild(textDiv);

    return medalContainer;
}

function createMedal(medalcolor, link, imglink) {
    var medalDiv = document.createElement("div");
    medalDiv.setAttribute("class", "medal " + medalcolor);
    var medalOuter = document.createElement("div");
    medalOuter.setAttribute("class", "medal-outer");
    var medalInner = document.createElement("div");
    medalInner.setAttribute("class", "medal-inner");
    var medalLink = document.createElement("a");
    medalLink.setAttribute("href", link);
    var medalImg = document.createElement("img");
    medalImg.setAttribute("src", imglink);
    medalImg.setAttribute("class", "medal-img");

    medalLink.appendChild(medalImg);
    medalInner.appendChild(medalLink);
    medalOuter.appendChild(medalInner);
    medalDiv.appendChild(medalOuter);

    return medalDiv;
}

function submitSuggestion() {
    document.getElementById("subbtn").setAttribute("class", "btn btn-primary form-control");

    $.ajax({
        url: '/suggestion',
        type: 'POST',
        dataType: 'json',
        data: csrf_name + "=" + csrf_value + "&" + $('form#suggestionForm').serialize(),
        success: function(data) {
            processStatus(data);
        }
    });
}

/*
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
*/