package com.example.proyectofinalmoviles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CardAdapter(private val items: List<Fruit>) :
    RecyclerView.Adapter<CardAdapter.CardViewHolder>(){
    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return CardViewHolder(view)
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int){

        }
    }

    private fun onItemClick(position: Int) {
        onItemClickListener?.onItemClick(position)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val currentItem = items[position]

        holder.prodTextView.text = currentItem.prod.toString()
        holder.prodDescTextView.text = currentItem.description.toString()
        holder.prodPriceTextView.text = "$ " + currentItem.price.toString()
        holder.imgImageView.setOnClickListener{
            onItemClick(position)
        }
    }




    override fun getItemCount(): Int {
        return items.size
    }

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val prodTextView: TextView = itemView.findViewById(R.id.tvProd)
        val prodDescTextView: TextView = itemView.findViewById(R.id.tvDescription)
        val prodPriceTextView: TextView = itemView.findViewById(R.id.tvPrice)
        val imgImageView: ImageView = itemView.findViewById(R.id.ivProd)
    }
}