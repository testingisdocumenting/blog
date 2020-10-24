package org.testingisdocumenting.examples.gamestore.server.auth;

import graphql.kickstart.servlet.context.DefaultGraphQLServletContext;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

@Component
public class AuthorizationService {
    private static final String AUTH_KEY = "authorization";

    private final Set<String> admins;

    public AuthorizationService() {
        this.admins = fetchAdmins();
    }

    public String userId(HttpHeaders httpHeaders) {
        String auth = httpHeaders.toSingleValueMap().get(AUTH_KEY);
        return AuthUtils.extractUserIdFromAuth(auth);
    }

    public String userId(DataFetchingEnvironment env) {
        DefaultGraphQLServletContext context = env.getContext();
        String auth = context.getHttpServletRequest().getHeader(AUTH_KEY);
        return AuthUtils.extractUserIdFromAuth(auth);
    }

    public boolean isAdmin(String userId) {
        return admins.contains(userId);
    }

    private Set<String> fetchAdmins() {
        return Collections.singleton("uid-admin");
    }
}
