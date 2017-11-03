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

function processStatus(data) {
    switch (data.request) {
        case "getfrontpage":
            loadFrontpage(data);
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

function getFrontpageSuccess(message) {
    var longestfriendship = message.longestFriendship;
    if(longestfriendship != null) {
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
        diva.innerHTML = frienda.personaname;
        divb.innerHTML = friendb.personaname;

        hrefa.appendChild(imga);
        hrefb.appendChild(imgb);
        longestfrienda.appendChild(hrefa);
        longestfriendb.appendChild(hrefb);
        longestfrienda.appendChild(diva);
        longestfriendb.appendChild(divb);

        document.getElementById("longestfriendshipdate").innerHTML = longestfriendship.friendDateAsString;
    }
    for(var i = 0; i < message.donators.length; i++) {
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
        div.innerHTML = message.donators[i].personaname;
        innerspan.innerHTML = "donator";

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
    document.getElementById("registeredaccounts").innerHTML = message.registeredusers;
    document.getElementById("friendgain").innerHTML = message.bondedfriendships;
    document.getElementById("friendloss").innerHTML = message.ruinedfriendships;
}

getFrontpage();