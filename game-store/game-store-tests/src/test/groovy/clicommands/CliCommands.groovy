package clicommands

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

class CliCommands {
    static final def gameStoreServer = cli.command(
            "java -jar ../../../../game-store-backend/target/game-store-backend-0.1-SNAPSHOT.jar")
    static final def adminTool = cli.command(
            "java -jar ../../../../game-store-cli/target/admin-tool-jar-with-dependencies.jar")
}
