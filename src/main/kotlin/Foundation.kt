import handler.ExecutionHandler
import handler.InvocationHandlerManager
import handler.MockingHandler
import java.lang.IllegalStateException
import java.lang.reflect.Proxy

inline fun <reified T : Any> mock(builder: Register<T>.() -> Unit): T {
    val kClass = T::class
    val clazz = kClass.java

    val kotlinStubs: KotlinStubs<T> = arrayListOf()
    RegisterImpl(kotlinStubs).apply(builder)

    val mockingHandler = MockingHandler()
    val invocationHandlerManager = InvocationHandlerManager(mockingHandler)

    val type = Proxy.newProxyInstance(clazz.classLoader, arrayOf(clazz), invocationHandlerManager) as T
    kotlinStubs.forEach { (call, returns) ->
        mockingHandler.mock(call, returns, type)
    }

    invocationHandlerManager.invocationHandler = ExecutionHandler(mockingHandler.stubs)

    return type
}

inline fun <reified T : Any> verify(instance: T, verifier: Verifier<T>.() -> Unit) {
    if (!Proxy.isProxyClass(instance.javaClass))
        throw IllegalStateException("$instance is not a mock")
    val invocationHandlerManager = Proxy.getInvocationHandler(instance) as? InvocationHandlerManager ?:
        throw IllegalStateException("${InvocationHandlerManager::class} not found in $instance")
}