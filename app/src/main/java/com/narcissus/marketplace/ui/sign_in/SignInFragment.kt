package com.narcissus.marketplace.ui.sign_in

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
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
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.ApiException
import com.narcissus.marketplace.R
import com.narcissus.marketplace.core.navigation.destination.SignUpDestination
import com.narcissus.marketplace.core.navigation.navigator
import com.narcissus.marketplace.core.util.launchWhenStarted
import com.narcissus.marketplace.databinding.FragmentSignInBinding
import com.narcissus.marketplace.domain.auth.SignInResult
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
    private val hasNavigatedFromUserProfile by lazy { args.isNavigatedFromUserProfile }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignInBinding.bind(view)
        initToolbar()
        initTextChangedListeners()
        initSignInListener()
        initSignUpListener()
        observeAuthState()
    }

    private fun initToolbar() {
        val navController = findNavController()
        binding.tbSignIn.setupWithNavController(navController)
        binding.tbSignIn.setNavigationIcon(R.drawable.ic_close)
    }

    private fun initTextChangedListeners() {
        binding.layoutEmailPasswordInputs.etEmail.doAfterTextChanged {
            binding.layoutEmailPasswordInputs.tiEmail.error = null
        }

        binding.layoutEmailPasswordInputs.etPassword.doAfterTextChanged {
            binding.layoutEmailPasswordInputs.tiPassword.error = null
        }
    }

    private fun initSignInListener() {
        binding.btnSignInWithEmail.setOnClickListener {
            viewModel.signInWithEmailPassword(
                binding.layoutEmailPasswordInputs.etEmail.text.toString(),
                binding.layoutEmailPasswordInputs.etPassword.text.toString(),
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
        viewModel.signInResultFlow.onEach { result ->
            when (result) {
                is SignInResult.Error -> toastError()
                is SignInResult.InvalidEmail -> showInvalidEmailError()
                is SignInResult.BlankPassword -> showBlankPasswordError()
                is SignInResult.UserNotFound -> showUserNotFoundError()
                is SignInResult.WrongPassword -> showWrongPasswordError()
                is SignInResult.Success -> navigateBack(hasNavigatedFromUserProfile)
            }
        }.launchWhenStarted(viewLifecycleOwner.lifecycleScope)
    }

    private fun navigateToSignUp() {
        navigator.navigate(SignUpDestination)
    }

    private fun toastError() {
        Toast.makeText(context, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()
    }

    private fun showInvalidEmailError() {
        binding.layoutEmailPasswordInputs.tiEmail.error =
            getString(R.string.wrong_email_format)
    }

    private fun showBlankPasswordError() {
        binding.layoutEmailPasswordInputs.tiPassword.error =
            getString(R.string.password_is_blank)
    }

    private fun showUserNotFoundError() {
        binding.layoutEmailPasswordInputs.tiEmail.error =
            getString(R.string.user_not_found)
    }

    private fun showWrongPasswordError() {
        binding.layoutEmailPasswordInputs.tiPassword.error =
            getString(R.string.wrong_password)
    }

    private fun navigateBack(hasNavigatedFromUserProfile: Boolean) {
        if (hasNavigatedFromUserProfile) {
            findNavController().popBackStack()
        } else {
            // navigateToCheckOut
        }
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
            } else if (!areGooglePlayServicesAvailable()) {
                showPlayServicesUnavailableErrorDialog()
            }
        }

    private fun areGooglePlayServicesAvailable() =
        GoogleApiAvailability
            .getInstance()
            .isGooglePlayServicesAvailable(requireContext()) == ConnectionResult.SUCCESS

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
        AlertDialog.Builder(requireContext())
            .setMessage(
                getString(
                    R.string.google_signin_error
                )
            )
            .setPositiveButton(getString(R.string.ok), null)
            .create().show()
    }

    private fun showPlayServicesUnavailableErrorDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage(
                getString(
                    R.string.play_services_unavailable
                )
            )
            .setPositiveButton(getString(R.string.ok), null)
            .create().show()
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
