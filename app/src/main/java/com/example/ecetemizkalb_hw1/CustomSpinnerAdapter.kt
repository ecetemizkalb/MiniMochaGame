package com.example.ecetemizkalb_hw1

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.ecetemizkalb_hw1.cat.Cat
import com.example.ecetemizkalb_hw1.R

class CustomSpinnerAdapter(var contextt: Context, var spinnerItemValues: ArrayList<Cat>)
    : ArrayAdapter<Cat>(contextt, R.layout.spinner_item, spinnerItemValues)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?,parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    @SuppressLint("MissingInflatedId")
    fun getCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflator = contextt.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflator.inflate(R.layout.spinner_item, parent, false)

        val constraintLayout:ConstraintLayout = view.findViewById(R.id.itemConstraintLayout)
        val imgItemMood:ImageView = view.findViewById(R.id.imgItemMood)
        val tvItemCatMood:TextView = view.findViewById(R.id.tvItemCatMood)

        val selectedMood = spinnerItemValues.get(position)
        tvItemCatMood.text = selectedMood.getMood()
        selectedMood.getEmojiId()?.let { imgItemMood.setImageResource(it) }

        if (position % 2 == 0)
            constraintLayout.setBackgroundColor(Color.rgb(233,247,239))
        else
            constraintLayout.setBackgroundColor(Color.WHITE)
        return view
    }
}
