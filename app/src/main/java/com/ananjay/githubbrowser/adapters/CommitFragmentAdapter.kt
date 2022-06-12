package com.ananjay.githubbrowser.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ananjay.githubbrowser.R
import com.ananjay.githubbrowser.database.MyRepoModel
import com.ananjay.githubbrowser.models.commit.CommitModel
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import org.w3c.dom.Text

class CommitFragmentAdapter(var list: List<CommitModel> , val context: Context) : RecyclerView.Adapter<CommitFragmentAdapter.HomeFragmentViewHolder>() {

    inner class HomeFragmentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var date = itemView.findViewById<TextView>(R.id.tv_date)
        var commitId = itemView.findViewById<TextView>(R.id.tv_commitId)
        var commitMsg = itemView.findViewById<TextView>(R.id.tv_commitMsg)
        var userAvatar = itemView.findViewById<ShapeableImageView>(R.id.iv_userAvatar)
        var userName = itemView.findViewById<TextView>(R.id.tv_commit_userName)

//        init {
//            itemView.setOnClickListener{
//                listener.invoke(adapterPosition)
//            }
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFragmentViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_commit, parent, false)
        return HomeFragmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeFragmentViewHolder, position: Int) {
        holder.date.text = list[position].commit.author.date
        holder.commitId.text = list[position].author.id.toString()
        holder.commitMsg.text = list[position].commit.message
        holder.userName.text = list[position].commit.author.name
        Glide.with(context)
            .load(list[position].author.avatar_url)
            .error(R.drawable.user_avatar)
            .into(holder.userAvatar)
    }
    override fun getItemCount(): Int {
        return list.size
    }
}