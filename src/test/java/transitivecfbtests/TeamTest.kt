package transitivecfbtests

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
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

    fun setClemsonSchedule() {
        clemson.schedule.add(Game(1, 1, 15, "Clemson", 45, "Kent State", false,0))
        clemson.schedule.add(Game(2, 2, 15, "Clemson", 46, "Auburn", false,0))
        clemson.schedule.add(Game(3, 3, 15, "Clemson", 47, "Louisville", false,0))
        clemson.schedule.add(Game(4, 4, 15, "Clemson", 48, "Boston College", false,0))
        clemson.schedule.add(Game(5, 5, 15, "Clemson", 49, "Virginia Tech", false,0))
        clemson.schedule.add(Game(6, 6, 15, "Clemson", 50, "Wake Forest", false,0))
        clemson.schedule.add(Game(7, 7, 51, "Syracuse", 15, "Clemson", false,0))
        clemson.schedule.add(Game(8, 9, 15, "Clemson", 52, "Georgia Tech", false,0))
        clemson.schedule.add(Game(9, 10, 15, "Clemson", 53, "North Carolina State", false,0))
        clemson.schedule.add(Game(10, 11, 15, "Clemson", 54, "Florida State", false,0))
        clemson.schedule.add(Game(11, 12, 15, "Clemson", 0, "FCS", false,0))
        clemson.schedule.add(Game(12, 13, 15, "Clemson", 55, "South Carolina", false,0))

        clemson.transSchedule.add(Game(-1, 1, 15, "Clemson", 45, "Kent State", true,1))
        clemson.transSchedule.add(Game(-2, 2, 15, "Clemson", 46, "Auburn", true,1))
        clemson.transSchedule.add(Game(-3, 3, 15, "Clemson", 47, "Louisville", true,1))
        clemson.transSchedule.add(Game(-4, 4, 15, "Clemson", 48, "Boston College", true,1))
        clemson.transSchedule.add(Game(-5, 5, 15, "Clemson", 49, "Virginia Tech", true,1))
        clemson.transSchedule.add(Game(-6, 6, 15, "Clemson", 50, "Wake Forest", true,1))
        clemson.transSchedule.add(Game(-7, 7, 51, "Syracuse", 15, "Clemson", true,1))
        clemson.transSchedule.add(Game(-8, 9, 15, "Clemson", 52, "Georgia Tech", true,1))
        clemson.transSchedule.add(Game(-9, 10, 53, "North Carolina State", 15, "Clemson", true,2))
        clemson.transSchedule.add(Game(-10, 11, 15, "Clemson", 54, "Florida State", true,2))
        clemson.transSchedule.add(Game(-11, 12, 15, "Clemson", 0, "FCS", true,0))
        clemson.transSchedule.add(Game(-12, 13, 55, "South Carolina", 15, "Clemson", true,2))
    }

    fun setFSUSchedule() {

    }

    fun setISUSchedule() {

    }

    @Test
    fun getGame() {
        val game = Game(7, 7, 51, "Syracuse", 15, "Clemson", false,0)
        assertEquals(game, clemson.getGame(7), "Did not get correct game from getGame()")
    }

    @Test
    fun sameConference() {
        assertTrue(clemson.sameConference(fsu), "Clemson is not in same conference as FSU")
        assertFalse(clemson.sameConference(isu), "Clemson is in the same conference as ISU")
    }

    @Test
    fun commonOpponentScore() {

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