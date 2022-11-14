package id.co.project.bwamov.ui.homePage

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Film (
    var desc: String ?="",
    var director: String ?="",
    var genre: String ?="",
    var judul: String ?="",
    var poster: String ?="",
    var rating: String ?=""
// new comment
/**
 * cek for last new updating feature
 * **/
): Parcelable