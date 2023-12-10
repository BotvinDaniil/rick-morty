package com.example.rmcompose.presentation.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.rmcompose.R
import com.example.rmcompose.data.model.Result
import com.example.rmcompose.data.model.episodes.EpisodesDTO


@Composable
fun DescribePersonScreen(
    person: Result,
    episodes: EpisodesDTO
) {
    LazyColumn(
        modifier = Modifier
            .background(color = Color.DarkGray)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        item {
            PersonDetails(person = person, episodes = episodes)
        }

        items(episodes) { episode ->
            EpisodeView(
                name = episode.name,
                airDate = episode.airDate,
                episode = episode.episode
            )
        }

    }
}


@Composable
fun EpisodeView(name: String, airDate: String, episode: String) {
    Box(
        Modifier
            .background(color = Color.DarkGray)
            .padding(start = 5.dp, end = 5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(10))
                .background(color = Color.Gray)
                .padding(start = 40.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(text = name, fontSize = 18.sp, color = Color.White)
                Text(text = airDate, fontSize = 14.sp, color = Color.White)
            }
            Text(
                text = episode,
                fontSize = 14.sp,
                color = Color.LightGray,
                modifier = Modifier.padding(end = 6.dp)
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PersonDetails(
    person: Result,
    episodes: EpisodesDTO
) {
    Column(
        Modifier
            .background(color = Color.DarkGray)
            .padding(5.dp)
    ) {
        GlideImage(
            model = person.image, contentDescription = "Person Photo", modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(color = Color.Green),
            contentScale = ContentScale.Crop
        )
        Box(Modifier.background(color = Color.DarkGray)) {
            Column(
                modifier = Modifier
                    .padding(start = 40.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = person.name,
                    fontSize = 30.sp,
                    color = Color.White,
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
                )
                Text(
                    text = "Live Status",
                    color = Color.LightGray,
                    modifier = Modifier.padding(top = 10.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 10.dp)
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
                    Text(text = person.status, color = Color.White)
                }
                Text(
                    text = "Species and gender:", color = Color.LightGray,
                    modifier = Modifier.padding(top = 10.dp)
                )
                Text(
                    text = "${person.species}(${person.gender})",
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Text(
                    text = "Last Known Location:", color = Color.LightGray,
                    modifier = Modifier.padding(top = 10.dp)
                )
                Text(
                    text = person.location.name, color = Color.White,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Text(
                    text = "First seen in:", color = Color.LightGray,
                    modifier = Modifier.padding(top = 10.dp)
                )
                Text(
                    text = if (episodes.isEmpty()) "" else episodes[0].name, color = Color.White,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Text(
                    text = "Episodes:", color = Color.LightGray,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
        }
    }
}




