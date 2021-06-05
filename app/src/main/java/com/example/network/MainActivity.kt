package com.example.network

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var furl=""
    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        updatev()
        btn.setOnClickListener{
            updatev()
        }
        share.setOnClickListener{
                val intent=Intent(Intent.ACTION_SEND)
                 intent.type="text/plain"
                intent.putExtra(Intent.EXTRA_TEXT,"look this is a awesome meme $furl")
                val chooser=Intent.createChooser(intent,"share this")
                startActivity(chooser)
        }
    }

    @ExperimentalStdlibApi
    @SuppressLint("SetTextI18n")
    private fun updatev() {
        progress.visibility=View.VISIBLE
       val url="https://meme-api.herokuapp.com/gimme"
     //   val queue = Volley.newRequestQueue(this)  dont need queue cause we have singleton object

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
           { response ->
                // Display the first 500 characters of the response string.
               furl=response.getString("url")//meme api url,not local url,its a meme api url thats contain meme image
               Glide.with(this).load(furl).listener(
                   object :RequestListener<Drawable>{
                       override fun onLoadFailed(
                           e: GlideException?,
                           model: Any?,
                           target: Target<Drawable>?,
                           isFirstResource: Boolean
                       ): Boolean {
                          progress.visibility=View.GONE
                           return false
                       }

                       override fun onResourceReady(
                           resource: Drawable?,
                           model: Any?,
                           target: Target<Drawable>?,
                           dataSource: DataSource?,
                           isFirstResource: Boolean
                       ): Boolean {
                          progress.visibility=View.GONE
                           return false
                       }
                   }
               ).into(img)
            },
            {
                Toast.makeText(this,"network error",Toast.LENGTH_SHORT).show()
            })
      MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

}
