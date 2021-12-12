package com.kalabukhov.app.translator.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kalabukhov.app.translator.R
import com.kalabukhov.app.translator.databinding.ItemWordBinding
import com.kalabukhov.app.translator.domain.entity.DataModel
import com.kalabukhov.app.translator.domain.entity.WordEntity
import com.kalabukhov.app.translator.ui.main.AdapterWords
import com.kalabukhov.app.translator.ui.main.MainActivity

class AdapterHistoryWords (
    private var onItemViewClickListener: HistoryWords.OnItemViewClickListener
): RecyclerView.Adapter<AdapterHistoryWords.MainViewHolder>() {

    private var dataModel: List<WordEntity> = listOf()

    fun setDataModel(data: List<WordEntity>) {
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
        fun bind(dataModel: WordEntity) = with(binding) {
            headerTextviewRecyclerItem.text = dataModel.tittle
            descriptionTextviewRecyclerItem.text = dataModel.translation
            root.setOnClickListener {
                onItemViewClickListener.onItemViewClick(dataModel)
            }
        }
    }
}