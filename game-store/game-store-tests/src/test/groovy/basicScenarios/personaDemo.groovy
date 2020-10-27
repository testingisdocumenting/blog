package basicScenarios

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

// persona-demo
def John = persona('John') // define John persona
def Bob = persona('Bob')

scenario('persona demo') {
    println cfg.myCustomValue // custom value from config

    John {
        println cfg.myCustomValue // custom value from config in John's context
    }

    Bob {
        println cfg.myCustomValue
    }
}
// persona-demo-end