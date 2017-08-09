<#import "master.ftlh" as m/>
<@m.master>

<style>
    .longestfriend {
        background: red;
        background: -webkit-linear-gradient( 180deg, #1a1b1f 10%,#1a1b1f 40%,#1e1f26 52%,#1a1b1f 61%,#1a1b1f 85%);
        background: linear-gradient( 180deg, #1a1b1f 10%,#1a1b1f 40%,#1e1f26 52%,#1a1b1f 61%,#1a1b1f 85%);
        padding: 20px;
        margin: 0 auto;
    }

    .longestfriend .longestfriendtest {
        text-align: left;
    }
</style>
<div class="container">
    <div class="row">
        <div class="col-xs-4">
            <div class="col-xs-6">
                <span id="friendgain"></span> friends has been gained this month.
            </div>
            <div class="col-xs-6">
                <span id="friendloss"></span> friends has been lost this month.
            </div>
        </div>
        <div class="col-xs-12">
            <div class="longestfriend">
                Longest friendship goes to <span id="longestfrienda"></span> and <span id="longestfriendb"></span>.
                <div class="longestfriendtest"></div>
            </div>
        </div>
    </div>
</div>
<script src="js/frontpage.js"></script>
</@m.master>