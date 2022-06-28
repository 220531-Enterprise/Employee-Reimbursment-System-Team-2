let table = document.querySelector('table');
let pendingButton = document.getElementById('pending_requests')
let approvedButton = document.getElementById('approved_requests')
let deniedButton = document.getElementById('denied_requests')
let reimbs = fetchReimbs();

pendingButton.addEventListener('click', fetchPending);
approvedButton.addEventListener('click', fetchApproved);
deniedButton.addEventListener('click', fetchDenied);

function buildTable(data) {
    let header = document.createElement('thead');
    let headerRow = document.createElement('tr'); 
    header.appendChild(headerRow);
    table.appendChild(header); 

    let th1 = document.createElement('th');
    th1.innerHTML = 'Request Date'

    let th2 = document.createElement('th');
    th1.innerHTML = 'Amount'

    let th3 = document.createElement('th');
    th1.innerHTML = 'Description'

    let th4 = document.createElement('th');
    th1.innerHTML = 'Catagory'

    let th5 = document.createElement('th');
    th1.innerHTML = 'Status'

    let th6 = document.createElement('th');
    th1.innerHTML = 'Approved By'

    headerRow.appendChild(th1);
    headerRow.appendChild(th2);
    headerRow.appendChild(th3);
    headerRow.appendChild(th4);
    headerRow.appendChild(th5);
    headerRow.appendChild(th6);

    data.forEach(r => {
        let row = document.createElement('tr');
        let td1 = document.createElement('td');
        let td2 = document.createElement('td');
        let td3 = document.createElement('td');
        let td4 = document.createElement('td');
        let td5 = document.createElement('td');
        let td6 = document.createElement('td');

        td1.innerHTML = r.dateSubmitted;
        td2.innerHTML = r.amount;
        td3.innerHTML = r.description;
        td4.innerHTML = r.type;
        td5.innerHTML = r.status;
        td6.innerHTML = r.resolverId > 0 ? r.resolverId : "N/A";

        row.appendChild(td1);
        row.appendChild(td2);
        row.appendChild(td3);
        row.appendChild(td4);
        row.appendChild(td5);
        row.appendChild(td6);

        table.appendChild(row);
    });
}

function fetchReimbs() {
    let hostname = window.location.hostname;
    return fetch(`http://${hostname}/employee-servlet-app/find-requests`)
        .then(response => response.json());
}

function fetchPending() { // TODO finish this
    console.log(reimbs)
}