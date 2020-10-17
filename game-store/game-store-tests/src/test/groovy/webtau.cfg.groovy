@GrabConfig(systemClassLoader=true)
@Grab(group='com.h2database', module='h2', version='1.4.199')

import auth.HttpHeaderProvider
import listeners.E2eTestListener

testListeners = [E2eTestListener]
httpHeaderProvider = HttpHeaderProvider.&provide

url = "http://localhost:8080"
waitTimeout = 10000

docPath = "../../../target/doc-artifacts/"

dbUrl = "jdbc:h2:file:~/gamestore/h2-db;AUTO_SERVER=TRUE"
dbDriverClassName = "org.h2.Driver"
dbUserName = "sa"
dbPassword = "password"

personas {
    John {
        userIdToInject = 'uid-john'
    }

    Bob {
        userIdToInject = 'uid-bob'
    }

    Admin {
        userIdToInject = 'uid-admin'
    }
}

environments {
    cloud {
        url = "google.cloud"
    }

    uat {
        url = ""
    }
}