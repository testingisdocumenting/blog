package scenarios

import static clicommands.WebTauCommand.*
import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

def repl = createLazyResource("repl") {
    def listOfScenarios = [
            "adminCliTool.groovy",
            "userPreferencesRest.groovy",
            "userPreferencesGraphQL.groovy",
            "userPreferencesUi.groovy"].collect { "scenarios/gamestore/$it" }

    def process = webtauCli.runInBackground(listOfScenarios.join(" ") + " repl",
            cli.env([WEBTAU_CP: 'h2-1.4.199.jar']).workingDir(cfg.workingDir))

    process.output.waitTo contain("userPreferencesUi.groovy")
    process.clearOutput()

    return process
}

scenario('clear db before') {
    db.update("delete from Game")
}

scenario('repl basic') {
    repl.with {
        clearOutput()

        send('2 + 2\n')
        output.waitTo contain('4')

        send('a = 5\n')
        output.waitTo contain('5')

        send('a + 3\n')
        output.waitTo contain('8')
    }

    cli.doc.capture('game-store-repl-basic')
}

scenario('actuator health full') {
    repl.with {
        clearOutput()
        send('http.get("http://localhost:8080/actuator/health")\n')

        output.waitTo contain('executed HTTP')
    }

    cli.doc.capture('game-store-repl-actuator-health')
}

scenario('actuator health base url') {
    repl.with {
        clearOutput()
        send('cfg.url = "http://localhost:8080"\n')
        output.waitTo contain("===>")

        send('http.get("/actuator/health")\n')
        output.waitTo contain('executed HTTP')
    }

    cli.doc.capture('game-store-repl-base-url-actuator-health')
}

scenario('add new game') {
    repl.with {
        clearOutput()
        send('http.post("/api/game", [id: "g1", title: "Slay The Spire",\n' +
                'type: "card rpg", priceUsd: 20])\n')
        output.waitTo contain('executed HTTP')
    }

    cli.doc.capture('game-store-repl-post-new-game')
}

scenario('db interaction') {
    repl.with {
        clearOutput()
        send('http.post("/api/game", [id: "g2", title: "Doom",\n' +
                'type: "fps", priceUsd: "40"])\n')
        output.waitTo contain('fps')

        // need smarter contains, like multiple in a row

        clearOutput()
        send('db.query("select * from Game")\n')
        output.waitTo contain('fps')
    }

    cli.doc.capture('game-store-db-query')
}

scenario('browser interaction') {
    repl.with {
        clearOutput()
        send('browser.open("https://google.com")\n')
        send('$(\'input[title="Search"]\')\n')
        send('$(\'input[title="Search"]\').setValue("test\\n")\n')
        send('$(\'input[title="Search"]\')\n')
        send('$(\'h3\')\n')
        send('$(\'h3\').count.waitTo >= 0\n')

        sleep 1000 // TODO we need a way to compare ansi output with non ansi text
        output.waitTo(contain('greater'), 20_000)
    }

    cli.doc.capture('browser-basic-repl')
}

scenario('run scenario by selection') {
    repl.with {
        clearOutput() // we print the ls list at the repl start, so we wait for it, clear all, and do an explicit ls
        send('ls\n')
        output.waitTo contain("userPreferencesRest.groovy")
    }
    cli.doc.capture('test-files-list-repl')

    repl.with {
        clearOutput()
        send('s "userPreferencesRest"\n')
        output.waitTo contain("save preferences with personas auth")
    }
    cli.doc.capture('scenarios-list-repl')

    repl.with {
        clearOutput()
        send('r "save preferences with personas auth"\n')

        sleep 1000 // TODO we need a way to compare ansi output with non ansi text
    }
    cli.doc.capture('http-persona-auth-ran-with-repl')

    repl.with {
        clearOutput()
        send('db.query("select * from user_preferences")\n')

        output.waitTo contain("Strategy")
    }
    cli.doc.capture('db-query-after-http-repl')
}

scenario('clear db after') {
    db.update("delete from Game")
}
