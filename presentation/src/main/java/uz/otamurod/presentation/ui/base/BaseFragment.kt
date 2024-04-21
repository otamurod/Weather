package uz.otamurod.presentation.ui.base

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import uz.otamurod.domain.preferences.WeatherApplicationPreferencesApi
import uz.otamurod.presentation.arch.delegate.LifecycleDelegate
import uz.otamurod.presentation.arch.delegate.LifecycleDelegateOwner
import uz.otamurod.presentation.arch.viewbinding.BindingFactory
import uz.otamurod.presentation.arch.viewbinding.NestedInflater
import uz.otamurod.presentation.arch.viewbinding.ViewBindingDelegate
import uz.otamurod.presentation.arch.viewbinding.terminalInflater
import javax.inject.Inject

abstract class BaseFragment : Fragment(), NestedInflater, LifecycleDelegateOwner {
    @Inject
    lateinit var preferences: WeatherApplicationPreferencesApi

    private val lifecycleDelegateController = LifecycleDelegate.Controller(
        object : LifecycleDelegate.ContextProvider {
            override val context: Context get() = requireContext()
            override val lifecycleScope: LifecycleCoroutineScope get() = this@BaseFragment.lifecycleScope
        },
    )

    final override fun <T : LifecycleDelegate> lifecycleDelegate(
        initialization: LifecycleDelegate.Initialization,
        provider: () -> T,
    ) = lifecycleDelegateController.lifecycleDelegate(initialization, provider)

    private val viewBindingDelegate by lifecycleDelegate(LifecycleDelegate.immediately) { ViewBindingDelegate() }

    fun <Binding : ViewBinding> viewBinding(factory: BindingFactory<Binding>) =
        viewBindingDelegate.createPrimaryViewBindingHandler(factory)

    fun <Binding : ViewBinding> viewBinding(@IdRes viewId: Int?, binder: (View) -> Binding) =
        viewBindingDelegate.createSecondaryViewBindingHandler {
            if (viewId == null) {
                binder(it)
            } else {
                binder(it.findViewById(viewId))
            }
        }

    private var contextThemeWrapper: Context? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflateNested(
            container, viewBindingDelegate.terminalInflater(inflater)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleDelegateController.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleDelegateController.onCreateView(view)
    }

    override fun onStart() {
        super.onStart()
        lifecycleDelegateController.onStart()
    }

    override fun onResume() {
        super.onResume()
        lifecycleDelegateController.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lifecycleDelegateController.onDestroyView()
    }

    override fun getContext(): Context? {
        return if (contextThemeWrapper == null) {
            super.getContext()
        } else {
            contextThemeWrapper
        }
    }

    override fun onPause() {
        super.onPause()
        lifecycleDelegateController.onPause()
    }

    override fun onStop() {
        super.onStop()
        lifecycleDelegateController.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleDelegateController.onDestroy()
    }
}
