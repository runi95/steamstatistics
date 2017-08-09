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
    var frienda = message[0];
    var friendb = message[1];
    if(frienda != null && friendb != null) {
        document.getElementById("longestfrienda").innerHTML = frienda.personaname;
        document.getElementById("longestfriendb").innerHTML = friendb.personaname;
    }
}

getFrontpage();