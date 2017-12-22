package transitivecfbtests

import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GameTest {

    var game = Game()

    @Before
    fun setUp() {
        game = Game(-18, 2, 15, "Louisville", 45, "Memphis", true, 2)
    }

    @Test
    fun setTeam1() {
        game.winner = null
        assertEquals(null, game.winner, "Team1 wrong set with null")

        game.winner = "Louisville"
        assertEquals("Louisville", game.winner, "Team1 wrong set with no ranking")

        game.winner = "(19) Louisville"
        assertEquals("Louisville", game.winner, "Team1 wrong set with ranking")
    }

    @Test
    fun setTeam2() {
        game.loser = null
        assertEquals(null, game.loser, "Team2 wrong set with null")

        game.loser = "Memphis"
        assertEquals("Memphis", game.loser, "Team2 wrong set with no ranking")

        game.loser = "(19) Memphis"
        assertEquals("Memphis", game.loser, "Team2 wrong set with ranking")
    }

    @Test
    fun sanitizeNames() {
        game.winner = "Central Florida"
        assertEquals("UCF", game.winner, "Central Florida was not changed")

        game.winner = "Louisiana State"
        assertEquals("LSU", game.winner, "Louisiana State was not changed")

        game.winner = "Mississippi"
        assertEquals("Ole Miss", game.winner, "Mississippi was not changed")

        game.winner = "Pittsburgh"
        assertEquals("Pitt", game.winner, "Pittsburgh was not changed")

        game.winner = "Southern California"
        assertEquals("USC", game.winner, "Southern California was not changed")

        game.winner = "Southern Methodist"
        assertEquals("SMU", game.winner, "Southern Methodist was not changed")

        game.winner = "Texas-El Paso"
        assertEquals("UTEP", game.winner, "Texas-El Paso was not changed")

        game.winner = "Texas-San Antonio"
        assertEquals("UTSA", game.winner, "Texas-San Antonio was not changed")

        game.winner = "Louisville"
        assertEquals("Louisville", game.winner, "Louisville was changed")
    }

    @Test
    fun isTeam1() {
        val team1 = Team(15, "Louisville")
        var result = game.isTeam1(team1)
        assertTrue(result, "Team1 was not winner")

        val team2 = Team(45, "Memphis")
        result = game.isTeam1(team2)
        assertFalse(result, "Team2 was winner")
    }

    @Test
    fun getOpponentName() {
        val team1 = Team(15, "Louisville")
        var result = game.getOpponentName(team1)
        assertEquals("Memphis", result, "Team1 did not receive correct opponent name")

        val team2 = Team(45, "Memphis")
        result = game.getOpponentName(team2)
        assertEquals("Louisville", result, "Team2 did not receive correct opponent name")
    }

    @Test
    fun testToString() {
        val expected = "Game(id=-18, week=2, winnerId=15, winner=Louisville, loserId=45, loser=Memphis)"
        val result = game.toString()
        assertEquals(expected, result, "To string does not get expected result")
    }

    @Test
    fun equals() {
        val game2 = Game(-18, 2, 15, "Louisville", 45, "Memphis", true, 2)

        assertTrue(game.equals(game2), "Game did not equal Game 2")

        game2.winnerId = 55
        assertTrue(game.equals(game2), "Game did not equal Game 2")

        game2.winner = "Iowa State"
        assertTrue(game.equals(game2), "Game did not equal Game 2")

        game2.loserId = 67
        assertTrue(game.equals(game2), "Game did not equal Game 2")

        game2.loser = "Oklahoma"
        assertTrue(game.equals(game2), "Game did not equal Game 2")

        game2.transitive = false
        assertTrue(game.equals(game2), "Game did not equal Game 2")

        game2.transitiveCode = 1
        assertTrue(game.equals(game2), "Game did not equal Game 2")

        game2.id = -22
        assertFalse(game.equals(game2), "Game was equal to Game 2")

        game2.week = 3
        game2.id = -18
        assertFalse(game.equals(game2), "Game was equal to Game 2")

        game2.id = -22
        assertFalse(game.equals(game2), "Game was equal to Game 2")
    }

}
