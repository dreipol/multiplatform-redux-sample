package ch.dreipol.rezhycle.fragments

import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ch.dreipol.rezhycle.R
import com.mikepenz.aboutlibraries.LibsFragmentCompat

open class LicenceFragment : Fragment() {

    private val libsFragmentCompat: LibsFragmentCompat = LibsFragmentCompat()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val aboutLibrariesInflater = inflater.cloneInContext(ContextThemeWrapper(inflater.context, R.style.AboutLibrariesTheme))
        return libsFragmentCompat.onCreateView(
            aboutLibrariesInflater.context, aboutLibrariesInflater, container, savedInstanceState,
            arguments
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        libsFragmentCompat.onViewCreated(view)
    }

    override fun onDestroyView() {
        libsFragmentCompat.onDestroyView()
        super.onDestroyView()
    }
}