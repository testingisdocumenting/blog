import {extractAuthToken} from './auth/authTokens';

export function getJsonData(url) {
    const init = {
        method: 'GET',
        headers: withAuth({
            'Accept': 'application/json'
        })
    }

    return fetch(url, init)
        .then(handleErrors)
        .then(response => response.json());
}

export function putJsonData(url, data) {
    const init = {
        method: 'PUT',
        body: JSON.stringify(data),
        headers: withAuth({
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        })
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

function withAuth(headers) {
    const authToken = extractAuthToken()
    return authToken ? {...headers, Authorization: 'Bearer ' + authToken} : headers
}

