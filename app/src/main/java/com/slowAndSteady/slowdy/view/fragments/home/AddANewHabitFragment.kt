package com.slowAndSteady.slowdy.view.fragments.home

import android.accessibilityservice.GestureDescription
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.service.voice.VoiceInteractionSession.VisibleActivityCallback
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.slowAndSteady.slowdy.R
import com.slowAndSteady.slowdy.data.entity.HabitEntity
import com.slowAndSteady.slowdy.databinding.FragmentAddANewHabitBinding
import com.slowAndSteady.slowdy.viewModel.home.MainViewModel

class AddANewHabitFragment : Fragment() {
    private lateinit var binding: FragmentAddANewHabitBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddANewHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.habitNameValidationAlert.visibility = View.GONE
        binding.createHabitButton.setOnClickListener {
            val habitName = binding.habitNameInput.text.toString()
            if (habitName.isBlank()) {
                binding.habitNameValidationAlert.visibility = View.VISIBLE
            }
            else {
                binding.habitNameValidationAlert.visibility = View.GONE
                viewModel.createAndUpdateHabit(HabitEntity(0, habitName))
            }
            }
        }
    }



