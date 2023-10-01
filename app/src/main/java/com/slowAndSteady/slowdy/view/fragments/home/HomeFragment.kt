package com.slowAndSteady.slowdy.view.fragments.home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.slowAndSteady.slowdy.data.entity.HabitEntity
import com.slowAndSteady.slowdy.databinding.FragmentHomeBinding
import com.slowAndSteady.slowdy.utils.Utils
import com.slowAndSteady.slowdy.view.adapter.HabitViewAdapter
import com.slowAndSteady.slowdy.viewModel.home.MainViewModel
import kotlinx.coroutines.launch
import java.util.Calendar

class HomeFragment : Fragment(), HabitViewAdapter.HabitClickListener {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        navController = NavHostFragment.findNavController(this)

        binding.dateToday.text = Utils.getFormattedDate(Calendar.getInstance())
        val habitLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.habitRecyclerView.layoutManager = habitLayoutManager
        lifecycleScope.launch {
            viewModel.allHabits.collect{
                val habitViewAdapter = HabitViewAdapter(it, this@HomeFragment)

                binding.habitRecyclerView.adapter = habitViewAdapter

            }
        }
        binding.addANewHabit.setOnClickListener{
            val action = HomeFragmentDirections.actionHomeFragmentToAddANewHabitFragment()
            navController.navigate(action)
        }


        return binding.root
    }

    override fun onHabitClicked(habitEntity: HabitEntity) {
        val action = HomeFragmentDirections.actionHomeFragmentToHabitLogFragment(habitEntity.habitID)
        navController.navigate(action)
  }
}
