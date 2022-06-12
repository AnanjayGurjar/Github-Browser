package com.ananjay.githubbrowser.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ananjay.githubbrowser.R
import com.ananjay.githubbrowser.database.MyRepoModel
import com.ananjay.githubbrowser.models.branch.BranchModel
import org.w3c.dom.Text

class BranchFragmentAdapter(var list: List<BranchModel>,  private var listener : (Int) -> Unit) : RecyclerView.Adapter<BranchFragmentAdapter.HomeFragmentViewHolder>() {

    inner class HomeFragmentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var branchName = itemView.findViewById<TextView>(R.id.tv_branchName)

        init {
            itemView.setOnClickListener{
                listener.invoke(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFragmentViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_branch, parent, false)
        return HomeFragmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeFragmentViewHolder, position: Int) {
        holder.branchName.text = list[position].name
    }
    override fun getItemCount(): Int {
        return list.size
    }
}