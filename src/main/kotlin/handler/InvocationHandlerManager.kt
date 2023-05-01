package handler

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class InvocationHandlerManager(
    var invocationHandler: InvocationHandler? = null,
) : InvocationHandler {
    override fun invoke(proxy: Any?, method: Method?, parameters: Array<out Any>?) =
        invocationHandler?.invoke(proxy, method, parameters)
}