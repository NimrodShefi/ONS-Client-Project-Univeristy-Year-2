function addItemRow(element) {
    const itemTable = element.closest("TABLE");
    const itemRowIndex = $(element).parent().parent().index('tr');
    console.log(itemTable);
    console.log(itemRowIndex);

    const row = itemTable.insertRow(itemRowIndex+1);

    const cell1 = row.insertCell(0);
    const cell2 = row.insertCell(1);
    const cell3 = row.insertCell(2);
    const cell4 = row.insertCell(3);

    cell1.innerHTML = "<label>Topic Item: </label>";
    cell2.innerHTML = "<input id='items' name='items' placeholder='Insert item...' class='form-control' required>";
    cell3.innerHTML = "<button type='button' onclick='deleteRow(this)' class='btn btn--secondary btn--small'>-</button>";
    cell4.innerHTML = "<button type='button' onclick='addItemRow(this)' class='btn btn--secondary btn--small'>+</button>";
}

function deleteRow(el) {
    const rowSize = el.closest("TABLE").rows.length;
    if (rowSize > 2) {
        $(el).closest('tr').remove();
    }
}
