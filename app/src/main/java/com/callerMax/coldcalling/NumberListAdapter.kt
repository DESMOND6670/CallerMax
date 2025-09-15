package com.callerMax.coldcalling

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NumberListAdapter(
    private val onRemoveClick: (String) -> Unit
) : RecyclerView.Adapter<NumberListAdapter.NumberViewHolder>() {
    
    private var numbers = listOf<String>()
    
    fun updateNumbers(newNumbers: List<String>) {
        numbers = newNumbers
        notifyDataSetChanged()
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return NumberViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        holder.bind(numbers[position])
    }
    
    override fun getItemCount(): Int = numbers.size
    
    inner class NumberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(android.R.id.text1)
        
        fun bind(number: String) {
            textView.text = number
            itemView.setOnClickListener {
                onRemoveClick(number)
            }
        }
    }
}
