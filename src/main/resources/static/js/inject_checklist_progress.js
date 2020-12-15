var injectChecklistProgress = (function (id) {

    $.ajax({
        type: "GET",
        url: "/api/personal-checklist/" + id + "/progress",
        dataType: "json"
        , cache: false
        , timeout: 600000
        , async: true
        , encode: true
    })
        .done(function (data) {
            console.log(data);
            document.getElementById('checkedCount').innerHTML = data.checkedItemsCount;
            document.getElementById('checkItemsCount').innerHTML = data.checklistItemsCount;
        })
        .fail(function (jqXHR, textStatus, errorThrown) {
            console.log("failed");
        });
})