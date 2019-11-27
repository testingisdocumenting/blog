znaiSearchData = [["entry@@testing-makes-you-fast-day-one@@tests-are-here-to-speed-you-up","Entry","Testing Makes You Fast Day One","Tests Are Here To Speed You Up","\"I am not writing tests right now as they are going to slow me down\" is one of the most common excuses I heard on the projects I worked.One possible reason for being slowed down by tests is the lack of tests writing experience.But I think there is another, bigger reason or rather untapped, most likely unrealized super power. The power of exercising any piece of your software in O(1) keystrokes.Let me try to explain."],["entry@@testing-makes-you-fast-day-one@@building-a-medieval-castle","Entry","Testing Makes You Fast Day One","Building A Medieval Castle","We are going to build a Castle, add features to it, and do a manual inspection of our creation as we go."]]
/*
 * Copyright 2019 TWO SIGMA OPEN SOURCE, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

znaiSearchIdx = lunr(function () {
    this.ref('id')
    this.field('section')
    this.field('pageTitle')
    this.field('pageSection')
    this.field('text')

    this.metadataWhitelist = ['position']

    znaiSearchData.forEach(function (e) {
        this.add({
            id: e[0],
            section: e[1],
            pageTitle: e[2],
            pageSection: e[3],
            text: e[4],
        })
    }, this)
})
