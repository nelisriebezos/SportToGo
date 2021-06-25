function laadGebruikerDataIn() {
    let fetchoptions = {
        method: "GET",
        headers: {
            'Authorization' : 'Bearer ' + window.sessionStorage.getItem("myJWT")
        }}
    fetch("/restservices/gebruiker/settings", fetchoptions)
        .then(response => {
            return Promise.all([response.status, response.json()])
        })
        .then(([status, myJson]) => {
            laadGebruikerDataInStatusHandler(status, myJson);
        })
        .catch(error => console.log(error))
}

function laadGebruikerDataInStatusHandler(status, myJson) {
    if (status === 200) {
        let usernamediv = document.querySelector('#usernamelable')
        let emaillable = document.querySelector('#emaillable')
        usernamediv.innerHTML = myJson.gebruikernaam;
        emaillable.innerHTML = myJson.emailadres;
        return myJson;
    }
    else if (status === 401) return console.log(myJson.error)
    else return console.log("Er ging iets fout")
}

function stuurWachtwoordOp() {
    let oww = document.getElementById('oudwachtwoord');
    let nww = document.getElementById('nieuwwachtwoord');
    let hnww = document.getElementById('hernieuwwachtwoord');

    if (oww.value.length === 0 ||
        nww.value.length === 0 ||
        hnww.value.length === 0) {
        document.querySelector("#messagediv").innerHTML = "Vul alle gegevens in";
    } else {

        if (nww.value === hnww.value) {
            let formData = new FormData(document.querySelector("#gegevenswijzigenform"));
            let fetchOptions = {
                method: "PUT",
                body: new URLSearchParams(formData),
                headers: {
                    'Authorization': 'Bearer ' + window.sessionStorage.getItem("myJWT")
                }
            };

            fetch("/restservices/gebruiker/wachtwoord", fetchOptions)
                .then(response => {
                    return Promise.all([response.status, response.json()])
                })
                .then(([status, myJson]) => {
                    stuurWachtwoordOpStatusHandler(status, myJson)
                })
                .catch(error => console.log(error))
        }
        document.getElementById("messagediv").innerHTML = "Nieuw wachtwoord en herhaling komen niet overeen";
    }
}

function stuurWachtwoordOpStatusHandler(status, myJson) {
    if (status === 200) {
        closeWachtwoordDialog()
        return document.querySelector("#wachtwoordveranderddiv").innerHTML = "Uw wachtwoord is gewijzigd";
    }
    else if (status === 409) {
        document.getElementById("messagediv").innerHTML = myJson.error;
        return console.log(myJson.error)
    }
    else if (status === 401) {
        return console.log(myJson.error)
    }
    else return console.log("Er ging iets fout")
}

function openWachtwoordDialog() {
    let dialog = document.getElementById("wachtwoorddialog");
    dialog.show();
}

function closeWachtwoordDialog() {
    let dialog = document.getElementById("wachtwoorddialog");
    document.querySelector("#messagediv").innerHTML = "";
    document.getElementById("oudwachtwoord").value = "";
    document.getElementById("nieuwwachtwoord").value = "";
    document.getElementById("hernieuwwachtwoord").value = "";
    dialog.close();
}


document.addEventListener('DOMContentLoaded', laadGebruikerDataIn);
