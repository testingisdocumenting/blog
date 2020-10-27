package auth

import org.testingisdocumenting.webtau.http.HttpHeader
import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

class HttpHeaderProvider {
    static provide(String fullUrl, String url, // this will be called for every HTTP request
                   HttpHeader httpHeaders) {
        def userId = cfg.userId // grab custom userId config value, depends on the Persona context
        return userId ?
                httpHeaders.with("Authorization", // create new http header based on the provided one with additional entry
                                 "Bearer ${generateToken(userId)}"):
                httpHeaders
    }

    // generate token based on your auth system, dummy impl for the demo purposes only
    private static def generateToken(String userId) {
        return userId.bytes.encodeBase64().toString()
    }
}

