function laadPaginaIn() {
    let schemabox = document.querySelector("#schemabox");
    schemabox.innerHTML = "";
    document.getElementById("sessieaanmaakalert").innerHTML = "";
    closeVerwijderDialog();
    closeAanmaakDialog();
    laadSessiesIn();
    laadSchemasIn();
}

function laadSessiesIn() {
    let fetchoptions = {
        method: "GET",
        headers: {
            'Authorization' : 'Bearer ' + window.sessionStorage.getItem("myJWT")
        }};
        fetch("/restservices/sessie", fetchoptions)
            .then(response => {
                return Promise.all([response.status, response.json()])
            })
            .then(([status, myJson]) => {
                laadSessiesInStatusHandler(status, myJson);
            })
            .catch(error => console.log(error))
}

function laadSessiesInStatusHandler(status, myJson) {
    if (status === 200) {
        console.log(myJson)
        for (data of myJson) {
            printSessie(data);
        }
        maakDialogOption(myJson, "sessieselect");
        return myJson;
    }
    else if (status === 401) return console.log(myJson.error)
    else return console.log("Er ging iets fout")
}

function laadSchemasIn() {
    let fetchoptions = {
        method: "GET",
        headers: {
            'Authorization' : 'Bearer ' + window.sessionStorage.getItem("myJWT")
        }};
    fetch("/restservices/schema", fetchoptions)
        .then(response => {
            return Promise.all([response.status, response.json()])
        })
        .then(([status, myJson]) => {
            laadSchemasInStatusHandler(status, myJson);
        })
        .catch(error => console.log(error))
}

function laadSchemasInStatusHandler(status, myJson) {
    if (status === 200) return maakSchemaOption(myJson, "schemakeuze");
    else if (status === 401) return console.log(myJson.error)
    else return console.log("Er ging iets fout")
}

function maakDialogOption(myJson, element) {
    let select = document.getElementById(element);
    let options1 = document.createElement("option");

    for (let i = select.length - 1; i >= 0; i--) {
        select.remove(i);
    }

    options1.value = "";
    options1.innerHTML = "Kies een sessie";
    select.appendChild(options1);

    for (sessie of myJson) {
        let options2 = document.createElement("option");
        options2.value = sessie.naam;
        options2.innerHTML = sessie.naam;
        select.appendChild(options2);
    }
}

function maakSchemaOption(myJson, element) {
    let select = document.getElementById(element);
    let options1 = document.createElement("option");

    for (let i = select.length - 1; i >= 0; i--) {
        select.remove(i);
    }

    options1.value = "";
    options1.innerHTML = "Kies een schema";
    select.appendChild(options1);

    for (schema of myJson) {
        let options2 = document.createElement("option");
        options2.value = schema.naam;
        options2.innerHTML = schema.naam;
        select.appendChild(options2);
    }
}

function maakSessieAan() {
    let schk = document.getElementById("schemakeuze")
    let nm = document.getElementById("naamkeuze")
    let dt = document.getElementById("datumkeuze")
    let bt = document.getElementById("begintijdkeuze")
    let et = document.getElementById("eindtijdkeuze")

    if (schk.value.length === 0 ||
        nm.value.length === 0 ||
        dt.value.length === 0 ||
        bt.value.length === 0 ||
        et.value.length === 0) {
        document.querySelector("#sessieaanmaakalert").innerHTML = "Vul alle gegevens in"
    } else {

        let formData = new FormData(document.querySelector("#trainingGegevens"));
        let fetchoptions = {
            method: "POST",
            body: new URLSearchParams(formData),
            headers: {
                'Authorization': 'Bearer ' + window.sessionStorage.getItem("myJWT")
            }
        };
        fetch("/restservices/sessie", fetchoptions)
            .then(response => {
                return Promise.all([response.status, response.json()])
            })
            .then(([status, myJson]) => {
                maakSessieStatusHandler(status, myJson);
            })
            .catch(error => console.log(error))
    }
}

function maakSessieStatusHandler(status, myJson) {
    if (status === 200) return laadPaginaIn()
    else if(status === 401) return console.log(myJson.error);
    else if (status=== 409) {
        document.getElementById("sessieaanmaakalert").innerHTML = myJson.error;
        return console.log(myJson.error);
        }
    else return console.log("Er ging iets fout")
    }

function verwijderSessie() {
    let ss = document.getElementById("sessieselect");

    if (ss.value.length === 0) {
        document.querySelector("#sessiedivmessage").innerHTML = "Geef sessie aan"
    } else {

        let formData = new FormData(document.querySelector("#verwijdersessiekeuze"));
        let fetchoptions = {
            method: "DELETE",
            body: new URLSearchParams(formData),
            headers: {
                'Authorization': 'Bearer ' + window.sessionStorage.getItem("myJWT")
            }
        };
        fetch("/restservices/sessie", fetchoptions)
            .then(response => {
                return Promise.all([response.status, response.json()])
            })
            .then(([status, myJson]) => {
                verwijderSessieStatusHandler(status, myJson);
            })
            .catch(error => console.log(error))
    }
}

function verwijderSessieStatusHandler(status, myJson) {
    if (status === 200) return laadPaginaIn()
    else if (status === 401) return console.log(myJson.error)
    else if (status === 409) return console.log(myJson.error)
    else return console.log("Er ging iets fout")
}

function printSessie(data) {
    let datadiv = document.querySelector('#schemabox')

    let beginuur = data.beginTijd.hour;
    let beginminuut = data.beginTijd.minute;
    let einduur = data.eindTijd.hour;
    let eindminuut = data.eindTijd.minute;

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

function openVerwijderDialog() {
    let dialog = document.getElementById("sessiedialog");
    document.querySelector("#sessiedivmessage").innerHTML = ""
    closeAanmaakDialog();
    dialog.show();
}

function closeVerwijderDialog() {
    let dialog = document.getElementById("sessiedialog");
    document.getElementById("sessieselect").value = "";
    dialog.close();
}

function openAanmaakDialog() {
    let dialog = document.getElementById("sessietoevoegendialog");
    document.getElementById("sessieaanmaakalert").innerHTML = "";
    closeVerwijderDialog()
    dialog.show();
}

function closeAanmaakDialog() {
    let dialog = document.getElementById("sessietoevoegendialog");
    document.querySelector("#sessieaanmaakalert").innerHTML = ""
    document.getElementById("schemakeuze").value = "";
    document.getElementById("naamkeuze").value = "";
    document.getElementById("datumkeuze").value = "";
    document.getElementById("begintijdkeuze").value = "";
    document.getElementById("eindtijdkeuze").value = "";
    dialog.close();
}

document.addEventListener('DOMContentLoaded', laadPaginaIn);
