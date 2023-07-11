package com.slowAndSteady.slowdy.view.fragments.home
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.slowAndSteady.slowdy.R
import com.slowAndSteady.slowdy.data.entity.HabitEntity
import com.slowAndSteady.slowdy.databinding.FragmentAddANewHabitBinding
import com.slowAndSteady.slowdy.viewModel.home.MainViewModel

private var selectedColor: Int = 0
class AddANewHabitFragment : Fragment() {
    private lateinit var binding: FragmentAddANewHabitBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
            } else {
                binding.habitNameValidationAlert.visibility = View.GONE
                viewModel.createAndUpdateHabit(HabitEntity(0, habitName, habitColor = selectedColor ))
            }
        }
        binding.colorChoice1.setOnClickListener {
            selectedColor = ContextCompat.getColor(requireContext(), R.color.habit_color_1)
        }
        binding.colorChoice2.setOnClickListener {
            selectedColor = ContextCompat.getColor(requireContext(), R.color.habit_color_2)

        }
        binding.colorChoice3.setOnClickListener {
            selectedColor = ContextCompat.getColor(requireContext(), R.color.habit_color_3)

        }
        binding.colorChoice4.setOnClickListener {
            selectedColor = ContextCompat.getColor(requireContext(), R.color.habit_color_4)

        }
        binding.colorChoice5.setOnClickListener {
            selectedColor = ContextCompat.getColor(requireContext(), R.color.habit_color_5)

        }

    }
}
