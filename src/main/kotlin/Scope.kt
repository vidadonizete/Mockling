typealias Call<T, R> = T.() -> R
typealias Result<R> = () -> R

interface Register<T> {
    fun <R> every(call: Call<T, R>): PartialStub<R>
}

class RegisterImpl<T>(
    private val stubManager: StubManager<T>
) : Register<T> {
    override fun <R> every(call: Call<T, R>) =
        PartialStubImpl(call, stubManager)
}

interface PartialStub<R> {
    infix fun returns(result: Result<R>)
}

class PartialStubImpl<T, R>(
    private val call: Call<T, R>,
    private val stubManager: StubManager<T>,
) : PartialStub<R> {
    override fun returns(result: Result<R>) {
        stubManager.stubs.add(Stub(call, result))
    }
}

data class Stub<T, R>(
    val call: Call<T, R>,
    val result: Result<R>
)

class StubManager<T>(
    val stubs: ArrayList<Stub<T, Any?>> = arrayListOf()
)

interface Verifier<T> {
    fun <R> times(times: Int, call: T.() -> R)
}