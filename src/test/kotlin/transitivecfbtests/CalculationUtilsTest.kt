package transitivecfbtests

import org.junit.Before
import org.junit.Test
import transitivecfb.Game
import kotlin.test.*

class CalculationUtilsTest {

    private var teams = transitivecfb.parseData("2017")

    @Before
    fun setUp() {
        transitivecfb.calculateTransitiveResults(teams)
    }

    @Test
    fun parseData() {
        val parseTeams = transitivecfb.parseData("2016")

        assertNotNull(parseTeams, "Parse Data returns null list")
        for (team in parseTeams) {
            assertNotNull(team.schedule, "Team schedule is Null")
            for (game in team.schedule) {
                assertNotNull(game.winnerId, "winnerId Null")
                if (game.winnerId == 0) {
                    assertEquals("FCS", game.winner, "winner not equal to FCS when id of 0")
                }

                assertNotNull(game.loserId, "loserId Null")
                if (game.loserId == 0) {
                    assertEquals("FCS", game.loser, "loser not equal to FCS when id of 0")
                }
            }
        }
    }

    @Test
    fun getOtherTeam() {
        val clemson = teams.get(0)
        val ncstate = teams.get(1)
        val charlotte = teams.get(56)

        // get winner non FCS
        var result = transitivecfb.getOtherTeam(clemson.schedule.get(8), clemson, teams)
        assertEquals(ncstate, result, "Other team was not NCState")

        // get loser non FCS
        result = transitivecfb.getOtherTeam(ncstate.schedule.get(8), ncstate, teams)
        assertEquals(clemson, result, "Other team was no Clemson")

        // get loser FCS
        result = transitivecfb.getOtherTeam(ncstate.schedule.get(2), ncstate, teams)
        assertNull(result, "Other team was not null")

        // get winner FCS
        result = transitivecfb.getOtherTeam(charlotte.schedule.get(2), charlotte, teams)
        assertNull(result, "Other team was not null")
    }

    @Test
    fun compareTeams() {
        val clemson = teams.get(0)
        val ncstate = teams.get(1)
        val bc = teams.get(3)
        val charlotte = teams.get(56)

        // FCS Loss
        var result = transitivecfb.compareTeams(charlotte, null, charlotte.schedule.get(2))
        assertEquals(-1, result, "Wrong Compare Result")

        // FCS Win
        result = transitivecfb.compareTeams(ncstate, null, ncstate.schedule.get(2))
        assertEquals(1, result, "Wrong Compare Result")

        // Regular Win
        result = transitivecfb.compareTeams(clemson, bc, clemson.schedule.get(3))
        assertEquals(1, result, "Wrong Compare Result")

        // Regular Loss
        result = transitivecfb.compareTeams(bc, clemson, bc.schedule.get(3))
        assertEquals(-1, result, "Wrong Compare Result")

        // Transitive Win
        result = transitivecfb.compareTeams(ncstate, clemson, ncstate.transSchedule.get(8))
        assertEquals(2, result, "Wrong Compare Result")

        // Transitive Loss
        result = transitivecfb.compareTeams(clemson, ncstate, clemson.transSchedule.get(8))
        assertEquals(-2, result, "Wrong Compare Result")
    }

