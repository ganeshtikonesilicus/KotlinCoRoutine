package com.example.kotlincoroutines

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlincoroutines.model.Data


/**
 * Created by Ganesh Tikone on 4/2/19.
 * Company: Silicus Technologies Pvt. Ltd.
 * Email: ganesh.tikone@silicus.com
 * Class: CodeAdapter
 * Description: CodeAdapter for RecyclerView
 */
class CodeAdapter : RecyclerView.Adapter<CodeAdapter.CodeViewHolder>() {

    private val listOfCode = mutableListOf<Data>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CodeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_code_standard, parent, false)
        return CodeViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listOfCode.size
    }

    override fun onBindViewHolder(holder: CodeViewHolder, position: Int) {
        val data = listOfCode[position]
        holder.codeTileTextView.text = data.code
        holder.codeSubTileTextView.text = data.description
    }

    /**
     * Update Data
     */
    fun updateData(codes: List<Data>){
        listOfCode.clear()
        listOfCode.addAll(codes)
        notifyDataSetChanged()
    }

    /**
     * ViewHolder Class for R.layout.row_code_standard
     */
    class CodeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val codeTileTextView: AppCompatTextView = itemView.findViewById(R.id.codeTitle)
        val codeSubTileTextView: AppCompatTextView = itemView.findViewById(R.id.codeSubTitle)
    }
}