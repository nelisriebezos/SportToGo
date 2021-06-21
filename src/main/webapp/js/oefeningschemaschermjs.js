function laadPaginaIn() {
    document.querySelector('#oefeningenLijst').innerHTML = "";
    closeAanmaakDialog();
}

function laadSchemasIn() {
    var fetchoptions = {
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
            var select = document.getElementById("schemakeuze");
            var options = document.createElement("option");

            for (let i = select.length - 1; i >= 0; i--) {
                select.remove(i);
            }

            options.innerHTML = "Kies een schema";
            select.appendChild(options);
            for (schema of myJson) {
                maakSchemaOption(schema);
            }
            return myJson;
        })
        .then(myJson => {
            var schemalijst = document.getElementById("schemalijst");
            var schemabeschrijving = document.getElementById("schemabeschrijving");
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
    var fetchoptions = {
        method: "GET",
        headers: {
            'Authorization' : 'Bearer ' + window.sessionStorage.getItem("myJWT")
        }}
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
                oefeningbeschrijving.innerHTML = oefeningbeschrijving.innerHTML+ oefeningtype.beschrijving + "<br/>"
            }
            return myJson;
        })
        .then(myJson => {
            var select = document.getElementById("oefeningkeuze");
            var options = document.createElement("option");

            for (let i = select.length - 1; i >= 0; i--) {
                select.remove(i);
            }
            options.innerHTML = "Kies een oefening";
            select.appendChild(options);

            for (oefeningtype of myJson) {
                maakOefeningOption(oefeningtype);
            }
            return myJson;
        })
        .catch(error => console.log(error))
}

function zendOefeningData() {
    var formData = new FormData(document.querySelector("#oefeningGegevens"));
    var fetchoptions = {
        method: "POST",
        body: new URLSearchParams(formData),
        headers: {
            'Authorization' : 'Bearer ' + window.sessionStorage.getItem("myJWT")
        }};
    fetch("restservices/oefening", fetchoptions)
        .then(response => {
            if (response.ok) return response;
            if (response.status === 409) throw new Error("Oefening niet toegevoegd");
            if (response.status === 401) throw new Error("Gebruiker niet geauthoriseerd");
            else throw new Error("Er is iets fout gegaan");
        })
        .then(() => {
            var messagediv = document.getElementById("messagediv");
            messagediv.innerHTML = "Oefening is aangemaakt en toegevoegd"
            closeAanmaakDialog()
        })
        .catch(error => console.log(error))
}

function maakOefeningOption(oefeningtype) {
    var select = document.getElementById("oefeningkeuze");
    var options = document.createElement("option");

    options.value = oefeningtype.naam;
    options.innerHTML = oefeningtype.naam;
    select.appendChild(options);
}

function maakSchemaOption(schema) {
    var select = document.getElementById("schemakeuze");
    var options = document.createElement("option");

    options.value = schema.naam;
    options.innerHTML = schema.naam;
    select.appendChild(options);
}

function openAanmaakDialog() {
    var dialog = document.getElementById("oefeningtoevoegendiv");
    dialog.show();
}

function closeAanmaakDialog() {
    var dialog = document.getElementById("oefeningtoevoegendiv");
    dialog.close();
}

document.addEventListener('DOMContentLoaded', laadOefeningenIn);
document.addEventListener('DOMContentLoaded', laadPaginaIn);
document.addEventListener('DOMContentLoaded', laadSchemasIn);
