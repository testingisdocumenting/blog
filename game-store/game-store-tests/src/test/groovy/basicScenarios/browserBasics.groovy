package basicScenarios

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

// browser-basics
scenario('basic browser interaction') {
    browser.open("https://google.com")
    $('input[title="Search"]').setValue("test\n") // set value based on the underlying component
    browser.doc.capture("browser-basic")

    $('h3').count.waitTo >= 1 // explicit, simplified wait on condition
    $("h3").get(~/Speedtest.*Ookla/).click() // example of filter based on regular expression

    browser.url.full.should == ~/speedtest.net/ // full url assertion
}
// browser-basics-end

scenario('implementation details') {
    // impl-details
    $("#filter").setValue("civ") // exposing css selector to the test is going to haunt you later
    $('[class*="GamesList_title"]').waitTo == ['Civilization 6'] // class based selection is more likely to become out of sync
    // impl-details-end
}
