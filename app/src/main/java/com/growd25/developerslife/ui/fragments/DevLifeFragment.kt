package com.growd25.developerslife.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.growd25.developerslife.R
import com.growd25.developerslife.presentation.DevLifeVmFactory
import com.growd25.developerslife.presentation.DevLifeViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class DevLifeFragment : Fragment(R.layout.fragment_devlife) {

    @Inject lateinit var devLifeVMFactory: DevLifeVmFactory
    private lateinit var devLifeViewModel: DevLifeViewModel

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        devLifeVMFactory = DevLifeVMFactory(DefaultDevLifeRepository())
        devLifeViewModel = ViewModelProvider(this, devLifeVMFactory).get(DevLifeViewModel::class.java)
    }





    companion object {
        fun newInstance() = DevLifeFragment()
    }
}