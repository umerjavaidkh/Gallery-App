package com.android.galleryapp.ui

import android.net.Uri
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.android.galleryapp.R
import com.android.galleryapp.data.model.Album
import com.android.galleryapp.data.model.MediaFile
import com.android.galleryapp.data.model.MediaType
import com.android.galleryapp.viewmodel.SharedGalleryViewModel
import com.android.galleryapp.viewmodel.uistate.AlbumUiState
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class DetailScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val mockAlbums = listOf(
        Album("Album1", listOf(MediaFile(1, Uri.parse("/path/file1"), "file1", MediaType.IMAGE))),
        Album("Album2", listOf(MediaFile(2, Uri.parse("/path/file2"), "file2", MediaType.VIDEO)))
    )

    private val  currentUiState = MutableStateFlow<AlbumUiState>(AlbumUiState.Success(mockAlbums))

    private var mockedViewModel: SharedGalleryViewModel = mockk(relaxed = true) {
        every { uiState } returns currentUiState
        every { getMediaFilesForAlbum(any()) } returns mockAlbums[0].media!!
    }

    @Test
    fun should_display_content_and_heading()  {
        // Given
        val albumId = "Album1"
        val mockedViewModel = mockedViewModel

        composeRule.setContent {
            DetailScreen(
                albumId,
                viewModel = mockedViewModel
            )
        }

        // then
        composeRule.onNodeWithText(albumId).assertIsDisplayed()
        composeRule.onNodeWithContentDescription("file1").assertExists()
    }

    @Test
    fun should_call_go_back_form_view_model() {
        // Given
        val albumId = "Album1"
        var iconContentDescription = ""
        val mockedViewModel = mockedViewModel
        composeRule.setContent {
            DetailScreen(
                albumId,
                viewModel = mockedViewModel
            )

            iconContentDescription = stringResource(R.string.navigation_icon_dec)
        }

        // When
        composeRule.onNodeWithContentDescription(iconContentDescription).performClick()

        // then
        verify { mockedViewModel.goBack() }
    }

}