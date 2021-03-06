package scenarios.gamestore

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*
import static personas.Personas.*

// without-auth
scenario('save preferences without auth') {
    http.put('/api/user-preferences', [favoriteGenre: 'RPG']) {
        statusCode.should == 403 // forbidden, as this end-point requires authentication
    }
}
// without-auth-end

// with-explicit-auth
scenario('save preferences with explicit auth') {
    def token = generateToken('user-a') // generates token using our system underlying auth system

    http.put('/api/user-preferences',
            http.header([Authorization: "Bearer ${token}"]), // explicitly pass Bearer token
            [favoriteGenre: 'RPG']) {
        userId.should == 'user-a' // make sure server recognized token and properly authenticated user
    }
    http.doc.capture('game-store-put-user-preferences')
}
// with-explicit-auth-end

// with-personas-put
scenario('save preferences with personas auth') {
    Alice { // Alice's context
        http.put('/api/user-preferences', [favoriteGenre: 'RPG']) {
            userId.should == 'uid-alice' // validating that we updated the right user
            favoriteGenre.should == 'RPG'
        }
    }

    Bob { // Bob's context
        http.put('/api/user-preferences', [favoriteGenre: 'Strategy']) {
            userId.should == 'uid-bob' // validating that we updated the right user
            favoriteGenre.should == 'Strategy'
        }
    }
}
// with-personas-put-end

// with-personas-get
scenario('read preferences with personas auth') {
    Alice { // Alice's context
        http.get('/api/user-preferences') {
            favoriteGenre.should == 'RPG' // we get back the Alice's favorite genre
        }
        http.doc.capture('game-store-get-user-preferences')
    }

    Bob { // Bob's context
        http.get('/api/user-preferences') {
            favoriteGenre.should == 'Strategy' // we get back the Bob's favorite genre
        }
    }
}
// with-personas-get-end

static def generateToken(String userId) {
    return userId.bytes.encodeBase64().toString()
}
