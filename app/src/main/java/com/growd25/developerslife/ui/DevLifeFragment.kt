package com.growd25.developerslife.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.growd25.developerslife.R
import com.growd25.developerslife.model.PostsCategory
import com.growd25.developerslife.presentation.Message
import com.growd25.developerslife.presentation.DevLifeVmFactory
import com.growd25.developerslife.presentation.DevLifeViewModel
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
        devLifeViewModel.viewState.observe(viewLifecycleOwner, Observer(::consumeState))
        tab_layout.addOnTabSelectedListener(SimpleOnTabSelectedListener { tab ->
            val category = when (tab.position) {
                0 -> PostsCategory.LATEST
                1 -> PostsCategory.TOP
                2 -> PostsCategory.HOT
                else -> error("Unknown tab position")
            }
            devLifeViewModel.acceptMessage(Message.OnCategoryChanged(category))
        })
        prev_button.setOnClickListener { devLifeViewModel.acceptMessage(Message.OnPrevClicked) }
        next_button.setOnClickListener { devLifeViewModel.acceptMessage(Message.OnNextClicked) }
        error_button.setOnClickListener { devLifeViewModel.acceptMessage(Message.OnRetryClicked) }
    }

    private fun consumeState(state: DevLifeViewState) {
        Log.i("den", "consumeState:$state")
        val tabIndex = when (state.category) {
            PostsCategory.LATEST -> 0
            PostsCategory.TOP -> 1
            PostsCategory.HOT -> 2
        }
        tab_layout.getTabAt(tabIndex)?.select()
        progress_bar.visibility(state.isProgressVisible)
        post_group.visibility(state.isPostVisible)
        error_group.visibility(state.isErrorVisible)
        Glide.with(this).asGif().load(state.post?.gifURL).into(post_image)
        description_text.text = state.post?.description
        prev_button.isEnabled = state.isPrevButtonEnabled
        post_group.visibility(state.isPostVisible)
        post_group.visibility(state.isPostVisible)

    }

    companion object {
        fun newInstance() = DevLifeFragment()
    }
}
