fun main(args: Array<String>) {
    val options = arrayOf("Rock", "Paper", "Scissors")
    val gameChoice = getGameChoice(options)
    val userChoice = getUserChoice(options)
    printResult(userChoice, gameChoice)
}

fun getGameChoice(options: Array<String>) = options[(0..2).random()]

fun getUserChoice(options: Array<String>): String {
    do {
        println("Please enter one of the following: ${options.joinToString(", ")}.")
        val userInput = readLine()
        if (userInput != null && userInput in options) return userInput
        else println("You must enter a valid input!")
    } while (true)
}

fun printResult(userChoice: String, gameChoice: String) {
    val result: String = if (userChoice == gameChoice) "It's a tie."
    else if ((userChoice == "Rock" && gameChoice == "Scissors")
            || (userChoice == "Scissors" && gameChoice == "Paper")
            || (userChoice == "Paper" && gameChoice == "Rock")) "You win!"
    else "Game wins!"
    println(result)
}