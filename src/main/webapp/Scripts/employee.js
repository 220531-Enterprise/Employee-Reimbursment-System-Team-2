const url = "http://" + window.location.hostname + ":8080/employee-servlet-app/";
const backdrop = document.getElementById('backdrop');
//requests
const submitRequestButton = document.getElementById('submit-request');
const requestModal = document.getElementById('request-modal');
const cancelRequestModalButton = requestModal.querySelector('.btn--passive');
const submitRequestModalButton = requestModal.querySelector('.btn--success');
//accounts
const accountButton = document.getElementById('btn-account')
const accountModal = document.getElementById('account-modal')
const cancelAccountModalButton = accountModal.querySelector('.btn--passive')

// opening and closing modals
const openRequestModal = () => {
    requestModal.classList.add('visible')
    openBackDrop();
}
const openAccountModal = () => {
    accountModal.classList.add('visible')
    openBackDrop()
}
const closeModals = () => {
    if (requestModal.classList.contains('visible')) requestModal.classList.remove('visible')
    if (accountModal.classList.contains('visible')) accountModal.classList.remove('visible')
    closeBackDrop()
}

const openBackDrop = () => {
    backdrop.classList.add('visible');
}

const closeBackDrop = () => {
    backdrop.classList.remove('visible')
}

// submit forms
const submitRequest = () => {
    console.log('sending request')
    let amount = document.getElementById('amount').value
    let type = document.getElementById('type').value
    let description = document.getElementById('description').value
    let message = document.getElementById('request-message')
    if (!amount || !type) {
        console.log('error')
        message.style = "color: red"
        message.innerHTML = 'Invalid input.'
        return;
    }

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

    }).then(response => {
        if (!response.ok) {
            message.style = "color: red"
            message.innerHTML = 'Oops, something went wrong. Please try again later.'
            throw Error('ERROR')
        }
        message.style = "color: green;"
        message.innerHTML = "Request Submitted successfully!"
    }).catch(error => {
        console.log(error)
    })
}

// event listeners
submitRequestButton.addEventListener('click', openRequestModal);
backdrop.addEventListener('click', closeModals);
cancelRequestModalButton.addEventListener('click', closeModals);
accountButton.addEventListener('click', openAccountModal)
cancelAccountModalButton.addEventListener('click', closeModals)
submitRequestModalButton.addEventListener('click', submitRequest)