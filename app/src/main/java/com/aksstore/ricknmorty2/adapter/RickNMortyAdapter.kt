package com.aksstore.ricknmorty2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aksstore.ricknmorty2.R
import com.aksstore.ricknmorty2.data.RNMResult
import com.bumptech.glide.Glide
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class RickNMortyAdapter @Inject constructor(@ActivityContext private val context: Context) :
    PagingDataAdapter<RNMResult, RickNMortyAdapter.MyViewHolder>(CustomComparator) {


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.rick_morty_item, parent, false)

        return MyViewHolder(itemView, context)
    }


    class MyViewHolder(itemView: View, val context: Context) : RecyclerView.ViewHolder(itemView) {

        private val name: TextView
        private val imgView : ImageView

        init {
            name = itemView.findViewById(R.id.tvRickNMortyCharacterName)
            imgView = itemView.findViewById(R.id.imgProfile)
        }

        fun bind(rnmResult: RNMResult) {
            name.text = rnmResult.name
            Glide.with(context).load(rnmResult.image).into(imgView)
        }

    }

    object CustomComparator : DiffUtil.ItemCallback<RNMResult>() {
        override fun areItemsTheSame(oldItem: RNMResult, newItem: RNMResult): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RNMResult, newItem: RNMResult): Boolean {
          return oldItem == newItem
        }

    }

}