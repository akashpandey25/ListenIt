package com.example.listenit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var myRecyclerView: RecyclerView
    lateinit var myAdapter: myAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myRecyclerView=findViewById(R.id.recycview)
        val retrofitbuilder= Retrofit.Builder()
            .baseUrl("https://deezerdevs-deezer.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
        val retroData=retrofitbuilder.getData("eminem")

        retroData.enqueue(object : Callback<myDataX?> {
            override fun onResponse(call: Call<myDataX?>, response: Response<myDataX?>) {
//               if api call success it got executed
                val datalist= response.body()?.data!!
//                val textView=findViewById<TextView>(R.id.textview1)
//                textView.text=datalist.toString()
                myAdapter= myAdapter(this@MainActivity, datalist)
                myRecyclerView.adapter=myAdapter
                myRecyclerView.layoutManager=LinearLayoutManager(this@MainActivity)
//                add log for checking everything is working properly
                Log.d("TAG : onResponse", "onResponse: " + response.body())
            }

            override fun onFailure(call: Call<myDataX?>, t: Throwable) {
//              if api call fails it got executed
                Log.d("TAG : onFailure", "onFailure: " + t.message)
            }
        })

    }
}