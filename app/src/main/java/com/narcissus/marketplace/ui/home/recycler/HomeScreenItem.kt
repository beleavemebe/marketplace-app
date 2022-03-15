package com.narcissus.marketplace.ui.home.recycler

import android.animation.Animator
import android.animation.AnimatorInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.recyclerview.widget.DiffUtil
import coil.load
import com.google.android.material.tabs.TabLayout
import com.hannesdorfmann.adapterdelegates4.dsl.AdapterDelegateViewBindingViewHolder
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.ListItemBannerBinding
import com.narcissus.marketplace.databinding.ListItemFeaturedContentBinding
import com.narcissus.marketplace.databinding.ListItemHeadlineBinding
import com.narcissus.marketplace.databinding.ListItemProductListBinding
import com.narcissus.marketplace.model.SpecialOfferBanner
import com.narcissus.marketplace.ui.products.ProductListItem
import com.narcissus.marketplace.ui.products.ProductsAdapter
import java.lang.Exception

typealias HeadlineBinding = ListItemHeadlineBinding
typealias BannerBinding = ListItemBannerBinding
typealias ProductsOfTheDayBinding = ListItemProductListBinding
typealias FeaturedContentBinding = ListItemFeaturedContentBinding
typealias FeaturedDelegate = AdapterDelegateViewBindingViewHolder<HomeScreenItem.FeaturedTabs, FeaturedContentBinding>
typealias ProductListBinding = ListItemProductListBinding

sealed class HomeScreenItem {
    data class Headline(@StringRes val titleResId: Int) : HomeScreenItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                inflater: LayoutInflater,
                parent: ViewGroup,
            ) = ListItemHeadlineBinding.inflate(inflater, parent, false)

            val delegate
                get() =
                    adapterDelegateViewBinding<Headline, HomeScreenItem, HeadlineBinding>(
                        ::inflateBinding,
                    ) {
                        bind {
                            binding.tvDetailsTitle.text = context
                                .getString(item.titleResId)
                        }
                    }
        }
    }

    data class Banners(val banner: SpecialOfferBanner) : HomeScreenItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                inflater: LayoutInflater,
                parent: ViewGroup,
            ) = BannerBinding.inflate(inflater, parent, false)

            fun delegate(onClick: (link: String) -> Unit) =
                adapterDelegateViewBinding<Banners, HomeScreenItem, BannerBinding>(
                    ::inflateBinding,
                ) {
                    bind {
                        binding.ivImage.load(item.banner.imgUrl)
                        binding.root.setOnClickListener {
                            onClick(item.banner.destinationLink)
                        }
                    }
                }
        }
    }

    data class ProductsOfTheDay(val products: List<ProductOfTheDayItem>) : HomeScreenItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                inflater: LayoutInflater,
                parent: ViewGroup,
            ) = ProductsOfTheDayBinding.inflate(inflater, parent, false)

            fun delegate(onProductClicked: (id: String) -> Unit) =
                adapterDelegateViewBinding<ProductsOfTheDay, HomeScreenItem, ProductsOfTheDayBinding>(
                    ::inflateBinding,
                ) {
                    val adapter = ProductsOfTheDayAdapter(onProductClicked)
                    binding.rvProducts.adapter = adapter
                    bind {
                        adapter.items = item.products
                    }
                }
        }
    }

    class FeaturedTabs : HomeScreenItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                inflater: LayoutInflater,
                parent: ViewGroup,
            ) = FeaturedContentBinding.inflate(inflater, parent, false)

            fun delegate(onTabSelected: (FeaturedTab) -> Unit) =
                adapterDelegateViewBinding<FeaturedTabs, HomeScreenItem, FeaturedContentBinding>(
                    ::inflateBinding,
                ) {
                    val increaseTabAnimator = AnimatorInflater.loadAnimator(context, R.animator.increase_tab)
                    val decreaseTabAnimator = AnimatorInflater.loadAnimator(context, R.animator.decrease_tab)

                    initOnTabSelectedListener(onTabSelected, increaseTabAnimator, decreaseTabAnimator)
                    inflateTabs()
                }

            private fun FeaturedDelegate.initOnTabSelectedListener(
                onTabSelected: (FeaturedTab) -> Unit,
                increaseTabAnimator: Animator,
                decreaseTabAnimator: Animator,
            ) {
                val onTabSelectedListener =
                    createOnProductsTabSelectedListener(
                        onTabSelected, increaseTabAnimator, decreaseTabAnimator,
                    )
                binding.tlPages.addOnTabSelectedListener(onTabSelectedListener)
            }

            private fun createOnProductsTabSelectedListener(
                onTabSelected: (FeaturedTab) -> Unit,
                selectedTabAnimator: Animator,
                unselectedAnimator: Animator,
            ) = object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    onTabSelected(tab.getFeaturedTabEnum())
                    val tvToIncrease = tab.customView as? TextView ?: return
                    selectedTabAnimator.setTarget(tvToIncrease)
                    selectedTabAnimator.start()
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                    val tvToDecrease = tab.customView as? TextView ?: return
                    unselectedAnimator.setTarget(tvToDecrease)
                    unselectedAnimator.start()
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {}

                private fun TabLayout.Tab.getFeaturedTabEnum(): FeaturedTab {
                    return when (position) {
                        0 -> FeaturedTab.TOP_RATED
                        1 -> FeaturedTab.TOP_SALES
                        2 -> FeaturedTab.EXPLORE
                        else -> error("Cannot figure out the enum for tab at $position")
                    }
                }
            }

            private fun FeaturedDelegate.inflateTabs() {
                // TODO: logged multiple times. fix pls
                Log.d("FeaturedTabs", "inflate tabs called")

                fun newTab(@StringRes titleResId: Int) =
                    binding.tlPages.newTab().apply {
                        setCustomView(R.layout.tab_view)
                        (customView as TextView).setText(titleResId)
                    }

                binding.tlPages.addTab(newTab(R.string.top_rated))
                binding.tlPages.addTab(newTab(R.string.top_sales))
                binding.tlPages.addTab(newTab(R.string.explore))
            }
        }
    }

    data class Products(val products: List<ProductListItem>) : HomeScreenItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                inflater: LayoutInflater,
                parent: ViewGroup,
            ) = ProductListBinding.inflate(inflater, parent, false)

            fun delegate(onProductClicked: (id: String) -> Unit) =
                adapterDelegateViewBinding<Products, HomeScreenItem, ProductListBinding>(
                    ::inflateBinding,
                ) {
                    val adapter = ProductsAdapter(onProductClicked)
                    binding.rvProducts.adapter = adapter
                    bind {
                        adapter.items = item.products
                    }
                }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HomeScreenItem>() {
            override fun areItemsTheSame(
                oldItem: HomeScreenItem,
                newItem: HomeScreenItem,
            ): Boolean {
                return when (oldItem) {
                    is Banners -> newItem is Banners
                    is FeaturedTabs -> newItem is FeaturedTabs
                    is ProductsOfTheDay -> newItem is ProductsOfTheDay
                    is Headline -> newItem is Headline && oldItem.titleResId == newItem.titleResId
                    is Products -> newItem is Products && oldItem.products == newItem.products
                }
            }

            override fun areContentsTheSame(
                oldItem: HomeScreenItem,
                newItem: HomeScreenItem,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
