package com.oranle.es.module.base

import android.view.View
import android.widget.*
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.oranle.es.R
import com.oranle.es.data.entity.Assessment
import com.oranle.es.data.entity.SingleChoice
import com.oranle.es.data.entity.User
import com.oranle.es.module.base.view.JzvdStdMp3
import com.oranle.es.util.ImageUtil
import timber.log.Timber

@BindingAdapter("app:items")
fun <E> setItems(listView: RecyclerView, items: List<E>) {

    Timber.d("set items size ${items.size} adapter is null?${listView.adapter == null}")

    (listView.adapter as ListAdapter<E, *>).submitList(items)
}

@BindingAdapter("app:img")
fun setUrl(img: ImageView, url: String?) {

    Timber.d("set setUrl ${img.toString()} url is $url")

    ImageUtil.loadAssetsImage(img, url)
}

@BindingAdapter("app:jz_init")
fun setJzPlayerUrl(mp3: JzvdStdMp3, url: String?) {

    Timber.d("set setJzPlayerUrl $mp3 url is $url")

    if (url == null || url.isBlank()) {
        Timber.d("set setJzPlayerUrl is blank")
        return
    }

    mp3.setUp(url, "音频播放")
    mp3.thumbImageView.setImageResource(R.drawable.ic_launcher)

//        val s =
//            "http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4"
//        val ss = "/sdcard/es-web/datiqin.mp3"
//        mp3_view.setUp(ss,"饺子闭眼睛")
//        mp3_view.thumbImageView.setImageResource(R.drawable.ic_launcher)
}

@BindingAdapter("app:bind_options")
fun initOptionRadioGroup(radioGroup: RadioGroup, singleChoice: SingleChoice) {
    val childCount = radioGroup.childCount
    val options = singleChoice.optionList()
    if (childCount < options.size) {
        Timber.e("options size(${options.size}) bigger than radio group children ($childCount)")
        return
    }

    for (childIndex in 0..childCount) {
        val child = radioGroup.getChildAt(childIndex)
        if (child is RadioButton) {
            if (childIndex < options.size) {
                child.visibility = View.VISIBLE
                child.text = options[childIndex]
            } else {
                child.visibility = View.GONE
                child.text = null
            }
        } else {
            Timber.d("child is not type Radiobutton,index $childIndex ,$child")
        }
    }
}

@BindingAdapter("app:bind_user_spinner")
fun bindUserSpinner(spinner: Spinner, students: List<User>?) {

    Timber.d("bindUserSpinner ${students?.size}")

    val studentName = mutableListOf<String>()

    students?.forEach {
        studentName.add(it.userName)
    }

    bindAdapter(spinner, studentName.toTypedArray())
}

@BindingAdapter("app:bind_assessment_spinner")
fun bindAssessmentSpinner(spinner: Spinner, assessments: List<Assessment>?) {

    Timber.d("bindAssessmentSpinner ${assessments?.size}")

    val titleList = mutableListOf<String>()

    assessments?.forEach {
        titleList.add(it.title)
    }

    bindAdapter(spinner, titleList.toTypedArray())
}

fun <T> bindAdapter(spinner: Spinner, arrays: Array<T>) {
    val schoolAdapter = ArrayAdapter<T>(
        spinner.context,
        android.R.layout.simple_list_item_1,
        arrays
    )
    spinner.adapter = schoolAdapter
}

@BindingAdapter("app:setOrientation")
fun setOrientation(linearLayout: LinearLayout, orientation: Int) {

    Timber.d("setOrientation $orientation")

    linearLayout.orientation = orientation
}