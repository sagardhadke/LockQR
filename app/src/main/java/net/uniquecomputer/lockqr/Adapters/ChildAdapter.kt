package net.uniquecomputer.lockqr.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.uniquecomputer.lockqr.Model.ChildItem
import net.uniquecomputer.lockqr.QRCode
import net.uniquecomputer.lockqr.databinding.ChildIteamBinding

class ChildAdapter(private val context: Context, private val childItem: List<ChildItem>) : RecyclerView.Adapter<ChildAdapter.ChildViewHolder>() {

    inner class ChildViewHolder(private val binding: ChildIteamBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.childIteamText
        val image = binding.childIteamImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val view = ChildIteamBinding.inflate(LayoutInflater.from(context), parent, false)
        return ChildViewHolder(view)
    }

    override fun getItemCount(): Int {
        return childItem.size
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val item = childItem[position]
        holder.image.setImageResource(item.image)
        holder.title.text = item.title

        holder.itemView.setOnClickListener {
            val intent = Intent(context, QRCode::class.java)
            intent.putExtra("position", position)
            intent.putExtra("title", item.title)
            context.startActivity(intent)
        }
    }
}
