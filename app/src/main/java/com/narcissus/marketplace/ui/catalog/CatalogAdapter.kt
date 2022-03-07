package com.narcissus.marketplace.ui.catalog


import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.ItemDepartmentCardBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class CatalogAdapter: RecyclerView.Adapter<CatalogAdapter.DepartmentViewHolder>() {

    var departmentList: List<DepartmentModel> = emptyList()

    inner class DepartmentViewHolder(
        val binding: ItemDepartmentCardBinding,
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartmentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDepartmentCardBinding.inflate(inflater, parent, false)
        return DepartmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DepartmentViewHolder, position: Int) {
        val department = (departmentList[position])

        holder.binding.name.text = department.name

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val departmentStorageReference = Firebase.storage.reference
//                val firebaseAuth = FirebaseAuth.getInstance()
//                val user = firebaseAuth.currentUser
                val departmentImagePath = departmentStorageReference.child(
                    "departmentImage/${department.image}"
                )
                val maxDownloadSize = 1L * 1024 * 1024
                val bytes = departmentImagePath.getBytes(maxDownloadSize).await()
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

                withContext(Dispatchers.Main) {
                    holder.binding.image.load(bitmap) {
//                        crossfade(true)
//                        crossfade(500)
//                        placeholder(R.drawable.ic_hint)
                        transformations(CoilGradientTransformation())
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {

                }
            }
        }
    }

    override fun getItemCount(): Int {
        return departmentList.size
    }
}

data class DepartmentModel(
    val name:String,
    val image:String
)

