package basicScenarios

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

// table-data-permute
scenario('table data permutation') {
    def tableData = ["name" | "score"   | "type"               | "disabled"] {
                    ____________________________________________________________________
                       "A"  | 100        | "T0R"               | false
                       "B"  | cell.above | "MBR"               | true  // cell.above reuses value from the row above
                       "C"  | 200        | permute("PO", "AC") | permute(true, false) } // permute generates multiple rows based on the values passed

    println "table data:"
    println tableData
    println tableData.collect { it.type } // standard collection operations are available on table data
}
// table-data-permute-end
