async function zendOefeningData() {
    let formData = new FormData(document.querySelector("#oefeningGegevens"));
    let fetchOptions = {
        method: "POST",
        body: new URLSearchParams(formData)
    }
    const response = await fetch("/restservices/gebruiker/" + window.sessionStorage.getItem("user"), fetchOptions)
    if (response.ok) {
        const myJson = await response.json();
        console.log(myJson);
    } else {
        console.log(response.status)
    }
}