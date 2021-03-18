fun main () {
    val array = arrayOf("Wolf", "Rabbit", null)
    for (item in array) item?.let { println(it) }
}