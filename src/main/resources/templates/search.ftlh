<#import "master.ftlh" as m/>
<@m.master>
<h3>Search is still under development!</h3>
<div class="col-sm-6 col-sm-offset-3 col-xs-12">
    <div class="box" style="min-height: 90px;">
        <div id="loader" class="loader" style="position: relative; margin: 0 auto;"></div>
        <div id="divdiv"></div>
    </div>
</div>
<script>
    var searchList = {};

    $.ajax({
        type: "GET",
        dataType: "json",
        url: "/getsearch/${srch}",
        success: function (data) {
            load(data.message);
        },
        complete: function () {
            document.getElementById("loader").setAttribute("class", "");
        }
    });

    function load(list) {
        var isEmpty = true;
        for (var i in list) {
            isEmpty = false;
            if (!searchList.hasOwnProperty(list[i].steamid)) {
                searchList[list[i].steamid] = list[i];
                addFriend(list[i], "", "yellow", "divdiv");
            }
        }

        if(isEmpty) {
            banner("#b63e3e", "remove", "No users found!");
        }
    }
</script>
</@m.master>