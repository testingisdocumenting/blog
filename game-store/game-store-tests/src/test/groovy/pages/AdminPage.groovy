package pages

import static org.testingisdocumenting.webtau.WebTauDsl.$
import static org.testingisdocumenting.webtau.WebTauDsl.browser

class AdminPage {
    def message = $('#message')
    def sendMessage = $('button').get('send message')

    def reopen() {
        browser.reopen("/#/admin")
    }
}
