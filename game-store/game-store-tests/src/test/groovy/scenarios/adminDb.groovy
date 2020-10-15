package scenarios

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

scenario('db access') {
    db.query("select * from GAME")
}