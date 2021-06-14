function sendLoginData() {
    var formData = new FormData(document.querySelector("#loginform"));
    var encData = new URLSearchParams(formData);

    fetch("/restservices/authenticate", {method: "POST", body: encData})
        .then((response) => {
            if (response.ok) {
                return response.json();
            } else throw "Wrong username / password";
        })
        .then(myJson => {
            window.sessionStorage.setItem("myJWT", myJson.JWT)
            location.href = 'homeScherm.html'
        })
        .catch(error => console.log(error))
}
