package pages

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

class LandingPage {
    def titles = $('[class*="GamesList_title"]')
    def filterText = $("#filter")
    def filterBelow60 = $("#below60")
    def labelBelow60 = $("span").get('Below $60')
    def adminMessage = $('#admin-message')

    def reopen() {
        browser.reopen("/")
    }
}
