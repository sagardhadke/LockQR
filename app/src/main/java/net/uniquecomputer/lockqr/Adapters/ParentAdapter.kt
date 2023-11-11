package net.uniquecomputer.lockqr.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.uniquecomputer.lockqr.Model.ParentItem
import net.uniquecomputer.lockqr.databinding.ParentIteamBinding

class ParentAdapter(private val context: Context,private val parentList: List<ParentItem>) : RecyclerView.Adapter<ParentAdapter.ParentViewHolder>() {

    inner class ParentViewHolder(private val binding: ParentIteamBinding): RecyclerView.ViewHolder(binding.root){
        val title = binding.parentName
        val childRecyclerview = binding.childRecyclerview
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val view = ParentIteamBinding.inflate(LayoutInflater.from(context),parent,false)
        return ParentViewHolder(view)
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        val parentItem = parentList[position]
        holder.title.text = parentItem.title

        holder.childRecyclerview.setHasFixedSize(true)
        holder.childRecyclerview.layoutManager = GridLayoutManager(context,3)
        val adapter = ChildAdapter(context,parentItem.mList)
        holder.childRecyclerview.adapter = adapter

    }

    override fun getItemCount(): Int {
        return parentList.size
    }

}