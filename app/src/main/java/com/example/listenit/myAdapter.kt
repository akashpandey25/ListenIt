package com.example.listenit

import android.app.Activity
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationBarItemView
import com.squareup.picasso.Picasso
import java.io.IOException

class myAdapter (val context: Activity, val datalist: List<Data>):
    RecyclerView.Adapter<myAdapter.MyViewHolder>(){
       class MyViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
           val musicImage: ImageView
           val musicTitle: TextView
           val play : ImageButton
           val pause : ImageButton
           init{
               musicImage=itemView.findViewById(R.id.imagemusic)
               musicTitle=itemView.findViewById(R.id.songtitle)
               play=itemView.findViewById(R.id.playButton)
               pause= itemView.findViewById(R.id.pauseButton2)
           }

    }
    companion object{
        private var currentMediaPlayer: MediaPlayer? =null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView=LayoutInflater.from(context).inflate(R.layout.eachitem, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentDataX=datalist[position]
        val mediaPlayer=MediaPlayer.create(context, currentDataX.preview.toUri())

        holder.musicTitle.text=currentDataX.title

        Picasso.get().load(currentDataX.album.cover).into(holder.musicImage);
        holder.play.setOnClickListener{
            if(currentMediaPlayer!=null && currentMediaPlayer!!.isPlaying){
                currentMediaPlayer!!.stop()
                currentMediaPlayer!!.release()
                currentMediaPlayer=null
            }
            //handling the playing of one song at a time
            currentMediaPlayer=MediaPlayer().apply{
                try {
                    setDataSource(currentDataX.preview)
                    prepare()
                    start()
                }
                catch (e:IOException){
                    e.printStackTrace()
                }
            }
//            mediaPlayer.start()
        }
        holder.pause.setOnClickListener{
            if (currentMediaPlayer != null && currentMediaPlayer!!.isPlaying) {
                currentMediaPlayer!!.stop()
                currentMediaPlayer!!.release()
                currentMediaPlayer = null
            }
//            mediaPlayer.stop()
        }
    }


}