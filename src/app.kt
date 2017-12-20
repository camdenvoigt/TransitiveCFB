fun main(args: Array<String>) {
    //TODO: take command line arguments
    var teams = parseData("2017")
    calculateTransitiveResults(teams)
    printResults(teams)
    teams.get(29).printTransativeSchedule()
}
