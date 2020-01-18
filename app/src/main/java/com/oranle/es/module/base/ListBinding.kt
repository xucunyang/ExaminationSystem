package com.oranle.es.module.base

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.oranle.es.R
import com.oranle.es.data.entity.Assessment
import com.oranle.es.data.entity.ReportRule
import com.oranle.es.data.entity.SingleChoice
import com.oranle.es.data.entity.User
import com.oranle.es.module.base.view.JzvdStdMp3
import com.oranle.es.module.examination.viewmodel.TypedScore
import com.oranle.es.module.ui.administrator.fragment.WrapReportBean
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


@BindingAdapter("app:bind_spinner")
fun bindSpinner(spinner: Spinner, selections: List<String>?) {

    Timber.d("bindSpinner $selections")
    selections?.apply {
        bindAdapter(spinner, toTypedArray())
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
fun bindAssessmentSpinner(spinner: Spinner, assessments: List<Assessment?>?) {

    Timber.d("bindAssessmentSpinner ${assessments?.size}")

    val titleList = mutableListOf<String>()

    assessments?.forEach {
        if (it == null) {
            titleList.add("未选择...")
        } else {
            titleList.add(it.title)
        }
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

@BindingAdapter("app:bindDynamicScoreDetail")
fun bindDynamicScoreDetail(linearLayout: LinearLayout, bean: WrapReportBean) {

    Timber.d("bindDynamicScoreDetail $bean")

    val context = linearLayout.context

    val titleLinearLayout = linearLayout.findViewById<LinearLayout>(R.id.title_detail)
    val detailLinearLayout = linearLayout.findViewById<LinearLayout>(R.id.score_detail)

    val rules = bean.rules.sortedBy { it.id }
    val scoreList = bean.typedScore.sortedBy { it.ruleId }

    val lp = linearLayout.layoutParams as LinearLayout.LayoutParams
    rules.forEach {
        val textView = getTextView(it.typeStr, 15f, context)
        lp.weight = 1F
        titleLinearLayout.addView(textView, lp)
    }

    val classifyScore = classify(rules, scoreList)

    classifyScore.forEach { typedScore ->
        val textView = getTextView(typedScore.score.toString(), 22f, context)
        lp.weight = 1F
        detailLinearLayout.addView(textView, lp)
    }

}

fun classify(rules: List<ReportRule>, scoreList: List<TypedScore>)
        : Set<TypedScore> {
    val classifyList = mutableSetOf<TypedScore>()

    rules.forEachIndexed() { index, rule ->
        var scoreTotal = 0F
        scoreList.forEach { detail ->
            if (rule.id == detail.ruleId) {
                scoreTotal += detail.score
            }
        }
        classifyList.add(
            TypedScore(
                index = index,
                ruleId = rule.id,
                select = "none",
                score = scoreTotal
            )
        )
    }
    return classifyList
}

fun getTextView(text: String, textSize: Float, context: Context): TextView {
    val tv = TextView(context)
    tv.text = text
    tv.gravity = Gravity.CENTER
    tv.textSize = textSize
    tv.setPadding(0, 10, 0, 10)
    return tv
}

@BindingAdapter("app:bind_classify_detail")
fun bindClassifyDetail(layout: LinearLayout, bean: WrapReportBean) {
    val context = layout.context

    val rules = bean.rules.sortedBy { it.id }
    val scoreList = bean.typedScore.sortedBy { it.ruleId }
    val classifyScore = classify(rules, scoreList)

    // title
    layout.addView(getLayout(context, bean.assessment.title, bean.totalScore()))
    // add children
    classifyScore.forEachIndexed() { index, typedScore ->
        layout.addView(getLayout(context, rules[index].typeStr, typedScore.score))
    }
}

@SuppressLint("SetTextI18n")
fun getLayout(context: Context, typeStr: String, score: Float): View {
    val child = LayoutInflater.from(context).inflate(R.layout.item_reporrt_detail, null, false)
    val titleTv = child.findViewById<TextView>(R.id.type)
    val scoreTv = child.findViewById<TextView>(R.id.score)
    titleTv.text = "【$typeStr】"
    scoreTv.text = "得分：$score"
    return child
}