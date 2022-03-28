package com.narcissus.marketplace.ui.sign_up

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.narcissus.marketplace.R
import com.narcissus.marketplace.core.navigation.destination.SignInDestination
import com.narcissus.marketplace.core.navigation.navigator
import com.narcissus.marketplace.core.util.launchWhenStarted
import com.narcissus.marketplace.databinding.FragmentSignUpBinding
import com.narcissus.marketplace.domain.auth.PasswordRequirement
import com.narcissus.marketplace.domain.auth.SignUpResult
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignUpViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignUpBinding.bind(view)
        initToolBar()
        initSignUpButton()
        initSignInButton()
        observeSignUpState()
    }

    private fun initSignInButton() {
        binding.tvSignInBtnRight.setOnClickListener {
            navigateToSignUp()
        }
    }

    private fun initToolBar() {
        val navController = findNavController()
        binding.tbSignUp.setupWithNavController(navController)
    }

    private fun initSignUpButton() {
        binding.btnSignUpWithEmail.setOnClickListener {
            viewModel.signUpWithEmailPassword(
                binding.etFullName.text.toString(),
                binding.layoutEmailPasswordInputs.emailTextInputLayout.editText?.text.toString(),
                binding.layoutEmailPasswordInputs.passwordTextInputLayout.editText?.text.toString(),
            )
        }
    }

    private fun observeSignUpState() {
        viewModel.signUpResultFlow.onEach { result ->
            when (result) {
                is SignUpResult.BlankFullName -> setNameLayoutError()
                is SignUpResult.Error -> showErrorToast()
                is SignUpResult.InvalidEmail -> setEmailLayoutError()
                is SignUpResult.InvalidPassword -> setPasswordLayoutError(result.failedRequirements)
                is SignUpResult.Success -> navigateTo()
                else -> {}
            }
        }.launchWhenStarted(viewLifecycleOwner.lifecycleScope)
    }

    private fun setNameLayoutError() {
        binding.tiFullName.helperText = getString(R.string.name_empty)
    }

    private fun setEmailLayoutError() {
        binding.layoutEmailPasswordInputs.emailTextInputLayout.helperText =
            getString(R.string.wrong_email)
    }

    private fun setPasswordLayoutError(failedRequirements: List<PasswordRequirement>) {
        binding.layoutEmailPasswordInputs.passwordTextInputLayout.helperText =
            getString(R.string.short_password)
    }

    private fun navigateTo() {
    }

    private fun navigateToSignUp() {
        // todo: infinite loop btw
        val destination: SignInDestination by inject {
            parametersOf(false)
        }
        navigator.navigate(destination)
    }

    private fun showErrorToast() {
        Toast.makeText(context, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
