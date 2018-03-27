<#import "master.ftlh" as m/>
<@m.master>
<h3>Search is still under development!</h3>
<div id="divdiv"></div>
<script>
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
            addFriend(list[i], "", "yellow", "divdiv");
        }
    }
</script>
</@m.master>