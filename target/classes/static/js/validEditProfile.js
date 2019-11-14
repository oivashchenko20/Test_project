function profileValid() {
    var password = document.Profile.password;
    var firstName = document.Profile.firstName;
    var lastName = document.Profile.lastName;
    var mobile = document.Profile.mobile;


    if (password.value.length > 15) {
        window.alert("Password have incorrect size ");
        return false;
    }

    if (!validateName(firstName.value)) {
        window.alert("invalid syntax first name");
        return false;
    }

    if (!validateName(lastName.value)) {
        window.alert("Invalid syntax last name");
        return false;
    }

    validateMobile(mobile.value);

}

function validateName(firstName) {
    if (/[^a-zA-Z0-9\-\/]/.test(firstName)) {
        return false;
    }
    return true;
}

function validateMobile(mobile) {
    if (/^[0-9]{3}-[0-9]{3}-[0-9]{4}$/.test(mobile)) {
        return true;
    } else {
        window.alert("Invalid phone number! Example: xxx-xxx-xxxx");
        return false;
    }
}




