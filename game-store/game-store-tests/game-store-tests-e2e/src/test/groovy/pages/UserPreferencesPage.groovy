package pages

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

class UserPreferencesPage {
    def userId = $('#user-id') // user id to assert on
    def favoriteGenre = $('#favorite-genre') // genre to validate/set
    def saveButton = $('#save')
    def saveResultMessage = $('#save-result')

    def open() {
        browser.reopen("/#/user")
    }

    def save() { // exposed action - regular method
        saveButton.click()
    }
}
