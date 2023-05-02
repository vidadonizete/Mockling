package handler

import Call
import Default
import Returns
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

    override fun invoke(proxy: Any?, method: Method?, parameters: Array<out Any>?): Any? {
        ongoingStub = OngoingStub(method, parameters ?: emptyArray())
        return Default.BY_JAVA_CLASS[method?.returnType]
    }

    fun <T, R> mock(call: Call<T, R>, returns: Returns<R>, type: T) {
        call(type)
        ongoingStub?.apply {
            stubs += Stub(method, parameters, returns())
        }
        ongoingStub = null
    }
}