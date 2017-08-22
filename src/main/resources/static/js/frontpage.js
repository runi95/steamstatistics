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
        var imga = document.createElement("img");
        var imgb = document.createElement("img");
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
    document.getElementById("friendgain").innerHTML = message.ruinedfriendships;
    document.getElementById("friendloss").innerHTML = message.bondedfriendships;
}

getFrontpage();