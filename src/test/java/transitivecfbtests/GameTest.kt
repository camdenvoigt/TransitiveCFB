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
        game.team1 = null
        assertEquals(null, game.team1, "Team1 wrong set with null")

        game.team1 = "Louisville"
        assertEquals("Louisville", game.team1, "Team1 wrong set with no ranking")

        game.team1 = "(19) Louisville"
        assertEquals("Louisville", game.team1, "Team1 wrong set with ranking")
    }

    @Test
    fun setTeam2() {
        game.team2 = null
        assertEquals(null, game.team2, "Team2 wrong set with null")

        game.team2 = "Memphis"
        assertEquals("Memphis", game.team2, "Team2 wrong set with no ranking")

        game.team2 = "(19) Memphis"
        assertEquals("Memphis", game.team2, "Team2 wrong set with ranking")
    }

    @Test
    fun sanitizeNames() {
        game.team1 = "Central Florida"
        assertEquals("UCF", game.team1, "Central Florida was not changed")

        game.team1 = "Louisiana State"
        assertEquals("LSU", game.team1, "Louisiana State was not changed")

        game.team1 = "Mississippi"
        assertEquals("Ole Miss", game.team1, "Mississippi was not changed")

        game.team1 = "Pittsburgh"
        assertEquals("Pitt", game.team1, "Pittsburgh was not changed")

        game.team1 = "Southern California"
        assertEquals("USC", game.team1, "Southern California was not changed")

        game.team1 = "Southern Methodist"
        assertEquals("SMU", game.team1, "Southern Methodist was not changed")

        game.team1 = "Texas-El Paso"
        assertEquals("UTEP", game.team1, "Texas-El Paso was not changed")

        game.team1 = "Texas-San Antonio"
        assertEquals("UTSA", game.team1, "Texas-San Antonio was not changed")

        game.team1 = "Louisville"
        assertEquals("Louisville", game.team1, "Louisville was changed")
    }

    @Test
    fun isTeam1() {
        val team1 = Team(15, "Louisville")
        var result = game.isTeam1(team1)
        assertTrue(result, "Team1 was not team1")

        val team2 = Team(45, "Memphis")
        result = game.isTeam1(team2)
        assertFalse(result, "Team2 was team1")
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
        val expected = "Game(id=-18, week=2, team1Id=15, team1=Louisville, team2Id=45, team2=Memphis)"
        val result = game.toString()
        assertEquals(expected, result, "To string does not get expected result")
    }

    @Test
    fun equals() {
        val game2 = Game(-18, 2, 15, "Louisville", 45, "Memphis", true, 2)

        assertTrue(game.equals(game2), "Game did not equal Game 2")

        game2.team1Id = 55
        assertTrue(game.equals(game2), "Game did not equal Game 2")

        game2.team1 = "Iowa State"
        assertTrue(game.equals(game2), "Game did not equal Game 2")

        game2.team2Id = 67
        assertTrue(game.equals(game2), "Game did not equal Game 2")

        game2.team2 = "Oklahoma"
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
