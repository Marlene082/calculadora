package com.agenciacristal.calculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.calculadora.R
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    val SUMA = "+"
    val RESTA = "-"
    val MULTIPLICACION = "*"
    val DIVISION = "/"
    val PORCENTAJE = "%"

    var operacionActual = ""
    var primerNumero: Double = Double.NaN
    var segundoNumero: Double = Double.NaN

    lateinit var tvTemp: TextView
    lateinit var tvResult: TextView

    lateinit var formatoDecimal: DecimalFormat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        formatoDecimal = DecimalFormat("#.##########")
        tvTemp = findViewById(R.id.tvTemp)
        tvResult = findViewById(R.id.tvResult)
    }

    fun cambiarOperador(b: View) {
        val boton = b as Button
        if (tvTemp.text.isNotEmpty()) {
            calcular()
            operacionActual = when (boton.text.toString().trim()) {
                "÷" -> DIVISION
                "x" -> MULTIPLICACION
                else -> boton.text.toString().trim()
            }
            tvResult.text = formatoDecimal.format(primerNumero) + operacionActual
            tvTemp.text = ""
        }
    }

    fun calcular() {
        try {
            if (!primerNumero.isNaN()) {
                if (tvTemp.text.toString().isNotEmpty()) {
                    segundoNumero = tvTemp.text.toString().toDouble()
                    when (operacionActual) {
                        SUMA -> primerNumero += segundoNumero
                        RESTA -> primerNumero -= segundoNumero
                        MULTIPLICACION -> primerNumero *= segundoNumero
                        DIVISION -> primerNumero /= segundoNumero
                        PORCENTAJE -> primerNumero %= segundoNumero
                    }
                }
            } else {
                if (tvTemp.text.toString().isNotEmpty()) {
                    primerNumero = tvTemp.text.toString().toDouble()
                }
            }
            tvTemp.text = ""
        } catch (e: Exception) {
            tvResult.text = "Error"
        }
    }

    fun seleccionarNumero(b: View) {
        val boton = b as Button
        tvTemp.append(boton.text.toString())
    }

    fun igual(b: View) {
        calcular()
        tvResult.text = formatoDecimal.format(primerNumero)
        operacionActual = ""
    }

    fun borrar(b: View) {
        val boton = b as Button
        when (boton.text.toString().trim()) {
            "C" -> {
                if (tvTemp.text.isNotEmpty()) {
                    tvTemp.text = tvTemp.text.dropLast(1)
                }
            }
            "CA" -> {
                primerNumero = Double.NaN
                segundoNumero = Double.NaN
                tvTemp.text = ""
                tvResult.text = ""
            }
        }
    }
}
