<div class="container">
    <h2>Suggestions</h2>
    <table class="table table-hover">
        <thead>
        <tr>
            <th>Creation Date</th>
            <th>Steamid</th>
            <th>Category</th>
            <th>Description</th>
        </tr>
        </thead>
        <tbody>
        <#list suggestions as suggestion>
        <tr id="sugg_${suggestion.id}" style="color: #337ab7">
            <td>${suggestion.steamid}</td>
            <td>${suggestion.category}</td>
            <td>${suggestion.description}</td>
        </tr>
        </#list>
        </tbody>
    </table>
</div>
