package scenarios.gamestore
// hide
import utils.TestUtils // hide
// hide

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.* // single convenient import for IDE auto complete
import static pages.Pages.* // convenient access to all page objects
import static personas.Personas.* // convenient access to all Personas

scenario('setup up') { // hide
    TestUtils.resetData() // hide
} // hide
// hide
scenario('sending admin message') {
    landing.reopen() // open landing page in a default browser

    def message = 'Shop is going to be closed for maintenance'

    Admin {
        admin.reopen() // open admin page in Admin's browser
        admin.message.setValue(message)
        admin.sendMessage.click() // send notification message
        browser.doc.capture('admin-send-message')
    }

    landing.adminMessage.waitTo == message // in the default browser, wait for the message to arrive
    browser.doc.capture('landing-received-message')
}