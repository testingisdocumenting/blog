package pages

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

class LoginPage {
    def name = $('#name') // name to wait on
    def password = $('#password')
    def loginButton = $('#login')

    void login(name, password) { // action to login with
        this.name.setValue(name)
        this.password.setValue(password)

        loginButton.click()
    }
}
