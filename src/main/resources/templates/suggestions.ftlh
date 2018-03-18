<#import "master.ftlh" as m/>
<@m.master>
    <div class="col-xs-4 col-xs-offset-4" id="suggestionsdiv">
    </div>
    <style>
        .suggestionbox {
            position: relative;
            overflow: hidden;
            height: auto;
            background: rgba(55, 55, 55, 0.5);
            margin-top: 20px;
            padding: 10px;
            border-radius: 15px;
            border: 2px solid #111111;

            opacity: 1;
            -webkit-transition: opacity 500ms linear;
            transition: opacity 500ms linear;
        }

        .suggestionbox-approved {
            background: rgba(55, 75, 55, 0.5);
        }

        .suggestiondate {
            position: absolute;
            display: block;
            right: 10px;
            /* float: right; */
            color: rgba(120, 120, 120, 0.7);
        }

        .suggestion-stamp {
            position: absolute;
            display: block;
            left: 10px;
            color: rgba(120, 155, 120, 0.7);
        }

        .suggestionsteamid {
            color: rgba(150, 150, 150, 0.7);
        }

        .suggestiontitle {
            text-align: center;
            font-size: 2em;
            color: #48a0d0;
        }

        .suggestioninput {
            text-align: start;
            clear: right;
            display: block;
        }

        .suggestiontype {
            display: block;
            margin-right: 5px;
            clear: left;
            float: left;
            color: #48a0d0;
        }

        .suggestiontest {
            margin-top: 10px;
        }

        .btn-suggestion-red {
            margin-right: 8px;
            color: #fff;
            background-color: #b63e3e;
            border-color: #8e3434;
        }
        .btn-suggestion-red:focus,
        .btn-suggestion-red.focus {
            color: #fff;
            background-color: #921616;
            border-color: #532525;
        }
        .btn-suggestion-red:hover {
            color: #fff;
            background-color: #921616;
            border-color: #532525;
        }
        .btn-suggestion-red:active,
        .btn-suggestion-red.active,
        .open > .dropdown-toggle.btn-suggestion-red {
            color: #fff;
            background-color: #921616;
            border-color: #532525;
        }
        .btn-suggestion-red:active:hover,
        .btn-suggestion-red.active:hover,
        .open > .dropdown-toggle.btn-suggestion-red:hover,
        .btn-suggestion-red:active:focus,
        .btn-suggestion-red.active:focus,
        .open > .dropdown-toggle.btn-suggestion-red:focus,
        .btn-suggestion-red:active.focus,
        .btn-suggestion-red.active.focus,
        .open > .dropdown-toggle.btn-suggestion-red.focus {
            color: #fff;
            background-color: #921616;
            border-color: #532525;
        }
        .btn-suggestion-red:active,
        .btn-suggestion-red.active,
        .open > .dropdown-toggle.btn-suggestion-red {
            background-image: none;
        }
        .btn-suggestion-red.disabled:hover,
        .btn-suggestion-red[disabled]:hover,
        fieldset[disabled] .btn-suggestion-red:hover,
        .btn-suggestion-red.disabled:focus,
        .btn-suggestion-red[disabled]:focus,
        fieldset[disabled] .btn-suggestion-red:focus,
        .btn-suggestion-red.disabled.focus,
        .btn-suggestion-red[disabled].focus,
        fieldset[disabled] .btn-suggestion-red.focus {
            background-color: #921616;
            border-color: #532525;
        }

        .btn-suggestion-green {
            margin-left: 8px;
            color: #fff;
            background-color: #3eb63e;
            border-color: #348e34;
        }
        .btn-suggestion-green:focus,
        .btn-suggestion-green.focus {
            color: #fff;
            background-color: #169216;
            border-color: #255325;
        }
        .btn-suggestion-green:hover {
            color: #fff;
            background-color: #169216;
            border-color: #255325;
        }
        .btn-suggestion-green:active,
        .btn-suggestion-green.active,
        .open > .dropdown-toggle.btn-suggestion-green {
            color: #fff;
            background-color: #169216;
            border-color: #255325;
        }
        .btn-suggestion-green:active:hover,
        .btn-suggestion-green.active:hover,
        .open > .dropdown-toggle.btn-suggestion-green:hover,
        .btn-suggestion-green:active:focus,
        .btn-suggestion-green.active:focus,
        .open > .dropdown-toggle.btn-suggestion-green:focus,
        .btn-suggestion-green:active.focus,
        .btn-suggestion-green.active.focus,
        .open > .dropdown-toggle.btn-suggestion-green.focus {
            color: #fff;
            background-color: #169216;
            border-color: #255325;
        }
        .btn-suggestion-green:active,
        .btn-suggestion-green.active,
        .open > .dropdown-toggle.btn-suggestion-green {
            background-image: none;
        }
        .btn-suggestion-green.disabled:hover,
        .btn-suggestion-green[disabled]:hover,
        fieldset[disabled] .btn-suggestion-green:hover,
        .btn-suggestion-green.disabled:focus,
        .btn-suggestion-green[disabled]:focus,
        fieldset[disabled] .btn-suggestion-green:focus,
        .btn-suggestion-green.disabled.focus,
        .btn-suggestion-green[disabled].focus,
        fieldset[disabled] .btn-suggestion-green.focus {
            background-color: #169216;
            border-color: #255325;
        }

        .approved {
            border: 2px solid #3eb63e;
            border-radius: 4px;
            display: inline-block;
            padding: 6px 12px;
            margin-bottom: 0;
            font-size: 14px;
            font-weight: normal;
            line-height: 1.42857143;
            text-align: center;
            white-space: nowrap;
            vertical-align: middle;
            -ms-touch-action: manipulation;
            touch-action: manipulation;
            -webkit-user-select: none;
            -moz-user-select: none;
            -ms-user-select: none;
            user-select: none;
            background-image: none;
        }
    </style>
    <script>
        var csrf_name = $('meta[name="csrf_name"]').attr('content');
        var csrf_value = $('meta[name="csrf_value"]').attr('content');

        function loadSuggestions(data, isApproved) {
            var div = document.getElementById("suggestionsdiv");
            var box = document.createElement("div");
            var datediv = document.createElement("div");
            var titlediv = document.createElement("div");
            var steamiddiv = document.createElement("div");
            var steamidinput = document.createElement("a");
            var categorydiv = document.createElement("div");
            var categoryinput = document.createElement("div");
            var descdiv = document.createElement("div");
            var descinput = document.createElement("div");

            var btndiv = document.createElement("div");
            var removebtn = document.createElement("button");

            btndiv.setAttribute("style", "margin-top: 16px; margin-bottom: 4px;");
            btndiv.appendChild(removebtn);

            if(isApproved) {
                approve = document.createElement("div");
                approve.setAttribute("class", "suggestion-stamp");
                approve.appendChild(document.createTextNode("Approved!"));
                box.appendChild(approve);

                box.setAttribute("class", "suggestionbox suggestionbox-approved");
            } else {
                approvebtn = document.createElement("button");
                approvebtn.setAttribute("class", "btn btn-suggestion-green");
                approvebtn.setAttribute("onclick", "approvesuggestion('" + data.id + "')");
                approvebtn.innerHTML = "Approve";

                btndiv.appendChild(approvebtn);

                box.setAttribute("class", "suggestionbox");
            }

            box.setAttribute("id", data.id)
            datediv.setAttribute("class", "suggestiondate");
            datediv.appendChild(document.createTextNode(data.creationDate));
            titlediv.setAttribute("class", "suggestiontitle");
            titlediv.appendChild(document.createTextNode(data.title));
            steamiddiv.setAttribute("class", "suggestiontype");
            steamiddiv.appendChild(document.createTextNode("Steamid:"));
            steamidinput.setAttribute("class", "suggestioninput suggestionsteamid");
            steamidinput.setAttribute("href", "https://steamcommunity.com/profiles/" + data.steamid);
            steamidinput.appendChild(document.createTextNode(data.steamid));
            categorydiv.setAttribute("class", "suggestiontype");
            categorydiv.appendChild(document.createTextNode("Category:"));
            categoryinput.setAttribute("class", "suggestioninput");
            categoryinput.appendChild(document.createTextNode(data.category));
            descdiv.setAttribute("class", "suggestiontype");
            descdiv.appendChild(document.createTextNode("Description:"));
            descinput.setAttribute("class", "suggestioninput");
            descinput.appendChild(document.createTextNode(data.description));
            removebtn.setAttribute("class", "btn btn-suggestion-red");
            removebtn.setAttribute("onclick", "removesuggestion('" + data.id + "')");
            removebtn.innerHTML = "Remove";

            box.appendChild(datediv);
            box.appendChild(titlediv);
            box.appendChild(steamiddiv);
            box.appendChild(steamidinput);
            box.appendChild(categorydiv);
            box.appendChild(categoryinput);
            box.appendChild(descdiv);
            box.appendChild(descinput);
            box.appendChild(btndiv);

            div.appendChild(box);
        }

        getSuggestions();

        function getSuggestions() {
            $.ajax({
                type: "GET",
                dataType: "json",
                url: "/admin/getsuggestions",
                success: function (data) {
                    if(data.status == "200") {
                        for(var i = 0; i < data.message.length; i++) {
                            loadSuggestions(data.message[i], false);
                        }
                    }
                    getApprovedSuggestions();
                }
            });
        }

        function getApprovedSuggestions() {
            $.ajax({
                type: "GET",
                dataType: "json",
                url: "/admin/getapprovedsuggestions",
                success: function (data) {
                    if(data.status == "200") {
                        for(var i = 0; i < data.message.length; i++) {
                            loadSuggestions(data.message[i], true);
                        }
                    }
                }
            });
        }

        function removeSuggestionSuccessful(data) {
            if(data.status === "200") {
                var suggestion = document.getElementById(data.message);
                suggestion.style.opacity = '0';
                setTimeout(function() {
                    suggestion.parentNode.removeChild(suggestion);
                }, 500);
            }
        }

        function approveSuggestionSuccessful(data) {
            if(data.status === "200") {
                var suggestion = document.getElementById(data.message.id);
                suggestion.style.opacity = '0';
                setTimeout(function() {
                    suggestion.parentNode.removeChild(suggestion);
                    loadSuggestions(data.message, data.message.approved);
                }, 500);
            }
        }

        function removesuggestion(id) {
            $.ajax({
                url: '/admin/removesuggestion',
                type: 'POST',
                dataType: 'json',
                data: csrf_name + "=" + csrf_value + "&id=" + id,
                success: function(data) {
                    removeSuggestionSuccessful(data);
                }
            });
        }

        function approvesuggestion(id) {
            $.ajax({
                url: '/admin/approvesuggestion',
                type: 'POST',
                dataType: 'json',
                data: csrf_name + "=" + csrf_value + "&id=" + id,
                success: function(data) {
                    approveSuggestionSuccessful(data);
                }
            });
        }
    </script>
</@m.master>