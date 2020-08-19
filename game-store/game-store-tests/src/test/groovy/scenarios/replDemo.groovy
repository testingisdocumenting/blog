package scenarios

import static clicommands.WebTauCommand.webtauCli
import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

def repl = createLazyResource("repl") {
    def process = webtauCli.runInBackground("repl", cli.env([WEBTAU_CP: 'h2-1.4.199.jar']))
    process.output.waitTo contain(':help')

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

scenario('list games') {
    repl.with {
        clearOutput()
        send('http.get("/api/game")\n')
        output.waitTo contain('executed HTTP')
    }
    cli.doc.capture('game-store-repl-list-empty-games')

    repl.with {
        clearOutput()
        send('body.page.totalElements.shouldBe >= 0\n')
        output.waitTo contain('expected')
    }
    cli.doc.capture('game-store-repl-list-check-total')

    repl.with {
        clearOutput()
        send('body\n')
        output.waitTo contain('totalElements')
    }
    cli.doc.capture('game-store-repl-list-body-after-check')
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
        output.waitTo contain('executed HTTP')

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
        output.waitTo contain('greater')
    }

    cli.doc.capture('browser-basic-repl')
}

scenario('clear db after') {
    db.update("delete from Game")
}
