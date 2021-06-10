/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

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