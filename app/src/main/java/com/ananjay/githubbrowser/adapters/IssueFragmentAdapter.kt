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
import com.ananjay.githubbrowser.models.issue.IssueModel
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.coroutineScope
import org.w3c.dom.Text

class IssueFragmentAdapter(var list: List<IssueModel>, var context: Context) : RecyclerView.Adapter<IssueFragmentAdapter.HomeFragmentViewHolder>() {

    class HomeFragmentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var title = itemView.findViewById<TextView>(R.id.tv_issue)
        var userName = itemView.findViewById<TextView>(R.id.tv_userName)
        var userImage = itemView.findViewById<ShapeableImageView>(R.id.iv_user_avatar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFragmentViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_issue, parent, false)
        return HomeFragmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeFragmentViewHolder, position: Int) {
        holder.title.text = list[position].title
        holder.userName.text = list[position].user.login
        Glide.with(context)
            .load(list[position].user.avatar_url)
            .error(R.drawable.user_avatar)
            .into(holder.userImage)
    }
    override fun getItemCount(): Int {
        return list.size
    }
}