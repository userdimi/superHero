package de.colognecode.superheroes.presentation.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import de.colognecode.superheroes.databinding.FragmentSuperHeroesOverviewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SuperHeroesOverviewFragment : Fragment() {

    val viewModel: SuperHeroesOverviewViewModel by viewModel()
    private var binding: FragmentSuperHeroesOverviewBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = FragmentSuperHeroesOverviewBinding.inflate(inflater, container, false)
        return this.binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSuperHeroesFromRepository()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this.binding = null
    }

}