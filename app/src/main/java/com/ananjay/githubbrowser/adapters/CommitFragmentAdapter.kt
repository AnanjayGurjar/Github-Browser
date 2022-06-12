package com.ananjay.githubbrowser.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ananjay.githubbrowser.R
import com.ananjay.githubbrowser.models.commit.CommitModel
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CommitFragmentAdapter(var list: List<CommitModel> , val context: Context) : RecyclerView.Adapter<CommitFragmentAdapter.HomeFragmentViewHolder>() {

    private val TAG = "CommitFragmentAdapter"
    inner class HomeFragmentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var date = itemView.findViewById<TextView>(R.id.tv_date)
        var commitId = itemView.findViewById<TextView>(R.id.tv_commitId)
        var commitMsg = itemView.findViewById<TextView>(R.id.tv_commitMsg)
        var userAvatar = itemView.findViewById<ShapeableImageView>(R.id.iv_userAvatar)
        var userName = itemView.findViewById<TextView>(R.id.tv_commit_userName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFragmentViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_commit, parent, false)
        return HomeFragmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeFragmentViewHolder, position: Int) {
        //T16:58:41Z
        val date = convertDate(list[position].commit.author.date)


        holder.date.text = date
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

    private fun convertDate(dateFromat: String): String{
        val form = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        var date: Date? = null
        try {
            date = form.parse(dateFromat)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val postFormater = SimpleDateFormat("dd-MM-yyyy")
        val newDateStr = postFormater.format(date)
        return newDateStr
    }
}