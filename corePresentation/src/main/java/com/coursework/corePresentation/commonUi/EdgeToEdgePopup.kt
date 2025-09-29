package com.coursework.corePresentation.commonUi

//import androidx.lifecycle.ViewTreeLifecycleOwner
//import androidx.lifecycle.ViewTreeViewModelStoreOwner
//import androidx.savedstate.ViewTreeSavedStateRegistryOwner
import android.content.Context
import android.os.Build
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.findViewTreeSavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import android.graphics.Rect as AndroidRect

class EdgeToEdgePopup(
    context: Context,
    private val onDismissRequest: () -> Unit,
    private val parentView: View,
    private val content: @Composable () -> Unit
) : AbstractComposeView(context) {

    init {
        // Dispose composition when detached
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindow)
        setBackgroundColor(0) // transparent
    }

    private val windowManager =
        context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private var added = false

    fun show() {
        if (added) return

        // Attach Android view-tree owners so Compose can find LocalLifecycleOwner, etc.
        setViewTreeLifecycleOwner(parentView.findViewTreeLifecycleOwner())
        setViewTreeViewModelStoreOwner(parentView.findViewTreeViewModelStoreOwner())
        setViewTreeSavedStateRegistryOwner(parentView.findViewTreeSavedStateRegistryOwner())

        val params = WindowManager.LayoutParams().apply {
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.MATCH_PARENT

            // Not setting FLAG_NOT_FOCUSABLE makes this view able to receive input.
            // If you want it to not steal focus, change flags accordingly.
            format = -3 // PixelFormat.TRANSLUCENT
            flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
                    WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS

            // Make this a sub-window of the parent Activity so OEMs (e.g., MIUI) apply edge-to-edge consistently
            type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL
            token = parentView.applicationWindowToken

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                layoutInDisplayCutoutMode =
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                fitInsetsTypes = 0
                fitInsetsSides = 0
                isFitInsetsIgnoringVisibility = true
            }

        }

        systemUiVisibility = (SYSTEM_UI_FLAG_LAYOUT_STABLE
                or SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)

        isFocusableInTouchMode = true
        requestFocus()
        setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                onDismissRequest()
                true
            } else {
                false
            }
        }

        windowManager.addView(this, params)
        added = true
    }

    fun dismiss() {
        if (!added) return
        try {
            // Tear down Compose content first
            windowManager.removeView(this)
            disposeComposition()

        } catch (_: Throwable) {
            // ignore if already removed
        } finally {
            added = false
        }
    }

    @Composable
    override fun Content() {
        var contentBounds by remember { mutableStateOf(AndroidRect()) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        // offset is Offset(x, y) in px relative to this view
                        if (!contentBounds.contains(offset.x.toInt(), offset.y.toInt())) {
                            dismiss()
                        }
                    }
                }
        ) {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .onGloballyPositioned { coordinates ->
                        // bounds relative to window; convert to Android Rect
                        val b = coordinates.boundsInWindow()
                        contentBounds = AndroidRect(
                            b.left.toInt(),
                            b.top.toInt(),
                            b.right.toInt(),
                            b.bottom.toInt()
                        )
                    }
            ) {
                content()
            }
        }
    }
}

/**
 *
 * Popup that uses the entire screen. If you don't want to draw under
 * system bars use Modifiers such as navigationBarsPadding or statusBarsPadding
 * in the content parameter
 * */
@Composable
fun EdgeToEdgePopup(
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    val ctx = LocalContext.current
    val view = LocalView.current
    val popup = remember {
        EdgeToEdgePopup(ctx, onDismissRequest, view) {
            MaterialTheme {
                content()
            }
        }
    }

    LaunchedEffect(Unit) {
        popup.show()
    }

    DisposableEffect(Unit) {
        onDispose {
            popup.dismiss()
        }
    }
}

