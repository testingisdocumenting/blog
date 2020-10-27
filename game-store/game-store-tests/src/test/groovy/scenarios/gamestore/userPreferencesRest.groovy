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
        userId.should == 'user-a'
    }
}
// with-explicit-auth-end

// with-personas-put
scenario('save preferences with personas auth') {
    John { // John's context
        http.put('/api/user-preferences', [favoriteGenre: 'RPG']) {
            userId.should == 'uid-john' // validating that we updated the right user
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
    John {
        http.get('/api/user-preferences') {
            favoriteGenre.should == 'RPG'
        }
    }

    Bob {
        http.get('/api/user-preferences') {
            favoriteGenre.should == 'Strategy'
        }
    }
}
// with-personas-get-end

// with-personas-admin-get
scenario('admin read preferences') {
    Admin {
        http.get('/api/user-preferences/uid-john') {
            favoriteGenre.should == 'RPG'
        }

        http.get('/api/user-preferences/uid-bob') {
            favoriteGenre.should == 'Strategy'
        }
    }
}
// with-personas-admin-get-end

static def generateToken(String userId) {
    return userId.bytes.encodeBase64().toString()
}
