<#import "master.ftlh" as m/>
<@m.master>
<div style="font-size: 2em;">
    You have encountered an <span style="color: #c9302c;">error</span>!
</div>
<br />
<div style="font-size: 1.25em;">
    Please try to...<br />
    1. Try to <a onclick="refresh()" href="#">refresh</a> the page.<br />
    2. Go back to the <a href="/">frontpage</a>.
</div>
<script>
    removeLogin();
    function removeLogin() {
        var href = document.getElementById("steamhref");
        if(href) {
            href.parentNode.removeChild(href);
        }
    }
    function refresh() {
        location.reload();
    }
</script>
</@m.master>