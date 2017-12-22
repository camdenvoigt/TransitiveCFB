package transitivecfbtests

import com.opencsv.bean.CsvToBeanBuilder
import java.io.FileReader

/*
 Parses csv files for the given years and constructs a list of teams and their schedules
 */
fun parseData(year: String): List<Team> {
    var teams: List<Team> = CsvToBeanBuilder<Team>(FileReader("./resources/$year/fbs_schools_$year.csv")).withType(Team::class.java).build().parse()
    var games: List<Game> = CsvToBeanBuilder<Game>(FileReader("./resources/$year/fbs_results_$year.csv")).withType(Game::class.java).build().parse()

    //TODO: make this better if possible
    for (game in games) {
        for (team in teams) {
            if (game.winner == team.name) {
                game.winnerId = team.id
                team.schedule.add(game)
            } else if (game.loser == team.name) {
                game.loserId = team.id
                team.schedule.add(game)
            }
        }
        if (game.winnerId == null) {
            game.winnerId = 0
            game.winner = "FCS"
        } else if (game.loserId == null) {
            game.loserId = 0
            game.loser = "FCS"
        }
    }

    return teams
}

/*
    Algorithm for calculating the transitive results for a given set of teams
 */
fun calculateTransitiveResults(teams: List<Team>, depth: Int = 1) {
    for (i in 1..14) {
        for (team in teams) {
            team.marked = false
        }

        for (team in teams) {
            if (team.marked) continue
            val game = team.getGame(i) ?: continue
            val otherTeam = getOtherTeam(game, team, teams)
            val compareValue = compareTeams(team, otherTeam, game)
            updateTeams(team, otherTeam, game, compareValue)
        }
    }
}

/*
    Get the opposing team from a game
 */
fun getOtherTeam(game: Game, team: Team, teams: List<Team>): Team? {
    var team2: Team? = null

    if (game.isTeam1(team)) {
        if (game.loser == "FCS") {
            return null
        }
        val i = teams.indexOf(Team(game.loserId!!, game.loser!!))
        team2 = teams.get(i)
    } else {
        if (game.winner == "FCS") {
            return null
        }
        val i = teams.indexOf(Team(game.winnerId!!, game.winner!!))
        team2 = teams.get(i)
    }

    return team2
}

/*
    Compares two teams for a given game.
    Return
        2 if team 1 wins by transitive property
        1 if team 1 wins by playing game
        0 if there is a tie
        -1 if team 2 wins by playing game
        -2 if team 2 wins by transitive property
 */
fun compareTeams(team1: Team, team2: Team?, game: Game, depth: Int = 1): Int {
    // Team2 is an FCS Team
    if (team2 == null) {
        if (game.isTeam1(team1)) {
            // Team 1 wins by playing game
            return 1
        } else {
            // Team 2 wins by playing game
            return -1
        }
    }

    val commonOpponentScore = team1.commonOpponentScore(team2, game.week)

    if (commonOpponentScore > 0) {
        // Team 1 wins by transitive property
        return 2
    } else if (commonOpponentScore < 0) {
        // Team 2 wins by transitive property
        return -2
    } else {
        if (game.isTeam1(team1)) {
            // Team 1 wins by playing game
            return 1
        } else {
            // Team 2 wins by playing game
            return -1
        }
    }
}

/*
    Update the transitive results for both teams
 */
fun updateTeams(team1: Team, team2: Team?, oldGame: Game, transCode: Int) {
    var winnerName: String = ""
    var loserName: String = ""
    var winnerId: Int = 0
    var loserId: Int = 0

    if (transCode > 0) {
        // Winner stuff
        winnerName = team1.name
        winnerId = team1.id
        team1.transOverallWins += 1
        if (team1.sameConference(team2)) {
            team1.transConfWins += 1
        }
        team1.marked = true

        // Loser stuff
        if (team2 != null) {
            loserName = team2.name
            loserId = team2.id
            team2.transOverallLosses += 1
            if (team2.sameConference(team1)) {
                team2.transConfLosses += 1
            }
            team2.marked = true
        } else {
            loserName = "FCS"
        }
    } else if (transCode < 0) {
        // Winner Stuff
        if (team2 != null) {
            winnerName = team2.name
            winnerId = team2.id
            team2.transOverallWins += 1
            if (team2.sameConference(team1)) {
                team2.transConfWins += 1
            }
            team2.marked = true
        } else {
            winnerName = "FCS"
        }

        // Loser Stuff
        loserName = team1.name
        loserId = team1.id
        team1.transOverallLosses += 1
        if (team1.sameConference(team2)) {
            team1.transConfLosses += 1
        }
        team1.marked = true
    } else {
        println("BAD TRANS CODE")
        return
    }

    val game = Game(-oldGame.id, oldGame.week, winnerId, winnerName, loserId, loserName, true, translateTransCode(transCode))
    team1.transSchedule.add(game)
    team2?.transSchedule?.add(game)
}

/*
    Translates codes to make them more usable for saved games
 */
fun translateTransCode(code: Int): Int {
    when (code) {
        2 -> return 2
        1 -> return 1
        -1 -> return 1
        -2 -> return 2
    }
    return 0
}

/*
    Prints results of all teams in a formated table in console
 */
fun printResults(teams: List<Team>) {
    val divider = "+-----+------------------------+-----------------+-------------------+-------------------+------------------------------+"
    val header = "| id  |          Team          | Original Record | Transitive Record | Conference Record | Transitive Conference Record |"
    println(divider)
    println(header)
    println(divider)
    for (team in teams) {
        val formatString = "| %-3d | %-22s |      %-6s     |       %-6s      |       %-6s      |            %-6s            |"
        println(String.format(formatString, team.id, team.name, team.getRecord(), team.getTransRecord(), team.getConfRecord(), team.getTransConfRecord()))
    }
    println(divider)
}
