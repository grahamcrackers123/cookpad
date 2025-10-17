// ShoppingListAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cookpad.Ingredient
import com.google.android.material.checkbox.MaterialCheckBox
import com.example.cookpad.R // Make sure this is your app's package

class ShoppingListAdapter(private val items: List<Ingredient>) :
    RecyclerView.Adapter<ShoppingListAdapter.IngredientViewHolder>() {

    inner class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameCheckbox: MaterialCheckBox = itemView.findViewById(R.id.ingredientCheckbox)
        private val quantityTextView: TextView = itemView.findViewById(R.id.ingredientMeasurement)

        fun bind(item: Ingredient) {
            // set name and state of checkbox
            nameCheckbox.text = item.name
            nameCheckbox.isChecked = item.isChecked

            // set measurement
            quantityTextView.text = item.amount

            // listener for when checkbox is checked/unchecked
            nameCheckbox.setOnCheckedChangeListener { _, isChecked ->
                item.isChecked = isChecked
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.shopping_list_item, parent, false)
        return IngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}