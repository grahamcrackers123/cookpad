package com.example.cookpad

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecipeAdapter(
    private var recipes: MutableList<Recipe>,
    private val onItemClick: (Recipe) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>(), Filterable {

    private var originalRecipes: List<Recipe> = ArrayList(recipes)

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.recipeName)
        private val imageView: ImageView = itemView.findViewById(R.id.recipeImage)

        fun bind(recipe: Recipe) {
            nameTextView.text = recipe.title

            Glide.with(itemView.context)
                .load(recipe.image)
                .placeholder(R.drawable.ic_image) // A default image while loading
                .into(imageView)

            itemView.setOnClickListener {
                onItemClick(recipe)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipe_item, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipes[position])
    }

    override fun getItemCount() = recipes.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = mutableListOf<Recipe>()
                if (constraint.isNullOrEmpty()) {
                    filteredList.addAll(originalRecipes)
                } else {
                    val filterPattern = constraint.toString().lowercase().trim()
                    originalRecipes.forEach { recipe ->
                        if (recipe.title.lowercase().contains(filterPattern)) {
                            filteredList.add(recipe)
                        }
                    }
                }
                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                recipes.clear()
                if (results?.values is List<*>) {
                    recipes.addAll(results.values as List<Recipe>)
                }
                notifyDataSetChanged()
            }
        }
    }
}
