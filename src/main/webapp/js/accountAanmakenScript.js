const invoergegevensform = document.querySelector("#invoergegevensform");
if(invoergegevensform) {
    invoergegevensform.addEventListener("submit", function(e) {
        submitForm(e, this);
    });
}

function buildHeaders(authorization = null) {
    const headers = {
        "Content-Type": "application/json",
        "Authorization": (authorization) ? authorization : "Bearer TOKEN_MISSING"
    };
    return headers;
}

function buildJsonFormData(form) {
    const jsonFormData = {};
    for (const pair of new FormData(form)) {
        jsonFormData[pair[0]] = pair[1];
    }
    return jsonFormData
}

async function performPostHttpRequest(fetchLink, headers, body) {
    if (!fetchLink || !headers || !body) {
        throw new Error("one or more Post request parameters haven't passed");
    }
    try {
        const rawResponse = await fetch(fetchLink, {
            method: "POST",
            headers: headers,
            body: JSON.stringify(body)
        });
        const content = await rawResponse.json();
        return content;
    }
    catch(err) {
        console.error(`Error at fetch POST: ${err}`);
        throw err;
    }
}

async function sendData(e, form) {
    e.preventDefault();

    const btnSubmit = document.getElementById('aanmaken');
    btnSubmit.disabled = true;
    setTimeout(() => btnSubmit.disabled = false, 2000);

    const jsonFormData = buildJsonFormData(form);

    const headers = buildHeaders();

    const response = await performPostHttpRequest('/restservices/GebruikerResource', headers, jsonFormData);
    console.log(response);
}