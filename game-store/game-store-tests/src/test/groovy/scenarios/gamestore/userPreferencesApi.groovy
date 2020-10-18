package scenarios.gamestore

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*
import static personas.Personas.*

scenario('save preferences without auth') {
    http.put('/api/user-preferences', [favoriteGenre: 'RPG']) {
        statusCode.should == 403
    }
}

scenario('save preferences with explicit auth') {
    def token = generateToken('user-a')

    http.put('/api/user-preferences',
            http.header([Authorization: "Bearer ${token}"]),
            [favoriteGenre: 'RPG']) {
        userId.should == 'user-a'
    }
}

scenario('save preferences with personas auth') {
    John {
        http.put('/api/user-preferences', [favoriteGenre: 'RPG']) {
            userId.should == 'uid-john'
            favoriteGenre.should == 'RPG'
        }
    }

    Bob {
        http.put('/api/user-preferences', [favoriteGenre: 'Strategy']) {
            userId.should == 'uid-bob'
            favoriteGenre.should == 'Strategy'
        }
    }
}

scenario('read preferences with personas auth') {
    John {
        http.get('/api/user-preferences') {
            favoriteGenre.should == 'RPG'
        }
    }

    Bob {
        http.get('/api/user-preferences', [favoriteGenre: 'Strategy']) {
            favoriteGenre.should == 'Strategy'
        }
    }
}

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

static def generateToken(String userId) {
    return userId.bytes.encodeBase64().toString()
}
