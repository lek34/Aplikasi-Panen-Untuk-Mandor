package com.example.gpapanen

import ItemAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private val items = mutableListOf<Item>()
    private lateinit var adapter: ItemAdapter
    private lateinit var databaseHandler: DatabaseHandler

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView) // Menyambungkan recyclerView dengan elemen di layout XML
        adapter = ItemAdapter(items)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Inisialisasi databaseHandler di sini
        databaseHandler = DatabaseHandler(this)

        val items = databaseHandler.getAllItems()
        if (items.isEmpty()) {
            // Tampilkan pesan "Test Belum Ada Data"
            // Anda dapat menambahkannya langsung ke RecyclerView atau menampilkan pesan secara terpisah
            // Menampilkan pesan "Belum Ada Data" jika tidak ada item yang ditemukan
            findViewById<TextView>(R.id.pesan).text = "Belum Ada Data"
        } else {
            // Menampilkan data dari database ke RecyclerView
            adapter.setItems(items)
            adapter.notifyDataSetChanged()
        }





        val fabAdd: FloatingActionButton = findViewById(R.id.fabAdd)
        fabAdd.setOnClickListener {
            fabAdd.setOnClickListener {
                showAddItemDialog()
            }
        }


    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showAddItemDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Tambah Item Baru")

        val view = layoutInflater.inflate(R.layout.dialog_add_item, null)
        val editTextNama = view.findViewById<EditText>(R.id.editTextNama)
        val editTextPekerjaan = view.findViewById<EditText>(R.id.editTextPekerjaan)

        builder.setView(view)

        builder.setPositiveButton("Tambah") { dialog, _ ->
            val nama = editTextNama.text.toString()
            val pekerjaan = editTextPekerjaan.text.toString()
            val newItem = Item(0, nama, pekerjaan)

            // Menambahkan item ke RecyclerView
            val id = databaseHandler.addItem(newItem)
            newItem.id = id.toInt() // Mengatur ID yang diberikan oleh database
            adapter.addItem(newItem)
            this.recreate();
            dialog.dismiss()
        }

        builder.setNegativeButton("Batal") { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }

}