package auth

import static org.testingisdocumenting.webtau.WebTauDsl.browser
import static org.testingisdocumenting.webtau.cfg.WebTauConfig.getCfg

class BrowserNavigationHandler {
    private static STORAGE_KEY = 'authToken'

    static void handlePageOpen(passedUrl, fullUrl, currentUrl) {
        def userId = cfg.userId
        if (!userId) {
            return
        }

        if (browser.localStorage.getItem(STORAGE_KEY)) {
            return
        }

        browser.localStorage.setItem(STORAGE_KEY, generateToken(userId))
        browser.reopen(fullUrl)
    }

    // for the demo purposes only
    private static def generateToken(String userId) {
        return userId.bytes.encodeBase64().toString()
    }
}
