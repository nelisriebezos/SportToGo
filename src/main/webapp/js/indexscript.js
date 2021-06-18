function sendLoginData() {
    let messagediv = document.getElementById('messagediv');
    var formData = new FormData(document.querySelector("#loginform"));
    var fetchOptions = {
        method: "POST",
        body: new URLSearchParams(formData)
    };

    fetch("/restservices/authenticate", fetchOptions)
        .then((response) => {
            if (response.ok) {
                return response.json();
            }
            if (response.status === 401) {
                messagediv.innerHTML = "Verkeerde gebruikernaam / wachtwoord"
                throw "Wrong username / password";
            } else throw new Error("er ging iets mis")
        })
        .then(myJson => {
            window.sessionStorage.setItem("myJWT", myJson.JWT)
            location.href = 'homeScherm.html'
        })
        .catch(error => console.log(error))
}
