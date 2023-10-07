package com.slowAndSteady.slowdy.view.fragments.home


import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.slowAndSteady.slowdy.R
import com.slowAndSteady.slowdy.databinding.BottomSheetDialogBinding
import javax.annotation.Nullable

class BottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding : BottomSheetDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetDialogBinding.inflate(inflater, container , false)
        binding.okayButton.setOnClickListener {
            dismiss()
        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
    companion object {
        fun newInstance(): BottomSheetFragment {
            return BottomSheetFragment()
        }
    }
}