<#import "master.ftlh" as m/>
<@m.master>

<style>
    .longestfriend {
        background: repeating-linear-gradient(
                -55deg,
                #222,
                #222 10px,
                #282828 10px,
                #282828 20px
        );
        padding: 20px;
        margin: 0 auto;
        min-height: 32px;
    }

    .roundedAvatar img {
        border-radius: 50%;
        border-color: #1e1f26;
    }

    .monthly {
        border: 3px solid #1e1f26;
        background: #222222;
        padding: 10px;
    }

    .monthly .monthlytitle {
        color: #e1f1e1;
        font-size: 2em;
        text-shadow: 2px 3px rgba(100, 85, 100, .5);
    }

    .monthly .monthlystat {
        color: darkgrey;
    }

    .monthlynumb {
        color: teal;
    }

    .donatorHeader {
        background: #222;
    }

    .donatorHeader .donatorContent {
    }

    .donatorHeader .donatorContent .donator {
        padding: 10px;
    }

    .longestfriendship {
        background: #222222;
        padding: 10px;
    }

    .longestfriendship .longestfriendshipHeader {
        color: #a94442;
    }
</style>
<div class="container">
    <div class="row">
        <div class="col-xs-12">
            <div class="longestfriend">
                <div class="row">
                    <div class="col-xs-12 col-md-4">
                        <div class="roundedAvatar">
                            <div id="longestfrienda"></div>
                        </div>
                    </div>
                    <div class="col-xs-12 col-md-4">
                        <div class="longestfriendship" style="margin: 10px 10px">
                            <h4 class="longestfriendshipHeader">
                                Longest Friendship
                            </h4>
                            <div class="plusstest">
                                <span id="longestfriendshipdate"></span>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-12 col-md-4">
                        <div class="roundedAvatar">
                            <div id="longestfriendb"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-12 col-md-4">
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
        <div class="col-xs-12 col-md-4 col-md-offset-4">
            <div class="donatorHeader">
                Latest donators:
                <div class="donatorContent">
                    <div class="donator">
                        <div class="row">
                            <div class="col-xs-12">
                                <!-- This is just a temporary placeholder... -->
                                <div class="profile persona golden" href="/profile">
                                    <a class="profileLink" href="/"></a>

                                    <div class="profileAvatar golden">
                                        <img src="/css/img/countries/ae.png">
                                    </div>
                                    <div>
                                        OLA NORDMANN
                                        <br>
                                        <span>
                                    <span>
                                    PLACEHOLDER (01/01/1970)
                                    <br>
                                    </span>
                                </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="js/frontpage.js"></script>
</@m.master>