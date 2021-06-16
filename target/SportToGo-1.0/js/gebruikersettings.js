function laadGebruikerDataIn() {
    var fetchoptions = {
        method: "GET",
        headers: {
            'Authorization' : 'Bearer ' + window.sessionStorage.getItem("myJWT")
        }}
    fetch("/restservices/gebruiker/settings", fetchoptions)
        .then((response) => {
            if (response.ok)
                return response.json();
            else throw "gebruiker niet gevonden";
        })
        .then(myJson => {
            let usernamediv = document.querySelector('#usernamelable')
            let emaillable = document.querySelector('#emaillable')
            usernamediv.innerHTML = myJson.gebruikernaam;
            emaillable.innerHTML = myJson.emailadres;
        })
        .catch(error => console.log(error))
}

document.addEventListener('DOMContentLoaded', laadGebruikerDataIn);
