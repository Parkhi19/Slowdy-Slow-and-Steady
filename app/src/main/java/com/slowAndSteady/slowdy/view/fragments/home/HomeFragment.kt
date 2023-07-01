package com.slowAndSteady.slowdy.view.fragments.home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.slowAndSteady.slowdy.R
import com.slowAndSteady.slowdy.databinding.FragmentHomeBinding
import com.slowAndSteady.slowdy.databinding.FragmentSigninBinding
import com.slowAndSteady.slowdy.databinding.FragmentSignupBinding
import com.slowAndSteady.slowdy.model.HabitModel
import com.slowAndSteady.slowdy.utils.Utils
import com.slowAndSteady.slowdy.view.adapter.HabitViewAdapter
import com.slowAndSteady.slowdy.view.custom.HabitStreaks
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.dateToday.text = Utils.getFormattedDate(Calendar.getInstance())
        val habitLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val habitLists = listOf(
            HabitModel("1-11-1", "study", listOf(true, false, true, true, true, true, true, true, false, true, false, true, false)),
            HabitModel("1-11-1", "study", listOf(true, false, true)),
            HabitModel("1-11-1", "study", listOf(true, false, true)),
            HabitModel("1-11-1", "study", listOf(true, false, true)),
            HabitModel("1-11-1", "study", listOf(true, false, true)),
            HabitModel("1-11-1", "study", listOf(true, false, true)),
            HabitModel("1-11-1", "study", listOf(true, false, true)),
            HabitModel("1-11-1", "study", listOf(true, false, true)),
        )
        val habitViewAdapter = HabitViewAdapter(habitLists)

        binding.habitRecyclerView.adapter = habitViewAdapter
        binding.habitRecyclerView.layoutManager = habitLayoutManager

        return binding.root
    }
}
