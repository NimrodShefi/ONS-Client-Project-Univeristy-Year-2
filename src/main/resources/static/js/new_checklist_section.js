// function taken from: https://stackoverflow.com/questions/35404751/html-form-adding-button-to-generate-more-fields/35405069
$(document).ready(function () {
    $(".add-item").click(function () {
        var $clone = $("div.item").first().clone();
        $clone.find("#item").val("");
        $clone.append("<button type='button' class='remove-row'>-</button>");
        $clone.insertBefore(".add-item");
    });

    $(".form-new-checklist").on("click", ".remove-row", function () {
        $(this).parent().remove();
    });
});

$(document).ready(function () {
    $(".addTopic").click(function () {
        var $clone = $("div.checklistTopic").first().clone();
        $clone.find("#topic").val("");
        $clone.find("#topicDescription").val("");
        $clone.find("#item").val(""); // the 3 lines with .val are jsut so that when cloning the form, it will be cloned without the data from above
        $clone.append("<button type='button' class='remove-row'>-</button>");
        $clone.insertBefore(".addTopic");
    });

    $(".form-new-checklist").on("click", ".remove-row", function () {
        $(this).parent().remove();
    });
});