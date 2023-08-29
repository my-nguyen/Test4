package com.nguyen.test4.data.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.nguyen.test4.getOrAwaitValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

// optional and recommended
@SmallTest
@HiltAndroidTest
class ShoppingDaoTest {
    @Inject
    @Named("test_db")
    lateinit var database: ShoppingDatabase

    private lateinit var dao: ShoppingDao

    // InstantTaskExecutorRule is a JUnit Test Rule that swaps the background executor used by
    // the Architecture Components with a different one which executes each task synchronously
    // otherwise error: Cannot invoke observeForever on a background thread
    // from line 46 of getOrAwaitValue() when calling this.observeForever(observer)
    @get:Rule
    var executorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.shoppingDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    // this test causes error: Can not perform this action after onSaveInstanceState
    // even though I copied launchFragmentInHiltContainer() from the internet/Google
    /*@Test
    fun testLaunchFragmentInHiltContainer() {
        launchFragmentInHiltContainer<ShoppingFragment> {

        }
    }*/

    // runBlocking/runTest is a way to run a coroutine on the main thread
    @Test
    fun insertShoppingItem() = runTest {
        val item = ShoppingItem("Computer", 1, 1000f, "url", id = 1)
        dao.insertShoppingItem(item)

        // since observeAllShoppingItems() returns a LiveData, we need to unwrap the "LiveData" part
        // getOrAwaitValue() waits until the LiveData finishes then returns the value wrapped by LiveData
        val allItems = dao.observeAllShoppingItems().getOrAwaitValue()
        assertThat(allItems).contains(item)
    }

    @Test
    fun deleteShoppingItem() = runTest {
        val item = ShoppingItem("Computer", 1, 1000f, "url", id = 1)
        dao.insertShoppingItem(item)
        dao.deleteShoppingItem(item)

        val allItems = dao.observeAllShoppingItems().getOrAwaitValue()
        assertThat(allItems).doesNotContain(item)
    }

    @Test
    fun observeTotalPriceSum() = runTest {
        val item1 = ShoppingItem("Computer", 2, 10f, "url", id = 1)
        val item2 = ShoppingItem("Computer", 4, 5.5f, "url", id = 2)
        val item3 = ShoppingItem("Computer", 0, 100f, "url", id = 3)
        dao.insertShoppingItem(item1)
        dao.insertShoppingItem(item2)
        dao.insertShoppingItem(item3)

        val total = dao.observeTotalPrice().getOrAwaitValue()
        assertThat(total).isEqualTo(2*10 + 4*5.5f + 0*100)
    }
}