package com.narcissus.marketplace.ui.sing_up

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.FragmentSignUpBinding
import com.narcissus.marketplace.domain.util.AuthResult
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignUpViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignUpBinding.bind(view)
        initToolBar()
        initSignUpButton()
        observeSignUpState()
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
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.authResultFlow.collect { authResult ->
                when (authResult) {
                    is AuthResult.SignInSuccess -> navigateTo()
                    is AuthResult.SignUpEmptyInput -> setNameLayoutError()
                    is AuthResult.SignUpWrongEmail -> setEmailLayoutError()
                    is AuthResult.SignUpToShortPassword -> setPasswordLayoutError()
                    is AuthResult.Error -> showErrorToast()
                    else -> {}
                }
            }
        }
    }

    private fun setNameLayoutError() {
        binding.tiFullName.helperText = getString(R.string.name_empty)
    }

    private fun setEmailLayoutError() {
        binding.layoutEmailPasswordInputs.emailTextInputLayout.helperText =
            getString(R.string.wrong_email)
    }

    private fun setPasswordLayoutError() {
        binding.layoutEmailPasswordInputs.passwordTextInputLayout.helperText =
            getString(R.string.short_password)
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
