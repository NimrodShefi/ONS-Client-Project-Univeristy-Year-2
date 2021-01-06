// function ValidateEmail taken from: https://www.w3resource.com/javascript/form/email-validation.php
// however, regex was taken from here: https://stackoverflow.com/questions/46155/how-to-validate-an-email-address-in-javascript

function ValidateEmail(){
    const email = document.form1.email.value;
    const mailFormat = /^[^\s@<>+*/=!"£$%^&()`¬\\|;:?,#~]+@cardiff.ac.uk/;

    return !!email.match(mailFormat);
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
    return password === repeatPassword;
}

function ValidateName(name) {
    const nameFormat = /^[a-zA-Z]*$/;
    if (name){
        return !!name.match(nameFormat);
    } else {
        return false;
    }
}

function resetClassInputColour(){
    const elements = document.getElementsByClassName("input");
    for (let i = 0; i < elements.length; i++) {
        elements[i].style.borderColor="grey";
    }
}

function ValidateData() {
    let emailFormat = ValidateEmail();
    let passwordFormat = ValidatePassword();
    let samePasswordInput = ValidateSamePasswordInput();
    let firstNameInput = ValidateName(document.form1.firstName.value);
    let lastNameInput = ValidateName(document.form1.lastName.value);
    if (emailFormat && passwordFormat && samePasswordInput && firstNameInput && lastNameInput){
        return true;
    } else {
        resetClassInputColour();
        if (emailFormat === false){
            document.getElementById("error").innerText = "There is a problem in the email. example@cardiff.ac.uk";
            document.form1.email.focus();
            document.form1.email.style.borderColor = "red";
        } else if (passwordFormat === false){
            document.getElementById("error").innerText = "There is a problem in the password. Look at the rules provided";
            document.form1.password.focus();
            document.form1.password.style.borderColor = "red";
        } else if (samePasswordInput === false){
            document.getElementById("error").innerText = "The passwords don't match each other";
            document.form1.repeatPassword.focus();
            document.form1.repeatPassword.style.borderColor = "red";
        } else if (firstNameInput === false){
            document.getElementById("error").innerText = "There is a problem in the first name. Name can only contain letters";
            document.form1.firstName.focus();
            document.form1.firstName.style.borderColor = "red";
        } else if (lastNameInput === false){
            document.getElementById("error").innerText = "There is a problem in the last name. Name can only contain letters";
            document.form1.lastName.focus();
            document.form1.lastName.style.borderColor = "red";
        }

        return false;
    }
}
