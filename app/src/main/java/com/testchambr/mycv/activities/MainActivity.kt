package com.testchambr.mycv.activities

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.nitrico.lastadapter.LastAdapter
import com.testchambr.mycv.BR
import com.testchambr.mycv.R
import com.testchambr.mycv.contracts.MainContract
import com.testchambr.mycv.databinding.*
import com.testchambr.mycv.extensions.showToast
import com.testchambr.mycv.extensions.toggleVisibility
import com.testchambr.mycv.helpers.Utils
import com.testchambr.mycv.models.*
import com.testchambr.mycv.presenters.MainActivityPresenter
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
    private var items = mutableListOf<ItemSet>()

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

        // Minimizing all expanded sections
        toolbarCollapseButton.setOnClickListener {
            setViewState()
        }

        tryAgainButton.setOnClickListener {
            getCV()
        }
    }

    private fun prepare() {
        // To not repeat same codes for each section
        populateItems()

        // Setting up visibility property of views
        setupItems()

        // Displaying loading screen
        showLoadingOverlay()

        // Hiding all sections
        setViewState()
    }

    private fun populateItems() {
        items = mutableListOf(
            ItemSet(skillsetHeader, skillsetContainer, skillsetRecyclerView),
            ItemSet(personalProjectsHeader, personalProjectsContainer, personalProjectsRecyclerView),
            ItemSet(incompletePersonalProjectsHeader, incompletePersonalProjectsContainer, incompletePersonalProjectsRecyclerView),
            ItemSet(oldProjectsHeader, oldProjectsContainer, oldProjectsRecyclerView),
            ItemSet(workHeader, workContainer, workRecyclerView),
            ItemSet(educationHeader, educationContainer, educationRecyclerView),
            ItemSet(languagesHeader, languagesContainer, languagesRecyclerView),
            ItemSet(hobbiesHeader, hobbiesContainer, hobbiesRecyclerView)
        )
    }

    private fun setupItems() {
        items.forEach {
            val itemSet = it
            itemSet.header.setOnClickListener {
                itemSet.container.toggleVisibility()
            }

            setupRecyclerView(itemSet.recyclerView)
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.setHasFixedSize(false)
        recyclerView.isNestedScrollingEnabled = false
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun showLoadingOverlay() {
        loadingTextView.visibility = View.VISIBLE
        tryAgainButton.visibility = View.GONE

        loadingOverlay.visibility = View.VISIBLE
    }

    private fun hideLoadingOverlay() {
        loadingOverlay.visibility = View.GONE
    }

    private fun setViewState() {
        items.forEach {
            it.container.visibility = View.GONE
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

        // Skillset adapter
        LastAdapter(cv.skillset, BR.item)
            .map<Skill, ObjectSkillBinding>(R.layout.object_skill)
            .into(skillsetRecyclerView)

        // Personal projects adapter
        LastAdapter(cv.personalProjects, BR.item)
            .map<Project, ObjectSkillBinding>(R.layout.object_project)
            .into(personalProjectsRecyclerView)

        // Incomplete personal projects adapter
        LastAdapter(cv.incompletePersonalProjects, BR.item)
            .map<Project, ObjectSkillBinding>(R.layout.object_project)
            .into(incompletePersonalProjectsRecyclerView)

        // Old projects adapter
        LastAdapter(cv.oldProjects, BR.item)
            .map<Project, ObjectSkillBinding>(R.layout.object_project)
            .into(oldProjectsRecyclerView)

        // Work adapter
        LastAdapter(cv.work, BR.item)
            .map<Work, ObjectWorkBinding>(R.layout.object_work)
            .into(workRecyclerView)

        // Education adapter
        LastAdapter(cv.education, BR.item)
            .map<Education, ObjectEducationBinding>(R.layout.object_education)
            .into(educationRecyclerView)

        if (cv.background != null) {
            // Languages adapter
            LastAdapter(cv.background!!.languages, BR.item)
                .map<Language, ObjectLanguageBinding>(R.layout.object_language)
                .into(languagesRecyclerView)

            // Hobbies adapter
            LastAdapter(cv.background!!.hobbies, BR.item)
                .map<String, ObjectHobbyBinding>(R.layout.object_hobby)
                .into(hobbiesRecyclerView)

            languagesHeader.visibility = View.VISIBLE
            hobbiesHeader.visibility = View.VISIBLE
        } else {
            // Hiding these sections if background data is null
            languagesHeader.visibility = View.GONE
            hobbiesHeader.visibility = View.GONE
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
