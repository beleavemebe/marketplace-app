package com.narcissus.marketplace.ui.sign_in

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.FragmentSignInBinding
import com.narcissus.marketplace.domain.util.AuthResult
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class SignInFragment : Fragment(R.layout.fragment_sign_in) {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SignInViewModel by viewModel()

    private val args by navArgs<SignInFragmentArgs>()
    private val isNavigatedFromUserProfile by lazy { args.isNavigatedFromUserProfile }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignInBinding.bind(view)
        initToolbar()
        initSignInListener()
        observeAuthState()
    }

    private fun initToolbar() {
        val navController = findNavController()
        binding.tbSignIn.setupWithNavController(navController)
        binding.tbSignIn.setNavigationIcon(R.drawable.ic_close)
    }

    private fun initSignInListener() {
        binding.btnSignInWithEmail.setOnClickListener {
            cleanInputErrors()
            viewModel.signInWithEmailPassword(
                binding.layoutEmailPasswordInputs.emailTextInputLayout.editText?.text.toString(),
                binding.layoutEmailPasswordInputs.passwordTextInputLayout.editText?.text.toString(),
            )
        }
        binding.btnSignInWithGoogle.setOnClickListener {
            signInWithGoogleAccount()
        }
    }

    private fun observeAuthState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.authResultFlow.collect { authResult ->
                when (authResult) {
                    is AuthResult.SignInSuccess -> navigateToCallScreen(isNavigatedFromUserProfile)
                    is AuthResult.SignInWrongPasswordOrEmail -> setPasswordInputLayoutError()
                    is AuthResult.Error -> showErrorToast()
                    is AuthResult.WrongEmail -> setEmailInputLayoutError()
                }
            }
        }
    }

    private fun cleanInputErrors() {
        binding.layoutEmailPasswordInputs.passwordTextInputLayout.error = null
        binding.layoutEmailPasswordInputs.emailTextInputLayout.error = null
    }

    private fun navigateToCallScreen(isNavigatedFromUserProfile: Boolean) {
        Log.d("DEBUG", "AUTH OK!")
        if (isNavigatedFromUserProfile) {
            findNavController().popBackStack()
        } else {
            // navigateToCheckOut
        }
    }

    private fun setPasswordInputLayoutError() {
        binding.layoutEmailPasswordInputs.passwordTextInputLayout.error =
            getString(R.string.wrong_email_or_password)
    }

    private fun setEmailInputLayoutError() {
        binding.layoutEmailPasswordInputs.emailTextInputLayout.error =
            getString(R.string.wrong_email_format)
    }

    private fun showErrorToast() {
        Toast.makeText(context, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private lateinit var oneTapClient: SignInClient

    private val idTokenResultHandler =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                try {
                    val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
                    val idToken = credential.googleIdToken
                    when {
                        idToken != null -> {
                            viewModel.signInWithGoogleAccount(idToken)
                        }
                        else -> {
                            showSignInWithGoogleAccountErrorDialog()
                            Log.d("DEBUG", "No ID token!")
                        }
                    }
                } catch (e: ApiException) {
                    Log.d("DEBUG", "EXCEPTION")
                }

            } else {
                showSignInWithGoogleAccountErrorDialog()
            }
        }
    private val addAccountSettingsLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                try {
                    val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
                    val idToken = credential.googleIdToken
                    when {
                        idToken != null -> {
                            Log.d("DEBUG", "Got ID token add account.")
                            viewModel.signInWithGoogleAccount(idToken)
                        }
                        else -> {
                            Log.d("DEBUG", "No ID token!")
                            showSignInWithGoogleAccountErrorDialog()
                        }
                    }
                } catch (e: ApiException) {
                    Log.d("DEBUG", e.localizedMessage.toString())
                    showSignInWithGoogleAccountErrorDialog()
                }

            } else {
                showSignInWithGoogleAccountErrorDialog()
            }
        }

    private fun showSignInWithGoogleAccountErrorDialog() {
        AlertDialog.Builder(context)
            .setMessage("Something went wrong with logging into your Google account. Please sign in with your email and password or try again later")
            .setPositiveButton("OK", null).create()
            .show()
    }

    private var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()


    private fun signInWithGoogleAccount() {
        oneTapClient = Identity.getSignInClient(activity as Activity)
        oneTapClient.beginSignIn(getSignInRequest())
            .addOnSuccessListener(activity as Activity) { result ->
                try {
                    val intentSenderRequest =
                        IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                    idTokenResultHandler.launch(intentSenderRequest)

                } catch (e: IntentSender.SendIntentException) {
                    showSignInWithGoogleAccountErrorDialog()
                }
            }
            .addOnFailureListener(activity as Activity) { e ->
                // No saved credentials found. Launch the One Tap sign-up flow, or
                // do nothing and continue presenting the signed-out UI.
                Log.d("DEBUG", "failure: " + e.localizedMessage)
                val mGoogleSignInClient = GoogleSignIn.getClient(activity as Activity, gso)
                val signInIntent: Intent = mGoogleSignInClient.signInIntent
                addAccountSettingsLauncher.launch(signInIntent)
            }
    }


    private fun getSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.default_web_client_id))
                    .setFilterByAuthorizedAccounts(true)
                    .build(),
            )
            //    .setAutoSelectEnabled(true)
            .build()
    }


}
