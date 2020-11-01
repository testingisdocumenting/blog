package pages

class Pages {
    static final def maintenance = new AdminPage() // pre-creating stateless instances
    static final def login = new LoginPage()
    static final def landing = new LandingPage() // for all the pages our test suite needs to have access
    static final def userPreferences = new UserPreferencesPage()
    static final def report = new ReportPage()
}
