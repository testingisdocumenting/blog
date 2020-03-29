const blogTracker = {
    onPageOpen(pageId) {
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
    onScrollToSection(currentPageId, sectionIdTitle) {
        ga('send', 'event', {
            eventCategory: 'navigation',
            eventAction: 'scrollToSection',
            eventLabel: sectionIdTitle.id,
            currentPageId: currentPageId.fileName
        })
    },
    onSearchResultSelect(currentPageId, query, selectedPageId) {
        ga('send', 'event', {
            eventCategory: 'navigation',
            eventAction: 'searchResult',
            eventLabel: query + '-' + selectedPageId.fileName,
            currentPageId: currentPageId.fileName,
            selectedPageId: selectedPageId.fileName
        })
    },
    onPresentationOpen(currentPageId) {
        ga('send', 'event', {
            eventCategory: 'presentation',
            eventAction: 'open',
            eventLabel: '',
            currentPageId: currentPageId.fileName,
        })
    },
    onInteraction(currentPageId, type, id) {
        ga('send', 'event', {
            eventCategory: 'interaction',
            eventAction: type,
            eventLabel: currentPageId.fileName + '-' + id,
            currentPageId: currentPageId.fileName,
        })
    },
}

documentationTracking.addListener(blogTracker)