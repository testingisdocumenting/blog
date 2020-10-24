package auth

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

class BrowserNavigationHandler {
    private static STORAGE_KEY = 'authToken'

    static void handlePageOpen(passedUrl, fullUrl, currentUrl) {
        def userId = cfg.userId // take user from config based on current persona
        if (!userId || browser.localStorage.getItem(STORAGE_KEY)) { // if no user or token is inside storage, we are good to go
            return
        }

        browser.localStorage.setItem(STORAGE_KEY, generateToken(userId)) // set a new auth token generated based on current persona
        browser.reopen(fullUrl) // re-open original page
    }

    // for the demo purposes only
    private static def generateToken(String userId) {
        return userId.bytes.encodeBase64().toString()
    }
}
