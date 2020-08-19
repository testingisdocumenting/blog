@GrabConfig(systemClassLoader=true)
@Grab(group='com.h2database', module='h2', version='1.4.199')

import listeners.E2eTestListener

testListeners = [E2eTestListener]

url = "http://localhost:8080"
waitTimeout = 10000

docPath = "../../../target/doc-artifacts/"

dbUrl = "jdbc:h2:file:~/gamestore/h2-db;AUTO_SERVER=TRUE"
dbDriverClassName = "org.h2.Driver"
dbUserName = "sa"
dbPassword = "password"