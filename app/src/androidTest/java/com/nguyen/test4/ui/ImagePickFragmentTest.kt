package com.nguyen.test4.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.nguyen.test4.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import javax.inject.Inject
import com.nguyen.test4.R
import com.nguyen.test4.adapters.ImagesAdapter
import com.nguyen.test4.getOrAwaitValue
import com.nguyen.test4.repos.FakeRepositoryAndroid
import org.junit.Rule
import org.mockito.Mockito.verify

@HiltAndroidTest
@MediumTest
@ExperimentalCoroutinesApi
class ImagePickFragmentTest {
    @get:Rule
    var executorRule = InstantTaskExecutorRule()
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var factory: ShoppingFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun clickImage_popBackStackAndSetImageUrl() {
        val navController = mock(NavController::class.java)
        val imageUrl = "test"
        val testViewModel = ShoppingViewModel(FakeRepositoryAndroid())
        launchFragmentInHiltContainer<ImagePickFragment>(fragmentFactory = factory) { this as ImagePickFragment
            Navigation.setViewNavController(requireView(), navController)
            imagesAdapter.images = listOf(imageUrl)
            viewModel = testViewModel
        }

        onView(withId(R.id.recycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImagesAdapter.ViewHolder>(
                0, click()
            )
        )

        verify(navController).popBackStack()
        assertThat(testViewModel.imageUrl.getOrAwaitValue()).isEqualTo(imageUrl)
    }
}