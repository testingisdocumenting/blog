import groovy.grape.Grape
import auth.HttpHeaderProvider
import auth.BrowserOpenHandler
import listeners.E2eTestListener

Grape.grab(group:'com.h2database', module: 'h2', version: '1.4.200',
        classLoader: ClassLoader.getSystemClassLoader())

testListeners = [E2eTestListener]

url = "http://localhost:8080" // base url for all http requests
browserUrl = "http://localhost:8080"

openApiSpecUrl = "http://localhost:8080/v3/api-docs"

myCustomValue = "for my test purposes" // custom config value

waitTimeout = 10000
cliTimeout = 180000

docPath = "../../../target/doc-artifacts/"

dbUrl = "jdbc:h2:file:~/gamestore/h2-db;AUTO_SERVER=TRUE"
dbDriverClassName = "org.h2.Driver"
dbUserName = "sa"
dbPassword = "password"

browserWidth = 900
browserHeight = 600

// personas-auth-config
httpHeaderProvider = HttpHeaderProvider.&provide // implicit header provider
browserPageNavigationHandler = BrowserOpenHandler.&handleOpen // implicit page open handler

userId = '' // explicitly set default userId to be an empty string

personas {
    Alice {
        userId = 'uid-alice' // Alice's system specific user id
    }

    Bob {
        userId = 'uid-bob' // Bob's system specific user id
    }
}
// personas-auth-config-end
