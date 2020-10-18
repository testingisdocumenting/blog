package scenarios.gamestore

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*
import static pages.Pages.*
import static personas.Personas.*

scenario('user preferences redirects to login') {
    userPreferences.open()

    login.name.waitTo beVisible
    browser.url.ref.should == '/login'

    login.login('uid-test', 'dummy-password')

    userPreferences.userId.waitTo == 'uid-test'
    userPreferences.favoriteGenre.should == ''
}

scenario('implicit login with persona') {
    John {
        http.put('/api/user-preferences', [favoriteGenre: 'RPG'])

        userPreferences.open()

        userPreferences.userId.waitTo == 'uid-john'
        userPreferences.favoriteGenre.should == 'RPG'
    }
}

scenario('change preferences through UI') {
    John {
        userPreferences.open()

        userPreferences.favoriteGenre.waitTo beVisible()
        userPreferences.favoriteGenre.setValue('CRPG')
        userPreferences.save()

        userPreferences.saveResultMessage.waitTo == 'Saved'
    }

    Bob {
        http.get('/api/user-preferences') {
            favoriteGenre.should != 'CRPG'
        }
    }

    John {
        http.get('/api/user-preferences') {
            favoriteGenre.should == 'CRPG'
        }
    }
}
