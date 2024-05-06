package com.hienle.cleanarchitecture.feature.news.article

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.hien.le.news.kmm.ui.NewsItemUiState
import com.hien.le.news.kmm.ui.NewsViewModel
import com.hien.le.news.kmm.ui.NewsViewState
import com.hien.le.news.kmm.ui.Source
import newskmm.composeapp.generated.resources.Res
import newskmm.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun NewsScreen(
    modifier: Modifier = Modifier,
    onArticleClicked: (String) -> Unit,
    viewModel: NewsViewModel = NewsViewModel(),
) {
    val viewState by viewModel.uiState.collectAsState()
    NewsScreenScaffold(
        modifier = modifier,
        onArticleClicked = onArticleClicked,
        viewState = viewState,
    )
}

@Composable
fun NewsScreenScaffold(
    modifier: Modifier = Modifier,
    onArticleClicked: (String) -> Unit,
    viewState: NewsViewState,
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = modifier,
    ) {
        LazyColumn(modifier = Modifier.testTag("newsArticleLazyColumn")) {
            items(items = viewState.newsItems) {
                NewsArticleItem(newsItemUiState = it, onArticleClicked = onArticleClicked)
            }
        }
    }
}

@Composable
fun NewsArticleItem(
    newsItemUiState: NewsItemUiState,
    onArticleClicked: (String) -> Unit,
) {
    Surface(
        modifier =
        Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable { newsItemUiState.url?.let { onArticleClicked(it) } }
            .semantics { contentDescription = "News article item" },
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
        ) {
            Column(
                modifier = Modifier.weight(0.7f),
            ) {
                Text(
                    text = newsItemUiState.source?.name ?: "",
                    style = MaterialTheme.typography.labelSmall,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = newsItemUiState.title ?: "",
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = newsItemUiState.description ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = newsItemUiState.publishedAt ?: "",
                    style = MaterialTheme.typography.labelSmall,
                )
            }
            Image(
                painterResource(Res.drawable.compose_multiplatform), null,
                modifier =
                Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
            )
        }
    }
}

// Sample data
val newsArticles =
    listOf(
        NewsItemUiState(
            source = Source(id = "", name = "Biztoc.com"),
            author = "coingape.com",
            title = "Ethereum Founder Vitalik Buterin Reacts As Elon Musk Slams Microsoft",
            description = "In a remarkable and unforeseen turn of events witnessed recently, Ethereum founder Vitalik Buterin invited Elon Musk, the founder of X Corp. and Tesla, to team up...",
            url = "https://biztoc.com/x/89324d8b669c2591",
            urlToImage = "https://c.biztoc.com/p/89324d8b669c2591/s.webp",
            publishedAt = "2024-02-27T09:34:24Z",
            content = "In a remarkable and unforeseen turn of events witnessed recently, Ethereum founder Vitalik Buterin invited Elon Musk, the founder of X Corp. and Tesla, to team up...",
        ),
        NewsItemUiState(
            source = Source(id = "", name = "CNN"),
            author = "businessinsider.com",
            title = "Tesla's Optimus bot is looking a little steadier on its feet. Maybe the yoga stretches are paying off",
            description = "The prototype for Optimus was first revealed in September 2022. Tesla Optimus Elon Musk shared new footage of Tesla's humanoid robot, Optimus, in an X post on Saturday...",
            url = "https://biztoc.com/x/aca260fd8da1d159",
            urlToImage = "https://c.biztoc.com/p/aca260fd8da1d159/s.webp",
            publishedAt = "2024-02-27T09:26:06Z",
            content = "The prototype for Optimus was first revealed in September 2022.Tesla OptimusElon Musk shared new footage of Tesla's humanoid robot, Optimus, in an X post on Saturday...",
        ),
        // Add more news articles here if needed
    )

@Preview()
@Composable
fun NewsScreenScaffoldPreview() {
    NewsScreenScaffold(onArticleClicked = {}, viewState = NewsViewState(newsItems = newsArticles))
}
