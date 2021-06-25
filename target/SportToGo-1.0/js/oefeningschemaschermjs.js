function laadPaginaIn() {
    let oefeningtabel = document.getElementById("oefeningtabel");
    let schematabel = document.getElementById("schematabel");
    while(oefeningtabel.hasChildNodes() || schematabel.hasChildNodes()) {
        oefeningtabel.removeChild(oefeningtabel.firstChild);
        schematabel.removeChild(schematabel.firstChild);
    }
    closeAanmaakDialog();
    closeVSchemaDialog();
    closeMSchemaDialog();
    closeVOefeningDialog();
    laadOefeningenIn();
    laadSchemasIn();
}

function laadSchemasIn() {
    let fetchoptions = {
        method: "GET",
        headers: {
            'Authorization': 'Bearer ' + window.sessionStorage.getItem("myJWT")
        }
    };
    fetch("/restservices/schema", fetchoptions)
        .then(response => {
            return Promise.all([response.status, response.json()])
        })
        .then(([status, myJson]) => {
            laadSchemaInStatusHandler(status, myJson);
        })
        .catch(error => console.log(error))
}

function laadSchemaInStatusHandler(status, myJson) {
    if (status === 200) {
        maakSchemaOption(myJson, "schemakeuze");
        maakSchemaOption(myJson, "vwoefeningschema");
        maakSchemaOption(myJson, "schemaverwijderkeuze");

        for (data of myJson) {
            let oefeningtabel = document.getElementById("schematabel");
            let thead = oefeningtabel.createTHead();
            let row = thead.insertRow();

            let th = document.createElement("th");
            let text = document.createTextNode(data.naam);

            th.appendChild(text);
            row.appendChild(th);
            for (type of data.oefeningLijst) {
                let cell = row.insertCell();
                cell.innerHTML = type.oefeningType.naam + "<br/>"
                    + type.gewicht + "<br/>"
                    + type.setHoeveelheid;
            }
        }
        return myJson;
    }
    else if (status === 401) return console.log(myJson.error)
    else return console.log("Er ging iets fout")
}

function laadOefeningenIn() {
    let fetchoptions = {
        method: "GET",
        headers: {
            'Authorization': 'Bearer ' + window.sessionStorage.getItem("myJWT")
        }
    }
    fetch("/restservices/oefening", fetchoptions)
        .then(response => {
            return Promise.all([response.status, response.json()])
        })
        .then(([status, myJson]) => {
            laadOefeningInStatusHandler(status, myJson);
        })
        .catch(error => console.log(error))
}

function laadOefeningInStatusHandler(status, myJson) {
    if (status === 200) {
        for (oefeningtype of myJson) {
            let oefeningtabel = document.getElementById("oefeningtabel");
            let thead = oefeningtabel.createTHead();
            let row = thead.insertRow();

            let th = document.createElement("th");
            let text = document.createTextNode(oefeningtype.naam);

            th.appendChild(text);
            row.appendChild(th);

            let cell = row.insertCell();
            cell.innerHTML = oefeningtype.beschrijving;
        }
        maakOefeningOption(myJson, "oefeningkeuze");
        laadVOefeningInNullFunctie();
        return myJson;
    } else console.log("Er ging iets fout")
}

function voegOefeningToe() {
    let oef = document.getElementById("oefeningkeuze");
    let sch = document.getElementById("schemakeuze");
    let gw = document.getElementById("gewichtkeuze");
    let shvh = document.getElementById("sethoeveelheidkeuze");

    if (oef.value.length === 0 ||
        sch.value.length === 0 ||
        gw.value.length === 0 ||
        shvh.value.length === 0) {
        document.getElementById("oefeningaanmaakdiv").innerHTML = "Vul alle gegevens in"
    } else {
        let formData = new FormData(document.querySelector("#oefeningGegevens"));
        let fetchoptions = {
            method: "POST",
            body: new URLSearchParams(formData),
            headers: {
                'Authorization': 'Bearer ' + window.sessionStorage.getItem("myJWT")
            }
        };
        fetch("restservices/oefening", fetchoptions)
            .then(response => {
                return Promise.all([response.status, response.json()])
            })
            .then(([status, myJson]) => {
                voegOefeningToeStatusHandler(status, myJson)
            })
            .catch(error => console.log(error))
    }
}

function voegOefeningToeStatusHandler(status, myJson) {
    if (status === 200) {
        laadPaginaIn();
        return myJson;
    }
    else if (status === 409) return console.log(myJson.error)
    else if (status === 401) return console.log(myJson.error)
    else return console.log("Er gaat iets fout")
}


