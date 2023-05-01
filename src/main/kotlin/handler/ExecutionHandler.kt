package handler

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class ExecutionHandler(
    private val stubs: List<Stub>
) : InvocationHandler {
    override fun invoke(proxy: Any?, method: Method?, parameters: Array<out Any>?): Any? {
        return stubs.firstOrNull {
            it.method?.name == method?.name
        }?.result
    }
}