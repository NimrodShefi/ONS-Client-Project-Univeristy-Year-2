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

function ValidateSamePasswordInput() {
    const password = document.form1.password.value;
    const repeatPassword = document.form1.repeatPassword.value;
    if (password === repeatPassword){
        return true;
    } else {
        return false;
    }
}

function ValidateData() {
    let emailFormat = ValidateEmail();
    let passwordFormat = ValidatePassword();
    let samePasswordInput = ValidateSamePasswordInput();
    if (emailFormat && passwordFormat && samePasswordInput){
        return true;
    } else {
        if (emailFormat === false){
            document.form1.email.focus();
        } else if (passwordFormat === false){
            document.form1.password.focus();
        } else if (samePasswordInput === false){
            document.form1.repeatPassword.focus();
        }

        document.getElementById("error").style.display="block";
        return false;
    }
}
