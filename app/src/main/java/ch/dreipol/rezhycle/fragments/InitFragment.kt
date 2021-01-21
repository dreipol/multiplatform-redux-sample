package ch.dreipol.rezhycle.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ch.dreipol.rezhycle.databinding.FragmentInitBinding

open class InitFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentInitBinding.inflate(inflater).root
    }
}