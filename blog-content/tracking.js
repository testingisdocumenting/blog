const blogTracker = {
    onPageOpen(pageId) {
        ga('send', 'event', {
            eventCategory: 'navigation',
            eventAction: 'pageOpen',
            eventLabel: pageId.fileName
        })
    },
    onLinkClick(currentPageId, url) {
        ga('send', 'event', {
            eventCategory: 'navigation',
            eventAction: 'click',
            eventLabel: url,
            currentPageId: currentPageId.fileName
        })
    },
    onNextPage(currentPageId) {
        ga('send', 'event', {
            eventCategory: 'navigation',
            eventAction: 'tocNavigation',
            eventLabel: 'nextPage',
            currentPageId: currentPageId.fileName
        })
    },
    onPrevPage(currentPageId) {
        ga('send', 'event', {
            eventCategory: 'navigation',
            eventAction: 'tocNavigation',
            eventLabel: 'prevPage',
            currentPageId: currentPageId.fileName
        })
    },
    onTocItemSelect(currentPageId, tocItem) {
    },
    onSearchResultSelect(currentPageId, query, selectedPageId) {
        ga('send', 'event', {
            eventCategory: 'navigation',
            eventAction: 'searchResult',
            eventLabel: query,
            currentPageId: currentPageId.fileName,
            selectedPageId: currentPageId.fileName
        })
    },
    onPresentationOpen(currentPageId) {
        ga('send', 'event', {
            eventCategory: 'presentation',
            eventAction: 'open',
            eventLabel: '',
            currentPageId: currentPageId.fileName,
        })
    }
}

documentationTracking.addListener(blogTracker)