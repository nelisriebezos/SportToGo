function laadPaginaIn(jsonMetData) {
    alert("alert drie")
    document.querySelector("#placeholder").innerHTML = jsonMetData[0].naam;
}