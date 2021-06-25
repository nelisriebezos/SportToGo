function stuurGebruikerData() {
    let gbn = document.getElementById('nwgebruikernaam');
    let em = document.getElementById('emailadres');
    let ww = document.getElementById('nwwachtwoord');

    if (gbn.value.length === 0 ||
        em.value.length === 0 ||
        ww.value.length === 0 ) {
        document.querySelector('#postresponse').innerHTML = "U moet alles invullen"
    } else {
        let formData = new FormData(document.querySelector("#invoergegevensform"));
        let fetchOptions = {
            method: "POST",
            body: new URLSearchParams(formData)
        }
        fetch("/restservices/gebruiker/maakgebruiker", fetchOptions)
            .then(response => {
                return Promise.all([response.status, response.json()])
            })
            .then(([status, myJson]) => {
                stuurGebruikerDataStatusHandler(status, myJson);
            })
            .catch(error => console.log(error))
    }
}

function stuurGebruikerDataStatusHandler(status, myJson) {
    if (status === 200) {
        document.getElementById("messagediv").innerHTML = "Uw account is aangemaakt";
        return myJson;
    }
    else if (status === 409) {
        document.getElementById("postresponse").innerHTML = "Email staat al geregistreerd";
        return console.log(myJson.error)
    }
    else return console.log("Er ging iets fout")
}

function sendLoginData() {
    let gbrnm = document.getElementById("gebruikernaam");
    let ww = document.getElementById("wachtwoord");

    if (gbrnm.value.length === 0 || ww.value.length === 0) {
        messagediv.innerHTML = "Vul gebruikernaam / wachtwoord in"
    } else {

        let formData = new FormData(document.querySelector("#loginform"));
        let fetchOptions = {
            method: "POST",
            body: new URLSearchParams(formData)
        };

        fetch("/restservices/authenticate", fetchOptions)
            .then(response => {
                return Promise.all([response.status, response.json()])
            })
            .then(([status, myJson]) => {
                sendLoginDataStatusHandler(status, myJson)
            })
            .catch(error => console.log(error))
    }
}

function sendLoginDataStatusHandler(status, myJson) {
    if (status === 200) {
        window.sessionStorage.setItem("myJWT", myJson.JWT)
        location.href = 'integratieTestHomeScherm.html'
    }
    else if (status === 401) {
        messagediv.innerHTML = "Verkeerde gebruikernaam / wachtwoord"
        return console.log("Verkeerde inlog gegevens")
    }
    else return console.log("Er ging iets fout")
}
