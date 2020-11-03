package basicScenarios

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

// persona-demo
def Alice = persona('Alice') // define Alice persona
def Bob = persona('Bob') // define Bob persona

scenario('persona demo') {
    println cfg.userId // custom value from config

    Alice {
        println cfg.userId // custom value from config in Alice's context
    }

    Bob {
        println cfg.userId
    }
}
// persona-demo-end