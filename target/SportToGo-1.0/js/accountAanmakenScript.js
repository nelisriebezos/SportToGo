function stuurGebruikerData() {
    var formData = new FormData(document.querySelector("#invoergegevensform"));
    var fetchOptions = {
        method: "POST",
        body: new URLSearchParams(formData)
    }
    fetch("/restservices/gebruiker/maakgebruiker", fetchOptions)
        .then((response) => {
            let datadiv = document.querySelector('#postresponse')
            if (response.ok) {
                location.href='index.html';
            } else {
                console.log(response)
                datadiv.innerHTML = "Email staat al geregistreerd";
            }
        })
}
