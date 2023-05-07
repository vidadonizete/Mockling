object Default {
    val BY_JAVA_CLASS = hashMapOf(
        Char::class.java to Char.MIN_VALUE,
        Byte::class.java to Byte.MIN_VALUE,
        Int::class.java to Int.MIN_VALUE,
        Long::class.java to Long.MIN_VALUE,
        Float::class.java to Float.MIN_VALUE,
        Double::class.java to Double.MIN_VALUE,
        Boolean::class.java to false,
        List::class.java to emptyList<Any>(),
        Unit::class.java to Unit,
        String::class.java to String
    )
}