package com.slowAndSteady.slowdy.view.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import com.slowAndSteady.slowdy.databinding.FragmentHabitLogBinding
import com.slowAndSteady.slowdy.viewModel.home.MainViewModel


class HabitLogFragment : Fragment() {
    private lateinit var binding: FragmentHabitLogBinding
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by activityViewModels()
    private val args: HabitLogFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHabitLogBinding.inflate(inflater, container, false)

        binding.YesButton.setOnClickListener {
            val bottomSheetDialog = BottomSheetFragment()
            bottomSheetDialog.show(childFragmentManager, "Bottom Sheet Dialog Fragment")
            viewModel.markHabit(habitId = args.habitId, true)
        }

        binding.NoButton.setOnClickListener {
            val bottomSheetDialog = BottomSheetFragment()
            bottomSheetDialog.show(childFragmentManager, "Bottom Sheet Dialog Fragment")
            viewModel.markHabit(habitId = args.habitId, false)
        }

        return binding.root
    }
}
