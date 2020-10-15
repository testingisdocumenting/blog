package utils

import static org.testingisdocumenting.webtau.WebTauDsl.db

class TestUtils {
    static def resetData() {
        db.update("delete from GAME")

        def GAME = db.table("GAME")
        GAME << ["ID" | "TITLE"                   | "TYPE"            | "PRICE_USD"] {
                 ______________________________________________________________________
                 "g1" | "Slay The Spire"          | "card rpg"        | 20
                 "g2" | "Civilization 6"          | "strategy"        | 60
                 "g3" | "Doom"                    | "fps"             | 60
                 "g4" | "Last Of Us 2"            | "adventure drama" | 60
                 "g5" | "Inside"                  | "adventure"       | 10
                 "g6" | "Hearthstone"             | "card"            | 0
                 "g7" | "Division 2"              | "shooter rpg"     | 30
                 "g8" | "Assassin Creed Odyssey"  | "rpg"             | 40  }
    }
}