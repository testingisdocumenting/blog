package scenarios.gamestore // optional package declaration for IDE happiness

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.* // optional single import for IDE autocomplete

scenario('register new game') {
    def payload = [title: "Slay The Spire",  // no ID specified this time
                   type: "card rpg",
                   priceUsd: 20]

    def id = http.post("/api/game", payload) {
        return id // we grab id from response, return keyword is optional
    }
    http.doc.capture('http-new-game-post-streamlined')

    http.get("/api/game/${id}") { // using id for subsequent request
        body.should == payload // re-using POST payload to assert GET response. Only values in payload will be validated
    }
}