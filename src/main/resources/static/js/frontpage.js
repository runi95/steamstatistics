var csrf_token = $('meta[name="csrf-token"]').attr('content');

function getFrontpage() {
    $.ajax({
        type: "GET",
        dataType: "json",
        url: "/getfrontpage",
        success: function (data) {
            processStatus(data);
        }
    });
}

function getFrontpageTwo() {
    $.ajax({
        type: "GET",
        dataType: "json",
        url: "/getfrontpage2",
        success: function (data) {
            processStatus(data);
        }
    });
}

function processStatus(data) {
    switch (data.request) {
        case "getfrontpage":
            loadFrontpage(data);
            break;
        case "getfrontpage2":
            loadFrontpageTwo(data);
            break;
        case "suggestion":
            loadSuggestion(data);
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

function loadFrontpageTwo(data) {
    switch (data.status) {
        case "200":
            getFrontpageSuccessTwo(data.message);
            break;
        case "400":

            break;
    }
}

function loadSuggestion(data) {
    document.getElementById("suggestionText").value = "";
    document.getElementById("suggestionTitle").value = "";
    var alertdiv = document.getElementById("alertdiv");
    var alert = document.createElement("div");
    var a = document.createElement("a");

    var btn = document.getElementById("subbtn");
    switch(data.status) {
        case "200":
            /*
            a.setAttribute("href", "#");
            a.setAttribute("class", "close");
            a.setAttribute("data-dismiss", "alert");
            a.setAttribute("aria-label", "close");
            a.innerHTML = "&times;";
            alert.appendChild(a);
            alert.appendChild(document.createTextNode("Your suggestion has been successfully sent out, thank you!"));
            alert.setAttribute("class", "alert alert-success alert-dismissible fade in");
            alertdiv.appendChild(alert);
            */
            btn.setAttribute("class", "btn btn-primary form-control suggestion-success");
            break;
        case "408":
        default:
            btn.setAttribute("class", "btn btn-primary form-control suggestion-failure");
            /*
            a.setAttribute("href", "#");
            a.setAttribute("class", "close");
            a.setAttribute("data-dismiss", "alert");
            a.setAttribute("aria-label", "close");
            a.innerHTML = "&times;";
            alert.appendChild(a);
            alert.appendChild(document.createTextNode("Suggestion failed to send, please try again."));
            alert.setAttribute("class", "alert alert-danger alert-dismissible fade in");
            alertdiv.appendChild(alert);
            */
            break;
    }

    setTimeout(function(){
        btn.setAttribute("class", "btn btn-primary form-control");
    }, 3000);
}

function getFrontpageSuccessTwo(message) {
    for (var i = 0; i < message.topthreefriendships.length; i++) {
        var link = message.topthreefriendships[i].steamfriend.profileurl;
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
        //topThreeFriendshipsDiv.appendChild(createMedalContainer(medal, link, imglink, message.topthreefriendships[i].steamfriend.personaname, "has had a friend for " + message.topthreefriendships[i].friendshipdurationdate));
    }

    for (var i = 0; i < message.topthreehoarders.length; i++) {
        var link = message.topthreehoarders[i].steamfriend.profileurl;
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
        var link = message.topthreemonthlyhoarders[i].steamfriend.profileurl;
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

    document.getElementById("monthlygainvalue").appendChild(document.createTextNode(message.monthlygain));
    document.getElementById("monthlygaintext").appendChild(document.createTextNode(" friendships bonded"));
    document.getElementById("monthlylossvalue").appendChild(document.createTextNode(message.monthlyloss));
    document.getElementById("monthlylosstext").appendChild(document.createTextNode(" friendships ruined"));
    document.getElementById("joinedusersvalue").appendChild(document.createTextNode(message.joinedusers));
    document.getElementById("joineduserstext").appendChild(document.createTextNode(" new accounts"));
}

function populateMedalContainer(medalname, medalcolor, link, imglink, title, text) {
    var medallink = document.getElementById(medalname + "_" + medalcolor + "link");
    medallink.setAttribute("href", link);
    var medalimg = document.getElementById(medalname + "_" + medalcolor + "img");
    medalimg.setAttribute("src", imglink);
    var medaltitle = document.getElementById(medalname + "_" + medalcolor + "title");
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

function getFrontpageSuccess(message) {
    var longestfriendship = message.longestFriendship;
    if (longestfriendship != null) {
        var frienda = longestfriendship.frienda;
        var friendb = longestfriendship.friendb;
        var longestfrienda = document.getElementById("longestfrienda");
        var longestfriendb = document.getElementById("longestfriendb");
        var hrefa = document.createElement("a");
        var hrefb = document.createElement("a");
        var imga = document.getElementById("longestfriendaimg");
        var imgb = document.getElementById("longestfriendbimg");
        var diva = document.createElement("div");
        var divb = document.createElement("div");
        hrefa.setAttribute("href", frienda.profileurl);
        hrefb.setAttribute("href", friendb.profileurl);
        imga.setAttribute("src", frienda.avatarmedium);
        imgb.setAttribute("src", friendb.avatarmedium);
        diva.appendChild(document.createTextNode(frienda.personaname));
        divb.appendChild(document.createTextNode(friendb.personaname));

        hrefa.appendChild(imga);
        hrefb.appendChild(imgb);
        longestfrienda.appendChild(hrefa);
        longestfriendb.appendChild(hrefb);
        longestfrienda.appendChild(diva);
        longestfriendb.appendChild(divb);

        document.getElementById("longestfriendshipdate").appendChild(document.createTextNode(longestfriendship.friendDateAsString));
    }
    for (var i = 0; i < message.donators.length; i++) {
        var profile = document.createElement("div");
        var a = document.createElement("a");
        var profileavatar = document.createElement("div");
        var avatar = document.createElement("img");
        var div = document.createElement("div");
        var br = document.createElement("br");
        var span = document.createElement("span");
        var innerspan = document.createElement("span");
        var innerspanbr = document.createElement("br");

        profile.setAttribute("class", "profile persona golden");
        profile.setAttribute("href", message.donators[i].profileurl);
        a.setAttribute("class", "profileLink");
        a.setAttribute("href", message.donators[i].profileurl);
        profileavatar.setAttribute("class", "profileAvatar golden");
        img.setAttribute("src", message.donators[i].avatar);
        div.appendChild(document.createTextNode(message.donators[i].personaname));
        innerspan.appendChild(document.createTextNode("donator"));

        profile.appendChild(a);
        profile.appendChild(profileavatar);
        profile.appendChild(div);
        profileavatar.appendChild(avatar);
        div.appendChild(br);
        div.appendChild(span);
        span.appendChild(innerspan);
        innerspan.appendChild(innerspanbr);

        document.getElementById("donatordiv").appendChild(profile);
    }
    document.getElementById("registeredaccounts").appendChild(document.createTextNode(message.registeredusers));
    document.getElementById("friendgain").appendChild(document.createTextNode(message.bondedfriendships));
    document.getElementById("friendloss").appendChild(document.createTextNode(message.ruinedfriendships));
}

getFrontpageTwo();

function submitSuggestion() {
    document.getElementById("subbtn").setAttribute("class", "btn btn-primary form-control");

    $.ajax({
        url: '/suggestion',
        type: 'POST',
        dataType: 'json',
        data: $('form#suggestionForm').serialize() + "&_token=" + csrf_token,
        success: function(data) {
            processStatus(data);
        }
    });
}