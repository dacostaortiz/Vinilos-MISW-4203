package com.app.vinilos_misw4203.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
import com.app.vinilos_misw4203.R
import com.app.vinilos_misw4203.databinding.AlbumItemBinding
import com.app.vinilos_misw4203.models.Album

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private var _albums: List<Album> = emptyList()

    var albums: List<Album>
        get() = _albums
        set(value) {
            val diffCallback = AlbumDiffCallback(_albums, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            _albums = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val withDataBinding: AlbumItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            HomeViewHolder.LAYOUT,
            parent,
            false
        )
        return HomeViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.album = albums[position]

            it.cardContainer.setOnClickListener {
                val context = holder.itemView.context
                Toast.makeText(
                    context,
                    "Esta funcionalidad aún no está lista",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    class HomeViewHolder(val viewDataBinding: AlbumItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_item
        }
    }

    private class AlbumDiffCallback(
        private val oldList: List<Album>,
        private val newList: List<Album>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].albumId == newList[newItemPosition].albumId
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}