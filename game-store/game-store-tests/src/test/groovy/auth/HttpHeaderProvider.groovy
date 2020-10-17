package auth

import org.testingisdocumenting.webtau.http.HttpHeader
import org.testingisdocumenting.webtau.persona.Persona

import static org.testingisdocumenting.webtau.cfg.WebTauConfig.getCfg

class HttpHeaderProvider {
    static provide(String fullUrl, String url, HttpHeader httpHeaders) {
        def persona = Persona.currentPersona
        if (persona.isDefault()) {
            return httpHeaders
        }

        def userId = cfg.userIdToInject
        return httpHeaders.with("Authorization", "Bearer ${generateToken(userId)}")
    }

    // for the demo purposes
    static def generateToken(String userId) {
        return userId.bytes.encodeBase64().toString()
    }
}

