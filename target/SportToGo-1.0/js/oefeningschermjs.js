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
        .catch(error => console.log(error))
}

function getOefeningData() {
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

function maakOefeningOption(oefeningtype) {
    var select = document.getElementById("oefeningkeuze");
    var options = document.createElement("option");

    options.value = oefeningtype.naam;
    options.innerHTML = oefeningtype.naam;
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

document.addEventListener('DOMContentLoaded', getOefeningData);
document.addEventListener('DOMContentLoaded', laadPaginaIn);
document.addEventListener('DOMContentLoaded', laadSchemasIn);
