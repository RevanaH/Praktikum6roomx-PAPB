package com.example.praktikum6roomx

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.praktikum6roomx.data.OfflineTasksRepository
import com.example.praktikum6roomx.data.Task
import com.example.praktikum6roomx.data.TaskDatabase
import com.example.praktikum6roomx.ui.theme.Praktikum6roomxTheme

class MainActivity : ComponentActivity() {
    private val database by lazy { TaskDatabase.getDatabase(applicationContext) }
    private val viewModel: TaskViewModel by viewModels {
        TaskViewModelFactory(
            OfflineTasksRepository(
                database.taskDao()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Praktikum6roomxTheme {
                ToDoScreen(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoScreen(viewModel: TaskViewModel) {
    val context = LocalContext.current
    var activity by remember { mutableStateOf("") }
    var activityId by remember { mutableStateOf<Int?>(null) }
    val allActivity by viewModel.getAllTasks.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = Color(0xFF0F97FF),
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Text(
                        text = "TODO LIST",
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 20.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.ExtraBold
                    )
                },
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues)
            ) {
                TextField(
                    value = activity,
                    onValueChange = { activity = it },
                    shape = RoundedCornerShape(7.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 15.dp, 20.dp, 0.dp)
                )
                Spacer(modifier = Modifier.height(15.dp))
                Button(
                    onClick = {
                        if (activity.isNotEmpty()) {
                            if (activityId != null) {
                                viewModel.updateTask(Task(id = activityId!!, name = activity, isCompleted = false))
                            } else {
                                viewModel.addTask(Task(name = activity, isCompleted = false))
                            }
                            activity = ""
                            activityId = null
                        }else{
                            Toast.makeText(context, "Isi form terlebih dahulu", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 0.dp, 20.dp, 0.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0F97FF)
                    )
                ) {
                    Text(text = "Save")
                }
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(allActivity.size) { index ->
                        val task = allActivity[index]

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp, 0.dp, 10.dp, 0.dp)
                                .clickable {
                                    activity = task.name
                                    activityId = task.id
                                }
                        ) {
                            Checkbox(
                                checked = task.isCompleted,
                                onCheckedChange = { checked ->
                                    viewModel.updateTask(task.copy(isCompleted = checked))
                                },
                                colors = CheckboxDefaults.colors(
                                    checkmarkColor = Color.White,
                                    checkedColor = Color(0xFF0F97FF)
                                )
                            )

                            Text(
                                text = task.name,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            IconButton(
                                onClick = {
                                    viewModel.deleteTask(task)
                                },
                                modifier = Modifier.align(Alignment.CenterVertically)
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete")
                            }
                        }
                    }
                }
            }
        }
    )
}



//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    Praktikum6roomxTheme {
//        Greeting("Android")
//    }
//}