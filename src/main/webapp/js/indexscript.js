// function testknop() {
//     fetch("/restservices/test")
//         .then(resp => resp.json())
//         .then(myJson => {
//             console.log(myJson)
//             document.querySelector("#placeholder").innerHTML = myJson[0].naam
//         })
//         .catch(error=>console.log(error));
// }

async function sendLoginData(event) {
    let element = document.querySelector("#placeholder");

    let formData = new FormData(document.querySelector("#loginform"));
    let fetchOptions = {
        method: "POST",
        body: new URLSearchParams(formData)
    }
    let response = await fetch("/restservices/accounts", fetchOptions);

    if (response.status === 200) {
        location.href='homeScherm.html';
    } else {
        element.textContent = "statuscode : " + response.status;
    }
}
