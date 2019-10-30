function profileValid() {
    var password = document.Profile.password;
    var firstName = document.Profile.firstName;
    var lastName = document.Profile.lastName;
    var mobile = document.Profile.mobile;


    if (password.value.length > 15) {
        window.alert("Password have incorrect size ");
        return false;
    }

    if (!validateMobile(mobile.value)) {
        window.alert("Invalid phone number! Example: xxx-xxx-xxxx");
        return false;
    }

    if (!validateName(firstName.value)) {
        window.alert("invalid syntax")
    }

    if (!validateName(lastName.value)) {
        window.alert("Invalid syntax");
        return false;
    }


}

function validateName(firstName) {
    if (/^[a-zA-Z]{2,}$/.test(firstName)) {
        return false;
    }
    return true;
}

function validateMobile(mobile) {
    if (/(?:\d{3}-){2}\d{4}/.test(mobile)) {
        return false;
    }
    return true;
}




