package com.example.compose_example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose_example.ui.theme.Compose_ExampleTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.images.observe(this) { images ->
            setContent {
                Compose_ExampleTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        // Now passing a lambda for onTitleChange
                        UrlListWidget("akita", images) { updatedTitle ->
                            viewModel.searchByNameLimited(updatedTitle)
                        }
                    }
                }
            }
        }
        viewModel.searchByNameLimited("akita")
    }
}

@Composable
fun UrlListWidget(title: String, imageUrls: List<String>, onTitleChange: (String) -> Unit) {
    // State to hold the current value of the text field
    var textFieldValue by remember { mutableStateOf(title) }
    Column {
        // TextField for editable title
        TextField(
            value = textFieldValue,
            onValueChange = { newValue ->
                textFieldValue = newValue
                onTitleChange(newValue)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            textStyle = TextStyle(fontSize = 24.sp),
            singleLine = true, // Set to true to avoid line breaks
            // Add more customization as needed, e.g., placeholder, colors
        )
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = Color.Gray,
            thickness = 1.dp,
        )
        LazyColumn {
            itemsIndexed(imageUrls) { index, imageUrl ->
                Text(
                    text = imageUrl,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 16.sp
                )
                if (index < imageUrls.size - 1) {
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 16.dp),
                        color = Color.Gray,
                        thickness = 1.dp,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListOfImagesPreview() {
    Compose_ExampleTheme {
        UrlListWidget(
            title = "Text",
            imageUrls = listOf(
                "https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg",
                "https://images.dog.ceo/breeds/hound-afghan/n02088094_1007.jpg",
                "https://images.dog.ceo/breeds/hound-afghan/n02088094_1023.jpg"
            )
        ) {}
    }
}