<#import "master.ftlh" as m/>
<@m.master>
<div class="container">
    <div class="row">
        <h1 class="text-danger">Steam Statistics</h1>
    </div>
    <div class="row">
        <#if steamprofile??>
            <#if !steamprofilestats??>
            </#if>
            <#if steamprofile.communityvisibilitystate != "3">
                <div>
                    <dd><span class="text-danger">Warning</span>: Your account is not set to public, some functions on this
                    website may not work!</dd>
                </div>
            </#if>
            <div class="row">
                <div class="profileHeaderBackground">
                    <div class="profileHeader">
                        <div class="profileHeaderContent">
                            <div class="profileHeaderCenteredPersona" style="text-align: left;">
                                <div class="personaName">
                                    <span class="actualPersonaName" style="font-size: 24px;">${steamprofile.personaname}</span>
                                </div>
                                    <span>Friends Gained (this month): <span class="${steamfriends.monthIndex}">${steamfriends.friendsGainedLastMonth}</span></span>
                                    <br>
                                    <span>Friends Gained (this week): <span class="${steamfriends.weekIndex}">${steamfriends.friendsGainedLastWeek}</span></span>
                            </div>
                            <div class="profileAvatar profileHeaderSize ${steamprofile.profilestate}">
                                <div class="playerAvatarAutoSizeInner">
                                    <img src="${steamprofile.avatarfull}">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        <#else>
            <a href="/login">
                <img src="/css/img/sits_02.png" alt="Steam Login">
            </a>
        </#if>
    </div>
</div>

<script>
    $('#useragreementmodal').modal('show');
</script>

</@m.master>