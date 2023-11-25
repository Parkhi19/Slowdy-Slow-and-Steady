package com.slowAndSteady.slowdy.view.fragments.auth

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues.TAG
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
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.slowAndSteady.slowdy.R
import com.slowAndSteady.slowdy.databinding.FragmentSignupBinding
import com.slowAndSteady.slowdy.view.activity.MainActivity
import com.slowAndSteady.slowdy.viewModel.auth.AuthViewModel
import java.util.regex.Matcher
import java.util.regex.Pattern


class SignupFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    private lateinit var navController: NavController
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private lateinit var email: String
    private lateinit var password: String
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
                            task.exception?.printStackTrace()
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
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            openHomeFragment()
        }
        else{
            binding.signupBtn.setOnClickListener {
                email = binding.emailAddressInput.text.toString()
                password = binding.passwordInput.text.toString()
                if(!isEmailValid(email)){
                    Toast.makeText(requireContext(), "Invalid Email", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                else{
                    checkForFirstTime(email)
                    createAccount(email, password)
                }

            }
        }

                }

    private fun checkForFirstTime(email: String) {
        val sanitizedEmail = email.replace(".", ",")
        val database = FirebaseDatabase.getInstance()
        val usersRef = database.reference.child("users")

        usersRef.child(sanitizedEmail).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val exists = snapshot.exists()

                if (exists) {
                    showAlert("User Exists", "User with email $email already exists!", error = true)
                } else {
                    createAccount(email, password)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors here
            }
        })
    }
    fun isEmailValid(email: String?): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                Log.d(TAG, "createUserWithEmail:success", task.exception)
                if (task.isSuccessful) {
//                    Log.d(TAG, "createUserWithEmail:success")
                    task.result.user?.sendEmailVerification()
                      viewModel.emailSignUp(email, password,  binding.userNameInput.text.toString())
//                    Log.d(TAG, "Email sent.")
                    Toast.makeText(
                        requireContext(),
                        "Verification email sent to ${task.result.user?.email}",
                        Toast.LENGTH_SHORT,
                    ).show()
                    val dialogTitle = "Account Successfully Created!"
                    val dialogMessage = "Please verify your email before login "
                    showAlert(dialogTitle, dialogMessage, error = false)
                    if(task.result.user?.isEmailVerified == true){
                        Log.d(TAG, "createUserWithEmail:success, Email Verified: ${task.result.user?.isEmailVerified}")
                        openHomeFragment()
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        requireContext(),
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
//                    updateUI(null)
                }
            }
        // [END create_user_with_email]
    }


    private fun openHomeFragment() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
    private fun showAlert(title: String, message: String, error: Boolean) {
        val builder = AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, which ->
                dialog.cancel()
//                if (!error) {
//                   openHomeFragment()
//                }
            }
        val ok = builder.create()
        ok.show()
    }

}