package auth

import org.testingisdocumenting.webtau.http.HttpHeader
import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

class HttpHeaderProvider {
    static provide(String fullUrl, String url, // provide method will be called for every HTTP request
                   HttpHeader httpHeaders) {
        def userId = cfg.userId // grab custom userId config value, depends on the Persona context. Based on our current config, it is empty outside persona context.
        return userId ?
                httpHeaders.with("Authorization", // create new http header by taking a header passed by a test explicitly and adding Authorization key-value
                                 "Bearer ${generateToken(userId)}"):
                httpHeaders // original header passed by a test
    }

    // generate token based on your auth system, dummy impl for the demo purposes only
    private static def generateToken(String userId) {
        return userId.bytes.encodeBase64().toString()
    }
}

