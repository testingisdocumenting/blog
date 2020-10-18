package pages

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

class UserPreferencesPage {
    def userId = $('#user-id')
    def favoriteGenre = $('#favorite-genre')
    def saveButton = $('#save')
    def saveResultMessage = $('#save-result')

    def open() {
        browser.reopen("/#/user")
    }

    def save() {
        saveButton.click()
    }
}
