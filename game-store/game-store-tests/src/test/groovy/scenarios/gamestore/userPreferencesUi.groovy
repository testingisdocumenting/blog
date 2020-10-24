package scenarios.gamestore

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*
import static pages.Pages.*
import static personas.Personas.*

// explicit-login-start
scenario('user preferences redirects to login') {
    userPreferences.open() // open user preferences page

    login.name.waitTo beVisible
    browser.url.ref.should == '/login' // but landed on login page
    browser.doc.capture('login-screen')

    login.login('uid-test', 'dummy-password') // explicitly enter user name and password

    userPreferences.userId.waitTo == 'uid-test' // after redirect we can see user preferences
    userPreferences.favoriteGenre.should == ''
}
// explicit-login-end

// persona-login-start
scenario('implicit login with persona') {
    John {
        http.put('/api/user-preferences', [favoriteGenre: 'RPG'])

        userPreferences.open()

        userPreferences.userId.waitTo == 'uid-john'
        userPreferences.favoriteGenre.should == 'RPG'
        browser.doc.capture('user-preferences-screen')
    }
}
// persona-login-end

// change-through-ui-start
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
// change-through-ui-end
