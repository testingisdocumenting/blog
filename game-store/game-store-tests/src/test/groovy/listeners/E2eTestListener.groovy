package listeners

import org.testingisdocumenting.webtau.cli.CliBackgroundCommand
import org.testingisdocumenting.webtau.reporter.TestListener

import static clicommands.CliCommands.gameStoreServer
import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

class E2eTestListener implements TestListener {
    CliBackgroundCommand server

    @Override
    void beforeFirstTest() {
        if (!isServerRunning()) {
            startServer()
        }
    }

    static def isServerRunning() {
        return http.ping('/actuator/health')
    }

    def startServer() {
        server = gameStoreServer.runInBackground()
        server.output.waitTo contain("Started GameStoreApp")
    }
}
