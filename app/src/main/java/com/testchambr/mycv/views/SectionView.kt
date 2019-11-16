package com.testchambr.mycv.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.testchambr.mycv.R
import com.testchambr.mycv.extensions.toggleVisibility

class SectionView(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {

    var sectionHeader: View
    var sectionHeaderTextView: TextView
    var sectionTotalTextView: TextView
    var iconExpand: AppCompatImageView
    var sectionContent: View
    var sectionRecyclerView: RecyclerView

    init {
        inflate(context, R.layout.layout_section_view, this)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.SectionView)

        sectionHeader = findViewById(R.id.sectionHeader)
        sectionHeaderTextView = findViewById(R.id.sectionHeaderTextView)
        sectionTotalTextView = findViewById(R.id.sectionTotalTextView)
        iconExpand = findViewById(R.id.iconExpand)
        sectionContent = findViewById(R.id.sectionContent)
        sectionRecyclerView = findViewById(R.id.sectionRecyclerView)

        sectionHeader.setOnClickListener {
            toggle()
        }
        sectionHeaderTextView.text = attributes.getString(R.styleable.SectionView_text)

        sectionRecyclerView.setHasFixedSize(false)
        sectionRecyclerView.isNestedScrollingEnabled = false
        sectionRecyclerView.layoutManager = LinearLayoutManager(context)

        minimize()

        attributes.recycle()
    }

    fun setTotalItem(total: Int) {
        sectionTotalTextView.text = if (total > 1) {
             resources.getString(R.string.total_items, total)
        } else {
            resources.getString(R.string.total_item, total)
        }
    }

    fun toggle() {
        sectionContent.toggleVisibility()
    }

    fun expand() {
        sectionContent.visibility = View.VISIBLE
    }

    fun minimize() {
        sectionContent.visibility = View.GONE
    }

}