package com.slowAndSteady.slowdy.view.fragments.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.slowAndSteady.slowdy.R
import com.slowAndSteady.slowdy.databinding.FragmentSigninBinding
import com.slowAndSteady.slowdy.databinding.FragmentSignupBinding

class SignupFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        navController = NavHostFragment.findNavController(this)

        return binding.root
    }

}