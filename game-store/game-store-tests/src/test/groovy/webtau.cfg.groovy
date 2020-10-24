@GrabConfig(systemClassLoader=true)
@Grab(group='com.h2database', module='h2', version='1.4.199')

import auth.HttpHeaderProvider
import auth.BrowserNavigationHandler
import listeners.E2eTestListener

testListeners = [E2eTestListener]

// header-provider-start
httpHeaderProvider = HttpHeaderProvider.&provide
// header-provider-end

// browser-navigation-handler-start
browserPageNavigationHandler = BrowserNavigationHandler.&handlePageOpen
// browser-navigation-handler-end

url = "http://localhost:8080" // base url for all http requests
browserUrl = "http://localhost:3000"

myCustomValue = "for my test purposes" // custom config value

waitTimeout = 10000

docPath = "../../../target/doc-artifacts/"

dbUrl = "jdbc:h2:file:~/gamestore/h2-db;AUTO_SERVER=TRUE"
dbDriverClassName = "org.h2.Driver"
dbUserName = "sa"
dbPassword = "password"

// personas config
personas {
    John {
        myCustomValue = "JCV"
        userId = 'uid-john'
    }

    Bob {
        myCustomValue = "BCV"
        userId = 'uid-bob'
    }

    Admin {
        myCustomValue = "ACV"
        userId = 'uid-admin'
    }
}
// personas config-end

// environments config
environments {
    dev {
        url = "http://dev-server:8080"
        myCustomValue = "dev custom value"
    }

    cloud {
        url = "http://cloud.google.com/my-instance"
        myCustomValue = "cloud custom value"
    }
}
// environments config-end
