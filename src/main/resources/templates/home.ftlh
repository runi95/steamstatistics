<#import "master.ftlh" as m/>
<@m.master>
<div class="container">
    <div class="row">
        <h1 class="text-danger">Steam Statistics</h1>
    </div>
<div class="row">
    <#if steamprofile??>
        <#if !steamprofilestats??>

            <!-- Modal -->
            <div class="modal fade" role="dialog" id="useragreementmodal">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <!-- Modal Header -->
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">
                                <span aria-hidden="true">&times;</span>
                                <span class="sr-only">Close</span>
                            </button>
                            <h4 class="modal-title">User Agreement</h4>
                        </div>

                        <!-- Modal Body -->
                        <div class="modal-body">
                        ${useragreement}
                        </div>

                        <!-- Modal Footer -->
                        <div class="modal-footer">
                            <div class="col-xs-6 col-xs-offset-3">
                                <div class="col-xs-6">
                                    <a href="/logout" class="center-block btn btn-default">
                                        Cancel
                                    </a>
                                </div>
                                <div class="col-xs-6">
                                    <a href="/accept" class="center-block btn btn-success">Accept
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Modal End -->

        </#if>
        <#if steamprofile.privacyState != "public">
            <div>
                <span class="text-warning">Warning</span>: Your account is not set to public, some functions on this
                website may not work!
            </div>
        </#if>

        <div class="row">
            <div class="col-xs-6 col-xs-offset-3">
                <div class="profileAvatar profileHeader ${steamprofile.onlineState}">
                    <div class="playerAvatarAutoSizeInner">
                        <img src="${steamprofile.avatarFull}">
                    </div>
                </div>
                <div class="profileHeaderText">
                    <span><h3>${steamprofile.steamName}</h3></span>
                    ${steamprofile.onlineState}
                        <br>
                    ${steamprofile.stateMessage}
                </div>
            </div>
        </div>
    </div>

        <div class="profile persona ${steamprofile.onlineState}" href="/profile">
            <a class="profileLink" href="/profile"></a>

            <div class="profileAvatar ${steamprofile.onlineState}">
                <img src="${steamprofile.avatarIcon}">
            </div>
            <div>
            ${steamprofile.steamName}
                <br>
                <span>
                            <span>
                                ${steamprofile.stateMessage}
                                    <br>
                            </span>
                        </span>
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