package com.example.list_jetpackcompose.view

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.list_jetpackcompose.R
import com.example.list_jetpackcompose.response.SampleData
import com.example.list_jetpackcompose.ui.theme.Purple500
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Composable
fun SampleList(navController: NavController) {
    val context = LocalContext.current
    val dataFileString = getJsonDataFromAsset(context, "Data.json")
    val gson = Gson()
    val listSampleType = object : TypeToken<List<SampleData>>() {}.type
    var sampleData: List<SampleData> = gson.fromJson(dataFileString, listSampleType)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .wrapContentSize(Alignment.Center)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Purple500),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "List With JetPackCompose", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        LazyColumn(
            modifier = Modifier
                .padding(10.dp)
        ) {
            items(sampleData) { data ->
                SampleDataListItem(data, navController)
            }
        }
    }
}

@Composable
fun SampleDataListItem(data: SampleData, navController: NavController) {
    Card(
        modifier = Modifier
            .clickable {
                val itemVal = Gson().toJson(data)
                navController.navigate("sample_detail/${itemVal}")
            }
            .padding(10.dp)
            .fillMaxSize(),
        elevation = 5.dp,
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(painterResource(R.drawable.icon),
                contentDescription = "Image",
                modifier = Modifier
                    .width(70.dp)
                    .height(70.dp)
                    .padding(5.dp)
                    .clip(RoundedCornerShape(5.dp))
            )

            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(text = data.name, fontSize = 15.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.padding(5.dp))

                Text(text = data.desc, fontSize = 15.sp, fontWeight = FontWeight.Normal)
            }
        }
    }
}

fun getJsonDataFromAsset(context: Context, data: String): String {
    return context.assets.open(data).bufferedReader().use { it.readText() }
}