package net.uniquecomputer.lockqr

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import net.uniquecomputer.lockqr.databinding.ActivityQrcodeBinding
import net.uniquecomputer.lockqr.databinding.ChildIteamBinding

class ChildAdapter(private val context: Context, private val childItem: List<ChildItem>) :
    RecyclerView.Adapter<ChildAdapter.ChildViewHolder>() {

    inner class ChildViewHolder(private val binding: ChildIteamBinding): RecyclerView.ViewHolder(binding.root){
        val title = binding.childIteamText
        val image = binding.childIteamImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
       val view = ChildIteamBinding.inflate(LayoutInflater.from(context),parent,false)
        return ChildViewHolder(view)
    }

    override fun getItemCount(): Int {
        return childItem.size
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        holder.image.setImageResource(childItem[position].image)
        holder.title.text = childItem[position].title

        holder.itemView.setOnClickListener {
            val intent = Intent(context,QRCode::class.java)
            context.startActivity(intent)
            when (position) {
                0 -> {

                }
                1 -> {

                }
                2 -> {
                    Toast.makeText(context, "Email", Toast.LENGTH_SHORT).show()
                }
                3 -> {
                    Toast.makeText(context, "SMS", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}