package com.slowAndSteady.slowdy.view.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.slowAndSteady.slowdy.R
import com.slowAndSteady.slowdy.databinding.FragmentSettingsBinding
import com.slowAndSteady.slowdy.databinding.FragmentSignupBinding


class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        navController = NavHostFragment.findNavController(this)

        binding.settingsFragmentBackBtn.setOnClickListener {
            val action = SettingsFragmentDirections.actionSettingsFragmentToHomeFragment()
            navController.navigate(action)
        }
        return binding.root
    }

}