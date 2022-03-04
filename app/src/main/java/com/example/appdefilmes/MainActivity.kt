package com.example.appdefilmes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.appdefilmes.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        selecionarMenu()
    }


    fun selecionarMenu(){
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.page_1 -> telaMain()
                R.id.page_2 -> Toast.makeText(applicationContext, "Aqui", Toast.LENGTH_LONG).show()
                else -> false
            }
            true
        }
    }

    fun telaMinhaLista() {
        Toast.makeText(applicationContext, "Aqui", Toast.LENGTH_LONG).show()
        val intent = Intent(this, MyListActivity::class.java)
        startActivity(intent)
    }

    fun telaMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}