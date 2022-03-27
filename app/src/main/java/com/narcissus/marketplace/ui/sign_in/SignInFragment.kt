package com.narcissus.marketplace.ui.sign_in

import android.app.Activity
import android.app.AlertDialog
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
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.narcissus.marketplace.R
import com.narcissus.marketplace.core.navigation.destination.SignUpDestination
import com.narcissus.marketplace.core.navigation.navigator
import com.narcissus.marketplace.core.util.launchWhenStarted
import com.narcissus.marketplace.databinding.FragmentSignInBinding
import com.narcissus.marketplace.domain.util.AuthResult
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent

class SignInFragment : Fragment(R.layout.fragment_sign_in), KoinComponent {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SignInViewModel by viewModel()

    private val signInClient: GoogleSignInClient by inject()
    private val oneTapSignInRequest: BeginSignInRequest by inject()
    private val oneTapClient: SignInClient by lazy { Identity.getSignInClient(requireActivity()) }

    private val args by navArgs<SignInFragmentArgs>()
    private val isNavigatedFromUserProfile by lazy { args.isNavigatedFromUserProfile }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignInBinding.bind(view)
        initToolbar()
        initSignInListener()
        initSignUpListener()
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
            signInWithGoogleAccountByOneTapUI()
        }
    }

    private fun initSignUpListener() {
        binding.tvSignUpButtonRight.setOnClickListener {
            navigateToSignUp()
        }
    }

    private fun observeAuthState() {
        viewModel.authResultFlow.onEach { authResult ->
            when (authResult) {
                is AuthResult.SignInSuccess -> navigateToCallScreen(isNavigatedFromUserProfile)
                is AuthResult.SignInWrongPasswordOrEmail -> setPasswordInputLayoutError()
                is AuthResult.Error -> showEmailAuthErrorToast()
                is AuthResult.WrongEmail -> setEmailInputLayoutError()
            }
        }.launchWhenStarted(viewLifecycleOwner.lifecycleScope)
    }

    private fun cleanInputErrors() {
        binding.layoutEmailPasswordInputs.passwordTextInputLayout.error = null
        binding.layoutEmailPasswordInputs.emailTextInputLayout.error = null
    }

    private fun navigateToCallScreen(isNavigatedFromUserProfile: Boolean) {
        if (isNavigatedFromUserProfile) {
            findNavController().popBackStack()
        } else {
            // navigateToCheckOut
        }
    }

    private fun navigateToSignUp() {
        val destination: SignUpDestination by inject()
        navigator.navigate(destination)
    }

    private fun setPasswordInputLayoutError() {
        binding.layoutEmailPasswordInputs.passwordTextInputLayout.error =
            getString(R.string.wrong_email_or_password)
    }

    private fun setEmailInputLayoutError() {
        binding.layoutEmailPasswordInputs.emailTextInputLayout.error =
            getString(R.string.wrong_email_format)
    }

    private fun showEmailAuthErrorToast() {
        Toast.makeText(context, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private val googleAuthLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    processGoogleIdTokenResult(account.idToken)
                } catch (e: ApiException) {
                    showSignInWithGoogleAccountErrorDialog()
                }
            }
        }

    private val googleOneTapUIAuthLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                try {
                    val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
                    processGoogleIdTokenResult(credential.googleIdToken)
                } catch (e: ApiException) {
                    showSignInWithGoogleAccountErrorDialog()
                }
            }
        }

    private fun processGoogleIdTokenResult(idToken: String?) {
        if (idToken != null) {
            viewModel.signInWithGoogleAccount(idToken)
        } else {
            showSignInWithGoogleAccountErrorDialog()
        }
    }

    private fun showSignInWithGoogleAccountErrorDialog() {
        AlertDialog.Builder(context)
            .setMessage(getString(R.string.google_signin_error))
            .setPositiveButton(getString(R.string.ok), null).create()
            .show()
    }

    private fun signInWithGoogleAccountByOneTapUI() {
        oneTapClient.beginSignIn(oneTapSignInRequest)
            .addOnSuccessListener { beginResult ->
                try {
                    val intentSenderRequest =
                        IntentSenderRequest.Builder(beginResult.pendingIntent.intentSender).build()
                    googleOneTapUIAuthLauncher.launch(intentSenderRequest)
                } catch (e: ApiException) {
                    e.localizedMessage?.let { Log.w("One Tap UI Auth exception: ", it) }
                }
            }
            .addOnFailureListener {
                signInWithGoogleAccount()
            }
    }

    private fun signInWithGoogleAccount() {
        googleAuthLauncher.launch(signInClient.signInIntent)
    }
}
