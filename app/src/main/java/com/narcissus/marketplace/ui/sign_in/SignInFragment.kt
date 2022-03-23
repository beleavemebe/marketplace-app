package com.narcissus.marketplace.ui.sign_in

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.narcissus.marketplace.R
import com.narcissus.marketplace.core.util.launchWhenStarted
import com.narcissus.marketplace.databinding.FragmentSignInBinding
import com.narcissus.marketplace.domain.util.AuthResult
import kotlinx.coroutines.flow.onEach
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
    }

    private fun observeAuthState() {
        viewModel.authResultFlow.onEach { authResult ->
            when (authResult) {
                is AuthResult.SignInSuccess -> navigateToCallScreen(isNavigatedFromUserProfile)
                is AuthResult.SignInWrongPasswordOrEmail -> setPasswordInputLayoutError()
                is AuthResult.Error -> showErrorToast()
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
}
