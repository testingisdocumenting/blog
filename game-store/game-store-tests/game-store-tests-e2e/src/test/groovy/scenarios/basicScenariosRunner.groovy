package scenarios

import static clicommands.WebTauCommand.*
import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

scenario('run cli basics') {
    runCli('--workingDir=basicScenarios cliBasics.groovy')
}

scenario('run and capture http basics') {
    runCli('--workingDir=basicScenarios httpBasics.groovy')
    cli.doc.capture('webtau-cli-http-basics')
}

scenario('run and capture config basics') {
    runCli('--workingDir=basicScenarios configAccess.groovy', 1)
    cli.doc.capture('webtau-config-basics')
}

scenario('run and capture persona basics') {
    runCli('--workingDir=basicScenarios personaDemo.groovy --config=webtau.persona.cfg.groovy')

    cli.doc.capture('webtau-persona-basics')
}

scenario('run and capture browser basics') {
    runCli('--workingDir=basicScenarios browserBasics.groovy --config=webtau.cfg.groovy')

    cli.doc.capture('webtau-browser-basics')
}

scenario('run and capture table basics') {
    runCli('--workingDir=basicScenarios tableDataDemo.groovy --config=webtau.cfg.groovy')

    cli.doc.capture('webtau-table-basics')
}

static def runCli(String params, int expectedExitCode = 0) {
    // TODO remove cli.working once webtau is doing it by default
    webtauCli.run('--workingDir=basicScenarios ' + params, cli.workingDir(cfg.workingDir)) {
        exitCode.should == expectedExitCode
    }
}