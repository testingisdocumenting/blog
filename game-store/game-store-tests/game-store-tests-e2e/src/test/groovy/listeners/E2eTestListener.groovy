package listeners

import clicommands.CliCommands
import org.testingisdocumenting.webtau.cli.CliBackgroundCommand
import org.testingisdocumenting.webtau.TestListener

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
        server = CliCommands.gameStoreServer.runInBackground()
        server.output.waitTo(contain("Tomcat initialized"), 30_000)
    }
}
