function stuurGebruikerData() {
    var gbn = document.getElementById('gebruikernaam');
    var em = document.getElementById('emailadres');
    var ww = document.getElementById('wachtwoord');
    let datadiv = document.querySelector('#postresponse')

    if (gbn.value.length === 0 || em.value.length === 0 || ww.value.length === 0 ) {
        datadiv.innerHTML = "U moet alles invullen"
    } else {
        var formData = new FormData(document.querySelector("#invoergegevensform"));
        var fetchOptions = {
            method: "POST",
            body: new URLSearchParams(formData)
        }
        fetch("/restservices/gebruiker/maakgebruiker", fetchOptions)
            .then((response) => {

                if (response.ok) location.href='index.html';
                if (response.status === 409){
                    datadiv.innerHTML = "Email staat al geregistreerd";
                    throw new Error("Email staat al geregistreerd");}
                else throw new Error("Er ging iets fout");
            })
            .catch(error => console.log(error))
    }
}
