scenario('implicit config usage') {
    http.post("/relative-url") // implicit usage of core url config value
    browser.open("/test") // implicit usage of browser url and browser id
}
