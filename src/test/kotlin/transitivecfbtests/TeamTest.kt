package transitivecfbtests

import org.junit.Test

import org.junit.Before
import transitivecfb.Game
import transitivecfb.Team
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TeamTest {

    var clemson = Team()
    var fsu = Team()
    var isu = Team()

    @Before
    fun setUp() {
        clemson = Team(15, "Clemson")
        clemson.conference = "ACC"
        clemson.division = "Atlantic"
        clemson.overallWins = 12
        clemson.overalLosses = 1
        clemson.confWins = 7
        clemson.confLosses = 1
        clemson.transOverallWins = 9
        clemson.transOverallLosses = 3
        clemson.transConfWins = 6
        clemson.transConfLosses = 2
        setClemsonSchedule()

        fsu = Team(16, "Florida State")
        fsu.conference = "ACC"
        fsu.division = "Atlantic"
        setFSUSchedule()

        isu = Team(35, "Iowa State")
        isu.conference = "Big 12"
        isu.division = null
        setISUSchedule()
    }

    private fun setClemsonSchedule() {
        clemson.schedule.add(Game(1, 1, 15, "Clemson", 45, "Kent State", false, 0))
        clemson.schedule.add(Game(2, 2, 15, "Clemson", 46, "Auburn", false, 0))
        clemson.schedule.add(Game(3, 3, 15, "Clemson", 47, "Louisville", false, 0))
        clemson.schedule.add(Game(4, 4, 15, "Clemson", 48, "Boston College", false, 0))
        clemson.schedule.add(Game(5, 5, 15, "Clemson", 49, "Virginia Tech", false, 0))
        clemson.schedule.add(Game(6, 6, 15, "Clemson", 50, "Wake Forest", false, 0))
        clemson.schedule.add(Game(7, 7, 51, "Syracuse", 15, "Clemson", false, 0))
        clemson.schedule.add(Game(8, 9, 15, "Clemson", 52, "Georgia Tech", false, 0))
        clemson.schedule.add(Game(9, 10, 15, "Clemson", 53, "North Carolina State", false, 0))
        clemson.schedule.add(Game(10, 11, 15, "Clemson", 16, "Florida State", false, 0))
        clemson.schedule.add(Game(11, 12, 15, "Clemson", 0, "FCS", false, 0))
        clemson.schedule.add(Game(12, 13, 15, "Clemson", 55, "South Carolina", false, 0))

        clemson.transSchedule.add(Game(-1, 1, 15, "Clemson", 45, "Kent State", true, 1))
        clemson.transSchedule.add(Game(-2, 2, 15, "Clemson", 46, "Auburn", true, 1))
        clemson.transSchedule.add(Game(-3, 3, 15, "Clemson", 47, "Louisville", true, 1))
        clemson.transSchedule.add(Game(-4, 4, 15, "Clemson", 48, "Boston College", true, 1))
        clemson.transSchedule.add(Game(-5, 5, 15, "Clemson", 49, "Virginia Tech", true, 1))
        clemson.transSchedule.add(Game(-6, 6, 15, "Clemson", 50, "Wake Forest", true, 1))
        clemson.transSchedule.add(Game(-7, 7, 51, "Syracuse", 15, "Clemson", true, 1))
        clemson.transSchedule.add(Game(-8, 9, 15, "Clemson", 52, "Georgia Tech", true, 1))
        clemson.transSchedule.add(Game(-9, 10, 53, "North Carolina State", 15, "Clemson", true, 2))
        clemson.transSchedule.add(Game(-10, 11, 15, "Clemson", 16, "Florida State", true, 2))
        clemson.transSchedule.add(Game(-11, 12, 15, "Clemson", 0, "FCS", true, 0))
        clemson.transSchedule.add(Game(-12, 13, 55, "South Carolina", 15, "Clemson", true, 2))
    }

    private fun setFSUSchedule() {
        fsu.schedule.add(Game(13, 1, 56, "Alabama", 16, "Florida State", false, 0))
        fsu.schedule.add(Game(14, 4, 53, "North Carolina State", 16, "Florida State", false, 0))
        fsu.schedule.add(Game(15, 5, 16, "Florida State", 50, "Wake Forest", false, 0))
        fsu.schedule.add(Game(16, 6, 57, "Miami (FL)", 16, "Florida State", false, 0))
        fsu.schedule.add(Game(15, 7, 16, "Florida State", 57, "Duke", false, 0))
        fsu.schedule.add(Game(16, 8, 47, "Louisville", 16, "Florida State", false, 0))
        fsu.schedule.add(Game(17, 9, 48, "Boston College", 16, "Florida State", false, 0))
        fsu.schedule.add(Game(18, 10, 16, "Florida State", 51, "Syracuse", false, 0))
        fsu.schedule.add(Game(10, 11, 15, "Clemson", 16, "Florida State", false, 0))
        fsu.schedule.add(Game(19, 12, 16, "Florida State", 0, "FCS", false, 0))
        fsu.schedule.add(Game(20, 13, 15, "Florida State", 58, "Florida", false, 0))
        fsu.schedule.add(Game(21, 14, 16, "Florida State", 0, "FCS", false, 0))

        fsu.transSchedule.add(Game(-13, 1, 56, "Alabama", 16, "Florida State", true, 1))
        fsu.transSchedule.add(Game(-14, 4, 53, "North Carolina State", 16, "Florida State", true, 1))
        fsu.transSchedule.add(Game(-15, 5, 16, "Florida State", 50, "Wake Forest", true, 1))
        fsu.transSchedule.add(Game(-16, 6, 57, "Miami (FL)", 16, "Florida State", true, 1))
        fsu.transSchedule.add(Game(-15, 7, 16, "Florida State", 57, "Duke", true, 1))
        fsu.transSchedule.add(Game(-16, 8, 47, "Louisville", 16, "Florida State", true, 1))
        fsu.transSchedule.add(Game(-17, 9, 48, "Boston College", 16, "Florida State", true, 1))
        fsu.transSchedule.add(Game(-18, 10, 16, "Florida State", 51, "Syracuse", true, 1))
        fsu.transSchedule.add(Game(-10, 11, 15, "Clemson", 16, "Florida State", true, 2))
        fsu.transSchedule.add(Game(-19, 12, 16, "Florida State", 0, "FCS", true, 1))
        fsu.transSchedule.add(Game(-20, 13, 15, "Florida State", 58, "Florida", true, 1))
        fsu.transSchedule.add(Game(-21, 14, 16, "Florida State", 0, "FCS", true, 1))
    }

    private fun setISUSchedule() {
        isu.schedule.add(Game(22, 1, 35, "Iowa State", 0, "FCS", false, 0))
        isu.schedule.add(Game(23, 2, 60, "Iowa", 35, "Iowa State", false, 0))
        isu.schedule.add(Game(24, 3, 35, "Iowa State", 59, "Akron", false, 0))
        isu.schedule.add(Game(25, 5, 61, "Texas", 35, "Iowa State", false, 0))
        isu.schedule.add(Game(26, 6, 35, "Iowa State", 62, "Oklahoma", false, 0))
        isu.schedule.add(Game(27, 7, 35, "Iowa State", 63, "Kansas", false, 0))
        isu.schedule.add(Game(28, 8, 35, "Iowa State", 64, "Texas Tech", false, 0))
        isu.schedule.add(Game(29, 9, 35, "Iowa State", 65, "Texas Christan", false, 0))
        isu.schedule.add(Game(30, 10, 66, "West Virginia", 35, "Iowa State", false, 0))
        isu.schedule.add(Game(31, 11, 67, "Oklahoma State", 35, "Iowa State", false, 0))
        isu.schedule.add(Game(32, 12, 35, "Iowa State", 68, "Baylor", false, 0))
        isu.schedule.add(Game(33, 13, 69, "Kansas State", 35, "Iowa State", false, 0))

        isu.transSchedule.add(Game(-22, 1, 35, "Iowa State", 0, "FCS", true, 1))
        isu.transSchedule.add(Game(-23, 2, 60, "Iowa", 35, "Iowa State", true, 1))
        isu.transSchedule.add(Game(-24, 3, 35, "Iowa State", 59, "Akron", true, 1))
        isu.transSchedule.add(Game(-25, 5, 61, "Texas", 35, "Iowa State", true, 1))
        isu.transSchedule.add(Game(-26, 6, 35, "Iowa State", 62, "Oklahoma", true, 1))
        isu.transSchedule.add(Game(-27, 7, 35, "Iowa State", 63, "Kansas", true, 1))
        isu.transSchedule.add(Game(-28, 8, 35, "Iowa State", 64, "Texas Tech", true, 1))
        isu.transSchedule.add(Game(-29, 9, 35, "Iowa State", 65, "Texas Christan", true, 1))
        isu.transSchedule.add(Game(-30, 10, 35, "Iowa State", 66, "West Virginia", true, 2))
        isu.transSchedule.add(Game(-31, 11, 67, "Oklahoma State", 35, "Iowa State", true, 1))
        isu.transSchedule.add(Game(-32, 12, 35, "Iowa State", 68, "Baylor", true, 2))
        isu.transSchedule.add(Game(-33, 13, 35, "Iowa State", 69, "Kansas State", true, 2))
    }

    @Test
    fun getGame() {
        val game = Game(7, 7, 51, "Syracuse", 15, "Clemson", false, 0)
        assertEquals(game, clemson.getGame(7), "Did not get correct game from getGame()")
    }

    @Test
    fun sameConference() {
        assertTrue(clemson.sameConference(fsu), "Clemson is not in same conference as FSU")
        assertFalse(clemson.sameConference(isu), "Clemson is in the same conference as ISU")
    }

    @Test
    fun commonOpponentScore() {
        val team1 = Team(1, "Nebraska")

        // Team1 empty schedule
        var result = clemson.commonOpponentScore(team1, 11)
        assertEquals(0, result, "Team with no transtransSchedule doesn't return 0")

        // Team2 empty schedule
        result = team1.commonOpponentScore(clemson, 11)
        assertEquals(0, result, "Team with no transSchedule doesn't return 0")

        // winner has a common opponent with an extra win
        result = clemson.commonOpponentScore(fsu, 11)
        assertEquals(1, result, "Clemson and FSU common opponent score is incorrect")

        // loser has a common opponent with an extra loss
        result = fsu.commonOpponentScore(clemson, 11)
        assertEquals(-1, result, "FSU and Clemson common opponent score is incorrect")

        // winner has no common opponents with loser
        result = clemson.commonOpponentScore(isu, 11)
        assertEquals(0, result, "Clemson and ISU common opponent score incorrect")

        // loser has no common opponents with winner
        result = isu.commonOpponentScore(clemson, 11)
        assertEquals(0, result, "ISU and Clemson common opponent score incorrect")

        // loser and winner have same record against common opponents
        result = clemson.commonOpponentScore(fsu, 6)
        assertEquals(0, result, "Clemson and FSU common opponent score incorrect")

        result = fsu.commonOpponentScore(clemson, 6)
        assertEquals(0, result, "FSU and Clemson common oppoent score incorrect")
    }

    @Test
    fun getRecord() {
        assertEquals("12 - 1", clemson.getRecord())
    }

    @Test
    fun getTransRecord() {
        assertEquals("9 - 3", clemson.getTransRecord())
    }

    @Test
    fun getConfRecord() {
        assertEquals("7 - 1", clemson.getConfRecord())
    }

    @Test
    fun getTransConfRecord() {
        assertEquals("6 - 2", clemson.getTransConfRecord())
    }

    @Test
    fun testToString() {
        val expected = "Team(id=15, name=Clemson, conference=ACC, division=Atlantic,\nrecord= 12 - 1, conf record= 7 - 1," +
                " tran record= 9 - 3, tran conf record= 6 - 2)"
        assertEquals(expected, clemson.toString(), "Team toString does not return expected string")
    }

    @Test
    fun equals() {
        val team = Team(15, "Clemson")
        assertTrue(clemson.equals(team))
    }

}