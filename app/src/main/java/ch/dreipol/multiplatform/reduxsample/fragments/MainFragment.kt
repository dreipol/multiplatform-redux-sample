package ch.dreipol.multiplatform.reduxsample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.navArgs
import ch.dreipol.multiplatform.reduxsample.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    val arguments: MainFragmentArgs by navArgs()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // TODO take arguments to show correct Fragment
        val binding = FragmentMainBinding.inflate(layoutInflater)
        val navController = findNavController(this)
//        navController.navigate(WelcomeFragmentDirections.toMainNavigation())
        return binding.root
    }
}