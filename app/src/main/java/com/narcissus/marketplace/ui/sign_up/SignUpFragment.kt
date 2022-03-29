package com.narcissus.marketplace.ui.sign_up

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.narcissus.marketplace.R
import com.narcissus.marketplace.core.util.launchWhenStarted
import com.narcissus.marketplace.databinding.FragmentSignUpBinding
import com.narcissus.marketplace.domain.auth.PasswordRequirement
import com.narcissus.marketplace.domain.auth.SignUpResult
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignUpViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignUpBinding.bind(view)
        initToolbar()
        initTextChangedListeners()
        initSignUpButton()
        observeSignUpState()
    }

    private fun initToolbar() {
        val navController = findNavController()
        binding.tbSignUp.setupWithNavController(navController)
    }

    private fun initTextChangedListeners() {
        binding.etFullName.doAfterTextChanged {
            binding.tiFullName.error = null
        }

        binding.layoutEmailPasswordInputs.etEmail.doAfterTextChanged {
            binding.layoutEmailPasswordInputs.tiEmail.error = null
        }

        binding.layoutEmailPasswordInputs.etPassword.doAfterTextChanged {
            binding.layoutEmailPasswordInputs.tiPassword.error = null
        }
    }

    private fun initSignUpButton() {
        binding.btnSignUpWithEmail.setOnClickListener {
            viewModel.signUpWithEmailPassword(
                binding.etFullName.text.toString(),
                binding.layoutEmailPasswordInputs.etEmail.text.toString(),
                binding.layoutEmailPasswordInputs.etPassword.text.toString(),
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
        binding.layoutEmailPasswordInputs.tiEmail.helperText =
            getString(R.string.wrong_email)
    }

    private fun setPasswordLayoutError(failedRequirements: List<PasswordRequirement>) {
        binding.layoutEmailPasswordInputs.tiPassword.helperText =
            when (failedRequirements.first()) {
                is PasswordRequirement.NotBlank ->
                    getString(R.string.password_is_blank)
                is PasswordRequirement.NotTooShort ->
                    getString(R.string.password_is_too_short)
                is PasswordRequirement.HasLowercaseLetter ->
                    getString(R.string.missing_lowercase_letter)
                is PasswordRequirement.HasNumber ->
                    getString(R.string.missing_number)
                is PasswordRequirement.HasUppercaseLetter ->
                    getString(R.string.missing_uppercase_letter)
            }
    }

    private fun navigateTo() {
    }

    private fun showErrorToast() {
        Toast.makeText(context, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
