function laadPaginaIn() {
    closeAanmaakDialog();
}

function sendLoginData() {
    let gbrnm = document.getElementById("gebruikernaam");
    let ww = document.getElementById("wachtwoord");
    let messagediv = document.getElementById('messagediv');

    if (gbrnm.value.length === 0 || ww.value.length === 0) {
        messagediv.innerHTML = "Vul gebruikernaam / wachtwoord in"
    } else {

        let formData = new FormData(document.querySelector("#loginform"));
        let fetchOptions = {
            method: "POST",
            body: new URLSearchParams(formData)
        };

        fetch("/restservices/authenticate", fetchOptions)
            .then((response) => {
                if (response.ok) {
                    return response.json();
                }
                if (response.status === 401) {
                    messagediv.innerHTML = "Verkeerde gebruikernaam / wachtwoord"
                    throw "Wrong username / password";
                } else throw new Error("er ging iets mis")
            })
            .then(myJson => {
                window.sessionStorage.setItem("myJWT", myJson.JWT)
                location.href = 'homeScherm.html'
            })
            .catch(error => console.log(error))
    }
}

function stuurGebruikerData() {
    let gbn = document.getElementById('nwgebruikernaam');
    let em = document.getElementById('emailadres');
    let ww = document.getElementById('nwwachtwoord');
    let datadiv = document.querySelector('#postresponse')
    let messagediv = document.querySelector('#messagediv')

    if (gbn.value.length === 0 ||
        em.value.length === 0 ||
        ww.value.length === 0 ) {
        datadiv.innerHTML = "U moet alles invullen"
    } else {
        var formData = new FormData(document.querySelector("#invoergegevensform"));
        var fetchOptions = {
            method: "POST",
            body: new URLSearchParams(formData)
        }
        fetch("/restservices/gebruiker/maakgebruiker", fetchOptions)
            .then((response) => {
                if (response.ok) {
                    messagediv.innerHTML = "Uw account is aangemaakt";
                    closeAanmaakDialog();
                }
                if (response.status === 409){
                    datadiv.innerHTML = "Email staat al geregistreerd";
                    throw new Error("Email staat al geregistreerd");}
                else throw new Error("Er ging iets fout");
            })
            .catch(error => console.log(error))
    }
}

function openAanmaakDialog() {
    let dialog = document.getElementById("nieuwaccountdialog");
    dialog.show();
}

function closeAanmaakDialog() {
    let dialog = document.getElementById("nieuwaccountdialog");
    document.getElementById("nwgebruikernaam").value = "";
    document.getElementById("emailadres").value = "";
    document.getElementById("nwwachtwoord").value = "";
    dialog.close();
}

document.addEventListener('DOMContentLoaded', laadPaginaIn);
