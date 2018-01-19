package transitivecfb

import java.io.FileNotFoundException
import java.lang.System.exit

fun main(args: Array<String>) {
    try {
        val config = getConfiguration(args)
        val teams = parseData(config)
        calculateTransitiveResults(teams)
        output(config, teams)
    } catch (exception: IllegalArgumentException) {
        print("Incorrect Argument")
        exit(-1)
    } catch (exception: FileNotFoundException) {
        print("Could Not find Needed file")
        exit(-1)
    }
}
