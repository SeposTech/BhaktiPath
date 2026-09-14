package com.spiritual.bhaktipath.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.spiritual.bhaktipath.presentation.viewmodel.ItemsViewModel
import com.spiritual.bhaktipath.ui.theme.BhaktiPathTheme

// Spiritual Colors
private val TopBarColor = Color(0xFFE8751A)
private val PrimaryColor = Color(0xFF9A4D00)
private val PrimaryDark = Color(0xFF743700)
private val BackgroundColor = Color(0xFFFFF8E9)
private val CardColor = Color(0xFFFFFEFB)
private val SearchBackground = Color(0xFFFFFDF9)
private val SearchText = Color(0xFF3F3026)
private val SearchHint = Color(0xFF9A8F86)
private val SelectedTabBackground = Color(0xFFFFE4C2)
private val UnselectedTabColor = Color(0xFF81766D)
private val CardTextColor = Color(0xFF402719)
private val CardSubTextColor = Color(0xFF927E70)
private val IconBackground = Color(0xFFFFE8CA)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: ItemsViewModel = hiltViewModel(),
    onItemClick: (Int) -> Unit = {}
) {
    val selectedTab by viewModel.selectedTab.collectAsState()

    val tabs = listOf("Chalisa", "Aarti", "Mantra")
    val uiState by viewModel.uiState.collectAsState()

    val items = when (uiState) {
        is ItemsViewModel.UiState.Success -> {
            val itemsData = (uiState as ItemsViewModel.UiState.Success).items
            itemsData.firstOrNull()?.let { data ->
                when (selectedTab) {
                    0 -> data.chalisa
                    1 -> data.aarti
                    else -> data.mantra
                }
            } ?: emptyList()
        }

        else -> emptyList()
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = BackgroundColor,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "🪔 Bhakti Path",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = TopBarColor
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 20.dp,
                        end = 20.dp,
                        top = 18.dp,
                        bottom = 8.dp
                    )
            ) {
                Text(
                    text = "🪔 Bhakti Path",
                    color = PrimaryDark,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Begin your day with devotion",
                    color = CardSubTextColor,
                    fontSize = 13.sp
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 10.dp
                    ),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                tabs.forEachIndexed { index, title ->
                    DevotionalTab(
                        title = title,
                        selected = selectedTab == index,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            viewModel.setSelectedTab(index)
                        }
                    )
                }
            }

            when (uiState) {
                is ItemsViewModel.UiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = PrimaryColor)
                    }
                }

                is ItemsViewModel.UiState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 30.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "⚠️", fontSize = 42.sp)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Failed to load content",
                            color = PrimaryDark,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = (uiState as ItemsViewModel.UiState.Error).message,
                            color = CardSubTextColor,
                            fontSize = 13.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                is ItemsViewModel.UiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            start = 16.dp,
                            end = 16.dp,
                            top = 8.dp,
                            bottom = 24.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(items) { item ->
                            BhaktiItem(
                                title = item.title,
                                type = tabs[selectedTab],
                                onClick = {
                                    onItemClick(item.id)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DevotionalTab(
    title: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) {
                SelectedTabBackground
            } else {
                Color.White.copy(alpha = 0.75f)
            }
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (selected) 2.dp else 0.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 11.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                color = if (selected) PrimaryDark else UnselectedTabColor,
                fontSize = 14.sp,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
            )
        }
    }
}

@Composable
private fun BhaktiItem(
    title: String,
    type: String,
    onClick: () -> Unit
) {
    val icon = when (type) {
        "Chalisa" -> Icons.Default.MenuBook
        "Aarti" -> Icons.Default.MusicNote
        else -> Icons.Default.SelfImprovement
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = CardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = IconBackground,
                        shape = RoundedCornerShape(14.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = PrimaryColor,
                    modifier = Modifier.size(25.dp)
                )
            }

            Spacer(modifier = Modifier.size(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = CardTextColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "Read & listen",
                    color = CardSubTextColor,
                    fontSize = 12.sp
                )
            }

            IconButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.Default.ArrowForwardIos,
                    contentDescription = "Open",
                    tint = PrimaryColor,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewHomeScreen() {
    BhaktiPathTheme {
        HomeScreen()
    }
}