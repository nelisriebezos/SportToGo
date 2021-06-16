function sendLoginData() {
    var formData = new FormData(document.querySelector("#loginform"));
    var fetchOptions = {
        method: "POST",
        body: new URLSearchParams(formData)
    };

    fetch("/restservices/authenticate", fetchOptions)
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
