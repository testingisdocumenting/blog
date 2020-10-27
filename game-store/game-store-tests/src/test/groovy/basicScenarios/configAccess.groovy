scenario('implicit config usage') {
    http.post("/relative-url") // implicit usage of core url config value
    browser.open("/test") // implicit usage of browser url and browser id
}

scenario('explicit config access') {
    println cfg.url // explicit usage of url config value
    println cfg.myCustomValue // explicit usage of test domain specific config value
}