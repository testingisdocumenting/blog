export function getJsonData(url) {
    const init = {
        method: 'GET',
        headers: {
            'Accept': 'application/json'
        }
    }

    return fetch(url, init)
        .then(handleErrors)
        .then(response => response.json());
}

function handleErrors(response) {
    if (!response.ok) {
        console.error(response)
        throw new Error("can't fetch");
    }

    return response;
}

