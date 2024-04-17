package uz.otamurod.presentation.arch.viewbinding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Not a mixin, but allows to use other pseudo-mixins from this package to be added as part of view hierarchy
 * when view is inflated
 */
interface NestedInflater {
    /**
     * Represents mixin that is able to inflate part of view hierarchy and provide a container for next view to be
     * inflated in
     */
    fun interface LevelInflater {
        /**
         * Inflate view in [parent] with [attach] as attachToRoot parameter
         * @return pair of a view that is root of this inflater hierarchy, and container for next view to be inflated in
         */
        fun inflate(parent: ViewGroup?, attach: Boolean): Pair<View, ViewGroup>
    }

    /**
     * Represents a view on very bottom of inflated view hierarchy, usually it is an actual screen view that is inflated
     * by viewBindingDelegate
     */
    fun interface TerminalInflater {
        fun inflate(parent: ViewGroup?, attach: Boolean): View
    }

    /**
     * Inflate hierarchy in [parent] without attaching to root, having all [levelInflaters] inside each other, with
     * [terminalInflater] on very bottom
     */
    fun inflateNested(
        parent: ViewGroup?,
        terminalInflater: TerminalInflater,
        vararg levelInflaters: LevelInflater?,
    ): View {
        val levels = levelInflaters.filterNotNull()
        // if we don't have levels, just inflate terminal
        if (levels.isEmpty()) return terminalInflater.inflate(parent, false)

        // Get root and parent of first layer
        val (view, firstParent) = levels.first().inflate(parent, false)
        val lastParent = levels.drop(1).fold(firstParent) { p, inflater ->
            inflater.inflate(p, true).second
        }
        terminalInflater.inflate(lastParent, true)
        return view
    }
}

fun ViewBindingDelegate.terminalInflater(layoutInflater: LayoutInflater) =
    NestedInflater.TerminalInflater { parent, attach ->
        inflateView(layoutInflater, parent, attach)
    }
