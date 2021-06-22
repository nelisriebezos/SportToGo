function laadPaginaIn() {
    document.querySelector("#schemalijst").innerHTML = "";
    document.querySelector("#schemabeschrijving").innerHTML = "";
    document.querySelector("#oefeningBeschrijving").innerHTML = "";
    document.querySelector("#oefeningenLijst").innerHTML = "";
    closeAanmaakDialog();
    closeVSchemaDialog();
    closeMSchemaDialog();
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
            if (response.ok) return response.json();
            if (response.status === 401) throw new Error("Gebruiker niet geauthoriseerd");
            else throw new Error("Er is iets fout gegaan");
        })
        .then(myJson => {
            console.log(myJson)
            maakSchemaOption(myJson, "schemakeuze");
            maakSchemaOption(myJson, "schemaverwijderkeuze");
            return myJson;
        })
        .then(myJson => {
            let schemalijst = document.getElementById("schemalijst");
            let schemabeschrijving = document.getElementById("schemabeschrijving");
            for (data of myJson) {
                schemalijst.innerHTML = schemalijst.innerHTML + data.naam + "<br/>"

                for (type of data.oefeningLijst) {
                    schemabeschrijving.innerHTML = schemabeschrijving.innerHTML
                        + type.oefeningType.naam + "<br/>"
                        + type.gewicht + "<br/>"
                        + type.setHoeveelheid + "<br/>" + "<br/>"
                }
            }
        })
        .catch(error => console.log(error))
}


function laadOefeningenIn() {
    let fetchoptions = {
        method: "GET",
        headers: {
            'Authorization': 'Bearer ' + window.sessionStorage.getItem("myJWT")
        }
    }
    fetch("/restservices/oefening", fetchoptions)
        .then((response) => {
            if (response.ok) return response.json();
            else throw new Error("Er is iets fout gegaan");
        })
        .then(myJson => {
            console.log(myJson);
            let oefeninglijst = document.querySelector('#oefeningenLijst');
            let oefeningbeschrijving = document.querySelector('#oefeningBeschrijving');
            oefeninglijst.innerHTML = "";

            for (oefeningtype of myJson) {
                oefeninglijst.innerHTML = oefeninglijst.innerHTML + oefeningtype.naam + "<br/>"
                oefeningbeschrijving.innerHTML = oefeningbeschrijving.innerHTML + oefeningtype.beschrijving + "<br/>"
            }
            return myJson;
        })
        .then(myJson => {
            maakOefeningOption(myJson, "oefeningkeuze");
            return myJson;
        })
        .catch(error => console.log(error))
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
                if (response.ok) return response;
                if (response.status === 409) throw new Error("Oefening niet toegevoegd");
                if (response.status === 401) throw new Error("Gebruiker niet geauthoriseerd");
                else throw new Error("Er is iets fout gegaan");
            })
            .then(() => {
                laadPaginaIn()
            })
            .catch(error => console.log(error))
    }
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
                if (response.ok) return laadPaginaIn();
                if (response.status === 401) throw new Error("Gebruiker niet geauthoriseerd");
                if (response.status === 409) throw new Error("Schema is niet verwijderd");
                else throw new Error("Er is iets fout gegaan");
            })
            .catch(error => console.log(error))
    }
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
                if (response.ok) return laadPaginaIn();
                if (response.status === 401) throw new Error("Gebruiker niet geauthoriseerd");
                if (response.status === 409) throw new Error("Schema is niet aangemaakt");
                else throw new Error("Er is iets fout gegaan");
            })
            .catch(error => console.log(error))
    }
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
    dialog.show();
}

function closeAanmaakDialog() {
    let dialog = document.getElementById("oefeningtoevoegendiv");
    document.getElementById("oefeningkeuze").value = "";
    document.getElementById("schemakeuze").value = "";
    document.getElementById("gewichtkeuze").value = "";
    document.getElementById("sethoeveelheidkeuze").value = "";
    dialog.close();
}

function openVSchemaDialog() {
    let dialog = document.getElementById("schemaverwijderdialog");
    closeMSchemaDialog();
    dialog.show();
}

function closeVSchemaDialog() {
    let dialog = document.getElementById("schemaverwijderdialog");
    document.getElementById("schemaverwijderkeuze").value = "";
    dialog.close();
}

function openMSchemaDialog() {
    let dialog = document.getElementById("schemaaanmaakdialog");
    closeVSchemaDialog();
    dialog.show();
}

function closeMSchemaDialog() {
    let dialog = document.getElementById("schemaaanmaakdialog");
    document.getElementById("schemanaam").value = "";
    dialog.close();
}

document.addEventListener('DOMContentLoaded', laadPaginaIn);
