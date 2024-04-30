package uz.otamurod.presentation.arch.delegate

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleCoroutineScope
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * A helper interface that allows to define callbacks for lifecycle events. Classes with lifecycle that support this,
 * should call methods of all delegates when lifecycle event occurs.
 *
 * It is more simple, stable and consistent than [androidx.lifecycle.LifecycleObserver]
 */
interface LifecycleDelegate {
    fun useContextProvider(contextProvider: ContextProvider) {}

    fun onCreate(savedInstanceState: Bundle?) {}

    // Called *after* view is created unlike default Fragment behavior.
    // Naming is like that for consistency with onDestroyView
    fun onCreateView(rootView: View) {}

    fun onStart() {}

    fun onResume() {}

    fun onPause() {}

    fun onStop() {}

    fun onDestroyView() {}

    fun onDestroy() {}

    interface ContextProvider {
        val context: Context

        val lifecycleScope: LifecycleCoroutineScope
    }

    class Controller(private val contextProvider: ContextProvider) {

        private val delegates = mutableListOf<DelegateInstance<*>>()

        fun addDelegate(
            initialization: Initialization = Initialization.OnAccess,
            vararg delegates: () -> LifecycleDelegate,
        ) {
            val instances = delegates.map { DelegateInstance(it, initialization, contextProvider) }
            if (initialization == Initialization.Immediately) instances.forEach { it.value }
            this.delegates.addAll(instances)
        }

        fun onCreate(savedInstanceState: Bundle?) {
            delegates.filter { it.isInitialized(LifecycleStage.ON_CREATE) }
                .forEach { it.value.onCreate(savedInstanceState) }
        }

        fun onCreateView(rootView: View) {
            delegates.filter { it.isInitialized(LifecycleStage.ON_CREATE_VIEW) }
                .forEach { it.value.onCreateView(rootView) }
        }

        fun onStart() {
            delegates.filter { it.isInitialized(LifecycleStage.ON_START) }.forEach { it.value.onStart() }
        }

        fun onResume() {
            delegates.filter { it.isInitialized(LifecycleStage.ON_RESUME) }.forEach { it.value.onResume() }
        }

        fun onPause() {
            delegates.filter { it.isInitialized(null) }.asReversed().forEach { it.value.onPause() }
        }

        fun onStop() {
            delegates.filter { it.isInitialized(null) }.asReversed().forEach { it.value.onStop() }
        }

        fun onDestroyView() {
            delegates.filter { it.isInitialized(null) }.asReversed().forEach { it.value.onDestroyView() }
        }

        fun onDestroy() {
            delegates.filter { it.isInitialized(null) }.asReversed().forEach { it.value.onDestroy() }
        }

        fun <T : LifecycleDelegate> lifecycleDelegate(
            initialization: Initialization = Initialization.OnAccess,
            provider: () -> T,
        ): ReadOnlyProperty<Any, T> =
            object : ReadOnlyProperty<Any, T> {

                val instance = DelegateInstance(provider, initialization, contextProvider)

                override fun getValue(thisRef: Any, property: KProperty<*>): T = instance.value
            }
                .also { property ->
                    val instance = property.instance
                    if (initialization == Initialization.Immediately) instance.value
                    delegates.add(instance)
                }
    }

    private class DelegateInstance<out T : LifecycleDelegate>(
        provider: () -> T,
        val initialization: Initialization,
        val contextProvider: ContextProvider,
    ) {

        private var accessed = false

        val value by lazy {
            provider()
                .also {
                    it.useContextProvider(contextProvider)
                    accessed = true
                }
        }

        // Null signifies downcycle
        fun isInitialized(stage: LifecycleStage?): Boolean = accessed ||
            when (initialization) {
                is Initialization.Immediately -> true
                is Initialization.OnStage ->
                    if (stage != null) stage >= initialization.stage else initialization.callOnDownCycle

                is Initialization.OnAccess -> false
            }

        override fun toString(): String = if (accessed) {
            "DelegateInstance[$value, $initialization]"
        } else {
            "DelegateInstance[?, $initialization]"
        }
    }

    /**
     * A behavior of a delegate initialization
     */
    sealed interface Initialization {
        /**
         * Delegate will be initialized immediately when declared and will receive all calls to lifecycle methods
         */
        object Immediately : Initialization {
            override fun toString(): String = "Immediate"
        }

        /**
         * Delegate will be initialized on stage specified by [stage], and will receive calls to lifecycle methods
         * only started from that stage. If for any reason delegate was not initialized on upcyle (onCreate-onResume),
         * it will be initialized on downcycle (onPause-onDestroy) only if [callOnDownCycle] is true
         */
        data class OnStage(
            val stage: LifecycleStage,
            val callOnDownCycle: Boolean = false,
        ) : Initialization

        /**
         * Delegate will be initialized only when property is accessed, it will ignore
         * calls to lifecycle methods before that
         */
        object OnAccess : Initialization {
            override fun toString(): String = "OnAccess"
        }
    }

    /**
     * Stage of lifecycle on which delegate will be initialized
     */
    enum class LifecycleStage {
        ON_CREATE,
        ON_CREATE_VIEW,
        ON_START,
        ON_RESUME,
    }

    companion object {

        inline val immediately get() = Initialization.Immediately
        inline val onAccess get() = Initialization.OnAccess
        inline val onCreate get() = Initialization.OnStage(LifecycleStage.ON_CREATE)
        inline val onCreateView get() = Initialization.OnStage(LifecycleStage.ON_CREATE_VIEW)
        inline val onStart get() = Initialization.OnStage(LifecycleStage.ON_START)
        inline val onResume get() = Initialization.OnStage(LifecycleStage.ON_RESUME)
    }
}
