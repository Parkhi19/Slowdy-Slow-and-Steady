package com.slowAndSteady.slowdy.view.fragments.home
import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.slowAndSteady.slowdy.R
import com.slowAndSteady.slowdy.data.entity.HabitEntity
import com.slowAndSteady.slowdy.databinding.FragmentAddANewHabitBinding
import com.slowAndSteady.slowdy.viewModel.home.MainViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Calendar

class AddANewHabitFragment : Fragment() {
    private lateinit var habitBackgroundColorsMap: Map<ImageView, Int>
    private lateinit var binding: FragmentAddANewHabitBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddANewHabitBinding.inflate(inflater, container, false)
        habitBackgroundColorsMap = mapOf(
            binding.colorChoice1 to R.color.habit_color_1,
            binding.colorChoice2 to R.color.habit_color_2,
            binding.colorChoice3 to R.color.habit_color_3,
            binding.colorChoice4 to R.color.habit_color_4,
            binding.colorChoice5 to R.color.habit_color_5
        )
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
                viewModel.createAndUpdateHabit(HabitEntity(0, habitName, habitColor = habitBackgroundColorsMap.values.toList()[viewModel.habitColorSelectionIndex.value] ))
            }
        }
        lifecycleScope.launch {
            viewModel.habitColorSelectionIndex.collect { index ->
                habitBackgroundColorsMap.keys.forEach {
                    it.setImageResource(0)
                }
                habitBackgroundColorsMap.keys.toList()[index].setImageResource(R.drawable.selected_icon)
            }
        }
        habitBackgroundColorsMap.keys.toList().forEachIndexed { index, imageView ->
            imageView.setOnClickListener {
                viewModel.updateHabitColorIndex(index)
            }
        }

        binding.createReminderButton.setOnClickListener {
            val calendarInstance = Calendar.getInstance()
            val hour = calendarInstance.get(Calendar.HOUR_OF_DAY)
            val minute = calendarInstance.get(Calendar.MINUTE)
            val timePickerDialog = TimePickerDialog(
                requireContext(),
                { _, hourOfDay, minute ->
                    viewModel.updateReminderTime(hourOfDay, minute)
                    binding.createReminderButton.text = " We will remind you at $hourOfDay:$minute"
                },
                hour,
                minute,
                false
            )
            timePickerDialog.show()
        }


    }


}
