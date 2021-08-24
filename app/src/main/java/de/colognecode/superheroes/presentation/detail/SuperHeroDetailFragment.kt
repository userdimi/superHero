package de.colognecode.superheroes.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import de.colognecode.superheroes.R

class SuperHeroDetailFragment : Fragment() {

    companion object {
        fun newInstance() = SuperHeroDetailFragment()
    }

    private lateinit var viewModel: SuperHeroDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_super_hero_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SuperHeroDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
