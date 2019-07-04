package com.test.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.test.myapplication.databinding.ImageItemBinding
import com.test.myapplication.databinding.ViewPagerTestBinding
import kotlinx.android.synthetic.main.view_pager_test.*

class ViewPagerFragment : Fragment() {
    private lateinit var vpAdapter : VPAdapter
    private val imageList = listOf(
        "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1397110326i/18166936.jpg",
        "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1397110326i/18166936.jpg",
        "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1397110326i/18166936.jpg",
        "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1397110326i/18166936.jpg",
        "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1397110326i/18166936.jpg",
        "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1397110326i/18166936.jpg"
    )

    /**
     * Called to do initial creation of a fragment.  This is called after
     * [.onAttach] and before
     * [.onCreateView].
     *
     *
     * Note that this can be called while the fragment's activity is
     * still in the process of being created.  As such, you can not rely
     * on things like the activity's content view hierarchy being initialized
     * at this point.  If you want to do work once the activity itself is
     * created, see [.onActivityCreated].
     *
     *
     * Any restored child fragments will be created before the base
     * `Fragment.onCreate` method returns.
     *
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vpAdapter = VPAdapter(this)
    }

    /**
         * Called to have the fragment instantiate its user interface view.
         * This is optional, and non-graphical fragments can return null. This will be called between
         * [.onCreate] and [.onActivityCreated].
         *
         * A default View can be returned by calling [.Fragment] in your
         * constructor. Otherwise, this method returns null.
         *
         *
         * It is recommended to **only** inflate the layout in this method and move
         * logic that operates on the returned View to [.onViewCreated].
         *
         *
         * If you return a View from here, you will later be called in
         * [.onDestroyView] when the view is being released.
         *
         * @param inflater The LayoutInflater object that can be used to inflate
         * any views in the fragment,
         * @param container If non-null, this is the parent view that the fragment's
         * UI should be attached to.  The fragment should not add the view itself,
         * but this can be used to generate the LayoutParams of the view.
         * @param savedInstanceState If non-null, this fragment is being re-constructed
         * from a previous saved state as given here.
         *
         * @return Return the View for the fragment's UI, or null.
         */
        override

    fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: ViewPagerTestBinding =
            DataBindingUtil.inflate(inflater, R.layout.view_pager_test, container, false)

        binding.viewPager.apply {
            adapter = vpAdapter
            orientation = ViewPager2.ORIENTATION_VERTICAL
            registerOnPageChangeCallback(pageChangeCallback)
        }

        if(vpAdapter.itemCount == 0) {
            addData()
        }

        return binding.root
    }

    /**
     * Called when the view previously created by [.onCreateView] has
     * been detached from the fragment.  The next time the fragment needs
     * to be displayed, a new view will be created.  This is called
     * after [.onStop] and before [.onDestroy].  It is called
     * *regardless* of whether [.onCreateView] returned a
     * non-null view.  Internally it is called after the view's state has
     * been saved but before it has been removed from its parent.
     */
    override fun onDestroyView() {
        viewPager.unregisterOnPageChangeCallback(pageChangeCallback)
        super.onDestroyView()
    }

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        /**
         * This method will be invoked when a new page becomes selected. Animation is not
         * necessarily complete.
         *
         * @param position Position index of the new selected page.
         */
        override fun onPageSelected(position: Int) {
            if(position == vpAdapter.itemCount - 2) {
                addData()
            }
        }
    }

    private fun addData() {
        vpAdapter.add(imageList)
    }
}

class VPAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {
    private val urls = ArrayList<String>()

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int = urls.size

    override fun createFragment(position: Int): Fragment =
        BaseFragment().apply {
            arguments = Bundle().apply { putString("URL", urls[position]) }
        }

    fun add(items: List<String>) {
        val beforeAdd = urls.size
        urls.addAll(items)
        notifyItemRangeInserted(beforeAdd, items.size)
    }

}

class BaseFragment : Fragment() {
    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null. This will be called between
     * [.onCreate] and [.onActivityCreated].
     *
     * A default View can be returned by calling [.Fragment] in your
     * constructor. Otherwise, this method returns null.
     *
     *
     * It is recommended to **only** inflate the layout in this method and move
     * logic that operates on the returned View to [.onViewCreated].
     *
     *
     * If you return a View from here, you will later be called in
     * [.onDestroyView] when the view is being released.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return Return the View for the fragment's UI, or null.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding : ImageItemBinding=
            DataBindingUtil.inflate(inflater, R.layout.image_item, container, false)

        Glide.with(this).load(requireArguments()["URL"]).into(binding.imageItem)

        return binding.root
    }
}