function laadGebruikerDataIn() {
    var fetchoptions = {
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
    let messagediv = document.querySelector("#messagediv");
    let wachtwoordveranderddiv = document.querySelector("#wachtwoordveranderddiv");
    var nww = document.getElementById('nieuwwachtwoord').value;
    var hnww = document.getElementById('hernieuwwachtwoord').value;

    if (nww === hnww) {
        var formData = new FormData(document.querySelector("#gegevenswijzigenform"));
        var fetchOptions = {
            method: "PUT",
            body: new URLSearchParams(formData),
            headers: {
                'Authorization' : 'Bearer ' + window.sessionStorage.getItem("myJWT")
            }};

        fetch("/restservices/gebruiker/wachtwoord", fetchOptions)
            .then(response => {
                if (response.ok) {
                    closeWachtwoordDialog()
                    return wachtwoordveranderddiv.innerHTML = "Uw wachtwoord is gewijzigd";}
                if (response.status === 400) {
                    messagediv.innerHTML = "Nieuw wachtwoord mag niet hetzelfde zijn als de oude"
                    throw new Error("De twee wachtwoorden zijn hetzelfde")}
                if (response.status === 409) {
                    messagediv.innerHTML = "Oud wachtwoord klopt niet";
                    throw new Error("Oud wachtwoord klopt niet")}
                if (response.status === 401) throw new Error("Gebruiker niet geauthoriseerd");
                else throw new Error("Er ging iets fout");
            })
            .catch(error => console.log(error))
    }
    messagediv.innerHTML = "Nieuw wachtwoord en herhaling komen niet overeen";
}

function openWachtwoordDialog() {
    var dialog = document.getElementById("wachtwoorddialog");
    dialog.show();
}

function closeWachtwoordDialog() {
    var dialog = document.getElementById("wachtwoorddialog");
    dialog.close();
}


document.addEventListener('DOMContentLoaded', laadGebruikerDataIn);
