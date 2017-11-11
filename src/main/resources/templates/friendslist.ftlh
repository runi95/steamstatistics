<#import "master.ftlh" as m/>
<@m.master>
<div style="padding-bottom: 50px;">
    <nav class="navbar navbar-inverse navbar-fixed-top">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#mainNav">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/">Steam Statistics</a>
            </div>
            <div class="collapse navbar-collapse" id="mainNav">
                <ul class="nav navbar-nav">
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Profile
                            <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="/">Profile</a></li>
                            <li class="active"><a href="/friends">Friends</a></li>
                        </ul>
                    </li>
                    <!-- <li class="active"><a href="/">Profile</a></li> -->
                    <li><a href="/home">Frontpage</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <div style="padding: 7px 5px;">
                        <div id="steamhref" class="hide">
                            <a href="/login">
                                <img src="/css/img/sits_01.png" alt="Steam Login">
                            </a>
                        </div>
                    </div>
                </ul>
            </div>
        </div>
    </nav>
</div>
<div class="container">
    <div class="row">
        <div class="col-xs-10 col-xs-offset-1">
            Friends (<span id="friendcount"></span>)
            <div id="friends">

            </div>
        </div>
    </div>
</div>
<script src="js/steamstatistics.js"></script>
</@m.master>