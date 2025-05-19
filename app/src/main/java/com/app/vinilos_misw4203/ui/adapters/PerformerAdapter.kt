package com.app.vinilos_misw4203.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import android.os.Bundle
import androidx.navigation.findNavController
import com.app.vinilos_misw4203.R
import com.app.vinilos_misw4203.databinding.PerformerItemBinding
import com.app.vinilos_misw4203.models.Performer
import com.app.vinilos_misw4203.ui.PerformerFragmentDirections

class PerformerAdapter : RecyclerView.Adapter<PerformerAdapter.PerformerViewHolder>() {

    var performers: List<Performer> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerformerViewHolder {
        val withDataBinding: PerformerItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            PerformerViewHolder.LAYOUT,
            parent,
            false
        )
        return PerformerViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: PerformerViewHolder, position: Int) {
        val performer = performers[position]
        holder.viewDataBinding.also {
            it.performer = performer

            it.cardContainer.setOnClickListener { view ->
                val action = PerformerFragmentDirections
                    .actionPerformerFragmentToPerformerDetailFragment(performer.performerId)
                view.findNavController().navigate(action)
            }
        }
    }

    override fun getItemCount(): Int {
        return performers.size
    }

    class PerformerViewHolder(val viewDataBinding: PerformerItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.performer_item
        }
    }
}