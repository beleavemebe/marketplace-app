package com.narcissus.marketplace.ui.catalog

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.narcissus.marketplace.databinding.ListItemDepartmentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

typealias DepartmentBinding = ListItemDepartmentBinding

data class DepartmentListItem(
    val name: String,
    val image: String
) {
    companion object {
        @JvmStatic
        fun inflateBinding(
            inflater: LayoutInflater,
            parent: ViewGroup,
        ) = ListItemDepartmentBinding.inflate(inflater, parent, false)

        fun delegate(scope: CoroutineScope) =
            adapterDelegateViewBinding<DepartmentListItem, DepartmentListItem, DepartmentBinding>(
                ::inflateBinding
            ) {
                val storage = Firebase.storage.reference
                val maxDownloadSize = 1L * 1024 * 1024

                suspend fun fetchBitmap(imgName: String): Bitmap {
                    val path = storage.child("departmentImage/$imgName")

                    val bytes = withContext(Dispatchers.IO) {
                        path.getBytes(maxDownloadSize).await()
                    }

                    val bitmap = withContext(Dispatchers.Default) {
                        BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    }

                    return bitmap
                }

                bind {
                    binding.tvName.text = item.name
                    scope.launch {
                        val bitmap = fetchBitmap(item.image)
                        binding.ivImage.load(bitmap) {
                            transformations(GradientTransformation())
                        }
                    }
                }
            }
    }
}
