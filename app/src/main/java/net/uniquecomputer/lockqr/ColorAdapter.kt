package net.uniquecomputer.lockqr

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.uniquecomputer.lockqr.databinding.ActivityQrcodeBinding
import net.uniquecomputer.lockqr.databinding.ColorpaletteBinding

class ColorAdapter(var context: Context, private val colorsArrayList:ArrayList<ColorViewModel>, val newBinding : ActivityQrcodeBinding) : RecyclerView.Adapter<ColorAdapter.ViewHolder>(){

    inner class ViewHolder( val binding: ColorpaletteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ColorpaletteBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return colorsArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.setColor.setBackgroundResource(colorsArrayList[position].colorHax)
//        holder.binding.setColor.setCardBackgroundColor(colorsArrayList[position].colorHax)
        holder.itemView.setOnClickListener {
            val color = colorsArrayList[position].colorHax
            newBinding.qrcode.setColorFilter(color)
        }

    }

}