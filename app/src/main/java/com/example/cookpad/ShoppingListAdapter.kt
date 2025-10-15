// ShoppingListAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.example.cookpad.R // Make sure this is your app's package
import com.example.cookpad.IngredientItem

class ShoppingListAdapter(private val items: List<IngredientItem>) :
    RecyclerView.Adapter<ShoppingListAdapter.IngredientItemViewHolder>() {

    inner class IngredientItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameCheckbox: MaterialCheckBox = itemView.findViewById(R.id.ingredientCheckbox)
        private val quantityTextView: TextView = itemView.findViewById(R.id.ingredientMeasurement)

        fun bind(item: IngredientItem) {
            // set name and state of checkbox
            nameCheckbox.text = item.name
            nameCheckbox.isChecked = item.isChecked

            // set measurement
            quantityTextView.text = item.measurement

            // listener for when checkbox is checked/unchecked
            nameCheckbox.setOnCheckedChangeListener { _, isChecked ->
                item.isChecked = isChecked
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.shopping_list_item, parent, false)
        return IngredientItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}