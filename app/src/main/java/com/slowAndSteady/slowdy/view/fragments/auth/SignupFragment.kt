package com.slowAndSteady.slowdy.view.fragments.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.slowAndSteady.slowdy.R
import com.slowAndSteady.slowdy.databinding.FragmentSigninBinding
import com.slowAndSteady.slowdy.databinding.FragmentSignupBinding
import com.slowAndSteady.slowdy.view.activity.MainActivity
import com.slowAndSteady.slowdy.viewModel.auth.AuthViewModel

class SignupFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    private lateinit var navController: NavController
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private val viewModel by activityViewModels<AuthViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        navController = NavHostFragment.findNavController(this)


        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions)
        auth = Firebase.auth

        binding.googleSignUpBtn.setOnClickListener {
            val signInClient = googleSignInClient.signInIntent
            launcher.launch(signInClient)
        }
        return binding.root
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val resultObtained = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                if (resultObtained.isSuccessful) {
                    val account: GoogleSignInAccount? = resultObtained.result
                    val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                    auth.signInWithCredential(credential).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            viewModel.createUser(
                                account?.displayName.toString(),
                                account?.email.toString(),
                                task.result?.user?.uid.toString()
                            )
                            openHomeFragment()
                            Toast.makeText(
                                requireContext(),
                                "User already exists!!",
                                Toast.LENGTH_SHORT
                            ).show()

                        } else {
                            Toast.makeText(requireContext(), "Failed!!", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    // Handle sign-in failure
                }
            } else {
                Toast.makeText(requireContext(), "Failed!!", Toast.LENGTH_SHORT).show()
            }
        }

    private fun openHomeFragment() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

}