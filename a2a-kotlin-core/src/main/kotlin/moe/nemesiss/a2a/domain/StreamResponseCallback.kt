package moe.nemesiss.a2a.domain

interface StreamResponseCallback<T> {

    fun onNext(value: T)

    fun onComplete(value: T)

    fun onError(t: Throwable)
}