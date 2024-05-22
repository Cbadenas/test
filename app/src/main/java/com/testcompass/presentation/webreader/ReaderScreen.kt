package com.testcompass.presentation.webreader

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.Dispatchers

@Composable
fun ReaderScreen(
    viewModel: ReaderViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),

        ) {
        Column(
            modifier = Modifier
                .padding(top = 30.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if (state.value.isLoading) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                } else {
                    Button(onClick = {
                        viewModel.onAction(ReaderActions.ReadWebsite("https://www.compass.com/about/"))
                    }) {
                        Text("Process Website HTML")
                    }
                }

                Spacer(modifier = Modifier.padding(16.dp))

            }

            Spacer(modifier = Modifier.padding(16.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Unique Words Count: ${state.value.uniques}",
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.padding(8.dp))

            Spacer(modifier = Modifier.padding(16.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Website 10s",
                fontSize = 20.sp
            )

            if (state.value.website10s.size > 0) {
                Text(text = state.value.website10s.toString())
            }

            // I HAVE A 2nd OPTION HERE TO SHOW THE RESULTS
            // INSTEAD OF SHOWING THE ARRAY AS A STRING
            // I CAN USE A LazyColumn TO SHOW THE RESULTS
            // IF YOU WANT TO TRY IT OUT, JUST UNCOMMENT THE CODE BELOW
            // AND COMMENT THE TEXT COMPONENT ABOVE

//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                contentPadding = PaddingValues(16.dp),
//                verticalArrangement = Arrangement.spacedBy(16.dp)
//            ) {
//                items(state.value.website10s.size) { index ->
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .clip(shape = RoundedCornerShape(1.dp))
//                    ) {
//
//                        Text(text = state.value.website10s[index])
//                    }
//                }
//            }

        }
    }
}

@Composable
@Preview
fun PreviewScreen() {
    ReaderScreen(viewModel = ReaderViewModel(Dispatchers.IO))
}