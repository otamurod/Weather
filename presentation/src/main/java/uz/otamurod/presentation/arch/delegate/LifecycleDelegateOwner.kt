package uz.otamurod.presentation.arch.delegate

import kotlin.properties.ReadOnlyProperty

/**
 * Signifies a class that can attach a [LifecycleDelegate] to its lifecycle
 */
interface LifecycleDelegateOwner {
    /**
     * Create a delegate using [provider] and attach it to the lifecycle. [initialization] determines on which
     * stage provider should be called to instantiate the delegate
     * - [Immediate] will initialize the delegate immediately
     * - [OnStage] will initialize delegate on a specific lifecycle stage
     * - [OnAccess] will initialize the delegate on first access to the value of the property returned by this method
     */
    fun <T : LifecycleDelegate> lifecycleDelegate(
        initialization: LifecycleDelegate.Initialization,
        provider: () -> T,
    ): ReadOnlyProperty<Any, T>
}
