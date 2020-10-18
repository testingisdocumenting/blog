package pages

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

class LoginPage {
    def name = $('#name')
    def password = $('#password')
    def loginButton = $('#login')

    void login(name, password) {
        this.name.setValue(name)
        this.password.setValue(password)

        loginButton.click()
    }
}
