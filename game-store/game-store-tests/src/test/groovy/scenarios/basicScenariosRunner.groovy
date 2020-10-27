package scenarios

import static clicommands.WebTauCommand.getWebtauCli
import static org.testingisdocumenting.webtau.WebTauDsl.cli

scenario('run and capture http basics') {
    webtauCli.run('--workingDir=basicScenarios httpBasics.groovy')
    cli.doc.capture('webtau-cli-http-basics')
}

scenario('run and capture config basics') {
    webtauCli.run('--workingDir=basicScenarios configAccess.groovy') {
        exitCode.should != 0
    }

    cli.doc.capture('webtau-config-basics')
}

scenario('run and capture persona basics') {
    webtauCli.run('--workingDir=basicScenarios personaDemo.groovy --config=webtau.persona.cfg.groovy')

    cli.doc.capture('webtau-persona-basics')
}

scenario('run and capture browser basics') {
    webtauCli.run('--workingDir=basicScenarios browserBasics.groovy --config=webtau.persona.cfg.groovy')

    cli.doc.capture('webtau-browser-basics')
}

scenario('run and capture table basics') {
    webtauCli.run('--workingDir=basicScenarios tableDataDemo.groovy --config=webtau.persona.cfg.groovy')

    cli.doc.capture('webtau-table-basics')
}
