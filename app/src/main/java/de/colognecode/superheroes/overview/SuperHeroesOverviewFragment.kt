package de.colognecode.superheroes.overview

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.colognecode.superheroes.R

class SuperHeroesOverviewFragment : Fragment() {

    companion object {
        fun newInstance() = SuperHeroesOverviewFragment()
    }

    private lateinit var viewModel: SuperHeroesOverviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.super_heroes_overview_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SuperHeroesOverviewViewModel::class.java)
        // TODO: Use the ViewModel
    }

}