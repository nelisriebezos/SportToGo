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

document.addEventListener('DOMContentLoaded', laadSchemasIn);
