fetch("/restservices/test")
    .then(resp => resp.json())
    .then(myJson => {
        console.log(myJson)
        document.querySelector("#placeholder").innerHTML = myJson[0].naam
    })
    .catch(error=>console.log(error));
