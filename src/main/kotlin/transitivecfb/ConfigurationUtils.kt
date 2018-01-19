package transitivecfb

/*
    -o,O output type
    -y,Y year
    -l,L input location (Should be a directory)
    -m,M output location
    -x,X comma delimited list of schools to print results for
 */

fun getConfiguration(args: Array<String>): Config {
    val config = Config()

    if (args.size > 1) {
        var i = 0
        while (i < args.size) {
            val tag = args[i].toLowerCase()
            val value = args[i+1]

            if (!isTag(tag)) {
                throw IllegalArgumentException()
            }

            config.handleTag(tag, value)

            i += 2
        }
    }

    return config
}

fun isTag(arg: String): Boolean {
    when (arg) {
        "-o" -> return true
        "-y" -> return true
        "-l" -> return true
        "-r" -> return true
        "-s" -> return true
        "-x" -> return true
    }
    return false
}
