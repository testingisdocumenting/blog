package scenarios.gamestore

import utils.TestUtils

import static clicommands.CliCommands.adminTool
import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

scenario('prepare data for capture') {
    TestUtils.resetData()
    browser.reopen("/")

    def titles = $('[class*="GamesList_title"]')
    titles.count.waitToBe > 0

    browser.doc.capture("game-store-main-page")

    adminTool.run()
    cli.doc.capture("game-store-cli-output")
}