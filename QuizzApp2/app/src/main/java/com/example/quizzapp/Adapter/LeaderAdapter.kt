package com.example.quizzapp.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.quizzapp.Domain.UserModel
import com.example.quizzapp.databinding.ViewholderLeadersBinding

class LeaderAdapter : RecyclerView.Adapter<LeaderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewholderLeadersBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position], position)
    }

    inner class ViewHolder(private val binding: ViewholderLeadersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserModel, position: Int) {
            binding.titleTxt.text = user.name
            val drawableResourceId: Int = binding.root.resources.getIdentifier(
                user.pic, "drawable", binding.root.context.packageName
            )
            Glide.with(binding.root.context).load(drawableResourceId).into(binding.pic)
            binding.rowTxt.text = (position + 4).toString()
            binding.scoreTxt.text = user.score.toString()
        }
    }

    override fun getItemCount() = differ.currentList.size

    private val differCallback = object : DiffUtil.ItemCallback<UserModel>() {

        override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}
