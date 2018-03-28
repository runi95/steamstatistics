<#import "master.ftlh" as m/>
<@m.master>
<h3>Search is still under development!</h3>
<div id="divdiv"></div>
<script>
    var searchList = {};

    $.ajax({
        type: "GET",
        dataType: "json",
        url: "/getsearch/${srch}",
        success: function (data) {
            load(data.message);
        }
    });

    load();
    function load(list) {
        for(var i in list) {
            if(!searchList.hasOwnProperty(list[i].steamid)) {
                searchList[list[i].steamid] = list[i];
                addFriend(list[i], "", "yellow", "divdiv");
            }
        }
    }
</script>
</@m.master>