function verwijderSchema() {
    let sch = document.getElementById("schemaverwijderkeuze");

    if (sch.value.length === 0) {
        document.getElementById("schemateverwijderen").innerHTML = "Geef schema aan"
    } else {

        let formData = new FormData(document.querySelector("#schemaverwijderform"));
        let fetchoptions = {
            method: "DELETE",
            body: new URLSearchParams(formData),
            headers: {
                'Authorization': 'Bearer ' + window.sessionStorage.getItem("myJWT")
            }
        };
        fetch("/restservices/schema", fetchoptions)
            .then(response => {
                return Promise.all([response.status, response.json()])
            })
            .then(([status, myJson]) => {
                verwijderSchemaSTatusHandler(status, myJson);
            })
            .catch(error => console.log(error))
    }
}

function verwijderSchemaSTatusHandler(status, myJson) {
    if (status === 200) {
        laadPaginaIn();
        return myJson;
    }
    else if (status === 401) return console.log(myJson.error)
    else if (status === 409) return console.log(myJson.error)
    else return console.log("Er ging iets fout")
}

function maakSchema() {
    let nm = document.getElementById("schemanaam");

    if (nm.value.length === 0) {
        document.getElementById("schemaaanmaakdiv").innerHTML = "Voer een naam in"
    } else {

        let formData = new FormData(document.querySelector("#schemaaanmaakform"));
        let fetchoptions = {
            method: "POST",
            body: new URLSearchParams(formData),
            headers: {
                'Authorization': 'Bearer ' + window.sessionStorage.getItem("myJWT")
            }
        };
        fetch("/restservices/schema", fetchoptions)
            .then(response => {
                return Promise.all([response.status, response.json()])

            })
            .then(([status, myJson]) => {
                maakSchemaStatusHandler(status, myJson);
            })
            .catch(error => console.log(error))
    }
}

function maakSchemaStatusHandler(status, myJson) {
    if (status === 200) {
        laadPaginaIn();
        return myJson;
    }
    else if (status === 401) return console.log(myJson.error)
    else if (status === 409) {
        document.getElementById("schemaaanmaakdiv").innerHTML = "Schema bestaat al"
        return console.log(myJson.error)
    }
    else return console.log("Er ging iets fout")
}

function verwijderOefening() {
    let vos = document.getElementById("vwoefeningschema")
    let vo = document.getElementById("oefeningteverwijderen")

    if (vos.value.length === 0 ||
        vo.value.length === 0) {
        document.getElementById("verwijderoefeningdiv").innerHTML = "Vul alle gegevens in"
    } else {

        let formData = new FormData(document.querySelector("#verwijderoefeningform"));
        let fetchoptions = {
            method: "DELETE",
            body: new URLSearchParams(formData),
            headers: {
                'Authorization': 'Bearer ' + window.sessionStorage.getItem("myJWT")
            }
        };
        fetch("/restservices/schema/" + document.getElementById("vwoefeningschema").value + "/" +
            document.getElementById("oefeningteverwijderen").value, fetchoptions)
            .then(response => {
                return Promise.all([response.status, response.json()])
            })
            .then(([status, myJson]) => {
                verwijderOefeningStatusHandler(status, myJson);
            })
            .catch(error => console.log(error))
    }
}

function verwijderOefeningStatusHandler(status, myJson) {
    if (status === 200) {
        laadPaginaIn();
        return myJson;
    }
    else if (status === 409) return console.log(myJson.error)
    else if (status === 401) return console.log(myJson.error)
    else return console.log("Er ging iets fout")
}

function laadOefeningenVanSchema() {
    let vwschema = document.getElementById("vwoefeningschema");

    if (vwschema.value.length === 0) {
        laadVOefeningInNullFunctie();
        return null;
    } else {
        let fetchoptions = {
            method: "GET",
            headers: {
                'Authorization': 'Bearer ' + window.sessionStorage.getItem("myJWT")
            }
        };
        fetch("/restservices/schema/" + document.getElementById("vwoefeningschema").value, fetchoptions)
            .then(response => {
                return Promise.all([response.status, response.json()])
            })
            .then(([status, myJson]) => {
                laadOefeningVanSchemaStatusHandler(status, myJson);
            })
            .catch(error => console.log(error))
    }
}

function laadOefeningVanSchemaStatusHandler(status, myJson) {
    if (status === 200) {
        maakVOefeningOption(myJson, "oefeningteverwijderen");
        return myJson;
    }
    else if (status === 409) return console.log(myJson.error)
    else if (status === 401) return console.log(myJson.error)
    else return console.log("Er ging iets fout")
}

