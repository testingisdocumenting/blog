package basicScenarios

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*
import static personas.Personas.* // import all available personas to be in scope

scenario('persona demo') {
    println cfg.myCustomValue

    John { // John is taken from personas.Personas.*
        println cfg.myCustomValue
    }

    Bob {
        println cfg.myCustomValue
    }
}