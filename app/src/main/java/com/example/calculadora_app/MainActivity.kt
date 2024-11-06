package com.example.calculadora_app

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var displayTextView: TextView
    private var expression = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        displayTextView = findViewById(R.id.displayTextView)

        // Digits (0-9)
        val buttons = listOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6, R.id.button7,
            R.id.button8, R.id.button9
        )
        buttons.forEach { id ->
            findViewById<Button>(id).setOnClickListener { onDigitPressed((it as Button).text.toString()) }
        }

        // Operators
        findViewById<Button>(R.id.buttonAdd).setOnClickListener { onOperatorPressed("+") }
        findViewById<Button>(R.id.buttonSubtract).setOnClickListener { onOperatorPressed("-") }
        findViewById<Button>(R.id.buttonMultiply).setOnClickListener { onOperatorPressed("×") }
        findViewById<Button>(R.id.buttonDivide).setOnClickListener { onOperatorPressed("/") }

        // Special Buttons
        findViewById<Button>(R.id.buttonEqual).setOnClickListener { calculateResult() }
        findViewById<Button>(R.id.buttonClear).setOnClickListener { clearDisplay() }
    }

    private fun onDigitPressed(digit: String) {
        expression += digit
        displayTextView.text = expression
    }

    private fun onOperatorPressed(op: String) {
        if (expression.isNotEmpty() && !"+-×/".contains(expression.last())) {
            expression += op
            displayTextView.text = expression
        }
    }

    private fun calculateResult() {
        try {
            val result = evaluateExpression(expression)
            displayTextView.text = result.toString()
            expression = result.toString()
        } catch (e: Exception) {
            displayTextView.text = "Erro"
            expression = ""
        }
    }

    private fun clearDisplay() {
        expression = ""
        displayTextView.text = "0"
    }

    // Function to evaluate the mathematical expression using exp4j
    private fun evaluateExpression(expr: String): Double {
        // Replace "×" with "*" for calculation purposes
        val sanitizedExpression = expr.replace("×", "*")

        // Use ExpressionBuilder from exp4j to evaluate the expression
        val expression = ExpressionBuilder(sanitizedExpression).build()
        return expression.evaluate()
    }
}