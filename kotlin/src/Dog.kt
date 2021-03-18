class Dog (val name: String, weight_param: Int, breed_param: String) {
    init {
        println("Dog $name has been created.")
    }
    var activities = arrayOf("Walks")
    private val breed = breed_param.toUpperCase()
    init {
        println("The breed is $breed.")
    }
    var weight = weight_param
        set(value) {
            if (value > 0) field = value
        }
    val weightInKgs: Double
        get() = weight / 2.2
    fun bark() {
        if (weight < 20) println("Yip!") else println("Woof!")
    }
}

fun main(args: Array<String>) {
    val myDog = Dog("Frido", 70, "Mixed")
    myDog.bark()
    myDog.weight = 75
    println("Weight is: ${myDog.weightInKgs} kg.")
    myDog.weight = -2
    println("Weight is: ${myDog.weight} lbs.")
    myDog.activities = arrayOf("Walks", "Fetching balls", "Frisbee")
    for (item in myDog.activities) println("My dog ${myDog.name} enjoys $item.")
    val dogs = arrayOf(Dog("Kelpie", 20, "Westie"), Dog("Ripper", 10, "Poodle"))
    dogs[1].bark()
    dogs[1].weight = 15
    println("Weight for ${dogs[1].name} is ${dogs[1].weight}")
}