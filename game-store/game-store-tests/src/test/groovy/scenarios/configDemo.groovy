package scenarios

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

scenario('config demo') {
    println cfg.myCustomValue
}