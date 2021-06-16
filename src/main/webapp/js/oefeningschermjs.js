function getData() {
    var fetchoptions = {
        method: "GET",
        headers: {
            'Authorization' : 'Bearer ' + window.sessionStorage.getItem("myJWT")
        }}
    fetch("/restservices/oefening", fetchoptions)
        .then((response) => {
            if (response.ok)
                return response.json();
        })
        .then(myJson => {
            let oefeninglijst = document.querySelector('#oefeningenLijst');
            let oefeningbeschrijving = document.querySelector('#oefeningBeschrijving');
            oefeninglijst.innerHTML = "";
            console.log(myJson);
            for (oefeningtype of myJson) {
                oefeninglijst.innerHTML = + oefeningtype.naam + "<br/>"
                oefeningbeschrijving.innerHTML = + oefeningtype.beschrijving + "<br/>"
            }
        })
        .catch(error => console.log(error))
}

document.addEventListener('DOMContentLoaded', getData);
