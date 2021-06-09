// async function sendLoginData() {
//     let element = document.querySelector("#placeholder");
//
//     let formData = new FormData(document.querySelector("#loginform"));
//     let fetchOptions = {
//         method: "POST",
//         body: new URLSearchParams(formData)
//     }
//     fetch("/restservices/accounts", fetchOptions)
//         .then( resp => resp.json() )
//         .then( resp => { if (resp.loggedin == "true") {
//             window.sessionStorage.setItem("user", resp.id); //@TODO maak hier het opslaan van het token van
//             location.href='homeScherm.html';
//         } else {
//             element.textContent = "statuscode : " + resp.status;
//         }})
// }

// async function sendLoginData() {
//     let formData = new FormData(document.querySelector("#loginform"));
//     let fetchOptions = {
//         method: "POST",
//         body: new URLSearchParams(formData)
//     }
//     fetch("/restservices/authenticate", fetchOptions)
//         .then(response => response.json())
//         .then(function (myJson) {
//             console.log(myJson.emailAdres)
//             console.log(myJson)
//             window.sessionStorage.setItem("user", myJson.emailAdres);
//             location.href='homeScherm.html';
//         })
// }

// async function sendLoginData() {
//     let formData = new FormData(document.querySelector("#loginform"));
//     let fetchOptions = {
//         method: "POST",
//         body: new URLSearchParams(formData)
//     }
//     fetch("/restservices/authenticate", fetchOptions)
//         .then((response) => {
//             if (response.ok) {
//                 return response.json();
//                 // location.href='homeScherm.html';
//             } else {
//                 console.log(response.status)
//             }
//         })
//         .then(myJson => window.sessionStorage.setItem("myJWT", myJson.JWT))
//         .then(myJson => window.sessionStorage.setItem("user", myJson.emailadres))
//         .catch(error => console.log(error))
// }

function sendLoginData() {
    var formData = new FormData(document.querySelector("#loginform"));
    var encData = new URLSearchParams(formData);

    fetch("/restservices/authenticate", {method: "POST", body: encData})
        .then((response) => {
            if (response.ok) return response.json();
            else console.log(response);
            // else throw "Wrong username / password";
        })
        .then(myJson => window.sessionStorage.setItem("myJWT", myJson.JWT))
        .catch(error => console.log(error))
}