    @Test
    fun updateTeams() {
        val clemson = teams.get(0)
        val ncstate = teams.get(1)

        // Trans code is 2 and there are two FBS teams
        val game2 = Game(2000, 28, 0, "Clemson", 1, "North Carolina State", false, 0)
        transitivecfb.updateTeams(clemson, ncstate, game2, 2)
        val transGame2 = clemson.transSchedule.lastOrNull()
        assertNotNull(transGame2)
        assertEquals(-2000, transGame2!!.id)
        assertEquals(28, transGame2.week)
        assertEquals(1, transGame2.winnerId)
        assertEquals("Clemson", transGame2.winner)
        assertEquals(2, transGame2.loserId)
        assertEquals("North Carolina State", transGame2.loser)
        assertTrue(transGame2.transitive)
        assertEquals(2, transGame2.transitiveCode)

        // Trans code is 1 and there are two FBS teams
        val game3 = Game(2001, 29, 0, "Clemson", 1, "North Carolina State", false, 0)
        transitivecfb.updateTeams(clemson, ncstate, game3, 1)
        val transGame3 = clemson.transSchedule.lastOrNull()
        assertNotNull(transGame3)
        assertEquals(-2001, transGame3!!.id)
        assertEquals(29, transGame3.week)
        assertEquals(1, transGame3.winnerId)
        assertEquals("Clemson", transGame3.winner)
        assertEquals(2, transGame3.loserId)
        assertEquals("North Carolina State", transGame3.loser)
        assertTrue(transGame3.transitive)
        assertEquals(1, transGame3.transitiveCode)

        // Trans code is 1 and there is an FCS team for loser
        val game4 = Game(2002, 30, 0, "Clemson", 0, "FCS", false, 0)
        transitivecfb.updateTeams(clemson, null, game4, 1)
        val transGame4 = clemson.transSchedule.lastOrNull()
        assertNotNull(transGame4)
        assertEquals(-2002, transGame4!!.id)
        assertEquals(30, transGame4.week)
        assertEquals(1, transGame4.winnerId)
        assertEquals("Clemson", transGame4.winner)
        assertEquals(0, transGame4.loserId)
        assertEquals("FCS", transGame4.loser)
        assertTrue(transGame4.transitive)
        assertEquals(1, transGame4.transitiveCode)

        // Trans code is -2 and there is two FBS teams
        val game5 = Game(2003, 31, 0, "Clemson", 1, "North Carolina State", false, 0)
        transitivecfb.updateTeams(clemson, ncstate, game5, -2)
        val transGame5 = clemson.transSchedule.lastOrNull()
        assertNotNull(transGame5)
        assertEquals(-2003, transGame5!!.id)
        assertEquals(31, transGame5.week)
        assertEquals(2, transGame5.winnerId)
        assertEquals("North Carolina State", transGame5.winner)
        assertEquals(1, transGame5.loserId)
        assertEquals("Clemson", transGame5.loser)
        assertTrue(transGame5.transitive)
        assertEquals(2, transGame5.transitiveCode)

        // Trans code is -1 and there is two FBS teams
        val game6 = Game(2004, 32, 0, "Clemson", 1, "North Carolina State", false, 0)
        transitivecfb.updateTeams(clemson, ncstate, game6, -1)
        val transGame6 = clemson.transSchedule.lastOrNull()
        assertNotNull(transGame5)
        assertEquals(-2004, transGame6!!.id)
        assertEquals(32, transGame6.week)
        assertEquals(2, transGame6.winnerId)
        assertEquals("North Carolina State", transGame6.winner)
        assertEquals(1, transGame6.loserId)
        assertEquals("Clemson", transGame6.loser)
        assertTrue(transGame6.transitive)
        assertEquals(1, transGame6.transitiveCode)

        // Trans code is -1 and there is an FCS team for loser
        val game7 = Game(2005, 33, 0, "Clemson", 0, "FCS", false, 0)
        transitivecfb.updateTeams(clemson, null, game7, -1)
        val transGame7 = clemson.transSchedule.lastOrNull()
        assertNotNull(transGame5)
        assertEquals(-2005, transGame7!!.id)
        assertEquals(33, transGame7.week)
        assertEquals(0, transGame7.winnerId)
        assertEquals("FCS", transGame7.winner)
        assertEquals(1, transGame7.loserId)
        assertEquals("Clemson", transGame7.loser)
        assertTrue(transGame7.transitive)
        assertEquals(1, transGame7.transitiveCode)
    }

    @Test
    fun translateTransCode() {
        var result = transitivecfb.translateTransCode(2)
        assertEquals(2, result)

        result = transitivecfb.translateTransCode(1)
        assertEquals(1, result)

        result = transitivecfb.translateTransCode(-1)
        assertEquals(1, result)

        result = transitivecfb.translateTransCode(-2)
        assertEquals(2, result)
    }
}