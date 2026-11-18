package com.spiritual.bhaktipath.presentation.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.spiritual.bhaktipath.presentation.viewmodel.ItemsViewModel
import com.spiritual.bhaktipath.ui.theme.BhaktiPathTheme
import com.spiritual.bhaktipath.utils.AnalyticsHelper.logEvent
import com.spiritual.bhaktipath.utils.TrackScreen
import androidx.compose.ui.tooling.preview.Preview

// --------------------------------------------------
// Spiritual Theme Colors
// --------------------------------------------------

private val Saffron = Color(0xFFD97706)
private val DeepSaffron = Color(0xFFB45309)
private val Maroon = Color(0xFF7B263A)
private val DeepMaroon = Color(0xFF5B172B)
private val Gold = Color(0xFFD4A34A)

private val Background = Color(0xFFFFF8ED)
private val CardBackground = Color(0xFFFFFEFC)
private val LightSaffron = Color(0xFFFFE8C5)
private val SoftGold = Color(0xFFFFF0D6)

private val TextPrimary = Color(0xFF3D2618)
private val TextSecondary = Color(0xFF8A7563)
private val BorderColor = Color(0xFFF0DFC6)

// --------------------------------------------------
// Home Screen
// --------------------------------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: ItemsViewModel = hiltViewModel(),
    onItemClick: (Int) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    TrackScreen("HomeScreen")

    val selectedTab by viewModel.selectedTab.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    var selectedBottomItem by remember {
        mutableIntStateOf(0)
    }

    val context = LocalContext.current

    val tabs = listOf("चालीसा", "आरती", "मंत्र")

    val items = when (uiState) {
        is ItemsViewModel.UiState.Success -> {
            val itemsData =
                (uiState as ItemsViewModel.UiState.Success).items

            itemsData.firstOrNull()?.let { data ->
                TrackScreen("Selected Tab in Home Screen is $selectedTab")

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
        containerColor = Background,

        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "🪔 भक्तिपथ",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Text(
                            text = "भक्ति • शांति • सकारात्मकता",
                            fontSize = 10.sp,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                },

                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Maroon
                )
            )
        },

        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 4.dp
            ) {

                NavigationBarItem(
                    selected = selectedBottomItem == 0,
                    onClick = {
                        selectedBottomItem = 0
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text("होम")
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Maroon,
                        selectedTextColor = Maroon,
                        indicatorColor = LightSaffron,
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary
                    )
                )

                NavigationBarItem(
                    selected = selectedBottomItem == 1,
                    onClick = {
                        selectedBottomItem = 1

                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            "market://details?id=com.spiritual.bhaktipath".toUri()
                        )

                        try {
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            val webIntent = Intent(
                                Intent.ACTION_VIEW,
                                "https://play.google.com/store/apps/details?id=com.spiritual.bhaktipath".toUri()
                            )
                            context.startActivity(webIntent)
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text("रेटिंग")
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Maroon,
                        selectedTextColor = Maroon,
                        indicatorColor = LightSaffron,
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary
                    )
                )

                NavigationBarItem(
                    selected = selectedBottomItem == 2,
                    onClick = {
                        selectedBottomItem = 2

                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(
                                Intent.EXTRA_SUBJECT,
                                "भक्तिपथ"
                            )
                            putExtra(
                                Intent.EXTRA_TEXT,
                                "🙏 भक्तिपथ ऐप डाउनलोड करें:\n\n" +
                                        "चालीसा, आरती और मंत्र के साथ " +
                                        "भक्ति के मार्ग पर चलें।\n\n" +
                                        "https://play.google.com/store/apps/details?id=com.spiritual.bhaktipath"
                            )
                        }

                        context.startActivity(
                            Intent.createChooser(
                                shareIntent,
                                "ऐप शेयर करें"
                            )
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text("शेयर")
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Maroon,
                        selectedTextColor = Maroon,
                        indicatorColor = LightSaffron,
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary
                    )
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(innerPadding)
        ) {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 12.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // ------------------------------------------
                // Spiritual Hero Section
                // ------------------------------------------

                item {
                    SpiritualHeroCard()
                }

                // ------------------------------------------
                // Daily Spiritual Thought
                // ------------------------------------------

                item {
                    DailyThoughtCard()
                }

                // ------------------------------------------
                // Section Heading
                // ------------------------------------------

                item {
                    Column {
                        Text(
                            text = "🙏 भक्ति का मार्ग",
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold,
                            color = Maroon
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "अपने आराध्य के स्मरण से दिन की शुरुआत करें",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }
                }

                // ------------------------------------------
                // Tabs
                // ------------------------------------------

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
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
                }

                // ------------------------------------------
                // Loading State
                // ------------------------------------------

                if (uiState is ItemsViewModel.UiState.Loading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CircularProgressIndicator(
                                    color = Maroon
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                Text(
                                    text = "भक्ति सामग्री लोड हो रही है...",
                                    color = TextSecondary,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }

                // ------------------------------------------
                // Error State
                // ------------------------------------------

                if (uiState is ItemsViewModel.UiState.Error) {
                    item {
                        ErrorContent(
                            message = (uiState as ItemsViewModel.UiState.Error).message
                        )
                    }
                }

                // ------------------------------------------
                // Content List
                // ------------------------------------------

                if (uiState is ItemsViewModel.UiState.Success) {

                    if (items.isEmpty()) {
                        item {
                            EmptyContent()
                        }
                    } else {

                        items(
                            items = items,
                            key = { item -> item.id }
                        ) { item ->

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

                // ------------------------------------------
                // Bottom Spiritual Message
                // ------------------------------------------

                item {
                    BottomSpiritualMessage()
                }
            }
        }
    }
}

// --------------------------------------------------
// Hero Card
// --------------------------------------------------

@Composable
private fun SpiritualHeroCard() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(190.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Maroon,
                        DeepMaroon,
                        Color(0xFF8D3A20)
                    )
                ),
                shape = RoundedCornerShape(24.dp)
            )
            .border(
                width = 1.dp,
                color = Gold.copy(alpha = 0.5f),
                shape = RoundedCornerShape(24.dp)
            )
            .padding(20.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column {

                Text(
                    text = "॥ श्री गणेशाय नमः ॥",
                    color = Gold,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "आपका स्वागत है",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = "भक्ति के पावन मार्ग पर 🙏",
                    color = Color.White,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Column {

                Text(
                    text = "श्रद्धा रखें, विश्वास रखें",
                    color = Gold,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "ईश्वर हर कदम पर आपके साथ हैं।",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 12.sp
                )
            }
        }

        Text(
            text = "🪔",
            fontSize = 52.sp,
            modifier = Modifier.align(Alignment.TopEnd)
        )

        Text(
            text = "ॐ",
            fontSize = 58.sp,
            color = Gold.copy(alpha = 0.12f),
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}

// --------------------------------------------------
// Daily Thought
// --------------------------------------------------

@Composable
private fun DailyThoughtCard() {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = SoftGold
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = Color.White.copy(alpha = 0.8f),
                        shape = RoundedCornerShape(15.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "✨",
                    fontSize = 25.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "आज का आध्यात्मिक विचार",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Maroon
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "मन में श्रद्धा और कर्म में सच्चाई रखें। " +
                            "यही जीवन को सुंदर बनाता है।",
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                    color = TextPrimary
                )
            }
        }
    }
}

