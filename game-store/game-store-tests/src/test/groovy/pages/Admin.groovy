package pages

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

class Admin {
    def titles = $('[class*="GamesList_title"]')
    def filterText = $("#filter")
    def filterBelow60 = $("#below60")

    def reopen() {
        browser.reopen("/")
    }
}
