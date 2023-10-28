package com.driuft.randompets

import android.provider.ContactsContract.CommonDataKinds.Im
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PetAdapter(private val petList: List<Pair<String, String>>) :
    RecyclerView.Adapter<PetAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val petImage: ImageView
        val dogBreedName: TextView

        init {
            petImage = view.findViewById(R.id.pet_image)
            dogBreedName = view.findViewById(R.id.dog_breed_text)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.pet_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val (imageUrl, breedName) = petList[position]

        Glide.with(viewHolder.itemView)
            .load(imageUrl)
            .placeholder(R.drawable.paw_placeholder)
            .centerCrop()
            .into(viewHolder.petImage)

        viewHolder.dogBreedName.text = breedName // Display the breed name

        viewHolder.petImage.setOnClickListener {
            // Handle image click as needed
            // You can use breedName here if necessary
        }
    }

    override fun getItemCount() = petList.size
}