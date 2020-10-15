package scenarios.gamestore
// hide
import utils.TestUtils // hide
// hide

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*
import static pages.Pages.*

scenario('setup up') { // hide
    TestUtils.resetData() // hide
} // hide
// hide
def Admin = persona('admin')

scenario('sending admin message') {
    landing.reopen()

    def message = 'Shop is going to be closed for maintenance'

    Admin {
        admin.reopen()
        admin.message.setValue(message)
        admin.sendMessage.click()
        browser.doc.capture('admin-send-message')
    }

    landing.adminMessage.waitTo == message
    browser.doc.capture('landing-received-message')
}