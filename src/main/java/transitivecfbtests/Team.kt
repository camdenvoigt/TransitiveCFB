package transitivecfb

import com.opencsv.bean.CsvBindByPosition

class Team {

    @CsvBindByPosition(position = 0)
    var id: Int = 0

    @CsvBindByPosition(position = 1)
    var name: String = ""

    @CsvBindByPosition(position = 2)
    var conference: String = ""
        set(value) {
            if (value.endsWith(")")) {
                val i = value.indexOf("(")
                val conf = value.subSequence(0, i - 1)
                val div = value.subSequence(i + 1, value.length - 1)
                this.division = div.toString()
                field = conf.toString()
            } else {
                field = value
            }
        }

    var division: String? = null

    @CsvBindByPosition(position = 3)
    var overallWins: Int = 0

    @CsvBindByPosition(position = 4)
    var overalLosses: Int = 0

    @CsvBindByPosition(position = 6)
    var confWins: Int = 0

    @CsvBindByPosition(position = 7)
    var confLosses: Int = 0

    var schedule: ArrayList<Game> = ArrayList()

    var transOverallWins: Int = 0
    var transOverallLosses: Int = 0
    var transConfWins: Int = 0
    var transConfLosses: Int = 0
    var transSchedule: ArrayList<Game> = ArrayList()

    var marked: Boolean = false

    /* Constructors */
    constructor() {}

    constructor(id: Int, name: String) {
        this.id = id
        this.name = name
    }

    /* Public Helpers */

    /*
        Return game for a given week if there is no game return null
     */
    fun getGame(week: Int): Game? {
        schedule.map {
            if (it.week == week) {
                return it
            }
        }

        return null
    }

    /*
        Returns true if given team is in the same conference as the other
     */
    fun sameConference(otherTeam: Team?): Boolean {
        if (otherTeam == null) {
            return false
        }

        if (this.conference == otherTeam.conference) {
            return true
        }

        return false
    }

    /*
        Returns the advantage in common opponents that this has against the other team
     */
    fun commonOpponentScore(otherTeam: Team): Int {
        if (this.transSchedule.isEmpty() || otherTeam.transSchedule.isEmpty()) {
            return 0
        }
        var sum = 0

        //TODO: Improve this algorithm

        // For each game in this teams transitive schedule
        this.transSchedule.forEach { thisGame ->

            // Check against every game in the other teams transitive schedule
            otherTeam.transSchedule.forEach { thatGame ->

                // If they played the same opponent that wasn't FCS
                if (thisGame.getOpponentName(this) != "FCS" && thisGame.getOpponentName(this) == thatGame.getOpponentName(otherTeam)) {

                    // Check if the result was different and change sum
                    if (thisGame.isTeam1(this) && !thatGame.isTeam1(otherTeam)) {
                        // This team won but the other team lost
                        sum += 1
                    } else if (!thisGame.isTeam1(this) && thatGame.isTeam1(otherTeam)) {
                        // This team lost but the other team won
                        sum -= 1
                    }
                }
            }
        }

        return sum;
    }

    /*
        Gets the overall record of the team without transitive property
     */
    fun getRecord(): String {
        return "$overallWins - $overalLosses"
    }

    /*
        Gets the overall record of the team with transitive property
     */
    fun getTransRecord(): String {
        return "$transOverallWins - $transOverallLosses"
    }

    /*
        Gets the conference record of the team without transitive property
     */
    fun getConfRecord(): String {
        return "$confWins - $confLosses"
    }

    /*
        Gets the conference record of the team with transitive property
     */
    fun getTransConfRecord(): String {
        return "$transConfWins - $transConfLosses"
    }

    fun printTransativeSchedule() {
        val headers = "| team1Id |         team1          | team2Id |         team2          | code |"
        val divider = "+---------+------------------------+---------+------------------------+------+"
        println(divider)
        println(headers)
        println(divider)
        for (game in transSchedule) {
            val formatString = "|   %-3d   | %-22s |   %-3d   | %-22s |  %+2d  |"
            if (game.isTeam1(this)) {
                println(String.format(formatString, game.team1Id, game.team1, game.team2Id, game.team2, game.transitiveCode))
            } else {
                println(String.format(formatString, game.team2Id, game.team2, game.team1Id, game.team1, -game.transitiveCode))
            }
            //println(String.format(formatString, game.team1Id, game.team1, game.team2Id, game.team2, game.transitiveCode))
        }
        println(divider)
    }

    /* Overrides */

    override fun toString(): String {
        return "Team(id=$id, " +
                "name=$name, " +
                "conference=$conference, " +
                "division=$division,\n" +
                "record= $overallWins - $overalLosses, " +
                "conf record= $confWins - $confLosses, " +
                "schedule= $schedule\n" +
                "tran record= $transOverallWins - $transOverallLosses, " +
                "tran conf record= $transConfWins - $transConfLosses, " +
                "trans schedule= $transSchedule)"
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Team || this.id != other.id || this.name != other.name) {
            return false
        }

        return true
    }
}
