package com.example.userhub.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.fragment.app.DialogFragment
import com.example.userhub.databinding.ErrorDialogBinding

class ErrorDialog: DialogFragment() {

    private var _binding: ErrorDialogBinding? = null
    private val binding get() = _binding!!

    private var action: Action = Action.Empty

    companion object {
        fun create(action: Action = Action.Empty): ErrorDialog = ErrorDialog().apply {
            this.action = action
        }
    }

    override fun onStart() {
        super.onStart()
        val window = dialog?.window ?: return

        window.setLayout(WRAP_CONTENT, WRAP_CONTENT)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = ErrorDialogBinding.inflate(layoutInflater)

        setButton()
        initListeners()

        return AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .setCancelable(false)
            .create()

    }

    private fun setButton() {
        binding.btnOk.text = action.text
    }

    private fun initListeners() {
        with(binding) {
            btnOk.setOnClickListener { this@ErrorDialog.dismiss() }
        }
    }

    data class Action (val text: String, val onClickListener: (ErrorDialog) -> Unit) {
        companion object {
            val Empty = Action("") {
                //
            }
        }
    }
}