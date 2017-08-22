<#import "master.ftlh" as m/>
<@m.master>

<style>
    .longestfriend {
        height: 206px;
        background: red;
        background: -webkit-linear-gradient( 180deg, #1a1b1f 10%,#1a1b1f 40%,#1e1f26 52%,#1a1b1f 61%,#1a1b1f 85%);
        background: linear-gradient( 180deg, #1a1b1f 10%,#1a1b1f 40%,#1e1f26 52%,#1a1b1f 61%,#1a1b1f 85%);
        padding: 20px;
        margin: 0 auto;
    }

    .longestfriend .longestfriendtest {
        text-align: left;
    }

    .monthly {
        border-left: 3px solid #1e1f26;
        border-bottom: 3px solid #1e1f26;
        border-right: 3px solid #1e1f26;
        background: #1e1f36;
        padding: 10px;
    }

    .monthly .monthlytitle {
        color: #e1f1e1;
        font-size: 2em;
        text-shadow: 2px 3px rgba(100,85,100,.5);
    }

    .monthly .monthlystat {
        color: darkgrey;
    }

    .monthlynumb {
        color: teal;
    }
</style>
<div class="container">
    <div class="row">
        <div class="col-xs-12">
            <div class="longestfriend">
                Longest friendship goes to <span id="longestfrienda"></span> and <span id="longestfriendb"></span>.
                <div class="longestfriendtest"></div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="monthly">
                <div class="monthlytitle">
                    Monthly Stat
                </div>
                <div class="monthlystat">
                    <span id="friendgain" class="monthlynumb"></span> friendships bonded
                </div>
                <div class="monthlystat">
                    <span id="friendloss" class="monthlynumb"></span> friendships ruined
                </div>
            </div>
        </div>
        <div class="col-md-8">
            <span id="temp"></span>Placeholder for something coming.
        </div>
    </div>
</div>
<script src="js/frontpage.js"></script>
</@m.master>