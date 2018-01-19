package transitivecfb

import com.opencsv.CSVWriter
import java.io.FileWriter

/* Config Caller */

fun output(config: Config, teams: List<Team>) {
    when(config.outputType) {
        "print" -> printFullResultsTable(teams)
        "csv" -> fullResultsCSV(teams)
    }

    val printTeams = ArrayList<Team>()
    for (teamName in config.schoolOutputs) {
        for (team in teams) {
            if (team.name == teamName) {
                printTeams.add(team)
            }
        }
    }

    if(config.outputType == "csv") {
        transitiveScheduleTableCSV(printTeams)
    } else {
        for (team in printTeams) {
            when(config.outputType) {
                "print" -> printTransitiveScheduleTable(team)
            }
        }
    }

    if (config.outputType == "csv") {
        print("CSV files are located in ./resources/output/")
    }
}

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

/* CSV FUNCTIONS */

fun fullResultsCSV(teams: List<Team>) {
    val headers = "id,Team,Original Record,Transitive Record, Conference Record, Transitive Conference Record"
    val writer = CSVWriter(FileWriter("./resources/output/fullTransitiveResults.csv"))

    writer.writeNext(headers.split(",").toTypedArray())
    for (team in teams) {
        writer.writeNext(team.getCSVData().split(",").toTypedArray())
    }

    writer.close()
}

fun transitiveScheduleTableCSV(team: Team, append: Boolean) {
    val headers = "teamId,team,oppId,opponent,code"
    val writer = CSVWriter(FileWriter("./resources/output/transitiveSchedules.csv", append))

    writer.writeNext(headers.split(",").toTypedArray())
    for (game in team.transSchedule) {
        val formatString = "%d,%s,%d,%s,%d"
        if (game.isTeam1(team)) {
            writer.writeNext(String.format(formatString, game.winnerId, game.winner, game.loserId, game.loser, game.transitiveCode).split(",").toTypedArray())
        } else {
            writer.writeNext(String.format(formatString, game.loserId, game.loser, game.winnerId, game.winner, -game.transitiveCode).split(",").toTypedArray())
        }
    }

    writer.close()
}

fun transitiveScheduleTableCSV(teams: List<Team>) {
    for ((i, team) in teams.withIndex()) {
        if (i == 0) {
            transitiveScheduleTableCSV(team, false)
        } else {
            transitiveScheduleTableCSV(team, true)
        }
    }
}
