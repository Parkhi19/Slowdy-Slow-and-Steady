package com.slowAndSteady.slowdy.view.fragments.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.slowAndSteady.slowdy.R
import com.slowAndSteady.slowdy.databinding.FragmentLandingBinding
import com.slowAndSteady.slowdy.databinding.FragmentSigninBinding


class LandingFragment : Fragment() {

    private lateinit var binding: FragmentLandingBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLandingBinding.inflate(inflater, container, false)
        navController = NavHostFragment.findNavController(this)

        binding.loginButton.setOnClickListener {
            // Handle the button click event here
            val action = LandingFragmentDirections.actionLandingFragmentToSignInFragment()
            navController.navigate(action)
        }

        binding.signupButton.setOnClickListener{
            val action = LandingFragmentDirections.actionLandingFragmentToSignupFragment()
            navController.navigate(action)
        }
        binding.skipButton.setOnClickListener{
             val action = LandingFragmentDirections.actionLandingFragmentToSkipForNowFragment2()
            navController.navigate(action)
        }

        return binding.root
    }
}