package com.example.userhub.core.dialog

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.userhub.core.delegate.weak
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class BottomDialogFragmentLauncher @Inject constructor(): LifecycleObserver {

    private var activity: FragmentActivity? by weak()
    private var bottomDialogFragment: BottomSheetDialogFragment? by weak()

    fun show(bottomDialogFragment: BottomSheetDialogFragment, activity: FragmentActivity) {
        if (activity.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
            bottomDialogFragment.show(activity.supportFragmentManager, null)
        } else {
            this.activity = activity
            this.bottomDialogFragment = bottomDialogFragment
            activity.lifecycle.addObserver(this@BottomDialogFragmentLauncher)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onActivityResumed() {
        val activity = activity ?: return
        val dialogFragment = bottomDialogFragment ?: return

        dialogFragment.show(activity.supportFragmentManager, null)
        activity.lifecycle.removeObserver(this)
    }

}