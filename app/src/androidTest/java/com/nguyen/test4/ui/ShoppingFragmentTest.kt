package com.nguyen.test4.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.nguyen.test4.R
import com.nguyen.test4.adapters.ItemsAdapter
import com.nguyen.test4.data.room.ShoppingItem
import com.nguyen.test4.getOrAwaitValue
import com.nguyen.test4.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ShoppingFragmentTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    @get:Rule
    var executorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var factory: ShoppingFragmentFactoryAndroid

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun clickAddItem_navigateToAddItemFragment() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<ShoppingFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.fab_add)).perform(click())
        Mockito.verify(navController).navigate(ShoppingFragmentDirections.actionShoppingFragmentToAddItemFragment())
    }

    @Test
    fun swipeShoppingItem_deleteItem() {
        val item = ShoppingItem("test", 3, 3.3f, "", 1)
        var testViewModel: ShoppingViewModel? = null
        launchFragmentInHiltContainer<ShoppingFragment>(fragmentFactory = factory) {this as ShoppingFragment
            testViewModel = viewModel
            viewModel?.insertShoppingItem(item)
        }

        onView(withId(R.id.items)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ItemsAdapter.ViewHolder>(0, swipeLeft())
        )

        assertThat(testViewModel?.items?.getOrAwaitValue()).isEmpty()
    }
}