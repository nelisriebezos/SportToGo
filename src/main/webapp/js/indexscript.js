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

async function sendLoginData() {
    let formData = new FormData(document.querySelector("#loginform"));
    let fetchOptions = {
        method: "POST",
        body: new URLSearchParams(formData)
    }
    const response = await fetch("/restservices/authenticate", fetchOptions)
        if (response.ok) {
            const myJson = await response.json();
            window.sessionStorage.setItem("user", myJson.emailAdres);
            location.href='homeScherm.html';
        } else {
            console.log(response.status)
        }
}