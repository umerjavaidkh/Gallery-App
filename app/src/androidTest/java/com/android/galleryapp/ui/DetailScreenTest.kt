package com.android.galleryapp.ui

import android.net.Uri
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.android.galleryapp.R
import com.android.galleryapp.data.model.Album
import com.android.galleryapp.data.model.MediaFile
import com.android.galleryapp.data.model.MediaType
import com.android.galleryapp.viewmodel.SharedGalleryViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class DetailScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val mockAlbums = listOf(
        Album("Album1", Uri.parse("/path/file1"), listOf(MediaFile(1, Uri.parse("/path/file1"), "file1", "/path/file1", MediaType.IMAGE))),
        Album("Album2", Uri.parse("/path/file2"), listOf(MediaFile(2, Uri.parse("/path/file2"), "file2", "/path/file2", MediaType.VIDEO)))
    )
    private val albumsFlowData = MutableStateFlow(mockAlbums)

    private val mockedViewModel: SharedGalleryViewModel = mockk(relaxed = true) {
        every { albumsFlow } returns albumsFlowData
        every { getMediaFilesForAlbum(any()) } returns mockAlbums[0].media
    }

    @Test
    fun should_display_content_and_heading()  {
        // Given
        var headingText = ""
        val albumId = "Album1"
        val mockedViewModel = mockedViewModel

        composeRule.setContent {
            DetailScreen(
                albumId,
                viewModel = mockedViewModel
            )

            // When
            headingText = stringResource(R.string.detail_screen)
        }

        // then
        composeRule.onNodeWithText(headingText).assertIsDisplayed()
        composeRule.onNodeWithContentDescription("file1").assertExists()
    }

}