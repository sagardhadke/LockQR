package net.uniquecomputer.lockqr

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.uniquecomputer.lockqr.databinding.ColorpaletteBinding

class ColorAdapter(val context: Context, private val colorsArrayList:ArrayList<ColorViewModel>) : RecyclerView.Adapter<ColorAdapter.ViewHolder>(){

    inner class ViewHolder( val binding: ColorpaletteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ColorpaletteBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return colorsArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //set background color to card-view
        holder.binding.setColor.setCardBackgroundColor(colorsArrayList[position].colorHax)

    }

}