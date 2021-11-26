package com.kalabukhov.app.translator.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kalabukhov.app.translator.R
import com.kalabukhov.app.translator.databinding.ItemWordBinding
import com.kalabukhov.app.translator.domain.entity.DataModel

class AdapterWords (
    private var onItemViewClickListener: MainActivity.OnItemViewClickListener
) : RecyclerView.Adapter<AdapterWords.MainViewHolder>() {

    private var dataModel: List<DataModel> = listOf()

    fun setDataModel(data: List<DataModel>) {
        dataModel = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = MainViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_word, parent, false) as View
    )

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(dataModel[position])
    }

    override fun getItemCount(): Int = dataModel.size

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemWordBinding.bind(view)
        fun bind(dataModel: DataModel) = with(binding) {
            headerTextviewRecyclerItem.text = dataModel.text
            descriptionTextviewRecyclerItem.text = dataModel
                .meanings
                ?.get(0)
                ?.translation
                ?.translation
            root.setOnClickListener {
                onItemViewClickListener.onItemViewClick(dataModel)
            }
        }
    }
}