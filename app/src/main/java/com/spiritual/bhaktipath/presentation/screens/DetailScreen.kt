package com.spiritual.bhaktipath.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.spiritual.bhaktipath.presentation.viewmodel.DetailViewModel
import com.spiritual.bhaktipath.ui.theme.BhaktiPathTheme
import com.spiritual.bhaktipath.utils.TrackScreen

// --------------------------------------------------
// Spiritual Colors
// --------------------------------------------------

private val Maroon = Color(0xFF7B263A)
private val DeepMaroon = Color(0xFF5B172B)
private val Saffron = Color(0xFFD97706)
private val DeepSaffron = Color(0xFFB45309)
private val Gold = Color(0xFFD4A34A)

private val BackgroundColor = Color(0xFFFFF8ED)
private val TextPrimary = Color(0xFF3D2618)
private val TextSecondary = Color(0xFF8A7563)

// --------------------------------------------------
// Detail Screen
// --------------------------------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    onBackPress: () -> Unit = {},
    viewModel: DetailViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    val topBarTitle = when (uiState) {
        is DetailViewModel.UiState.Success ->
            (uiState as DetailViewModel.UiState.Success).title

        else -> "भक्ति"
    }

    TrackScreen("$topBarTitle :DetailScreen")

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = BackgroundColor,

        topBar = {
            DetailTopBar(
                title = topBarTitle,
                onBackPress = onBackPress
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
                .padding(innerPadding)
        ) {

            when (uiState) {

                is DetailViewModel.UiState.Loading -> {
                    DetailLoadingState()
                }

                is DetailViewModel.UiState.Success -> {
                    DetailSuccessState(
                        successState =
                            uiState as DetailViewModel.UiState.Success
                    )
                }

                is DetailViewModel.UiState.Error -> {
                    DetailErrorState(
                        errorMessage =
                            (uiState as DetailViewModel.UiState.Error).message
                    )
                }
            }
        }
    }
}

// --------------------------------------------------
// Top Bar
// --------------------------------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailTopBar(
    title: String,
    onBackPress: () -> Unit
) {

    CenterAlignedTopAppBar(
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "भक्ति • श्रद्धा • विश्वास",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 10.sp
                )
            }
        },

        navigationIcon = {
            IconButton(onClick = onBackPress) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        },

        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Maroon
        )
    )
}

// --------------------------------------------------
// Loading State
// --------------------------------------------------

@Composable
private fun DetailLoadingState() {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CircularProgressIndicator(
                color = Maroon,
                strokeWidth = 3.dp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "भक्ति सामग्री लोड हो रही है...",
                color = TextSecondary,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "🙏",
                fontSize = 25.sp
            )
        }
    }
}

// --------------------------------------------------
// Success State - No Card & Center Text
// --------------------------------------------------

@Composable
private fun DetailSuccessState(
    successState: DetailViewModel.UiState.Success
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center
    ) {

        // Diya Decoration

        Text(
            text = "🪔",
            fontSize = 36.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Spiritual Divider

        Text(
            text = "✦  ॥ श्री हरि ॥  ✦",
            color = Gold,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Main Content

        Text(
            text = successState.detail ?: "No content available",

            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),

            color = TextPrimary,

            fontSize = 18.sp,

            fontWeight = FontWeight.Normal,

            lineHeight = 32.sp,

            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Bottom Decoration

        Icon(
            imageVector = Icons.Default.SelfImprovement,
            contentDescription = null,
            tint = Saffron
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "श्रद्धा एवं भक्ति से पढ़ें 🙏",

            color = DeepSaffron,

            fontSize = 14.sp,

            fontWeight = FontWeight.SemiBold,

            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Spiritual Quote

        Text(
            text = "हरि नाम ही जीवन का आधार है",

            color = Maroon,

            fontSize = 14.sp,

            fontWeight = FontWeight.Bold,

            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "प्रेम, श्रद्धा और विश्वास के साथ प्रभु का स्मरण करें।",

            color = TextSecondary,

            fontSize = 12.sp,

            lineHeight = 20.sp,

            textAlign = TextAlign.Center
        )
    }
}

// --------------------------------------------------
// Error State
// --------------------------------------------------

@Composable
private fun DetailErrorState(
    errorMessage: String
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "🙏",
            fontSize = 45.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "कुछ समस्या हुई",

            color = Maroon,

            fontSize = 17.sp,

            fontWeight = FontWeight.Bold,

            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = errorMessage,

            color = TextSecondary,

            fontSize = 13.sp,

            textAlign = TextAlign.Center,

            lineHeight = 20.sp
        )
    }
}

// --------------------------------------------------
// Preview
// --------------------------------------------------

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PreviewDetailScreen() {

    BhaktiPathTheme {
        DetailScreen()
    }
}