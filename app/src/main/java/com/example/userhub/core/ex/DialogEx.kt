package com.example.userhub.core.ex

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.example.userhub.core.dialog.BottomDialogFragmentLauncher
import com.example.userhub.core.dialog.DialogLauncher
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

fun BottomSheetDialogFragment.showBottomDialog(launcher: BottomDialogFragmentLauncher, activity: FragmentActivity) {
    launcher.show(this, activity)
}

fun DialogFragment.showDialogFragment(launcher: DialogLauncher, activity: FragmentActivity) {
    launcher.show(this, activity)
}