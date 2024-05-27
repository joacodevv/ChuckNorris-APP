package com.joaquito.chucknorris

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.joaquito.chucknorris.R
import com.joaquito.chucknorris.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val jokeModel = MutableLiveData<JokeModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListeners()
    }

    private fun initListeners() {
        binding.btnGetJoke.setOnClickListener {
            binding.ivChuck.animation = AnimationUtils.loadAnimation(this, R.anim.shake_animation)
            makeCall()

        }
    }

    private fun showJoke() {
        jokeModel.observe(this, Observer {
            binding.tvJoke.text = it.joke
        })
    }

    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.chucknorris.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun makeCall(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(JokeApiClient::class.java).getRandomJoke()
            val quote = call.body()
            runOnUiThread {
                if (call.isSuccessful){
                    jokeModel.postValue(quote!!)
                    showJoke()
                }
            }
        }
    }


}