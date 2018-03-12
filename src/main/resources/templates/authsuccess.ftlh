<#import "master.ftlh" as m/>
<@m.master>
<div style="margin-top: 20px;font-size: 2em; color: #3eb63e;">
    Authentication successful! Please wait...
</div>
<script>
    initializeProfile();
    function initializeProfile() {
        $.ajax({
            type: "GET",
            dataType: "json",
            url: "/getprofile",
            complete: function () {
                $(location).attr('href', '/')
            }
        });
    }
</script>
</@m.master>