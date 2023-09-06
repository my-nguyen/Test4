package com.nguyen.test4.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.nguyen.test4.launchFragmentInHiltContainer
import com.nguyen.test4.repos.FakeRepositoryAndroid
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject
import com.nguyen.test4.R
import com.nguyen.test4.data.room.ShoppingItem
import com.nguyen.test4.getOrAwaitValue

@ExperimentalCoroutinesApi
@MediumTest
@HiltAndroidTest
class AddItemFragmentTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    @get:Rule
    var executorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var factory: ShoppingFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun pressBackButton_popBackStack() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<AddItemFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }
        pressBack()
        verify(navController).popBackStack()
    }

    @Test
    fun clickInsertIntoDb_shoppingItemInserted() {
        val testViewModel = ShoppingViewModel(FakeRepositoryAndroid())
        launchFragmentInHiltContainer<AddItemFragment>(fragmentFactory = factory) { this as AddItemFragment
            viewModel = testViewModel
        }

        onView(withId(R.id.name)).perform(replaceText("shopping item"))
        onView(withId(R.id.amount)).perform(replaceText("5"))
        onView(withId(R.id.price)).perform(replaceText("5.5"))
        onView(withId(R.id.add_item)).perform(click())

        assertThat(testViewModel.items.getOrAwaitValue())
            .contains(ShoppingItem("shopping item", 5, 5.5f, ""))
    }
}