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

function loadSuggestion(data) {
    var oldbanner = document.getElementById("infobanner");
    var newbanner = oldbanner.cloneNode(false);

    switch(data.status) {
        case "200":
            document.getElementById("suggestionText").value = "";
            document.getElementById("suggestionTitle").value = "";
            newbanner.setAttribute("class", "banner banner-move banner-success");
            var glyph = document.createElement("span");
            glyph.setAttribute("class", "glyphicon glyphicon-ok");
            glyph.setAttribute("style", "margin-right: 5px;");
            newbanner.appendChild(glyph);
            newbanner.appendChild(document.createTextNode("Suggestion successfully sent out!"));
            break;
        case "408":
        default:
            newbanner.setAttribute("class", "banner banner-move banner-failure");
            var glyph = document.createElement("span");
            glyph.setAttribute("class", "glyphicon glyphicon-remove");
            glyph.setAttribute("style", "margin-right: 5px;");
            newbanner.appendChild(glyph);
            newbanner.appendChild(document.createTextNode("Please try again..."));
            break;
    }
    oldbanner.parentNode.replaceChild(newbanner, oldbanner);
}

function getFrontpageSuccess(message) {
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

getFrontpage();

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