function maakVOefeningOption(myJson, element) {
    let select = document.getElementById(element);

    for (let i = select.length - 1; i >= 0; i--) {
        select.remove(i);
    }

    for (oefening of myJson.oefeningLijst) {
        let options = document.createElement("option");
        options.value = [oefening.oefeningType.naam, oefening.gewicht, oefening.setHoeveelheid];
        options.innerHTML = oefening.oefeningType.naam;
        select.appendChild(options);
    }
}

function laadOefeningTeVerwijderenIn() {
    let fetchoptions = {
        method: "GET",
        headers: {
            'Authorization' : 'Bearer ' + window.sessionStorage.getItem("myJWT")
        }};
    fetch("/restservices/schema/"+ document.getElementById("vwoefeningschema").value +"/"+
        document.getElementById("oefeningteverwijderen").value, fetchoptions)
        .then(response => {
            return Promise.all([response.status, response.json()])
        })
        .then(([status, myJson]) => {
            verwijderOefeningDivVuller(status, myJson);
        })
        .catch(error => console.log(error))
}

function verwijderOefeningDivVuller(status, myJson) {
    if (status === 200) {
        let div = document.getElementById("verwijderoefeningdiv");
        div.innerHTML = myJson.oefeningType.naam + "<br/>"
                        + myJson.gewicht + "<br/>"
                        + myJson.setHoeveelheid;
        return myJson;
    }
    else if (status === 409) return console.log(myJson.error)
    else if (status === 401) return console.log(myJson.error)
    else return console.log("Er ging iets fout")
}

function laadVOefeningInNullFunctie() {
    let select = document.getElementById("oefeningteverwijderen");
    let options = document.createElement("option");

    for (let i = select.length - 1; i >= 0; i--) {
        select.remove(i);
    }

    options.value = "";
    options.innerHTML = "Kies een oefening";
    select.appendChild(options);
}

function maakOefeningOption(myJson, element) {
    let select = document.getElementById(element);
    let options1 = document.createElement("option");

    for (let i = select.length - 1; i >= 0; i--) {
        select.remove(i);
    }

    options1.value = "";
    options1.innerHTML = "Kies een oefening";
    select.appendChild(options1);

    for (oefening of myJson) {
        var options2 = document.createElement("option");
        options2.value = oefening.naam;
        options2.innerHTML = oefening.naam;
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


function openAanmaakDialog() {
    let dialog = document.getElementById("oefeningtoevoegendiv");
    closeVSchemaDialog();
    closeMSchemaDialog();
    closeVOefeningDialog();
    dialog.show();
}

function closeAanmaakDialog() {
    let dialog = document.getElementById("oefeningtoevoegendiv");
    document.getElementById("oefeningkeuze").value = "";
    document.getElementById("schemakeuze").value = "";
    document.getElementById("gewichtkeuze").value = "";
    document.getElementById("sethoeveelheidkeuze").value = "";
    document.getElementById("oefeningaanmaakdiv").innerHTML = ""
    dialog.close();
}

function openVSchemaDialog() {
    let dialog = document.getElementById("schemaverwijderdialog");
    closeMSchemaDialog();
    closeAanmaakDialog();
    closeVOefeningDialog();
    dialog.show();
}

function closeVSchemaDialog() {
    let dialog = document.getElementById("schemaverwijderdialog");
    document.getElementById("schemaverwijderkeuze").value = "";
    document.getElementById("schemateverwijderen").innerHTML = ""
    dialog.close();
}

function openMSchemaDialog() {
    let dialog = document.getElementById("schemaaanmaakdialog");
    closeVSchemaDialog();
    closeMSchemaDialog();
    closeVOefeningDialog();
    closeAanmaakDialog()
    dialog.show();
}

function closeMSchemaDialog() {
    let dialog = document.getElementById("schemaaanmaakdialog");
    document.getElementById("schemanaam").value = "";
    document.getElementById("schemaaanmaakdiv").innerHTML = ""
    dialog.close();
}

function openVOefeningDialog() {
    let dialog = document.getElementById("verwijderoefeningdialog");
    laadVOefeningInNullFunctie();
    closeVSchemaDialog();
    closeMSchemaDialog();
    closeVOefeningDialog();
    closeAanmaakDialog();
    dialog.show();
}

function closeVOefeningDialog() {
    let dialog = document.getElementById("verwijderoefeningdialog");
    document.getElementById("vwoefeningschema").value = "";
    document.getElementById("oefeningteverwijderen").value = "";
    document.getElementById("verwijderoefeningdiv").innerHTML = "";
    dialog.close();
}

document.addEventListener('DOMContentLoaded', laadPaginaIn);
