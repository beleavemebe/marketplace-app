package com.narcissus.marketplace.ui.sign_in

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.FragmentSignInBinding
import com.narcissus.marketplace.domain.util.AuthResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.authFlow.collect {
                if (it is AuthResult.SignInSuccess && isNavigatedFromUserProfile) {
                    navigateToUserProfile()
                } else if (it is AuthResult.SignInSuccess && !isNavigatedFromUserProfile) {

                } else if (it is AuthResult.SignInWrongPasswordOrEmail) {
                    binding.layoutEmailPasswordInputs.passwordTextInputLayout.error =
                        getString(R.string.wrong_email_or_password)
                }
            }
        }
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
