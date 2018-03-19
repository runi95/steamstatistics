<#import "master.ftlh" as m/>
<@m.master>
<div style="position: fixed; left: 50%; z-index: 100; width: 50%">
    <div id="infobanner" class="banner"></div>
</div>
<div style="padding-bottom: 60px;">
    <nav class="navbar navbar-inverse navbar-fixed-top">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#mainNav">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/">SteamUsers.info</a>
            </div>
            <div class="collapse navbar-collapse" id="mainNav">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="/">Frontpage</a></li>
                    <li><a href="/profile">Profile</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <#if steamid??>
                        <ul class="nav navbar-nav">
                            <li id="steamlogout"><a href="/logout">Logout</a></li>
                        </ul>
                    <#else>
                        <div id="steamhref">
                            <div style="padding: 7px 5px;">
                                <a href="/login">
                                    <img src="/css/img/sits_01.png" alt="Steam Login">
                                </a>
                            </div>
                        </div>
                    </#if>
                </ul>
            </div>
        </div>
    </nav>
</div>
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
<div id="alertdiv" class="alert-div"></div>
<div class="col-sm-6">
    <div class="box" style="padding-left: 10px; padding-right: 10px;">
        <h3>Got any suggestions?</h3>
        <h4>post them below!</h4>
        <div class="row">
            <#if steamid ??>
                <div class="col-sm-6 col-sm-offset-3">
                    <form id="suggestionForm" class="form-horizontal" action="javascript:submitSuggestion();"
                          name="suggestionForm"
                          method="POST">
                        <div class="form-group">
                            <label for="category" class="control-label col-sm-3">Category:</label>
                            <div class="col-sm-9" style="color: #000000">
                                <select name="category" class="form-control">
                                    <option value="feature">Feature</option>
                                    <option value="bug">Bug</option>
                                    <option value="misc">Miscellaneous</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="title" class="control-label col-sm-3">Title:</label>
                            <div class="col-sm-9">
                                <input id="suggestionTitle" type="text" name="title" class="form-control"
                                       maxlength="32">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="description" class="control-label col-sm-3">Description:</label>
                            <div class="col-sm-9" style="color: #000000">
                                <textarea id="suggestionText" name="description" class="form-control"
                                          maxlength="1000"></textarea>
                            </div>
                        </div>
                        <input id="subbtn" type="submit" class="btn btn-primary form-control" value="Submit">
                    </form>
                </div>
            <#else>
                <div style="color: #666666">You need to be logged in to add suggestions!</div>
            </#if>
        </div>
    </div>
    <div class="box">
        <h3>Longest Friendships</h3>
        <div id="topthreefriendships">
            <div class="medal-container">
                <div class="medal-avatar">
                    <div class="medal gold">
                        <div class="medal-outer">
                            <div class="medal-inner">
                                <a id="topthreefriendships_goldlink">
                                    <img id="topthreefriendships_goldimg" class="medal-img"
                                         src="/css/img/defaultImageMedium.jpg">
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                <a id="topthreefriendships_goldtitle" class="gold-text medal-container-title"></a>
                <div id="topthreefriendships_goldtext" class="medal-container-text"></div>
            </div>
            <div class="medal-container">
                <div class="medal-avatar">
                    <div class="medal silver">
                        <div class="medal-outer">
                            <div class="medal-inner">
                                <a id="topthreefriendships_silverlink">
                                    <img id="topthreefriendships_silverimg" class="medal-img"
                                         src="/css/img/defaultImageMedium.jpg">
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                <a id="topthreefriendships_silvertitle" class="silver-text medal-container-title"></a>
                <div id="topthreefriendships_silvertext" class="medal-container-text"></div>
            </div>
            <div class="medal-container">
                <div class="medal-avatar">
                    <div class="medal bronze">
                        <div class="medal-outer">
                            <div class="medal-inner">
                                <a id="topthreefriendships_bronzelink">
                                    <img id="topthreefriendships_bronzeimg" class="medal-img"
                                         src="/css/img/defaultImageMedium.jpg">
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                <a id="topthreefriendships_bronzetitle" class="bronze-text medal-container-title"></a>
                <div id="topthreefriendships_bronzetext" class="medal-container-text"></div>
            </div>
        </div>
    </div>
    <div class="box">
        <h3>Friend Collectors</h3>
        <div id="topthreehoarders">
            <div class="medal-container">
                <div class="medal-avatar">
                    <div class="medal gold">
                        <div class="medal-outer">
                            <div class="medal-inner">
                                <a id="topthreehoarders_goldlink">
                                    <img id="topthreehoarders_goldimg" class="medal-img"
                                         src="/css/img/defaultImageMedium.jpg">
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                <a id="topthreehoarders_goldtitle" class="gold-text medal-container-title"></a>
                <div id="topthreehoarders_goldtext" class="medal-container-text"></div>
            </div>
            <div class="medal-container">
                <div class="medal-avatar">
                    <div class="medal silver">
                        <div class="medal-outer">
                            <div class="medal-inner">
                                <a id="topthreehoarders_silverlink">
                                    <img id="topthreehoarders_silverimg" class="medal-img"
                                         src="/css/img/defaultImageMedium.jpg">
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                <a id="topthreehoarders_silvertitle" class="silver-text medal-container-title"></a>
                <div id="topthreehoarders_silvertext" class="medal-container-text"></div>
            </div>
            <div class="medal-container">
                <div class="medal-avatar">
                    <div class="medal bronze">
                        <div class="medal-outer">
                            <div class="medal-inner">
                                <a id="topthreehoarders_bronzelink">
                                    <img id="topthreehoarders_bronzeimg" class="medal-img"
                                         src="/css/img/defaultImageMedium.jpg">
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                <a id="topthreehoarders_bronzetitle" class="bronze-text medal-container-title"></a>
                <div id="topthreehoarders_bronzetext" class="medal-container-text"></div>
            </div>
        </div>
    </div>
    <div class="box">
        <h3>Monthly Collectors</h3>
        <div id="topthreemonthlyhoarders">
            <div class="medal-container">
                <div class="medal-avatar">
                    <div class="medal gold">
                        <div class="medal-outer">
                            <div class="medal-inner">
                                <a id="topthreemonthlyhoarders_goldlink">
                                    <img id="topthreemonthlyhoarders_goldimg" class="medal-img"
                                         src="/css/img/defaultImageMedium.jpg">
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                <a id="topthreemonthlyhoarders_goldtitle" class="gold-text medal-container-title"></a>
                <div id="topthreemonthlyhoarders_goldtext" class="medal-container-text"></div>
            </div>
            <div class="medal-container">
                <div class="medal-avatar">
                    <div class="medal silver">
                        <div class="medal-outer">
                            <div class="medal-inner">
                                <a id="topthreemonthlyhoarders_silverlink">
                                    <img id="topthreemonthlyhoarders_silverimg" class="medal-img"
                                         src="/css/img/defaultImageMedium.jpg">
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                <a id="topthreemonthlyhoarders_silvertitle" class="silver-text medal-container-title"></a>
                <div id="topthreemonthlyhoarders_silvertext" class="medal-container-text"></div>
            </div>
            <div class="medal-container">
                <div class="medal-avatar">
                    <div class="medal bronze">
                        <div class="medal-outer">
                            <div class="medal-inner">
                                <a id="topthreemonthlyhoarders_bronzelink">
                                    <img id="topthreemonthlyhoarders_bronzeimg" class="medal-img"
                                         src="/css/img/defaultImageMedium.jpg">
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                <a id="topthreemonthlyhoarders_bronzetitle" class="bronze-text medal-container-title"></a>
                <div id="topthreemonthlyhoarders_bronzetext" class="medal-container-text"></div>
            </div>
        </div>
    </div>
    <form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_top">
        <input type="hidden" name="cmd" value="_s-xclick">
        <input type="hidden" name="encrypted" value="-----BEGIN PKCS7-----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-----END PKCS7-----
">
        <input type="image" src="https://www.paypalobjects.com/en_GB/i/btn/btn_donate_LG.gif" border="0" name="submit" alt="PayPal – The safer, easier way to pay online!">
        <img alt="" border="0" src="https://www.paypalobjects.com/en_US/i/scr/pixel.gif" width="1" height="1">
    </form>

</div>
<div class="col-sm-3">
    <div class="box">
        <h3>Approved Suggestions</h3>
        <div id="approvedsuggestions"></div>
    </div>
</div>
<div style="padding-bottom: 10px"></div>
<script src="js/steamstatistics.js"></script>
<script src="js/frontpage.js"></script>
</@m.master>