package org.testingisdocumenting.examples.gamestore.server.auth

import org.junit.Test

class AuthUtilsTest {
    @Test
    void "should extract user if from auth value"() {
        AuthUtils.extractUserIdFromAuth('Bearer dXNlci1h').should == 'user-a'
        AuthUtils.extractUserIdFromAuth('').should == ''
    }
}
