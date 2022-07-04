const registerButton = document.getElementById('register-button')
const message = document.getElementById('message')

function register() {
    message.innerHTML = ''
    let input = document.querySelectorAll('input')
    if(inputIsValid(input)) {
        fistName
        fetch("http://" + window.location.hostname + ":8080/employee-servlet-app/register", {
            method: 'POST',
            body: JSON.stringify({
                firstName: input[0].value,
                lastName: input[1].value,
                username: input[3].value,
                email: input[4].value,
                password: input[5].value
            }),
            headers: {
                "content-type": "application/json; charset=UTF-8"
            },
    }).then(r => message.innerHTML = r)
    }
    
}

function inputIsValid(input) {
    
    let nameReg = /^[A-Za-z]+$/
    let usernameReg = /^\w+$/
    let emailReg = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/
    let passReg = /"(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/
    if (!nameReg.test(input[0].value)){
        message.innerHTML = 'Please enter a valid first name.';
        return false;
    } else if (!nameReg.test(input[1].value)) {
        message.innerHTML = 'Please enter a valid last name.';
        return false;
    } else if (!usernameReg.test(input[2].value)) {
        message.innerHTML = 'Please enter a valid username.';
        return false;
    } else if (!emailReg.test(input[3].value)) {
        message.innerHTML = 'Please enter a valid email address.';
        return false;
    } else if (!passReg.test(input[4].value)) {
        message.innerHTML = 'Please enter a valid password.';
        return false;
    }
    return true;
}


registerButton.addEventListener('click', register)