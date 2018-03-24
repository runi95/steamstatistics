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
    <div class="col-xs-6">
        <div class="box">
            <h3>Average User Friendships</h3>
            <div id="graph-wrapper">
                <div class="graph-info">
                    <a id="datatoggle0" href="javascript:datatoggle('0')">
                        <div class="inner-test" style="background: #66afaf;"></div>
                        Average Gained
                    </a>
                    <a id="datatoggle1" href="javascript:datatoggle('1')">
                        <div class="inner-test" style="background: #ffa076;"></div>
                        Average Lost
                    </a>
                    <a id="datatoggle2" href="javascript:datatoggle('2')">
                        <div class="inner-test" style="background: #76df76;"></div>
                        Personal Gained
                    </a>
                    <a id="datatoggle3" href="javascript:datatoggle('3')">
                        <div class="inner-test" style="background: #df5656;"></div>
                        Personal Lost
                    </a>
                </div>
                <div class="graph-container">
                    <div id="graph-lines">
                        <canvas class="base" width="550" height="300"></canvas>
                        <canvas class="overlay" width="550" height="300"
                                style="position: absolute; left: 0px; top: 0px;"></canvas>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</@m.master>