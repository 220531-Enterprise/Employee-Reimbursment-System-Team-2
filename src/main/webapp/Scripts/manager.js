var script = document.createElement('script');
script.src = 'https://code.jquery.com/jquery-3.6.0.min.js';
document.getElementsByTagName('head')[0].appendChild(script);
const url = "http://" + window.location.hostname + ":8080/employee-servlet-app/";
const backdrop = document.getElementById('backdrop');

// Approve/Deny buttom
const approveButton = document.getElementById("approve");
const denyButton = document.getElementById("deny")
//requests
// const submitRequestButton = document.getElementById('submit-request');
const requestModal = document.getElementById('request-modal');
const cancelRequestModalButton = requestModal.querySelector('.btn--passive');
const submitRequestModalButton = requestModal.querySelector('.btn--success');
const requestModalAmountInputFeild = document.getElementById('amount');
const requestModalTypeInputFeild = document.getElementById('type');
const requestModalDescriptionInputFeild = document.getElementById('description');
const requestMessage = document.getElementById('request-message');
//accounts
const accountButton = document.getElementById('btn-account');
const accountModal = document.getElementById('account-modal');
const cancelAccountModalButton = accountModal.querySelector('.btn--passive');
const accountEditButton = document.getElementById('edit-btn');
const saveAccountButton = accountModal.querySelector('.btn--success');
const accountUsernameInputFeild = document.getElementById('user-name');
const accountFirstNameInputFeild = document.getElementById('first-name');
const accountLastNameInputFeild = document.getElementById('last-name');
const accountPasswordInputFeild = document.getElementById('password');
const accountEmailInputFeild = document.getElementById('email');
const accountMessage = document.getElementById('account-message');

// Three Manger Feature
const view_pending_request = document.getElementById('view-pd-rqst');
const view_Resolved_request = document.getElementById('view-rsld-rqst');
const view_All_Emp = document.getElementById('view-emp');
  
fetchUser();    
// opening and closing modals
function openRequestModal() {
    requestModal.classList.add('visible');
    openBackDrop();
}

function clearRequestModalInput() {
    requestModalAmountInputFeild.value = '';
    requestModalTypeInputFeild.value = '';
    requestModalDescriptionInputFeild.value = '';
    requestMessage.innerHTML = ''
}

function openAccountModal() {
    accountModal.classList.add('visible');
    openBackDrop();
}

function toggleAccountfields() {
        // disable all feilds and buttons
        saveAccountButton.toggleAttribute('disabled');
        accountUsernameInputFeild.toggleAttribute('disabled');
        accountFirstNameInputFeild.toggleAttribute('disabled');
        accountLastNameInputFeild.toggleAttribute('disabled');
        accountPasswordInputFeild.toggleAttribute('disabled');
        accountEmailInputFeild.toggleAttribute('disabled');
}
function openBackDrop() {
    backdrop.classList.add('visible');
}

function closeBackDrop() {
    backdrop.classList.remove('visible');
}

function closeModals() {
    if (requestModal.classList.contains('visible')) requestModal.classList.remove('visible');
    if (accountModal.classList.contains('visible')) accountModal.classList.remove('visible');
    accountMessage.innerHTML = '';
    clearRequestModalInput();
    toggleAccountfields();
    closeBackDrop();
}


// submit new reimbursment rquest forms
function submitRequest() {
    // capture inputs
    let amount = requestModalAmountInputFeild.value
    let type = requestModalTypeInputFeild.value
    let description = requestModalDescriptionInputFeild.value



    // if any required inputs are invalid display an error message to the screen and exit the function
    
    if (!amount || !type) {
        requestMessage.style = "color: red"
        requestMessage.innerHTML = 'Invalid input.'
        return;
    }
    // send post request to the server with input values as a JSON object
    fetch(`${url}request`, {

        method: 'POST',
        body: JSON.stringify({
            amount: amount,
            type: type,
            description: description
        }),
        headers: {
            "content-type": "application/json; charset=UTF-8"
        },

    }).then(response => { // if request failed print an error Message to the screen
        if (!response.ok) {
            requestMessage.style = "color: red"
            requestMessage.innerHTML = 'Oops, something went wrong. Please try again later.'
            throw Error('ERROR')
        
        }// if request succeeds print a success Message to the screen and clear input
        clearRequestModalInput()
        requestMessage.style = "color: green;"
        requestMessage.innerHTML = "Request Submitted successfully!"
    }).catch(error => {
        console.log(error)
        requestMessage.style = "color: red"
        requestMessage.innerHTML = 'Oops, something went wrong. Please try again later.'
    })
}

