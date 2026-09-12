package com.spiritual.bhaktipath.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.spiritual.bhaktipath.presentation.viewmodel.DetailViewModel
import com.spiritual.bhaktipath.ui.theme.BhaktiPathTheme

private val TopBarColor = Color(0xFFE8751A)
private val PrimaryDark = Color(0xFF743700)
private val BackgroundColor = Color(0xFFFFF8E9)
private val CardSubTextColor = Color(0xFF927E70)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    onBackPress: () -> Unit = {},
    viewModel: DetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val topBarTitle = when (uiState) {
        is DetailViewModel.UiState.Success -> (uiState as DetailViewModel.UiState.Success).title
        else -> "Detail"
    }

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
                        successState = uiState as DetailViewModel.UiState.Success
                    )
                }

                is DetailViewModel.UiState.Error -> {
                    DetailErrorState(
                        errorMessage = (uiState as DetailViewModel.UiState.Error).message
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailTopBar(
    title: String,
    onBackPress: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
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
            containerColor = TopBarColor
        )
    )
}

@Composable
private fun DetailLoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = PrimaryDark
        )
    }
}

@Composable
private fun DetailSuccessState(successState: DetailViewModel.UiState.Success) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = successState.detail ?: "No content available",
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 20.dp,
                    top = 32.dp,
                    end = 20.dp,
                    bottom = 32.dp
                ),
            color = Color(0xFF5D4037),
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 32.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun DetailErrorState(errorMessage: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "⚠️",
            fontSize = 42.sp
        )

        Text(
            text = errorMessage,
            color = CardSubTextColor,
            fontSize = 13.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDetailScreen() {
    BhaktiPathTheme {
        DetailScreen()
    }
}