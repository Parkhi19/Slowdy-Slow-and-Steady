package com.slowAndSteady.slowdy.view.fragments.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.slowAndSteady.slowdy.databinding.FragmentSignupBinding
import com.slowAndSteady.slowdy.databinding.FragmentSkipForNowBinding
import com.slowAndSteady.slowdy.view.activity.MainActivity
import com.slowAndSteady.slowdy.viewModel.auth.AuthState
import com.slowAndSteady.slowdy.viewModel.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SkipForNowFragment : Fragment() {
    private lateinit var binding: FragmentSkipForNowBinding
    private lateinit var navController: NavController
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSkipForNowBinding.inflate(inflater, container, false)
        navController = NavHostFragment.findNavController(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.skipButton.setOnClickListener {
            if (binding.skippedUserNameInput.text.toString().isNotEmpty()) {
                viewModel.anonymousSignIn(binding.skippedUserNameInput.text.toString())
            }
        }
        lifecycleScope.launch {
            viewModel.authState.collectLatest {
                when (it) {
                    AuthState.SUCCESS -> {
                        startActivity(
                            Intent(requireContext(), MainActivity::class.java))
                        requireActivity().finish()
                    }

                    AuthState.FAILED -> {
                        binding.skipButton.visibility = View.VISIBLE
                        binding.skippedProgressBar.visibility = View.INVISIBLE
                    }

                    AuthState.LOADING -> {
                        binding.skipButton.visibility = View.INVISIBLE
                        binding.skippedProgressBar.visibility = View.VISIBLE

                    }

                    AuthState.NONE -> {
                        binding.skipButton.visibility = View.VISIBLE
                        binding.skippedProgressBar.visibility = View.INVISIBLE
                    }
                }
            }
        }

    }
}