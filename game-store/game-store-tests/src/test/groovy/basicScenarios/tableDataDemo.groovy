package basicScenarios

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

// table-data-permute
scenario('table data demo') {
    def increment = cell.above + 1 // create increment cell function to auto increase value
    def tableData = ["id"      | "name" | "score"   | "type"               | "disabled"] {
                    ____________________________________________________________________________
                     cell.guid | "A"    | 100        | "T0R"               | false // cell.guid generates guid
                     cell.guid | "B"    | cell.above | "MBR"               | true  // cell.above reuses value from the row above
                     cell.guid | "C"    | increment  | permute("PO", "AC") | permute(true, false) } // permute generates multiple rows based on the values passed

    println("table data:")
    println(tableData)
    println(tableData.collect { it.score }) // standard collection operations are available on table data
}
// table-data-permute-end
