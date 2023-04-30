import java.lang.IllegalStateException
import java.lang.reflect.Proxy

inline fun <reified T : Any> mock(builder: Register<T>.() -> Unit): T {
    val stubManager = StubManager<T>()
    val register = RegisterImpl(stubManager).apply(builder)

    val kClass = T::class
    val clazz = kClass.java
    return Proxy.newProxyInstance(clazz.classLoader, arrayOf(clazz), HistoryHandler(kClass)) as T
}

inline fun <reified T : Any> verify(instance: T) {
    if (!Proxy.isProxyClass(instance.javaClass))
        throw IllegalStateException("$instance is not a mock")
    val historyHandler = Proxy.getInvocationHandler(instance) as? HistoryHandler<T> ?:
        throw IllegalStateException("${HistoryHandler::class} not found in $instance")
}