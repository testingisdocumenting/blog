package basicScenarios

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*
import static personas.Personas.* // import all available personas to be in scope

scenario('persona demo') {
    println cfg.userId

    Alice { // Alice is taken from personas.Personas.*
        println cfg.userId
    }

    Bob {
        println cfg.userId
    }
}