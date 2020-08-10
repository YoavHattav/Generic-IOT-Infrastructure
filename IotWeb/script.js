
const loginForm = document.getElementById("loginForm");
loginForm.addEventListener("submit", validate);

var attempt = 3; // Variable to count number of attempts.
// Below function Executes on click of login button.


function validate(e) {
    e.preventDefault();
    var username = document.getElementById("loginEmail").value;
    var pwd = document.getElementById("loginPwd").value;
    var body = {
        email: username,
        password: pwd
    };
    var json = JSON.stringify(body);

    console.log(json);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "http://10.1.0.21:8080/TomcatServer/login", true);
    xhr.onload = function () {
        if (xhr.status == 200) {
            console.log("200");
            console.log(xhr.response);
            sessionStorage.setItem('token', xhr.response);
            location.replace("file:///home/student/IotWeb/companyTerminal.html");
        }
        else {
            console.log("nottttttttttt");
            alert(xhr.response);
            --attempt;
            if (attempt == 0) {
                document.getElementById("loginEmail").disabled = true;
                document.getElementById("loginPwd").disabled = true;
                document.getElementById("loginBtn").disabled = true;
                alert("try later..");
            }
        }
    };
    xhr.send(json);
}

const signForm = document.getElementById("signupForm");
signForm.addEventListener("submit", signUp);

function signUp(e) {
    e.preventDefault();
    var username = document.getElementById("signupEmail").value;
    var pwd = document.getElementById("signupPwd").value;
    var add = document.getElementById("signupAddress").value;
    var name = document.getElementById("signupName").value;
    var body = {
        email: username,
        password: pwd,
        address : add,
        company_name : name
    };
    var json = JSON.stringify(body);

    console.log(json);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "http://10.1.0.21:8080/TomcatServer/companies", true);
    xhr.onload = function () {
        if (xhr.status == 200) {
            console.log("200");
            console.log(xhr.response);
            sessionStorage.setItem('token', xhr.response);
            location.replace("file:///home/student/IotWeb/companyTerminal.html");
        }
        else {
            console.log("could not signin");
            alert(xhr.response);
        }
    };
    xhr.send(json);
}