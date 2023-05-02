typealias Call<T, R> = T.() -> R
typealias Returns<R> = () -> R
typealias KotlinStubs<T> = ArrayList<KotlinStub<T, Any?>>

data class KotlinStub<T, R>(
    val call: Call<T, R>,
    val returns: Returns<R>
)

interface Register<T> {
    fun <R> every(call: Call<T, R>): PartialStub<R>
}

class RegisterImpl<T>(
    private val kotlinStubs: KotlinStubs<T> = arrayListOf()
) : Register<T> {
    override fun <R> every(call: Call<T, R>): PartialStub<R> =
        PartialStubImpl(call, kotlinStubs)
}

interface PartialStub<R> {
    infix fun returns(returns: Returns<R>)
}

internal class PartialStubImpl<T, R>(
    private val call: Call<T, R>,
    private val kotlinStubs: KotlinStubs<T> = arrayListOf(),
) : PartialStub<R> {
    override fun returns(returns: Returns<R>) {
        kotlinStubs += KotlinStub(call, returns)
    }
}

interface Verifier<T> {
    fun <R> times(times: Int, call: T.() -> R)
}