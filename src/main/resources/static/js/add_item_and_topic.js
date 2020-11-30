function addTopicRow() {
    const topicTable = document.getElementById("topicTable");

    const row = topicTable.insertRow();

    const cell1 = row.insertCell(0);
    const cell2 = row.insertCell(1);
    const cell3 = row.insertCell(2);

    cell1.innerHTML = "<input name='topics' placeholder=\"Enter name of topic\" required>";
    cell2.innerHTML = "<textarea name='topics' rows=\"5\" cols=\"30\"\n" +
        "                                  placeholder=\"Enter details about topic here...\" required></textarea>";
    cell3.innerHTML = "<button type='button' onclick='deleteRow(this)'>-</button>";
}

function addItemRow(element) {
    const itemTable = document.getElementById(element.closest("TABLE").id);
    console.log(itemTable);
    const itemRowIndex = $(element).parent().parent().index('tr');
    console.log(itemRowIndex);

    const row = itemTable.insertRow(itemRowIndex+1);

    const cell1 = row.insertCell(0);
    const cell2 = row.insertCell(1);
    const cell3 = row.insertCell(2);
    const cell4 = row.insertCell(3);

    cell1.innerHTML = "Topic Item:";
    cell2.innerHTML = "<input th:for='items' placeholder='insert item'>";
    cell3.innerHTML = "<button type='button' onclick='deleteRow(this)'>-</button>";
    cell4.innerHTML = "<button type='button' onclick='addItemRow(this)'>+</button>";
}

function deleteRow(el) {
    const rowSize = el.closest("TABLE").rows.length;
    if (rowSize > 2) {
        $(el).closest('tr').remove();
    }
}
