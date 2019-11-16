/*
 * *
 *  * Created by Berkay AKIN on 11/16/19 8:47 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 11/16/19 8:36 PM
 *
 */

package com.testchambr.mycv.activities

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.nitrico.lastadapter.LastAdapter
import com.testchambr.mycv.BR
import com.testchambr.mycv.R
import com.testchambr.mycv.contracts.MainContract
import com.testchambr.mycv.databinding.*
import com.testchambr.mycv.extensions.showToast
import com.testchambr.mycv.helpers.Utils
import com.testchambr.mycv.models.*
import com.testchambr.mycv.presenters.MainActivityPresenter
import com.testchambr.mycv.views.SectionView
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.appbar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.loading_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), MainContract.View {

    private val presenter: MainContract.Presenter = MainActivityPresenter(this)
    private var cv: CV? = null
    private var sections = mutableListOf<SectionView>()

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        getCV()
    }

    override fun initView() {
        setSupportActionBar(toolbar)

        // Light status bar if night mode is off
        if (!Utils.isNightModeOn(this)) {
            Utils.setLightStatusBar(this)
        }

        setContentView(R.layout.activity_main)

        // Preparing views
        prepare()

        // Collect all sections
        populateSections()

        // Minimizing all expanded sections
        toolbarCollapseButton.setOnClickListener {
            minimizeSections()
        }

        // Try again button action
        tryAgainButton.setOnClickListener {
            getCV()
        }
    }

    private fun populateSections() {
        sections = mutableListOf(
            sectionSkillset,
            sectionPersonalProjects,
            sectionIncompletePersonalProjects,
            sectionOldProjects,
            sectionWork,
            sectionEducation,
            sectionLanguages,
            sectionHobbies
        )
    }

    private fun prepare() {
        // Displaying loading screen
        showLoadingOverlay()
    }

    private fun showLoadingOverlay() {
        loadingTextView.visibility = View.VISIBLE
        tryAgainButton.visibility = View.GONE

        loadingOverlay.visibility = View.VISIBLE
    }

    private fun hideLoadingOverlay() {
        loadingOverlay.visibility = View.GONE
    }

    private fun minimizeSections() {
        sections.forEach {
            it.minimize()
        }
    }

    private fun getCV() {
        GlobalScope.launch {
            var hasFailed = false
            try {
                cv = presenter.getCV()
            } catch (e: Exception) {
                hasFailed = true
            }
            withContext(Dispatchers.Main) {
                if (hasFailed) {
                    loadingTextView.visibility = View.GONE
                    tryAgainButton.visibility = View.VISIBLE
                    showToast(resources.getString(R.string.error))
                } else {
                    hideLoadingOverlay()

                    cv?.let {
                        displayCV(it)
                    }
                }
            }
        }
    }

    private fun displayCV(cv: CV) {
        // Profile picture
        Glide.with(this)
            .load(R.drawable.profile)
            .centerCrop()
            .apply(RequestOptions.circleCropTransform())
            .into(profilePictureImageView)

        // Basic information
        nameTextView.text = cv.name
        titleTextView.text = cv.title
        locationTextView.text = cv.location

        // Total items
        sectionSkillset.setTotalItem(cv.skillset.size)
        sectionPersonalProjects.setTotalItem(cv.personalProjects.size)
        sectionIncompletePersonalProjects.setTotalItem(cv.incompletePersonalProjects.size)
        sectionOldProjects.setTotalItem(cv.oldProjects.size)
        sectionWork.setTotalItem(cv.work.size)
        sectionEducation.setTotalItem(cv.education.size)
        sectionLanguages.setTotalItem(cv.background?.languages?.size ?: 0)
        sectionHobbies.setTotalItem(cv.background?.hobbies?.size ?: 0)

        // Skillset adapter
        LastAdapter(cv.skillset, BR.item)
            .map<Skill, ObjectSkillBinding>(R.layout.object_skill)
            .into(sectionSkillset.sectionRecyclerView)

        // Personal projects adapter
        LastAdapter(cv.personalProjects, BR.item)
            .map<Project, ObjectSkillBinding>(R.layout.object_project)
            .into(sectionPersonalProjects.sectionRecyclerView)

        // Incomplete personal projects adapter
        LastAdapter(cv.incompletePersonalProjects, BR.item)
            .map<Project, ObjectSkillBinding>(R.layout.object_project)
            .into(sectionIncompletePersonalProjects.sectionRecyclerView)

        // Old projects adapter
        LastAdapter(cv.oldProjects, BR.item)
            .map<Project, ObjectSkillBinding>(R.layout.object_project)
            .into(sectionOldProjects.sectionRecyclerView)

        // Work adapter
        LastAdapter(cv.work, BR.item)
            .map<Work, ObjectWorkBinding>(R.layout.object_work)
            .into(sectionWork.sectionRecyclerView)

        // Education adapter
        LastAdapter(cv.education, BR.item)
            .map<Education, ObjectEducationBinding>(R.layout.object_education)
            .into(sectionEducation.sectionRecyclerView)

        if (cv.background != null) {
            // Languages adapter
            LastAdapter(cv.background!!.languages, BR.item)
                .map<Language, ObjectLanguageBinding>(R.layout.object_language)
                .into(sectionLanguages.sectionRecyclerView)

            // Hobbies adapter
            LastAdapter(cv.background!!.hobbies, BR.item)
                .map<String, ObjectHobbyBinding>(R.layout.object_hobby)
                .into(sectionHobbies.sectionRecyclerView)

            sectionLanguages.visibility = View.VISIBLE
            sectionHobbies.visibility = View.VISIBLE
        } else {
            // Hiding these sections if background data is null
            sectionLanguages.visibility = View.GONE
            sectionHobbies.visibility = View.GONE
        }
    }

    override fun onBackPressed() {
        var hasExpandedSection = false

        // Checking if there is any expanded section
        sections.forEach {
            if (it.isExpanded()) {
                hasExpandedSection = true
                return@forEach
            }
        }

        // Minimizing all sections conditionally
        if (hasExpandedSection) {
            minimizeSections()
        } else {
            super.onBackPressed()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        cv?.let {
            outState.putSerializable("cv", it)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        prepare()

        // If CV is loaded successfully once and saved into a bundle, loading from it
        if (savedInstanceState?.containsKey("cv") == true) {
            cv = savedInstanceState.getSerializable("cv") as CV

            hideLoadingOverlay()
            displayCV(cv!!)
        } else {
            // Make a request to get CV one more time
            getCV()
        }
    }
}
