package com.ananjay.githubbrowser.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ananjay.githubbrowser.R
import com.ananjay.githubbrowser.database.MyRepoModel
import org.w3c.dom.Text

class HomeFragmentAdapter(var list: List<MyRepoModel>,  var clickListener: ClickListener, private var listener : (Int) -> Unit) : RecyclerView.Adapter<HomeFragmentAdapter.HomeFragmentViewHolder>() {

    inner class HomeFragmentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var title = itemView.findViewById<TextView>(R.id.repo_title)
        var desc = itemView.findViewById<TextView>(R.id.repo_desc)
        var shareBtn = itemView.findViewById<ImageView>(R.id.iv_share)

        init {
            itemView.setOnClickListener{
                listener.invoke(adapterPosition)
            }
            shareBtn.setOnClickListener {
                listener.invoke(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFragmentViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false)
        return HomeFragmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeFragmentViewHolder, position: Int) {
        holder.title.text = list[position].repoName
        holder.desc.text = list[position].repoDescription
        holder.shareBtn.setOnClickListener {
            clickListener.onShareClicked(list[position])
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }
}
interface ClickListener{
    fun onShareClicked(repoModel: MyRepoModel)
}