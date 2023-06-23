package com.slowAndSteady.slowdy.view.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.slowAndSteady.slowdy.databinding.FragmentSigninBinding

class SignInFragment : Fragment() {
  private lateinit var binding: FragmentSigninBinding
  private lateinit var navController:NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSigninBinding.inflate(inflater, container, false)
        navController = NavHostFragment.findNavController(this)

        binding.button.setOnClickListener {
            // Handle the button click event here
            val action = SignInFragmentDirections.actionSignInFragmentToSignupFragment()
            navController.navigate(action)
        }

        return binding.root
    }

}
