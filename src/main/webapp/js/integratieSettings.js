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
        return document.querySelector("#messagediv").innerHTML = "Uw wachtwoord is gewijzigd";
    }
    else if (status === 400) {
        document.getElementById("messagediv").innerHTML = "Het gegeven wachtwoord klopt niet"
        return console.log(myJson.error)
    }
    else if (status === 409) {
        document.getElementById("messagediv").innerHTML = "Nieuw wachtwoord mag niet hetzelfde zijn als de oude";
        return console.log(myJson.error)
    }
    else if (status === 401) {
        return console.log(myJson.error)
    }
    else return console.log("Er ging iets fout")
}
