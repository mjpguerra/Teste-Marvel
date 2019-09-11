package com.marioguerra.marvelapp.app.ui.character_info

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.marioguerra.marvelapp.App

import com.marioguerra.marvelapp.R
import com.marioguerra.marvelapp.app.base.error.ErrorHandler
import com.marioguerra.marvelapp.app.base.hide
import com.marioguerra.marvelapp.app.base.longSnackBar
import com.marioguerra.marvelapp.app.base.show
import com.marioguerra.marvelapp.app.base.ui.BaseFragment
import com.marioguerra.marvelapp.app.ui.characters.CharactersFragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.marioguerra.marvelapp.app.ui.utils.addButtonAnimation
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.bottom_sheet_home.*
import kotlinx.android.synthetic.main.character_info_fragment.*
import kotlin.math.abs
/**
 * @author Mario Guerra on 11/09/2019
 */

class CharacterInfoFragment : BaseFragment() {
    enum class State {
        EXPANDED,
        COLLAPSED,
        IDLE
    }
    companion object {
        private const val CHARACTER_ID_KEY = "characterIdKey"
        private const val CHARACTER_NAME_KEY = "characterNameKey"
        private const val IMAGE_URL_KEY = "imageUrlKey"
        const val TAG = "CharacterInfoFragmentTag"
        fun newInstance(
            characterId: Int,
            characterName: String?,
            imageUrl: String?
        ): CharacterInfoFragment {
            return CharacterInfoFragment().apply {
                arguments = Bundle().apply {
                    putInt(CHARACTER_ID_KEY, characterId)
                    putString(CHARACTER_NAME_KEY, characterName)
                    putString(IMAGE_URL_KEY, imageUrl)
                }
            }
        }
    }
    private var mCurrentState = State.IDLE
    private lateinit var toolbar: Toolbar
    private lateinit var viewModel: CharacterInfoViewModel
    lateinit var collapsingToolbarLayout: CollapsingToolbarLayout
    private lateinit var tvHideBallance: TextView
    private lateinit var tvCharacterName: TextView
    private lateinit var floatingActionButton: FloatingActionButton

    lateinit var appBarLayout: AppBarLayout
    private lateinit var cvName: CardView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.character_info_fragment, container, false)

        collapsingToolbarLayout = view.findViewById(R.id.clCollapsing)
        appBarLayout = view.findViewById(R.id.ablCollapse)
        tvCharacterName= view.findViewById(R.id.tvCharacterName)
        toolbar = view.findViewById(R.id.toolbar) as Toolbar
        floatingActionButton= view.findViewById(R.id.floatingActionButton)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        cvName= view.findViewById(R.id.cvName)

        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            collapsingToolbarLayout.title = ""
            when {
                verticalOffset == 0 -> setCurrentStateAndNotify(State.EXPANDED) {
                    collapsingToolbarLayout.title = ""
                    collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.ExpandedText)
                }
                abs(verticalOffset) >= appBarLayout.totalScrollRange -> setCurrentStateAndNotify(State.COLLAPSED) {
                    collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedText)
                    collapsingToolbarLayout.title = arguments!!.getString(CHARACTER_NAME_KEY)
                    cvName.animate().alpha(0f).duration = 300
                    cvName.isClickable = true
                }else -> setCurrentStateAndNotify(State.IDLE) {
                    collapsingToolbarLayout.title = ""
                    cvName.animate().alpha(1.0f).duration = 300
                    cvName.isClickable = true
                    collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.HiddenText)
                }
            }
        })

        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.HiddenText)
        collapsingToolbarLayout.title = ""

        floatingActionButton.setOnClickListener {
            activity!!.finish()
        }


        return view
    }
    private fun setCurrentStateAndNotify(state: State, func: (() -> Unit)) {
        if (mCurrentState !== state) {
            mCurrentState = state
            func()
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val characterId = arguments!!.getInt(CHARACTER_ID_KEY)
        val characterName = arguments!!.getString(CHARACTER_NAME_KEY)
        val imageUrl = arguments!!.getString(IMAGE_URL_KEY)

        cvName.addButtonAnimation()
        floatingActionButton.addButtonAnimation()
        tvCharacterDescription.addButtonAnimation()

        val dataComponent = (activity!!.application as App).provideDataComponent()
        val characterDataSource = dataComponent.provideCharacterRepository()
        val viewModelFactory = CharacterInfoViewModel.Factory(characterId, characterDataSource)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(CharacterInfoViewModel::class.java)

        tvCharacterName.text = characterName ?: getString(R.string.description_not_found)
        loadCharacterAvatar(imageUrl)

        viewModel.getLoading().observe(viewLifecycleOwner, Observer {
            if (it) {
                //pbLoading.show()
                tvCharacterDescription.hide()
            } else {
                tvCharacterDescription.show()
               // pbLoading.hide()
            }
        })



        viewModel.getCharacterInfo().observe(viewLifecycleOwner, Observer {
            val description = it.description
            if (description != null && description.isNotEmpty()) {
                tvCharacterDescription.text = description
            } else {
                tvCharacterDescription.text = getString(R.string.description_not_found)
            }
        })

        viewModel.getError().observe(viewLifecycleOwner, Observer {
            val errorHandler = ErrorHandler(context!!)
            view?.longSnackBar(errorHandler.getErrorMessage(it))
        })

    }

    private fun loadCharacterAvatar(imageUrl: String?) {

        Picasso.get().load(imageUrl).fit().into(ivCharacterAvatar)

    }

}
