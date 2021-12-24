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
    Alice { // In the Alice's context
        http.put('/api/user-preferences', [favoriteGenre: 'RPG']) // prepare data to assert on - another example of http/browser test combination

        userPreferences.open() // open user preferences

        userPreferences.userId.waitTo == 'uid-alice' // we expect user preferences screen, not the login screen
        userPreferences.favoriteGenre.should == 'RPG' // our assertion matches what we set using REST API
        browser.doc.capture('user-preferences-screen')
    }
}
// persona-login-end

// change-through-ui-start
scenario('change preferences through UI') {
    Bob { http.put('/api/user-preferences', [favoriteGenre: 'Sim']) } // Set Bob's preference via HTTP

    Alice {
        userPreferences.open()

        userPreferences.favoriteGenre.waitTo beVisible() // wait for UI to load data from REST endpoint
        userPreferences.favoriteGenre.setValue('CRPG')
        userPreferences.save() // save user preferences

        userPreferences.saveResultMessage.waitTo == 'Saved' // wait for visual clue to appear
    }

    Bob { // In Bob's context
        http.get('/api/user-preferences') {
            favoriteGenre.should == 'Sim' // Bob's preferences were not affected by UI
        }
    }

    Alice { // In Alice's context
        http.get('/api/user-preferences') {
            favoriteGenre.should == 'CRPG' // Genre is the one we set
        }
    }
}
// change-through-ui-end
