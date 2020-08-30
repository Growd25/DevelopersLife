package com.growd25.developerslife.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.growd25.developerslife.R
import com.growd25.developerslife.model.PostsCategory
import com.growd25.developerslife.presentation.*
import com.growd25.developerslife.utils.SimpleOnTabSelectedListener
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
        devLifeViewModel.viewStateLiveData.observe(viewLifecycleOwner, Observer(::observeViewState))
        tab_layout.addOnTabSelectedListener(SimpleOnTabSelectedListener { tab ->
            devLifeViewModel.onCategoryChanged(tab.category)
        })
        prev_button.setOnClickListener { devLifeViewModel.onPrevClicked() }
        next_button.setOnClickListener { devLifeViewModel.onNextClicked() }
        error_button.setOnClickListener { devLifeViewModel.onRetryClicked() }
    }

    private fun observeViewState(viewState: ViewState) {
        tab_layout.getTabAt(viewState.category.tabPosition)?.select()
        post_group.visibility(viewState.dataState == DataState.LOADED)
        error_group.visibility(viewState.dataState == DataState.ERROR)
        progress_bar.visibility(viewState.dataState == DataState.LOADING)
        prev_button.isEnabled = viewState.isPrevEnabled
        description_text.text = viewState.post?.description
        Glide.with(this).asGif().load(viewState.post?.gifURL).into(post_image)
    }

    private val TabLayout.Tab.category
        get() =  when (position) {
            0 -> PostsCategory.LATEST
            1 -> PostsCategory.TOP
            2 -> PostsCategory.HOT
            else -> error("Unknown tab position")
        }

    private val PostsCategory.tabPosition
        get() = when (this) {
            PostsCategory.LATEST -> 0
            PostsCategory.TOP -> 1
            PostsCategory.HOT -> 2
        }

    companion object {
        fun newInstance() = DevLifeFragment()
    }
}
