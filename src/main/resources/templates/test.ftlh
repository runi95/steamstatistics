<link href="css/steamstatistics.css" rel="stylesheet">
<style>
    .profile_header_bg_texture {
        max-width: 976px;
        min-height: 224px;
        margin: 0 auto;
        background-color: #283242;
        /* rgb(26, 27, 31) rgb(30, 31, 38) rgb(40, 50, 66) rgb(30, 31, 38) rgb(26, 27, 31)*/
        background: -webkit-linear-gradient( 145deg, #1a1b1f 10%,#1a1b1f 40%,#1e1f26 52%,#1a1b1f 61%,#1a1b1f 85%);
        background: linear-gradient( 145deg, #1a1b1f 10%,#1a1b1f 40%,#1e1f26 52%,#1a1b1f 61%,#1a1b1f 85%);
    }

    .profileHeader {
        position: relative;
        max-width: 926px;
        margin: 0 auto;
        padding-top: 24px;
    }

    .profile_header_content {
        position: relative;
        padding-top: 8px;
        color: #898989;
        font-size: 13px;
    }

    .profile_header .profile_header_centered_persona {
        position: absolute;
        left: 186px;
        right: 268px;
        white-space: nowrap;
    }

    .profile_header .persona_name {
        line-height: 40px;
        font-size: 24px;
        color: #ffffff;

        text-overflow: ellipsis;
        white-space: nowrap;
        overflow: hidden;

        font-family: "Motiva Sans", Sans-serif;
        font-weight: 200; /* thin */

    }

    .playerAvatar.profile_header_size {
        position: relative;
        width: 166px;
        height: 166px;
    }
</style>
<div class="profile_header_bg_texture">
    <div class="profile_header">
        <div class="profile_header_content">
            <div class="profile_header_centered_persona">
                <div class="persona_name" style="font-size: 24px;">
                    <span class="actual_persona_name">Viridan Prism</span>
                </div>
            </div>
            <div class="playerAvatar profile_header_size online">
                <div class="playerAvatarAutoSizeInner">
                    <img src="http://cdn.akamai.steamstatic.com/steamcommunity/public/images/avatars/b8/b867b64f01bfccd3fa69836e1a9ce0bba90f0a75_full.jpg">
                </div>
            </div>
        </div>
    </div>
</div>