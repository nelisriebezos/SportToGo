function laadPaginaIn() {
    document.querySelector('#schemabox').innerHTML = "";
    document.getElementById("sessieaanmaakalert").innerHTML = "";
    closeVerwijderDialog();
    closeAanmaakDialog();
}

function laadSessiesIn() {
    var fetchoptions = {
        method: "GET",
        headers: {
            'Authorization' : 'Bearer ' + window.sessionStorage.getItem("myJWT")
        }};
        fetch("/restservices/sessie", fetchoptions)
            .then(response => {
                if (response.ok) return response.json();
                if (response.status === 401) throw new Error("Gebruiker niet geauthoriseerd");
                else throw new Error("Er is iets fout gegaan");
            })
            .then(myJson => {
                console.log(myJson)
                for (data of myJson) {
                    window.sessionStorage.setItem(data.naam, data.naam)
                    printSessie(data);
                }
                return myJson;
            })
            .then(myJson => {
                var dialogselect = document.getElementById("sessieselect");
                var dialogoptions = document.createElement("option");
                for (let i = dialogselect.length - 1; i >= 0; i--) {
                    dialogselect.remove(i);
                }
                dialogoptions.innerHTML = "Kies een sessie";
                dialogselect.appendChild(dialogoptions);
                for (data of myJson) {
                    maakDialogOption(data);
                }
                return myJson;
            })
            .catch(error => console.log(error))
}

function laadSchemasIn() {
    var fetchoptions = {
        method: "GET",
        headers: {
            'Authorization' : 'Bearer ' + window.sessionStorage.getItem("myJWT")
        }};
    fetch("/restservices/schema", fetchoptions)
        .then(response => {
            if (response.ok) return response.json();
            if (response.status === 401) throw new Error("Gebruiker niet geauthoriseerd");
            else throw new Error("Er is iets fout gegaan");
        })
        .then(myJson => {
            var select = document.getElementById("schemakeuze");
            var option = document.createElement("option");
            console.log(myJson)
            for (let i = select.length - 1; i >= 0; i--) {
                select.remove(i);
            }
            option.innerHTML = "Kies een schema";
            select.appendChild(option);
            for (data of myJson) {
                maakOption(data);
            }
            return myJson;
        })
        .catch(error => console.log(error))
}

function maakDialogOption(data) {
    var dialogselect = document.getElementById("sessieselect");
    var dialogoptions = document.createElement("option");

    dialogoptions.value = data.naam;
    dialogoptions.innerHTML = data.naam;
    dialogselect.appendChild(dialogoptions);
}

function maakOption(data) {
    var select = document.getElementById("schemakeuze");
    var option = document.createElement("option");

    option.value = data.naam;
    option.innerHTML = data.naam;
    select.appendChild(option);
}

function maakSessieAan() {
    var formData = new FormData(document.querySelector("#trainingGegevens"));
    var fetchoptions = {
        method: "POST",
        body: new URLSearchParams(formData),
        headers: {
            'Authorization' : 'Bearer ' + window.sessionStorage.getItem("myJWT")
        }};
    fetch("/restservices/sessie", fetchoptions)
        .then(response => {
            if (response.ok) return response.json();
            if (response.status === 401) throw new Error("Gebruiker niet geauthoriseerd");
            if (response.status === 409) {

                // response.json().then(r => console.log(r.error));

                document.getElementById("sessieaanmaakalert").innerHTML = "Sessie is niet aangemaakt"
                throw new Error("Sessie niet aangemaakt");
            }
            else throw new Error("Er is iets fout gegaan");
        })
        .then(() => {
            laadSessiesIn()
        })
        .catch(error => console.log(error))
}

function verwijderSessie() {
    var formData = new FormData(document.querySelector("#verwijdersessiekeuze"));
    var fetchoptions = {
        method: "DELETE",
        body: new URLSearchParams(formData),
        headers: {
            'Authorization' : 'Bearer ' + window.sessionStorage.getItem("myJWT")
        }};
    fetch("/restservices/sessie", fetchoptions)
        .then(response => {
            if (response.ok) return laadSessiesIn();
            if (response.status === 401) throw new Error("Gebruiker niet geauthoriseerd");
            if (response.status === 409) throw new Error("Sessie is niet verwijderd");
            else throw new Error("Er is iets fout gegaan");
        })
        .catch(error => console.log(error))
}

function printSessie(data) {
    let datadiv = document.querySelector('#schemabox')

    var beginuur = data.beginTijd.hour;
    var beginminuut = data.beginTijd.minute;
    var einduur = data.eindTijd.hour;
    var eindminuut = data.eindTijd.minute;

    beginuur = checkTime(beginuur);
    beginminuut = checkTime(beginminuut);
    einduur = checkTime(einduur);
    eindminuut = checkTime(eindminuut);

    datadiv.innerHTML = datadiv.innerHTML + data.naam + "<br/>"
        + data.dag.dayOfMonth + "-" + data.dag.monthValue + "-" + data.dag.year + "<br/>"
        + beginuur + ":" + beginminuut + "<br/>"
        + einduur + ":" + eindminuut + "<br/>"
        + data.schema.naam + "<br/>" + "<br/>"
}


function logUit() {
    sessionStorage.removeItem("myJWT");
    location.href = "index.html";
}

function checkTime(i) {
    if (i < 10) {
        i = "0" + i;
    }
    return i;
}

function laadSessieZienDialog() {
    var sessienaam = document.getElementById("sessieselect").value;
    var sessie = window.sessionStorage.getItem(sessienaam);
    sessieverwijderdiv = document.getElementById("sessiekeuzeteverwijderen");
    console.log(sessie)
    sessieverwijderdiv.innerHTML = sessie;
}

function openVerwijderDialog() {
    var dialog = document.getElementById("sessiedialog");
    closeAanmaakDialog();
    dialog.show();
}

function closeVerwijderDialog() {
    var dialog = document.getElementById("sessiedialog");
    dialog.close();
}

function openAanmaakDialog() {
    var dialog = document.getElementById("sessietoevoegendialog");
    closeVerwijderDialog()
    dialog.show();
}

function closeAanmaakDialog() {
    var dialog = document.getElementById("sessietoevoegendialog");
    dialog.close();
}

document.addEventListener('DOMContentLoaded', laadPaginaIn);
document.addEventListener('DOMContentLoaded', laadSessiesIn);
document.addEventListener('DOMContentLoaded', laadSchemasIn);
