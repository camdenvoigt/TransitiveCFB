package transitivecfb

fun main(args: Array<String>) {
    //TODO: take command line arguments
    var teams = parseData("2017")
    calculateTransitiveResults(teams)
    printFullResultsTable(teams)
    val team = teams.get(29)
    printTransitiveScheduleTable(team)

}
