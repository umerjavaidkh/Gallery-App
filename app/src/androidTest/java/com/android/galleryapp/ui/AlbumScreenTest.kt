package com.android.galleryapp.ui

import android.net.Uri
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
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

class AlbumScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

   /* @get:Rule
    val permissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )*/

    private val mockAlbums = listOf(
        Album("Album1", listOf(MediaFile(1, Uri.parse("/path/file1"), "file1", MediaType.IMAGE))),
        Album("Album2", listOf(MediaFile(2, Uri.parse("/path/file2"), "file2", MediaType.VIDEO)))
    )

    private val  currentUiState = MutableStateFlow<AlbumUiState>(AlbumUiState.Success(mockAlbums))

    private var mockedViewModel: SharedGalleryViewModel = mockk(relaxed = true) {
        every { uiState } returns currentUiState
    }

    @Test
    fun should_display_content_and_heading() {
        // Given
        var headingText = ""
        composeRule.setContent {
            AlbumScreen(
                viewModel = mockedViewModel
            )

            // When
            headingText = stringResource(R.string.album_screen)
        }

        // then
        composeRule.onNodeWithText(headingText).assertIsDisplayed()
        composeRule.onNodeWithText("Album2").assertIsDisplayed()
        composeRule.onNodeWithText("Album1").assertIsDisplayed()
    }

    @Test
    fun should_perform_click_aciton() {
        // Given
        val albumName = "Album1"
        composeRule.setContent {
            AlbumScreen(
                viewModel = mockedViewModel
            )
        }

        // When
        composeRule.onNodeWithText(albumName).performClick()

        // then
        verify { mockedViewModel.openDetailScreen(any()) }
    }

}