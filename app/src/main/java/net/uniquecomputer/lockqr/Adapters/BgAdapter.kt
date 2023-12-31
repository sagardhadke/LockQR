package net.uniquecomputer.lockqr.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import net.uniquecomputer.lockqr.Model.BgViewModel
import net.uniquecomputer.lockqr.databinding.ActivityQrcodeBinding
import net.uniquecomputer.lockqr.databinding.ColorpaletteBinding

class BgAdapter(var context: Context, private val bgArrayList:ArrayList<BgViewModel>, val newBinding : ActivityQrcodeBinding) : RecyclerView.Adapter<BgAdapter.ViewHolder>(){

    inner class ViewHolder( val binding: ColorpaletteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ColorpaletteBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bgArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.setColor.setBackgroundResource(bgArrayList[position].colorHax)
        holder.itemView.setOnClickListener {
            val bg = bgArrayList[position].colorHax
            newBinding.qrcode.setBackgroundResource(bg)
            val intent = Intent("custom-message")
            intent.putExtra("bgImg", bg)
            context.sendBroadcast(intent)
        }

    }

}