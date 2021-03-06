package eu.kanade.tachiyomi.ui.manga.track

import android.app.Dialog
import android.os.Bundle
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import eu.kanade.tachiyomi.R
import eu.kanade.tachiyomi.data.database.models.Track
import eu.kanade.tachiyomi.data.track.TrackManager
import eu.kanade.tachiyomi.ui.base.controller.DialogController
import it.sephiroth.android.library.numberpicker.NumberPicker
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get

class SetTrackChaptersDialog<T> : DialogController
    where T : SetTrackChaptersDialog.Listener {

    private val item: TrackItem
    private lateinit var listener: Listener

    constructor(target: T, item: TrackItem) : super(
        Bundle().apply {
            putSerializable(KEY_ITEM_TRACK, item.track)
        }
    ) {
        listener = target
        this.item = item
    }

    @Suppress("unused")
    constructor(bundle: Bundle) : super(bundle) {
        val track = bundle.getSerializable(KEY_ITEM_TRACK) as Track
        val service = Injekt.get<TrackManager>().getService(track.sync_id)!!
        item = TrackItem(track, service)
    }

    override fun onCreateDialog(savedViewState: Bundle?): Dialog {
        val item = item

        val dialog = MaterialDialog(activity!!)
            .title(R.string.chapters)
            .customView(viewRes = R.layout.track_chapters_dialog)
            .negativeButton(android.R.string.cancel)
            .positiveButton(android.R.string.ok) { dialog ->
                val view = dialog.getCustomView()
                // Remove focus to update selected number
                val np: NumberPicker = view.findViewById(R.id.chapters_picker)
                np.clearFocus()
                listener.setChaptersRead(item, np.progress)
            }

        val view = dialog.getCustomView()
        val np: NumberPicker = view.findViewById(R.id.chapters_picker)
        // Set initial value
        np.progress = item.track?.last_chapter_read ?: 0
        if (item.track?.total_chapters ?: 0 > 0) {
            np.maxValue = item.track?.total_chapters ?: 0
        }

        return dialog
    }

    interface Listener {
        fun setChaptersRead(item: TrackItem, chaptersRead: Int)
    }

    private companion object {
        const val KEY_ITEM_TRACK = "SetTrackChaptersDialog.item.track"
    }
}
