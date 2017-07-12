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
            <#if steamprofile.privacyState != "public">
                <div>
                    <span class="text-warning">Warning</span>: Your account is not set to public, some functions on this
                    website may not work!
                </div>
            </#if>
            <div class="row">
                <div class="profileHeaderBackground">
                    <div class="profileHeader">
                        <div class="profileHeaderContent">
                            <div class="profileHeaderCenteredPersona" style="text-align: left;">
                                <div class="personaName">
                                    <span class="actualPersonaName" style="font-size: 24px;">${steamprofile.steamName}</span>
                                </div>
                                    <br>
                                    <span>${steamprofile.onlineState}</span>
                                    <br>
                                    <span>${steamprofile.stateMessage}</span>
                            </div>
                            <div class="profileAvatar profileHeaderSize ${steamprofile.onlineState}">
                                <div class="playerAvatarAutoSizeInner">
                                    <img src="${steamprofile.avatarFull}">
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