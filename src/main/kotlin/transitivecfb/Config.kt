package transitivecfb

class Config {

    var year: String = "2017"
        set(value) {
            schoolFile = "fbs_schools_$value.csv"
            resultsFile = "fbs_results_$value.csv"
            inputLocation = "./resources/$value/"
            field = value
        }
    var inputLocation: String = "./resources/$year/"
    var schoolFile: String = "fbs_schools_$year.csv"
    var resultsFile: String = "fbs_results_$year.csv"
    var outputLocation: String = "./resources/output"
    var outputType: String = "print"
    var schoolOutputs: ArrayList<String> = ArrayList<String>()

    fun schoolFileLocation(): String {
        return inputLocation + schoolFile
    }

    fun resultsFileLocation(): String {
        return inputLocation + resultsFile
    }

    fun handleTag(tag: String, value: String) {
        when (tag) {
            "-o" -> outputType = value
            "-y" -> year = value
            "-l" -> inputLocation = value
            "-m" -> outputLocation = value
            "-x" -> schoolOutputs = parseSchools(value)
        }
    }

    private fun parseSchools(value: String) : ArrayList<String> {
        return ArrayList(value.split(","))
    }
}
