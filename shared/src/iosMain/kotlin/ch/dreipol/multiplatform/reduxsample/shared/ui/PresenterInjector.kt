package ch.dreipol.multiplatform.reduxsample.shared.ui

import ch.dreipol.dreimultiplatform.reduxkotlin.AttachView
import ch.dreipol.dreimultiplatform.reduxkotlin.DetachView
import ch.dreipol.dreimultiplatform.reduxkotlin.View
import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch

// Here only until a solution is found for rootDispatch & AttachView class not
// visible from swift.
fun attachView(view: View) = rootDispatch(AttachView(view))
fun detachView(view: View) = rootDispatch(DetachView(view))
fun clearView(view: View) = rootDispatch(DetachView(view))