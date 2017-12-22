package transitivecfb

import com.opencsv.bean.CsvBindByName

class Game {

    @CsvBindByName(column = "Rk", required = true)
    var id: Int = 0

    @CsvBindByName(column = "Wk", required = true)
    var week: Int = 0

    var winnerId: Int? = null

    @CsvBindByName(column = "Winner", required = true)
    var winner: String? = null
        set(value) {
            if (value is String && value.startsWith("(")) {
                val i = value.indexOf(")")
                val newVal = value.substring(i + 2)
                field = sanitizeNames(newVal)
            } else if (value == null) {
                field = null
            } else {
                field = sanitizeNames(value)
            }
        }

    var loserId: Int? = null

    @CsvBindByName(column = "Loser", required = true)
    var loser: String? = null
        set(value) {
            if (value is String && value.startsWith("(")) {
                val i = value.indexOf(")")
                val newVal = value.substring(i + 2)
                field = sanitizeNames(newVal)
            } else if (value == null) {
                field = null
            } else {
                field = sanitizeNames(value)
            }
        }

    var transitive: Boolean = false
    var transitiveCode: Int = 0

    /* Constructors */

    constructor() {}

    constructor(id: Int = -1, week: Int = 1, team1Id: Int? = 0, team1: String?, team2Id: Int? = 0, team2: String?,
                transitive: Boolean, transitiveCode: Int) {
        this.id = id
        this.week = week
        this.winnerId = team1Id
        this.winner = team1
        this.loserId = team2Id
        this.loser = team2
        this.transitive = transitive
        this.transitiveCode = transitiveCode
    }

    /* Public Helpers */

    /*
        Returns whether the given team is winner for this game
     */
    fun isTeam1(team: Team): Boolean {
        if (team.name == winner) {
            return true
        }

        return false
    }

    /*
        Gets the name of the opponent of the given team in this game
     */
    fun getOpponentName(team: Team): String {
        if (isTeam1(team)) {
            return loser!!
        } else {
            return winner!!
        }
    }

    /* Private Helpers */

    /*
        Cleans up names for teams with abbreviations in the schools file
    */
    private fun sanitizeNames(name: String): String {
        when (name) {
            "Central Florida" -> return "UCF"
            "Louisiana State" -> return "LSU"
            "Mississippi" -> return "Ole Miss"
            "Pittsburgh" -> return "Pitt"
            "Southern California" -> return "USC"
            "Southern Methodist" -> return "SMU"
            "Texas-El Paso" -> return "UTEP"
            "Texas-San Antonio" -> return "UTSA"
        }

        return name
    }

    /* Overrides */

    override fun toString(): String {
        return "Game(id=$id, " +
                "week=$week, " +
                "winnerId=$winnerId, " +
                "winner=$winner, " +
                "loserId=$loserId, " +
                "loser=$loser)"
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Game || this.id != other.id || this.week != other.week) {
            return false
        }

        return true
    }

}
