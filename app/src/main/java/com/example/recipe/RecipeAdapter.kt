package com.example.recipe

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.mistershorr.birthdaytracker.Person
import java.util.*

class RecipeAdapter (var recipeList: List<Person>) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textViewName : TextView
        val layout : ConstraintLayout

        init {
            textViewName = view.findViewById(R.id.textView_birthdayItem_name)
            layout = view.findViewById(R.id.layout_birthdayItem)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_home, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.layout.context
        val person = recipeList[position]
        holder.textViewName.text = person.name

        holder.layout.setOnClickListener {
            val detailIntent = Intent(context, BirthdayDetailActivity::class.java)
            detailIntent.putExtra(BirthdayDetailActivity.EXTRA_PERSON, person)
            context.startActivity(detailIntent)
        }
    }

    

    override fun getItemCount(): Int {
        return recipeList.size
    }
}