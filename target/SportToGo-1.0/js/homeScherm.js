// function laadPaginaIn() {
//
//     let url = " /restservices/userdata/"+window.sessionStorage.getItem("user");
//     let fetchoptions = {
//         method : "GET",
//         headers: {
//             //'Authentication' : ' Bearer '+ window.sessionStorage.getItem("myJWT")
//         }
//     }
//     fetch(url, fetchoptions).then( responser => {
//         if (response.ok()) response.json()
//     }
//     ).then(myJson => console.log(myJson)).catch(error => console.log(error));
// }

function laadPaginaIn() {
    let url = "/restservices/gebruiker/" + window.sessionStorage.getItem("user");
    fetch(url)
        .then( (response) => response.json())
        .then(function(myJson) {
            console.log(myJson)
            document.querySelector("#testdata").innerHTML = myJson.emailAdres;
        })
}

document.addEventListener('DOMContentLoaded', laadPaginaIn);
