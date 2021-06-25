function laadPaginaIn() {
    laadSessiesIn();
    laadSchemasIn();
    let schemabox = document.querySelector("#sessiediv");
    schemabox.innerHTML = "";
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

function printSessie(data) {
    let datadiv = document.querySelector('#sessiediv')

    let beginuur = data.beginTijd.hour;
    let beginminuut = data.beginTijd.minute;
    let einduur = data.eindTijd.hour;
    let eindminuut = data.eindTijd.minute;

    beginuur = checkTime(beginuur);
    beginminuut = checkTime(beginminuut);
    einduur = checkTime(einduur);
    eindminuut = checkTime(eindminuut);

    datadiv.innerHTML = datadiv.innerHTML + data.naam + "&#13;&#10;" +
        + data.dag.dayOfMonth + "-" + data.dag.monthValue + "-" + data.dag.year + "&#13;&#10;" +
        + beginuur + ":" + beginminuut + "&#13;&#10;"
        + einduur + ":" + eindminuut + "&#13;&#10;"
        + data.schema.naam  + "&#13;&#10;" + "&#13;&#10;"
}

function checkTime(i) {
    if (i < 10) {
        i = "0" + i;
    }
    return i;
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

document.addEventListener('DOMContentLoaded', laadPaginaIn);
