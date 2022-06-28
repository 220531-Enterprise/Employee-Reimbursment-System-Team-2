console.log("script loaded")
fetchUser();

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