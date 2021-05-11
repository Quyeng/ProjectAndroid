package com.example.projectandroid.activity.welcome

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.projectandroid.R
import com.example.projectandroid.activity.signin.SignInActivity
import com.example.projectandroid.activity.signup.SignUpActivity
import com.example.projectandroid.activity.welcome.WelcomeActivity
//import kotlinx.android.synthetic.main.welcome.*


class WelcomeActivity : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.welcome,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.id(signin).setOnClickListener {
                    //findNavController().navigate(R.id.action_boardingOneFragment_to_boardingTwoFragment)
                    parentFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<SignUpActivity>(R.id.frag)
                        addToBackStack(null)
                    }
        }

        signup.setOnClickListener {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<SignInActivity>(R.id.frag)
                addToBackStack(null)
            }
        }
    }
}