console.log('script is here')
const backdrop = document.getElementById('backdrop');
const submitRequestButton = document.getElementById('submit-request');
const requestModal = document.getElementById('request-modal');
const cancelRequestModalButton = requestModal.querySelector('.btn--passive');
const accountButton = document.getElementById('btn-account')
const accountModal = document.getElementById('account-modal')
const cancelAccountModalButton = accountModal.querySelector('.btn--passive')


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
submitRequestButton.addEventListener('click', openRequestModal);
backdrop.addEventListener('click', closeModals);
cancelRequestModalButton.addEventListener('click', closeModals);
accountButton.addEventListener('click', openAccountModal)
cancelAccountModalButton.addEventListener('click', closeModals)