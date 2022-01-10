package com.example.userhub.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.userhub.R
import com.example.userhub.core.types.Gender
import com.example.userhub.databinding.FilterDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class GenderFilterBottomDialog : BottomSheetDialogFragment() {

    private var _binding: FilterDialogBinding? = null
    private val binding get() = _binding!!

    private var gender: Gender? = null
    private var action: Action = Action.Gender

    companion object {
        fun create(gender: Gender? = null, action: Action = Action.Gender) =
            GenderFilterBottomDialog().apply {
                this.gender = gender
                this.action = action
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FilterDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setGender()
        initGenderListener()
    }

    private fun initGenderListener() {
        with(binding) {
            radioGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.rbMale -> {
                        action.genderSelected(Gender.MALE)
                        this@GenderFilterBottomDialog.dismiss()
                    }

                    R.id.rbFemale -> {
                        action.genderSelected(Gender.FEMALE)
                        this@GenderFilterBottomDialog.dismiss()
                    }
                }
            }

            tvClearSelection.setOnClickListener {
                radioGroup.clearCheck()
                action.genderSelected(null)
                this@GenderFilterBottomDialog.dismiss()
            }
        }
    }

    private fun setGender() {
        with(binding) {
            when (gender) {
                Gender.MALE -> rbMale.isChecked = true
                Gender.FEMALE -> rbFemale.isChecked = true
                else -> radioGroup.clearCheck()
            }

            when {
                radioGroup.checkedRadioButtonId != -1 -> tvClearSelection.isVisible = true
            }
        }
    }

    data class Action(val genderSelected: (Gender?) -> Unit) {
        companion object {
            val Gender = Action {
                //
            }
        }
    }
}