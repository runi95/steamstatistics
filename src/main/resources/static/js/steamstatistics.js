var chartYMax = 400;

// Set the individual height of bars
function displayGraph(bars, i) {
    // Changed the way we loop because of issues with $.each not resetting properly
    if (i < bars.length) {
        // Animate the height using the jQuery animate() function
        $(bars[i].bar).animate({
            height: bars[i].height
        }, 800);
        // Wait the specified time, then run the displayGraph() function again for the next bar
        barTimer = setTimeout(function () {
            i++;
            displayGraph(bars, i);
        }, 100);
    }
}

// Reset graph settings and prepare for display
function resetGraph(bars) {
    // Stop all animations and set the bar's height to 0
    $.each(bars, function (i) {
        $(bars[i].bar).stop().css('height', 0);
    });

    // Clear timers
    clearTimeout(barTimer);
    clearTimeout(graphTimer);

    // Restart timer
    graphTimer = setTimeout(function () {
        displayGraph(bars, 0);
    }, 200);
}

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

    var countryKeys = Object.keys(message.countryMap);
    var maxCounter = 0;
    for (var i = 0; i < countryKeys.length; i++) {
        var countriesAxis = document.getElementById("countries");
        var li = document.createElement("li");
        var span = document.createElement("span");
        span.innerHTML = countryKeys[i];
        li.appendChild(span);
        countriesAxis.appendChild(li);

        var counter = message.countryMap[countryKeys[i]];
        if (counter > maxCounter) {
            maxCounter = counter;
        }

        var barGroup = $('<div class="bar-group"></div>');
        var barObj = {};
        barObj.label = counter;
        barObj.height = Math.floor(barObj.label / chartYMax * 100) + '%';
        barObj.bar = $('<div class="bar" style="height: 50%"><span>' + barObj.label + '</span></div>')
            .appendTo(barGroup);
        barGroup.appendTo($("#bars"));
    }

    var counterAxis = document.getElementById("counter");
    var maxcounterli = document.createElement("li");
    var maxcounterspan = document.createElement("span");
    maxcounterspan.innerHTML = maxCounter;

    maxcounterli.appendChild(maxcounterspan);
    counterAxis.appendChild(maxcounterli);
    var loop = 5;
    for (var i = 1; i < loop; i++) {
        var li = document.createElement("li");
        var span = document.createElement("span");
        span.innerHTML = Math.floor((maxCounter / loop) * i);

        li.appendChild(span);
        counterAxis.appendChild(li);
    }

    /*
     for(var i = 0; i < countryKeys.length; i++) {
     addCountryMap(countryKeys[i], message.countryMap[countryKeys[i]]);
     }
     */
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