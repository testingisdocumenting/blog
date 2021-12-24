package scenarios

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

scenario('start static content server') {
    def docServer = server.serve('docs-content', '../../../../game-store-tests-doc-gen/target')
    docServer.setAsBaseUrl()
}

scenario('open docs') {
    browser.open('ue2e')
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