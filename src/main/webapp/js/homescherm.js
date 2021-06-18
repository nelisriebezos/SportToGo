function laadPaginaIn() {
    var fetchoptions = {
        method: "GET",
        headers: {
            'Authorization' : 'Bearer ' + window.sessionStorage.getItem("myJWT")
        }};

        fetch("/restservices/gebruiker", fetchoptions)
            .then((response) => {
                if (response.ok) return response.json();
                if (response.status === 401) throw new Error("Gebruiker niet geauthoriseerd");
                else throw new Error("Er is iets fout gegaan");
            })
            .then(myJson => {
                let datadiv = document.querySelector('#schemabox')
                var select = document.getElementById('schemaKeuze');
                datadiv.innerHTML = "";
                console.log(myJson)
                for (data of myJson) {
                    select.options[select.options.length] = new Option(data.schema.naam, data.schema.naam);
                    datadiv.innerHTML = datadiv.innerHTML + data.naam + "<br/>"
                        + data.dag.dayOfMonth + "-" + data.dag.monthValue + "-" + data.dag.year + "<br/>"
                        + data.beginTijd + "<br/>"
                        + data.eindTijd + "<br/>"
                        + data.schema.naam + "<br/>" + "<br/>"
                }
            })
            .catch(error => console.log(error))

}

function logUit() {
    sessionStorage.removeItem("myJWT");
    location.href = "index.html";
}

document.addEventListener('DOMContentLoaded', laadPaginaIn);
