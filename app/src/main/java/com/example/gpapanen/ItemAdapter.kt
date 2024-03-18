import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gpapanen.Item
import com.example.gpapanen.R

class ItemAdapter(private val items: MutableList<Item>) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_form, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.textNama.text = item.nama
        holder.textPekerjaan.text = item.pekerjaan
    }

    override fun getItemCount(): Int {
        return items.size
    }

    // Metode untuk menambahkan item baru ke RecyclerView
    @SuppressLint("NotifyDataSetChanged")
    fun addItem(item: Item) {
        items.add(item)
        notifyDataSetChanged() // Memberi tahu adapter bahwa data telah berubah
    }

    // Fungsi untuk mengatur daftar item yang akan ditampilkan
    @SuppressLint("NotifyDataSetChanged")
    fun setItems(newItems: List<Item>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged() // Memberi tahu adapter bahwa data telah berubah
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textNama: TextView = itemView.findViewById(R.id.nama)
        val textPekerjaan: TextView = itemView.findViewById(R.id.pekerjaan)
    }
}