function testknop() {
    fetch("/restservices/test")
        .then(resp => resp.json())
        .then(myJson => {
            console.log(myJson)
            document.querySelector("#placeholder").innerHTML = myJson[0].naam
        })
        .catch(error=>console.log(error));
}




// function loginPost() {
//     const methodOptions = {
//         method: 'POST',
//         headers: {'Content-Type': 'application/json'},
//         body: JSON.stringify({title: 'Stuur inloggegevens op'})
//     };
//     fetch()
// }

// var button = document.getElementById("account_aanmaken");
//
// button.addEventListener('click', function () {
//     console.log("test")
// })