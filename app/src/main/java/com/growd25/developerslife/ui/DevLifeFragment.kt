package com.growd25.developerslife.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.growd25.developerslife.R
import com.growd25.developerslife.model.Post
import com.growd25.developerslife.presentation.DataState
import com.growd25.developerslife.presentation.DevLifeVmFactory
import com.growd25.developerslife.presentation.DevLifeViewModel
import com.growd25.developerslife.presentation.PostState
import com.growd25.developerslife.utils.visibility
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_devlife.*
import javax.inject.Inject

class DevLifeFragment : Fragment(R.layout.fragment_devlife) {

    @Inject
    lateinit var devLifeVMFactory: DevLifeVmFactory
    private lateinit var devLifeViewModel: DevLifeViewModel

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        devLifeViewModel = ViewModelProvider(this, devLifeVMFactory)
            .get(DevLifeViewModel::class.java)
        devLifeViewModel.postStateLiveData.observe(viewLifecycleOwner, Observer(::observePostState))
        prev_button.setOnClickListener { devLifeViewModel.onPrevClicked() }
        next_button.setOnClickListener { devLifeViewModel.onNextClicked() }
        error_button.setOnClickListener { devLifeViewModel.onRetryClicked() }
    }

    private fun observePostState(postState: PostState) {
        post_group.visibility(postState.dataState == DataState.LOADED)
        error_group.visibility(postState.dataState == DataState.ERROR)
        progress_bar.visibility(postState.dataState == DataState.LOADING)
        prev_button.isEnabled = postState.isPrevEnabled
        description_text.text = postState.post?.description
        Glide.with(this).asGif().load(postState.post?.gifURL).into(post_image)
    }

    companion object {
        fun newInstance() = DevLifeFragment()
    }
}
