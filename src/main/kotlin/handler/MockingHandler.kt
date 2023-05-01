package handler

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class OngoingStub(
    val method: Method?,
    val parameters: Array<out Any?>
)

class Stub(
    val method: Method?,
    val parameters: Array<out Any?>,
    val result: Any?
)

class MockingHandler : InvocationHandler {
    private var ongoingStub: OngoingStub? = null
    val stubs = arrayListOf<Stub>()

    private val fallback = hashMapOf(
        Int::class.java to 0,
        Long::class.java to 0L,
        Float::class.java to 0F,
        Double::class.java to 0.0,
        Boolean::class.java to false,
        List::class.java to emptyList<Any>()
    )

    override fun invoke(proxy: Any?, method: Method?, parameters: Array<out Any>?): Any? {
        ongoingStub = OngoingStub(method, parameters ?: emptyArray())
        return fallback[method?.returnType]
    }

    fun <R : Any?> reportResult(result: R) {
        ongoingStub?.apply {
            stubs += Stub(method, parameters, result)
        }
        ongoingStub = null
    }
}