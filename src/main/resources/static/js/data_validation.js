// function ValidateEmail taken from: https://www.w3resource.com/javascript/form/email-validation.php
// however, regex was taken from here: https://stackoverflow.com/questions/46155/how-to-validate-an-email-address-in-javascript

function ValidateEmail(){
    const email = document.form1.email;
    const mailFormat = /^[^\s@<>+*/=!"£$%^&()`¬\\|;:?,#~]+@cardiff.ac.uk/;

    return !!email.value.match(mailFormat);
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

function ValidateFirstNameInput(){
    const firstName = document.form1.firstName.value;
    const nameFormat = /[0-9@<>+*/=!"£$%^&()`¬\\|;:?,#~]/;
    return !firstName.match(nameFormat);
}

function ValidateLastNameInput(){
    const lastName = document.form1.lastName.value;
    const nameFormat = /[0-9@<>+*/=!"£$%^&()`¬\\|;:?,#~]/;
    return !lastName.match(nameFormat);
}

function ValidateData() {
    let emailFormat = ValidateEmail();
    let passwordFormat = ValidatePassword();
    let samePasswordInput = ValidateSamePasswordInput();
    let firstNameInput = ValidateFirstNameInput();
    let lastNameInput = ValidateLastNameInput();
    if (emailFormat && passwordFormat && samePasswordInput && firstNameInput && lastNameInput){
        return true;
    } else {
        if (emailFormat === false){
            document.getElementById("error").innerText = "There is a problem in the email";
            document.form1.email.focus();
        } else if (passwordFormat === false){
            document.getElementById("error").innerText = "There is a problem in the password";
                document.form1.password.focus();
        } else if (samePasswordInput === false){
            document.getElementById("error").innerText = "The passwords don't match each other";
                document.form1.repeatPassword.focus();
        } else if (firstNameInput === false){
            document.getElementById("error").innerText = "First name can only contain letters";
            document.form1.firstName.focus();
        } else if (lastNameInput === false){
            document.getElementById("error").innerText = "Last name can only contain letters";
            document.form1.lastName.focus();
        }

        document.getElementById("error").style.display="block";
        return false;
    }
}
