package com.app.vinilos_misw4203.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.findNavController
import com.app.vinilos_misw4203.R
import com.app.vinilos_misw4203.databinding.PerformerItemBinding
import com.app.vinilos_misw4203.models.Performer
import com.app.vinilos_misw4203.ui.PerformerFragmentDirections
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class PerformerAdapter : RecyclerView.Adapter<PerformerAdapter.PerformerViewHolder>() {

    private var _performers: List<Performer> = emptyList()

    var performers: List<Performer>
        get() = _performers
        set(value) {
            val diffCallback = PerformerDiffCallback(_performers, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            _performers = value
            diffResult.dispatchUpdatesTo(this)
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
        holder.bind(performer)
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

        fun bind(performer: Performer) {
            Glide.with(itemView)
                .load(performer.image.toUri().buildUpon().scheme("https").build())
                .apply(RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_broken_image))
                .into(viewDataBinding.performerImage)
        }
    }

    private class PerformerDiffCallback(
        private val oldList: List<Performer>,
        private val newList: List<Performer>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].performerId == newList[newItemPosition].performerId
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}