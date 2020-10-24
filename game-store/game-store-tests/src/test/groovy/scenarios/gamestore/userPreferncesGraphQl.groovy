package scenarios.gamestore

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*
import static personas.Personas.*

// mutation-definition-start
def mutation = '''
mutation updatePreferences($favoriteGenre: String!) {
  updatePreferences(favoriteGenre: $favoriteGenre) { 
    userId
    favoriteGenre
  }
}
'''
// mutation-definition-end

// update-user-preferences-no-auth-start
scenario('update user preferences no auth') {
    graphql.execute(mutation, [favoriteGenre: 'CRPG']) {
        statusCode.should == 403
    }
}
// update-user-preferences-no-auth-end

// update-user-preferences-persona-start
scenario('update user preferences no auth') {
    John {
        graphql.execute(mutation, [favoriteGenre: 'CRPG']) {
            id.should == 'uid-john'
            favoriteGenre.should == 'CRPG'
        }
    }
}
// update-user-preferences-persona-end

