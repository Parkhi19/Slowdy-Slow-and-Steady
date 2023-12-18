package com.slowAndSteady.slowdy.view.fragments.auth
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
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
import com.slowAndSteady.slowdy.data.util.ResultState
import com.slowAndSteady.slowdy.data.util.SlowdyException
import com.slowAndSteady.slowdy.databinding.FragmentSigninBinding
import com.slowAndSteady.slowdy.view.activity.MainActivity
import com.slowAndSteady.slowdy.viewModel.auth.AuthViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSigninBinding
    private lateinit var navController: NavController
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private val  viewModel by activityViewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSigninBinding.inflate(inflater, container, false)
        navController = NavHostFragment.findNavController(this)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions)
        auth = Firebase.auth

        binding.googleSignIn.setOnClickListener {
            val signInClient = googleSignInClient.signInIntent
            launcher.launch(signInClient)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.userFetchFlow.collectLatest {
                when(it){
                    is ResultState.Error -> {
                        if(it.exception is SlowdyException.UserNotFoundException){
                            Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
                        }
                    }
                    is ResultState.Success<*> -> {
                        viewModel.populateDataInLocal(FirebaseAuth.getInstance().uid.toString())
                        openHomeFragment()
                    }
                    ResultState.Loading -> {}
                    ResultState.None -> {}
                }
            }
        }
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
                            Toast.makeText(requireContext(), "Done!!", Toast.LENGTH_SHORT).show()
                            viewModel.getUserFromRemote(task.result.user?.uid.toString())
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