async function sendLoginReq(event) {
    let element = document.querySelector("#postresponse");

    let formData = new FormData(document.querySelector("#invoergegevensform"));
    let fetchOptions = {
        method: "POST",
        body: new URLSearchParams(formData)
    }
    let response = await fetch("/restservices/accounts", fetchOptions);

    if (response.status === 200) {
        location.href='index.html';
    } else {
        location.href='index.html';
        // element.textContent = "statuscode : " + response.status;
    }
}

