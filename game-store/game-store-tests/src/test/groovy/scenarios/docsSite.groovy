package scenarios

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

scenario('open docs') {
    browser.open('http://mykolas-macbook-pro.local:4444/preview')
}

scenario('capture docs screenshots') {
    selectPageAndCapture('Web UI', 'game-store-docs-web-ui')
    selectPageAndCapture('Rest API', 'game-store-docs-rest-api')
    selectPageAndCapture('List Games', 'game-store-docs-cli')
}

def selectPageAndCapture(title, artifactTitle) {
    $(".toc-item a").get(title).click()
    $(".toc-item.selected a").get(title).waitTo beVisible

    browser.doc.capture(artifactTitle)
}