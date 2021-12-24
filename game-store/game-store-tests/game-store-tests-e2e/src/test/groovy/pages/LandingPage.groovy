package pages

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

class LandingPage {
    def titles = $('[class*="GamesList_title"]')
    def filterText = $("#filter") // lazy declaration for future usage, no actual attempt to find element is performed
    def filterBelow60 = $("#below60")
    def labelBelow60 = $("span").get('Below $60') // filters chaining is also lazy
    def adminMessage = $("#admin-message")

    def reopen() {
        browser.reopen("/")
    }
}
