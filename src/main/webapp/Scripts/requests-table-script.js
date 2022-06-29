console.log('js working')
let table = document.getElementById('request-table');
let pendingButton = document.getElementById('pending_requests')
//let approvedButton = document.getElementById('approved_requests')
//let deniedButton = document.getElementById('denied_requests')
let reimbs = fetchReimbs();
reimbs.then(data => buildTable(data))

pendingButton.addEventListener('click', fetchPending);
//approvedButton.addEventListener('click', fetchApproved);
//deniedButton.addEventListener('click', fetchDenied);

function buildTable(data) {

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

async function fetchReimbs() {
	console.log('fetching');
    let hostname = window.location.hostname;
    return fetch(`http://${hostname}:8080/employee-servlet-app/find-requests`)
    .then(r => r.json());
    
}

function fetchPending() { // TODO finish this
    console.log(reimbs);
}