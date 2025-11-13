package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.myapplication.Constants.INPUT1
import com.example.myapplication.Constants.INPUT2

class MainActivity : ComponentActivity() {

    private lateinit var input1: EditText
    private lateinit var input2: EditText

    private var leftNumber = 0
    private var rightNumber = 0

    private val intentFilter = IntentFilter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_practical_test01_main)

        input1 = findViewById(R.id.left_edit_text)
        input2 = findViewById(R.id.right_edit_text)

        input1.setText("0")
        input2.setText("0")
        val pressMeButton = findViewById<Button>(R.id.left_button)
        pressMeButton.setOnClickListener {
            leftNumber++
            input1.setText(leftNumber.toString())
//            startServiceIfConditionIsMet(leftNumber, rightNumber)
        }

        val pressMeToo = findViewById<Button>(R.id.right_button)
        pressMeToo.setOnClickListener {
            rightNumber++
            input2.setText(rightNumber.toString())
//            startServiceIfConditionIsMet(leftNumber, rightNumber)
        }

        val startServiceButton = findViewById<Button>(R.id.startService)
        startServiceButton.setOnClickListener {
            startServiceIfConditionIsMet(Constants.NUMBER_OF_CLICKS_THRESHOLD, 1)
        }

        val activityResultsLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, "The activity returned with result OK", Toast.LENGTH_LONG).show()
            }
            else if (result.resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "The activity returned with result CANCELED", Toast.LENGTH_LONG).show()
            }
        }

        val navigateToSecondaryActivityButton = findViewById<Button>(R.id.buton)
        navigateToSecondaryActivityButton.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            intent.putExtra(INPUT1, Integer.valueOf(input1.text.toString()))
            intent.putExtra(INPUT2, Integer.valueOf(input2.text.toString()))
            activityResultsLauncher.launch(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(INPUT1, input1.text.toString())
        outState.putString(INPUT2, input2.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState.containsKey(INPUT1) && savedInstanceState.containsKey(INPUT2)) {
            input1.setText(savedInstanceState.getString(INPUT1))
            input2.setText(savedInstanceState.getString(INPUT2))
            leftNumber = Integer.valueOf(input1.text.toString())
            rightNumber = Integer.valueOf(input2.text.toString())
        }
    }

//    private fun startServiceIfConditionIsMet(leftNumber: Int, rightNumber: Int) {
//        if (leftNumber + rightNumber > Constants.NUMBER_OF_CLICKS_THRESHOLD) {
//            val intent = Intent(applicationContext, TestService::class.java).apply {
//                putExtra(INPUT1, leftNumber)
//                putExtra(INPUT2, rightNumber)
//            }
//            applicationContext.startService(intent)
//        }
//    }
}
