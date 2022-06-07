package com.app.memorista.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.app.memorista.R
import com.app.memorista.databinding.FragmentIntroBinding
import com.app.memorista.models.UserUI
import com.app.memorista.ui.viewmodels.IntroViewModel
import com.app.memorista.utils.addRepeatedJob
import org.koin.androidx.viewmodel.ext.android.viewModel

class IntroFragment : Fragment(), View.OnClickListener {

    private var mBinding: FragmentIntroBinding? = null
    private val mViewModel by viewModel<IntroViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mBinding == null)
            mBinding = FragmentIntroBinding.inflate(inflater).apply {
                buttonContinue.setOnClickListener(this@IntroFragment)
            }
        handleValidationFlow()
        return mBinding?.root
    }

    private fun handleValidationFlow() {
        viewLifecycleOwner.addRepeatedJob(Lifecycle.State.STARTED) {
            mViewModel.validationFlow.collect { result ->
                if (result.isValid)
                    createUser(result.data)
                else
                    Toast.makeText(requireContext(), result.data, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createUser(name: String) {
        mViewModel.createUser(UserUI(name))
    }

    override fun onClick(v: View?) {
        findNavController().navigate(R.id.action_introFragment_to_homeFragment)
        //mViewModel.validate((mBinding?.editName?.text as? String?) ?: "")
    }

}