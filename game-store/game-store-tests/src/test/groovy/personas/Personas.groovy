package personas // user defined package name/directory

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

class Personas { // class name can be anything
    public static final def Admin = persona('Admin')
    public static final def John = persona('John')
    public static final def Bob = persona('Bob')
}
