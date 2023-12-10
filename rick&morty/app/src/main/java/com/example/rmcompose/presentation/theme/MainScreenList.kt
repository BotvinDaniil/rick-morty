package com.example.rmcompose.presentation.theme

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.rmcompose.R
import com.example.rmcompose.data.model.Result
import com.example.rmcompose.presentation.MainViewModel

@Composable
fun MainScreenList(
    persons: LazyPagingItems<Result>,
    viewModel: MainViewModel,
    navController: NavHostController
) {

    LazyColumn {
        items(count = persons.itemCount,
            key = persons.itemKey { it.id },
            contentType = persons.itemContentType { "Character" }) { index: Int ->
            PersonView(
                person = persons[index]!!,
                onItemClick = { onClick(persons[index]!!, viewModel, navController) })
        }
        persons.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item { CircularProgressIndicator() }
                }

                loadState.refresh is LoadState.Error -> {
                    val error = loadState.refresh as LoadState.Error
                    item {
                        Column(
                            modifier = Modifier.fillParentMaxSize(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            error.error.localizedMessage.let { Text(text = it!!) }
                            Button(onClick = { retry() }) {
                                Text(text = "Retry")
                            }
                        }
                    }
                }

                loadState.append is LoadState.Error -> {
                    val error = loadState.append as LoadState.Error
                    item {
                        Column(
                            modifier = Modifier.fillParentMaxSize(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            error.error.localizedMessage.let { Text(text = it!!) }
                            Button(onClick = { retry() }) {
                                Text(text = "Retry")
                            }
                        }
                    }
                }

            }

        }
    }

}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PersonView(person: Result, onItemClick: () -> Unit) {
    Box(
        Modifier
            .background(color = Color.DarkGray)
            .clickable { onItemClick() }) {
        Row(
            Modifier
                .padding(10.dp)
                .clip(shape = RoundedCornerShape(5))
                .fillMaxWidth()
                .height(180.dp)
                .background(color = Color.Gray)
        ) {
            GlideImage(
                model = person.image, contentDescription = "Person Image", modifier = Modifier
                    .size(width = 150.dp, height = 180.dp)
                    .background(color = Color.Green),
                contentScale = ContentScale.FillHeight
            )
            Column(Modifier.padding(start = 8.dp, top = 8.dp)) {
                Text(
                    text = person.name,
                    maxLines = 1,
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier.padding(10.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(10.dp)
                ) {
                    Image(
                        painter = painterResource(
                            id =
                            when (person.status) {
                                "Alive" -> R.drawable.baseline_circle_green
                                "Dead" -> R.drawable.baseline_circle_red
                                else -> R.drawable.baseline_circle_grey
                            }
                        ), contentDescription = "Status", modifier = Modifier.size(11.dp, 11.dp)
                    )
                    Text(text = "${person.status}(${person.species})", color = Color.White)
                }
                Text(
                    text = "Last Known Location:",
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                    color = Color.LightGray
                )
                Text(
                    text = person.location.name,
                    modifier = Modifier.padding(start = 10.dp),
                    color = Color.White
                )

            }

        }
    }
}

fun onClick(person: Result, viewModel: MainViewModel, navController: NavHostController) {
    val episodesList = mutableListOf<Int>()
    viewModel.person = person
    Log.d("TestingLoadAndOther", person.episode.toString())
    viewModel.person.episode.forEach { episode ->
        episodesList.add(episode.substringAfterLast("/").toInt())
    }
    viewModel.getEpisodesList(episodesList)
    navController.navigate(AppScreens.DescribesScreen.name)

}

