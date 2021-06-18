document.addEventListener('DOMContentLoaded', getData);

function getData() {
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
            let oefeninglijst = document.querySelector('#oefeningenLijst');
            let oefeningbeschrijving = document.querySelector('#oefeningBeschrijving');
            var select = document.getElementById('schemaKeuze');

            oefeninglijst.innerHTML = "";
            for (oefeningtype of myJson) {
                select.options[select.options.length] = new Option(oefeningtype.naam, oefeningtype.naam);
                oefeninglijst.innerHTML = oefeninglijst.innerHTML + oefeningtype.naam + "<br/>"
                oefeningbeschrijving.innerHTML = oefeningbeschrijving.innerHTML+ oefeningtype.beschrijving + "<br/>"
            }
        })
        .catch(error => console.log(error))
}

function zendOefeningData() {

}
