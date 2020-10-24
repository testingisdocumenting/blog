package auth

import org.testingisdocumenting.webtau.http.HttpHeader

import static org.testingisdocumenting.webtau.cfg.WebTauConfig.getCfg

class HttpHeaderProvider {
    static provide(String fullUrl,
                   String url,
                   HttpHeader httpHeaders) {
        def userId = cfg.userId
        if (!userId) {
            return httpHeaders
        }

        return httpHeaders.with("Authorization", "Bearer ${generateToken(userId)}")
    }

    // for the demo purposes only
    private static def generateToken(String userId) {
        return userId.bytes.encodeBase64().toString()
    }
}

