package pages

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

class MaintenancePage {
    def message = $('#mess2age')
    def sendMessage = $('button').get('send message')

    def reopen() {
        browser.reopen("/#/admin")
    }
}
