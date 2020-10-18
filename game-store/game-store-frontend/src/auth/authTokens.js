const tokenKey = 'authToken';

// this is for demo purposes only
// you most likely need to use 3rd party auth providers like Okta
export function storeAuthToken(userName, password) {
  const token = generateAuthToken(userName, password)
  window.localStorage.setItem(tokenKey, token)
}

// this is for demo purposes only
// you most likely need to use 3rd party auth providers like Okta
export function generateAuthToken(userName, password) {
  return btoa(userName)
}

// this is for demo purposes only
// you most likely need to use 3rd party auth providers like Okta
export function hasAuthToken() {
  return !!extractAuthToken()
}

// this is for demo purposes only
// you most likely need to use 3rd party auth providers like Okta
export function extractAuthToken() {
  return window.localStorage.getItem(tokenKey)
}
