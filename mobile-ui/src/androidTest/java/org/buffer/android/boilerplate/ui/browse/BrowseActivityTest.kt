package org.buffer.android.boilerplate.ui.browse

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.buffer.android.boilerplate.data.browse.Bufferoo
import org.buffer.android.boilerplate.data.repository.BufferooRepository
import org.buffer.android.boilerplate.ui.R
import org.buffer.android.boilerplate.ui.di.applicationModule
import org.buffer.android.boilerplate.ui.di.browseModule
import org.buffer.android.boilerplate.ui.test.util.BufferooFactory
import org.buffer.android.boilerplate.ui.test.util.RecyclerViewMatcher
import org.junit.*
import org.junit.runner.*
import org.koin.standalone.StandAloneContext.loadKoinModules
import org.koin.standalone.inject
import org.koin.test.KoinTest
import org.koin.test.declareMock

@RunWith(AndroidJUnit4::class)
class BrowseActivityTest : KoinTest {

    val mockBufferooRepository: BufferooRepository by inject()

    @Rule
    @JvmField
    val activity = ActivityTestRule<BrowseActivity>(BrowseActivity::class.java, false, false)

    @Before
    fun setUp() {
        loadKoinModules(applicationModule, browseModule)
        declareMock<BufferooRepository>()
    }

    @Test
    fun activityLaunches() {
        stubBufferooRepositoryGetBufferoos(Single.just(BufferooFactory.makeBufferooList(2)))
        activity.launchActivity(null)
    }

    @Test
    fun bufferoosDisplay() {
        val bufferoos = BufferooFactory.makeBufferooList(1)
        stubBufferooRepositoryGetBufferoos(Single.just(bufferoos))
        activity.launchActivity(null)

        checkBufferooDetailsDisplay(bufferoos[0], 0)
    }

    @Test
    fun bufferoosAreScrollable() {
        val bufferoos = BufferooFactory.makeBufferooList(20)
        stubBufferooRepositoryGetBufferoos(Single.just(bufferoos))
        activity.launchActivity(null)

        bufferoos.forEachIndexed { index, bufferoo ->
            onView(withId(R.id.recycler_browse)).perform(
                    RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(index)
            )
            checkBufferooDetailsDisplay(bufferoo, index)
        }
    }

    private fun checkBufferooDetailsDisplay(bufferoo: Bufferoo, position: Int) {
        onView(RecyclerViewMatcher.withRecyclerView(R.id.recycler_browse).atPosition(position))
                .check(matches(hasDescendant(withText(bufferoo.name))))
        onView(RecyclerViewMatcher.withRecyclerView(R.id.recycler_browse).atPosition(position))
                .check(matches(hasDescendant(withText(bufferoo.title))))
    }

    private fun stubBufferooRepositoryGetBufferoos(single: Single<List<Bufferoo>>) {
        whenever(mockBufferooRepository.getBufferoos())
                .thenReturn(single)
    }
}