//account modal

function saveAccountChanges() {
    // if button is disabled do nothing
    if (saveAccountButton.hasAttribute('disabled')) return;

    // disable all feilds and buttons
    toggleAccountfields()
    
    //capture field values
    let username = accountUsernameInputFeild.value;
    let firstName = accountFirstNameInputFeild.value;
    let lastName = accountLastNameInputFeild.value;
    let password = accountPasswordInputFeild.value;
    let email = accountEmailInputFeild.value;
    
    // make post request and send JSON with field values
    fetch(`${url}update-account`, {

        method: 'POST',
        body: JSON.stringify({
            username: username,
            firstName: firstName,
            lastName: lastName,
            password: password,
            email: email
        }),
        headers: {
            "content-type": "application/json; charset=UTF-8"
        },

    
    }).then(response => { // if request failed put an error message on the screen
        if (!response.ok) {
            accountMessage.style = "color: red";
            accountMessage.innerHTML = 'Oops, something went wrong. Please try again later.';
            throw Error('ERROR');

        } //if request was successful put a success accountMessage on the screen
        accountMessage.style = "color: green;";
        accountMessage.innerHTML = "Request Submitted successfully!";
    }).catch(error => {
        console.log(error);
        accountMessage.style = "color: red";
        accountMessage.innerHTML = 'Oops, something went wrong. Please try again later.';
    })
}

function fetchUser() {
    console.log("im working")

    // Fetch API is modern interface that allows you
    // to make HTTP requests to a server and process the results that 
    // you get back asynchrnously
    let hostname = window.location.hostname;  // this will grab the IP of where it's deployed  

    // the following request will work for testing on localhost
    
    fetch(`http://${hostname}:8080/employee-servlet-app/get-user`)
    // this is changed because the port will be inferred when deployed on Elastic beanstalk
    .then(response => response.json()) // trakes a json string and transforms
                                        // it to a javaScript object
    //.then(obj => console.log(obj)); // print the JS obj to the console
    .then(data => insertValues(data)); // this automatically passes the data that's been parsed


}

function insertValues(data) {
    let userNameTitle = document.getElementById('user-name-title')
    userNameTitle.innerText = data.username;

     document.getElementById('user-name').value = data.username;
     document.getElementById('first-name').value = data.firstName;
     document.getElementById('last-name').value = data.lastName;
     document.getElementById('password').value = data.password;
     document.getElementById('email').value = data.email;

}

function saveDecisionChanges(remb_data,decision) {
  
    console.log("saveDecisionChanges is working")
    //capture field values
  
    
  
    // make post request and send JSON with field values
    fetch(`${url}update-reimbs-status`, {
       
        method: 'POST',
        body: JSON.stringify({
            id: remb_data.id,
            decision: decision, 
            email: remb_data.email

          
        }),
        headers: {
            "content-type": "application/json; charset=UTF-8"
        },

    
    }).then(response => { // if request failed put an error message on the screen
        if (!response.ok) {
            // accountMessage.style = "color: red";
            // accountMessage.innerHTML = 'Oops, something went wrong. Please try again later.';
            throw Error('ERROR');
            

        } //if request was successful put a success accountMessage on the screen
        accountMessage.style = "color: green;";
        accountMessage.innerHTML = "Request Submitted successfully!";
        return true;
    }).catch(error => {
        console.log(error);
        accountMessage.style = "color: red";
        accountMessage.innerHTML = 'Oops, something went wrong. Please try again later.';
        return false;
    })
   
}


// event listeners
// general
backdrop.addEventListener('click', closeModals);
//new request
// submitRequestButton.addEventListener('click', openRequestModal);
cancelRequestModalButton.addEventListener('click', closeModals);
submitRequestModalButton.addEventListener('click', submitRequest)
// edit account
accountButton.addEventListener('click', openAccountModal)
cancelAccountModalButton.addEventListener('click', closeModals)
accountEditButton.addEventListener('click', toggleAccountfields)
saveAccountButton.addEventListener('click', saveAccountChanges)