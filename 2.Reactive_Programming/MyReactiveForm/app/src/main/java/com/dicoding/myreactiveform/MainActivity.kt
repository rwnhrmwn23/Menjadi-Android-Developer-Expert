package com.dicoding.myreactiveform

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dicoding.myreactiveform.databinding.ActivityMainBinding
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.functions.Function3

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            val emailStream = RxTextView.textChanges(edEmail)
                .skipInitialValue()
                .map { email ->
                    !Patterns.EMAIL_ADDRESS.matcher(email).matches()
                }
            emailStream.subscribe {
                showEmailExistAlert(it)
            }

            val passwordStream = RxTextView.textChanges(edPassword)
                .skipInitialValue()
                .map { password ->
                    password.length < 6
                }
            passwordStream.subscribe {
                showPasswordMinimalAlert(it)
            }

            val passwordConfirmationStream = Observable.merge(
                RxTextView.textChanges(edPassword)
                    .map { password ->
                        password.toString() != edConfirmPassword.text.toString()
                    },
                RxTextView.textChanges(edConfirmPassword)
                    .map { confirmPassword ->
                        confirmPassword.toString() != edPassword.text.toString()
                    }
            )
            passwordConfirmationStream.subscribe {
                showPasswordConfirmationAlert(it)
            }

            val invalidFieldStream = Observable.combineLatest(
                emailStream,
                passwordStream,
                passwordConfirmationStream,
                Function3 { emailInvalid: Boolean, passwordInvalid: Boolean, passwordConfirmationInvalid: Boolean ->
                    !emailInvalid && !passwordInvalid && !passwordConfirmationInvalid
                }
            )
            invalidFieldStream.subscribe {
                if (it) {
                    this.btnRegister.isEnabled  = true
                    this.btnRegister.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.purple_500))
                } else {
                    this.btnRegister.isEnabled  = false
                    this.btnRegister.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.darker_gray))
                }
            }
        }
    }

    private fun showEmailExistAlert(isNotValid: Boolean) {
        binding.edEmail.error = if (isNotValid) getString(R.string.email_not_valid) else null
    }

    private fun showPasswordMinimalAlert(isNotValid: Boolean) {
        binding.edPassword.error = if (isNotValid) getString(R.string.password_not_valid) else null
    }

    private fun showPasswordConfirmationAlert(isNotValid: Boolean) {
        binding.edConfirmPassword.error = if (isNotValid) getString(R.string.password_not_same) else null
    }
}
