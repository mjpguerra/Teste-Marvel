package com.marioguerra.marvelapp.app.ui.characters

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.marioguerra.marvelapp.App
import com.marioguerra.marvelapp.R
import com.marioguerra.marvelapp.app.base.error.ErrorHandler
import com.marioguerra.marvelapp.app.base.hide
import com.marioguerra.marvelapp.app.base.show
import com.marioguerra.marvelapp.app.base.ui.BaseFragment
import com.marioguerra.marvelapp.app.ui.WebActivity
import com.marioguerra.marvelapp.app.ui.character_info.CharacterInfoActivity
import com.marioguerra.marvelapp.app.ui.characters.adapter.CharacterDiffCallback
import com.marioguerra.marvelapp.app.ui.characters.adapter.CharactersAdapter
import com.marioguerra.marvelapp.app.ui.utils.addButtonAnimation
import com.marioguerra.marvelapp.app.ui.utils.showCustomDialog
import com.marioguerra.marvelapp.data.model.character.Character
import kotlinx.android.synthetic.main.characters_fragment.*
/**
 * @author Mario Guerra on 11/09/2019
 */

class CharactersFragment : BaseFragment() {

    companion object {
        private const val SPAN_COUNT = 1
        const val TAG = "CharactersFragmentTag"
        fun newInstance() = CharactersFragment()
    }

    private lateinit var viewModel: CharactersViewModel
    private lateinit var adapter: CharactersAdapter

    private val spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return 1
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataComponent = (activity!!.applicationContext as App).provideDataComponent()
        val characterDataSource = dataComponent.provideCharacterRepository()
        val viewModelFactory = CharactersViewModel.Factory(characterDataSource)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CharactersViewModel::class.java)

    }

    private val characterListener = object : CharactersAdapter.CharacterListener {
        override fun onCharacterClick(character: Character) {

            startActivity(Intent(activity!!, CharacterInfoActivity::class.java)
                .putExtra("id", character.id)
                .putExtra("name", character.name)
                .putExtra("image", character.image?.getFullPath())
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.characters_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupRecyclerView()
        imageView.addButtonAnimation()

        imageView.setOnClickListener {
            startActivity(Intent(activity!!, WebActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }

        getCharacter()
    }

    fun getCharacter(){


        viewModel.getPagination().observe(viewLifecycleOwner, Observer {
            handlePagination(it)
        })

        viewModel.getLoading().observe(viewLifecycleOwner, Observer {
            handleLoading(it)
        })

        viewModel.characters.observe(viewLifecycleOwner,
            Observer {
                adapter.submitList(it)
            }
        )

        viewModel.getError().observe(viewLifecycleOwner, Observer {
                handleError(it)
            })
    }

    private fun setupRecyclerView() {
        val characterDiffCallback = CharacterDiffCallback()
        adapter = CharactersAdapter(context!!, characterDiffCallback, characterListener)

        val layoutManager = GridLayoutManager(context, SPAN_COUNT).apply {
            spanSizeLookup = this@CharactersFragment.spanSizeLookup
        }

        rvCharacters.apply {
            this.layoutManager = layoutManager
            this.adapter = this@CharactersFragment.adapter
        }
    }

    private fun handlePagination(isPagination: Boolean) {
        if (isPagination) {
            adapter.showPagination()
        } else {
            adapter.hidePagination()
        }
    }

    private fun handleLoading(isLoading: Boolean) {
        if (isLoading) {
            pbLoading.show()
            rvCharacters.hide()
        } else {
            rvCharacters.show()
            pbLoading.hide()
        }
    }

    private fun handleError(throwable: Throwable) {
        val errorHandler = ErrorHandler(context!!)
      //  view?.longSnackBar(errorHandler.getErrorMessage(throwable))

        activity!!.showCustomDialog(image = R.drawable.ic_warning, title =
        R.string.dialog_retry_message, subtitleString = errorHandler.getErrorMessage(throwable),
            no = {
                activity!!.finish()
            }, noDismissesDialog = true, yes =
            {
                startActivity(Intent(activity!!, CharacterActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                activity!!.finish()

            }, yesDismissesDialog = true)
    }

}
