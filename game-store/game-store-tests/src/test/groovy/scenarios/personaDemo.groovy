package scenarios

import org.testingisdocumenting.webtau.persona.Persona

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

scenario('persona demo') {
    def John = persona('John')
    def Bob = persona('Bob')

    // do-something-start
    doSomething()

    John {
        doSomething()
    }

    Bob {
        doSomething()
    }
    // do-something-end
}

// do-something-def-start
def doSomething() {
    println "${Persona.currentPersona.id}, ${cfg.userId}, ${cfg.myCustomValue}"
}
// do-something-def-end