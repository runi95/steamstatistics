<#import "master.ftlh" as m/>
<@m.master>

<div class="container">
    <div class="row">
        <h1 class="text-danger">Welcome</h1>
    </div>
    <div class="row">
        <#if steamprofile ??>
            Steam name: ${steamprofile.steamName}
        <#else>
            <a href="/trade">
                <img src="/css/img/sits_02.png" alt="Steam Login">
            </a>
        </#if>
    </div>
</div>

</@m.master>