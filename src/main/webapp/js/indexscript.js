// function testknop() {
//     fetch("/restservices/test")
//         .then(resp => resp.json())
//         .then(myJson => {
//             console.log(myJson)
//             document.querySelector("#placeholder").innerHTML = myJson[0].naam
//         })
//         .catch(error=>console.log(error));
// }

async function sendLoginData() {
    let element = document.querySelector("#placeholder");

    let formData = new FormData(document.querySelector("#loginform"));
    let fetchOptions = {
        method: "POST",
        body: new URLSearchParams(formData)
    }
    fetch("/restservices/accounts", fetchOptions)
        .then( resp => resp.json() )
        .then( resp => { if (resp.loggedin == "true") {
            window.sessionStorage.setItem("user", resp.id); //@TODO maak hier het opslaan van het token van
            location.href='homeScherm.html';
        } else {
            element.textContent = "statuscode : " + resp.status;
        }})
}
