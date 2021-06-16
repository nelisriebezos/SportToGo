function laadPaginaIn() {
    var fetchoptions = {
        method: "GET",
        headers: {
            'Authorization' : 'Bearer ' + window.sessionStorage.getItem("myJWT")
        }}

        fetch("/restservices/gebruiker", fetchoptions)
            .then((response) => {
                if (response.ok) {
                    return response.json();
                }
                if (response.status === 403) {
                    throw new Error("403 werkt niet");
                }
                else {
                    throw new Error("error, lijst niet gevonden");
                }
            })
            .then(myJson => {
                let datadiv = document.querySelector('#schemabox')
                datadiv.innerHTML = "";
                for (sessie of myJson) {
                    datadiv.innerHTML = + sessie.naam + "<br/>"
                        + sessie.dag.dayOfMonth + "-" + sessie.dag.monthValue + "-" + sessie.dag.year +"<br/>"
                        + sessie.beginTijd + "<br/>"
                        + sessie.eindTijd + "<br/>"
                        + sessie.schema.naam + "<br/>"+ "<br/>"
                }
            })
            .catch(error => console.log(error))

}

document.addEventListener('DOMContentLoaded', laadPaginaIn);
