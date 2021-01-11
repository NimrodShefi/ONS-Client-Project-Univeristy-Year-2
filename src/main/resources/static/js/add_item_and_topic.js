function addItemRow(element) {
    const itemTable = element.target.closest("TABLE");
    const itemRowIndex = $(element.target).parent().parent().index('tr');

    const row = itemTable.insertRow(itemRowIndex + 1);

    const cell1 = row.insertCell(0);
    const cell2 = row.insertCell(1);
    const cell3 = row.insertCell(2);
    const cell4 = row.insertCell(3);

    cell1.innerHTML = "<label>Topic Item: </label>";
    cell2.innerHTML = "<input id='items' name='items' placeholder='Insert item...' class='form-control' required>";
    cell3.innerHTML = "<button type='button' id='remove' class='btn btn--secondary btn--small'>-</button>";
    cell4.innerHTML = "<button type='button' id='add' class='btn btn--secondary btn--small'>+</button>";
}

function deleteRow(el) {
    const rowSize = el.target.closest("TABLE").rows.length;
    if (rowSize > 1) {
        $(el.target).closest('tr').remove();
    }
}

$(document).on('click', '#add', function (event) {
    addItemRow(event);
})

$(document).on('click', '#remove', function (event) {
    deleteRow(event);
})