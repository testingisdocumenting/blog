package org.testingisdocumenting.examples.gamestore.server.auth;

import java.util.Base64;

/**
 * this is an extreme simplification for demo purposes only
 */
class AuthUtils {
    static String extractUserIdFromAuth(String auth) {
        return extractUserId(removeBearer(auth));
    }

    static String removeBearer(String auth) {
        return auth == null ? "" : auth.replaceFirst("Bearer ", "");
    }

    static String extractUserId(String token) {
        return new String(Base64.getDecoder().decode(token));
    }
}
