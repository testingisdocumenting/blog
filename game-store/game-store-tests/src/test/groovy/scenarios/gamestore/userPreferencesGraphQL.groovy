package scenarios.gamestore

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*
import static personas.Personas.*

// mutation-definition-start
def mutation = '''
mutation updateUserPreferences($favoriteGenre: String!) {
  updateUserPreferences(favoriteGenre: $favoriteGenre) { 
    userId
    favoriteGenre
  }
}
'''
// mutation-definition-end

// without-auth
scenario('save preferences without auth') {
    graphql.execute(mutation, [favoriteGenre: 'CRPG']) {
        errors.message.should == ['forbidden'] // shortcut to take a message from each error in the list
    }
    http.doc.capture('graphql-auth-error')
}
// without-auth-end

// with-explicit-auth
scenario('save preferences with explicit auth') {
    def token = generateToken('user-a')

    graphql.execute(mutation, [favoriteGenre: 'CRPG'],
            http.header([Authorization: "Bearer ${token}"])) {
        userId.should == 'user-a'
    }
}
// with-explicit-auth-end

// with-personas-put
scenario('save preferences with personas auth') {
    Alice { // Personas we defined and used for HTTP REST API
        graphql.execute(mutation, [favoriteGenre: 'RPG']) {
            userId.should == 'uid-alice' // make sure system picked Alice as a user
            favoriteGenre.should == 'RPG'
        }
    }

    Bob {
        graphql.execute(mutation, [favoriteGenre: 'Strategy']) {
            userId.should == 'uid-bob' // make sure system picked Bob as a user
            favoriteGenre.should == 'Strategy'
        }
    }
}
// with-personas-put-end

// query-definition-start
def query = '''
query {
  userPreferences {
    userId
    favoriteGenre
  }
}
'''
// query-definition-end

// with-personas-get
scenario('read preferences with personas auth') {
    Alice {
        graphql.execute(query) {
            favoriteGenre.should == 'RPG' // making sure correct data is returned back
        }
    }

    Bob {
        graphql.execute(query) {
            favoriteGenre.should == 'Strategy'  // Strategy games are not bad
        }
    }
}
// with-personas-get-end

static def generateToken(String userId) {
    return userId.bytes.encodeBase64().toString()
}
