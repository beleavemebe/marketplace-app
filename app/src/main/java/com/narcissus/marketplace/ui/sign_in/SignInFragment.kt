package com.narcissus.marketplace.ui.sign_in

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
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
        iniToolBar()
        initSignInListener()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.authFlow.collect { authResult ->
                when (authResult) {
                    is AuthResult.SignInSuccess->navigateToCallScreen(isNavigatedFromUserProfile)
                    is AuthResult.SignInWrongPasswordOrEmail->  setInputLayoutError()
                    is AuthResult.Error->showErrorToast()
                }
            }
        }
    }

    private fun showErrorToast() {
        Toast.makeText(context,getString(R.string.something_went_wrong),Toast.LENGTH_SHORT).show()
    }

    private fun navigateToCallScreen(isNavigatedFromUserProfile:Boolean) {
    if(isNavigatedFromUserProfile){
        navigateToUserProfile()
    }
    else{
        //navigateToCheckOut
    }
    }
    private fun setInputLayoutError(){
        binding.layoutEmailPasswordInputs.passwordTextInputLayout.error =
            getString(R.string.wrong_email_or_password)
    }

    private fun iniToolBar() {
        val navController = findNavController()
        val configuration = AppBarConfiguration(navController.graph)
        binding.tbSignIn.setupWithNavController(navController, configuration)
        binding.tbSignIn.setNavigationIcon(R.drawable.ic_close)
    }

    private fun initSignInListener() {
        binding.btnSignInWithEmail.setOnClickListener {
            binding.layoutEmailPasswordInputs.passwordTextInputLayout.error = null
            viewModel.signInWithEmailPassword(
                binding.layoutEmailPasswordInputs.emailTextInputLayout.editText?.text.toString(),
                binding.layoutEmailPasswordInputs.passwordTextInputLayout.editText?.text.toString(),
            )
        }
    }

    private fun navigateToUserProfile() {
        findNavController().navigate(SignInFragmentDirections.actionFragmentSignInToUser())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