// --------------------------------------------------
// Tabs
// --------------------------------------------------

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
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) {
                Maroon
            } else {
                Color.White
            }
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (selected) 3.dp else 0.dp
        )
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 13.dp),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = title,
                color = if (selected) {
                    Color.White
                } else {
                    TextPrimary
                },
                fontSize = 14.sp,
                fontWeight = if (selected) {
                    FontWeight.Bold
                } else {
                    FontWeight.Medium
                }
            )
        }
    }
}

// --------------------------------------------------
// Bhakti Item
// --------------------------------------------------

@Composable
private fun BhaktiItem(
    title: String,
    type: String,
    onClick: () -> Unit
) {

    val icon = when (type) {
        "चालीसा" -> Icons.AutoMirrored.Default.MenuBook
        "आरती" -> Icons.Default.AutoAwesome
        else -> Icons.Default.SelfImprovement
    }

    val subtitle = when (type) {
        "चालीसा" -> "श्रद्धा से चालीसा पाठ करें"
        "आरती" -> "भक्ति भाव से आरती करें"
        else -> "मंत्र जाप से मन शांत करें"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 0.7.dp,
                color = BorderColor,
                shape = RoundedCornerShape(20.dp)
            ),
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBackground
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Icon

            Box(
                modifier = Modifier
                    .size(54.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                LightSaffron,
                                SoftGold
                            )
                        ),
                        shape = RoundedCornerShape(17.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Maroon,
                    modifier = Modifier.size(27.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Text

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = title,
                    color = TextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = subtitle,
                    color = TextSecondary,
                    fontSize = 11.sp
                )

                Spacer(modifier = Modifier.height(7.dp))

                Text(
                    text = "पाठ करें  •  🙏",
                    color = DeepSaffron,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Arrow

            Icon(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = "Open",
                tint = Gold,
                modifier = Modifier.size(15.dp)
            )
        }
    }
}

// --------------------------------------------------
// Error Content
// --------------------------------------------------

@Composable
private fun ErrorContent(
    message: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "🙏",
                fontSize = 40.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "सामग्री लोड नहीं हो सकी",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Maroon
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = message,
                fontSize = 12.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center
            )
        }
    }
}

// --------------------------------------------------
// Empty Content
// --------------------------------------------------

@Composable
private fun EmptyContent() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 35.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "🪔",
            fontSize = 42.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "अभी कोई सामग्री उपलब्ध नहीं है",
            color = TextSecondary,
            fontSize = 14.sp
        )
    }
}

// --------------------------------------------------
// Bottom Message
// --------------------------------------------------

@Composable
private fun BottomSpiritualMessage() {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Maroon
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "🕉️",
                fontSize = 28.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "हरि नाम ही जीवन का आधार है",
                color = Gold,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "प्रेम से प्रभु का स्मरण करें और अपने जीवन में शांति अनुभव करें।",
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 12.sp,
                lineHeight = 18.sp,
                textAlign = TextAlign.Center
            )
        }
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
fun PreviewHomeScreen() {
    BhaktiPathTheme {
        HomeScreen()
    }
}