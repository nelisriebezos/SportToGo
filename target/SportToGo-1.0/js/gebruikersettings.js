function laadGebruikerDataIn() {
    let fetchoptions = {
        method: "GET",
        headers: {
            'Authorization' : 'Bearer ' + window.sessionStorage.getItem("myJWT")
        }}
    fetch("/restservices/gebruiker/settings", fetchoptions)
        .then((response) => {
            if (response.ok) return response.json();
            if (response.status === 401) throw new Error("Gebruiker niet geauthoriseerd");
            else throw "gebruiker niet gevonden";
        })
        .then(myJson => {
            let usernamediv = document.querySelector('#usernamelable')
            let emaillable = document.querySelector('#emaillable')
            usernamediv.innerHTML = myJson.gebruikernaam;
            emaillable.innerHTML = myJson.emailadres;
        })
        .catch(error => console.log(error))
}

function stuurWachtwoordOp() {
    let wachtwoordveranderddiv = document.querySelector("#wachtwoordveranderddiv");
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
                    if (response.ok) {
                        closeWachtwoordDialog()
                        return wachtwoordveranderddiv.innerHTML = "Uw wachtwoord is gewijzigd";
                    }
                    if (response.status === 400) {
                        messagediv.innerHTML = "Het gegeven wachtwoord klopt niet"
                        throw new Error("Gegeven wachtwoord klopt niet")
                    }
                    if (response.status === 409) {
                        messagediv.innerHTML = "Nieuw wachtwoord mag niet hetzelfde zijn als de oude";
                        throw new Error("De twee wachtwoorden zijn hetzelfde")
                    }
                    if (response.status === 401) throw new Error("Gebruiker niet geauthoriseerd");
                    else throw new Error("Er ging iets fout");
                })
                .catch(error => console.log(error))
        }
        messagediv.innerHTML = "Nieuw wachtwoord en herhaling komen niet overeen";
    }
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
