package pages

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

class MaintenancePage {
    def message = $('#message')
    def sendMessage = $('button').get('send message')

    def reopen() {
        browser.reopen("/#/admin")
    }
}
