<#import "master.ftlh" as m/>
<@m.master>
<script src="js/steamstatistics.js"></script>
<script src="/js/jquery.flot.min.js"></script>
<script src="/js/jquery.flot.categories.min.js"></script>
<script src="/js/graphs.js"></script>
<link href="/css/graphs.css" rel="stylesheet">
<div>
    <div class="col-sm-3">
        <div class="box">
            <h3>Monthly Stats</h3>
            <div id="monthlygain">
                <span id="monthlygainvalue" style="color: #6ca0e0"></span><span id="monthlygaintext"></span>
            </div>
            <div id="monthlyloss">
                <span id="monthlylossvalue" style="color: #6ca0e0"></span><span id="monthlylosstext"></span>
            </div>
            <div id="joinedusers">
                <span id="joinedusersvalue" style="color: #6ca0e0"></span><span id="joineduserstext"></span>
            </div>
        </div>
    </div>
    <div class="col-sm-6">
        <div class="box">
            <h3>Average User Friendships</h3>
            <div id="graphdiv"></div>
        </div>
    </div>
</div>
</@m.master>