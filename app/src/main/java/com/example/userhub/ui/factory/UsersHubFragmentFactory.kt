package com.example.userhub.ui.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.example.userhub.core.dialog.BottomDialogFragmentLauncher
import com.example.userhub.core.dialog.DialogLauncher
import com.example.userhub.ui.adapters.UsersAdapter
import com.example.userhub.ui.fragments.UserDetailFragment
import com.example.userhub.ui.fragments.UsersOverViewFragment
import javax.inject.Inject

class UsersHubFragmentFactory @Inject constructor(
    private val usersAdapter: UsersAdapter,
    private val glide: RequestManager,
    private val bottomDialogFragmentLauncher: BottomDialogFragmentLauncher,
    private val dialogLauncher: DialogLauncher
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            UsersOverViewFragment::class.java.name -> UsersOverViewFragment(usersAdapter, bottomDialogFragmentLauncher, dialogLauncher)
            UserDetailFragment::class.java.name -> UserDetailFragment(dialogLauncher, glide)

            else -> super.instantiate(classLoader, className)
        }
    }
}