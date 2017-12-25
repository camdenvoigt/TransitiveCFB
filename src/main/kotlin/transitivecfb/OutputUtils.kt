package transitivecfb

/* TABLE FUNCTIONS */

/*
    Returns a string containing the results from each team in the list in a formatted table
 */
fun getFullResultsTable(teams: List<Team>): String {
    val divider = "+-----+------------------------+-----------------+-------------------+-------------------+------------------------------+\n"
    val headers = "| id  |          Team          | Original Record | Transitive Record | Conference Record | Transitive Conference Record |\n"
    var table = ""

    table += divider
    table += headers
    table += divider
    for (team in teams) {
        val formatString = "| %-3d | %-22s |      %-6s     |       %-6s      |       %-6s      |            %-6s            |\n"
        table += String.format(formatString, team.id, team.name, team.getRecord(), team.getTransRecord(), team.getConfRecord(), team.getTransConfRecord())
    }
    table += divider

    return table
}

/*
    Return a string containing the transitive schedule of the given team in a formatted table
 */
fun getTransitiveScheduleTable(team: Team):String {
    val headers = "|  teamId  |          team          |  oppId  |        opponent        | code |\n"
    val divider = "+----------+------------------------+---------+------------------------+------+\n"
    var table = ""

    table += divider
    table += headers
    table += divider
    for (game in team.transSchedule) {
        val formatString = "|    %-3d   | %-22s |   %-3d   | %-22s |  %+2d  |\n"
        if (game.isTeam1(team)) {
            table += String.format(formatString, game.winnerId, game.winner, game.loserId, game.loser, game.transitiveCode)
        } else {
            table += String.format(formatString, game.loserId, game.loser, game.winnerId, game.winner, -game.transitiveCode)
        }
    }
    table += divider

    return table
}

/* PRINTING FUNCTIONS  */

/*
    Prints to console the results of each team in the given list in a formatted table
 */
fun printFullResultsTable(teams: List<Team>) {
    print(getFullResultsTable(teams))
}

/*
    Print to console the transitive schedule of the given team in a formatted table
 */
fun printTransitiveScheduleTable(team: Team) {
    print(getTransitiveScheduleTable(team))
}