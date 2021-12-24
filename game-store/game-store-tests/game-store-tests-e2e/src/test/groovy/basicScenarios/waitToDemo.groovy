package basicScenarios

import pages.Pages

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

scenario('waitTo demo') {
// wait-to-demo
    // waiting on query to finally return a value (eventual consistency)
    db.query("select * from games").waitTo contain([title: "Slay The Spire"])
    // waiting of file content to contain a line
    fs.textContent("my-file.txt").waitTo contain('important line')

    def command = cli.runInBackground("admin-tool")
    Pages.login.login("myname", "mypassword")
    // waiting on command line tool to print important message
    command.output.waitTo contain("<myname> logged in")
// wait-to-demo-end
}
