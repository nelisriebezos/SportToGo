function stuurWachtwoordOp() {
    let messagediv = document.querySelector("#messagediv");
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
                    messagediv.innerHTML = "Uw wachtwoord is gewijzigd";}
                if (response.status === 400) {
                    messagediv.innerHTML = "Nieuw wachtwoord mag niet hetzelfde zijn als de oude"
                    throw new Error("De twee wachtwoorden zijn hetzelfde")}
                if (response.status === 401) {
                    messagediv.innerHTML = "Oud wachtwoord klopt niet";
                    throw new Error("Oud wachtwoord klopt niet")}
                if (response.status === 409) throw new Error("Gebruiker niet gevonden");
                else throw new Error("Er ging iets fout");
            })
            .catch(error => console.log(error))
    }
    messagediv.innerHTML = "Nieuw wachtwoord en herhaling komen niet overeen";
}
