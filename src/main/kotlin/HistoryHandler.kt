import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import kotlin.reflect.KClass

class HistoryHandler<T : Any>(
    kClass: KClass<T>
) : InvocationHandler {
    override fun invoke(proxy: Any?, method: Method?, parameters: Array<out Any>?): Any {
        return "DONIZETE"
    }
}