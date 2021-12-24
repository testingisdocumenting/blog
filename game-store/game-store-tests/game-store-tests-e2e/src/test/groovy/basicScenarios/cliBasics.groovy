package basicScenarios

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

scenario('basic cli command') {
    // cli-basics
    cli.run("ls -l")

    cli.run("ls -l") {
        output.should contain("webtau.cfg.groovy")
    }
    // cli-basics-end

    cli.doc.capture("cli-basics-ls")
}