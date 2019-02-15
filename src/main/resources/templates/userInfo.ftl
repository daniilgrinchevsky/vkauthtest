<#import "common.ftl" as c>

<@c.page>
    <div class="ml-3">
    <h3>${userName}</h3>
    </div>
    <ul class="list-group" col-sm-3>
        <div class="col-sm-3">
        <#list friends as friend>
            <li class="list-group-item">${friend}</li>
        </#list>
        </div>
    </ul>
    ${session}
</@c.page>