// function ValidateEmail taken from: https://www.w3resource.com/javascript/form/email-validation.php
// however, regex was taken from here: https://stackoverflow.com/questions/46155/how-to-validate-an-email-address-in-javascript

function ValidateEmail(){
    const email = document.form1.email;
    const mailformat = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

    return !!email.value.match(mailformat);
}

function ValidatePassword() {
    const password = document.form1.password.value;
    // regex was taken from online and the format of code was from recommendation of IntellijJ instead of the usual if else format
    return !!(password.match(/[a-z]/g)
        && password.match(/[A-Z]/g)
        && password.match(/[0-9]/g)
        && password.match(/[^a-zA-Z\d]/g)
        && password.length >= 8);
}

function ValidateData() {
    if (ValidateEmail() && ValidatePassword()){
        return true;
    } else {
        if (ValidateEmail() === false){
            alert("You have entered an invalid email address!");
            document.form1.email.focus();
        } else if (ValidatePassword() === false){
            alert("You have entered an invalid password!");
            document.form1.password.focus();
        }
        return false;
    }
}
