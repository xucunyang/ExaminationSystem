package com.oranle.es.module.ui.innovation

import androidx.lifecycle.MutableLiveData
import com.oranle.es.R
import com.oranle.es.module.base.BaseViewModel

class VisualViewModel : BaseViewModel() {

    var titles = arrayOf<String>(
        "1、下图是一组重叠在一起的正方形，请用最快的速度数出一共有多少个正方形？",
        "2、下面4幅图中只有2幅图能够拼成一个整圆，是哪两幅？",
        "3、不考虑大小，你能数出下图中共有多少种不同形状的图形吗？",
        "4、请你快速数出这幅图中有多少个三角形?",
        "5、仔细观察下图，有一个特别的画面，你发现了吗？",
        "6、比较下面的两幅图片，判断他们中间的圆圈哪个更大一些？",
        "7、小明家的围墙坏了，小明要用多少块砖才能把墙砌好？",
        "8、请你从下面每组图中，找到与众不同的那个。",
        "9、ABCD四个图形，分别是由1至4中某几个图形组成的，请说出ABCD四个图形分别由哪几个图形组成？",
        "10、例图中的钥匙旋转180度之后是什么样？",
        "11、小动物们坐上了大转盘，如果分别沿着1/2/3/4这4条线折一折，小猫分别会和谁重合？",
        "12、a/b 两女孩的反影照片是属于右面1至6中的哪一副？",
        "13、如图所示，多边形缺少一角，A/B/C/D/E中哪一个是正确的选项？",
        "14、观察第一组图形，依据规律选择第二组图形中缺少的图形。",
        "15、下图BCDEF中，哪个图可以恰好与A图组成一个三角形？",
        "16、假设下图的砖尺寸相同，现有的方砖不会移走，那么还需要多少块这样的方砖才能构成一个立方体？"
    )
    var imgs = arrayOf<Int>(
        R.drawable.vision_1,
        R.drawable.vision_2,
        R.drawable.vision_3,
        R.drawable.vision_4,
        R.drawable.vision_5,
        R.drawable.vision_6,
        R.drawable.vision_7,
        R.drawable.vision_8,
        R.drawable.vision_9,
        R.drawable.vision_10,
        R.drawable.vision_11,
        R.drawable.vision_12,
        R.drawable.vision_13,
        R.drawable.vision_14,
        R.drawable.vision_15,
        R.drawable.vision_16
    )
    var answers = arrayOf<String>(
        "答案：29个",
        "答案：A和C",
        "答案：8种",
        "答案：35个三角形",
        "答案：嘴巴、鼻子、眼睛都是小鸟",
        "答案：一样大",
        "答案：7块砖",
        "答案：小鸟、蜻蜓、杯子",
        "答案：A由1/2/3      B由2/3/4    C由1/4/3     D由1/2/4",
        "答案:C",
        "答案：小猴、小熊、小猪、小牛",
        "答案：a——3      b——4",
        "答案：E",
        "答案：D",
        "答案：C",
        "答案：48块"
    )

    var title = MutableLiveData<String>()
    var topic = MutableLiveData<String>()
    var img = MutableLiveData<Int>(imgs[0])
    var NextText = MutableLiveData("Next")
    var isTopicShow = MutableLiveData(true)
    var isNextShow = MutableLiveData(false)
    var activityFinish = MutableLiveData(false)
    private var index = 0
    val isShow: Unit
        get() {
            isTopicShow.value = false
            isNextShow.value = true
            if (index == titles.size) {
                NextText.value = "返回"
            }
        }

    init {
        title.value = titles[0]
        topic.value = answers[0]
    }

    fun onNext(): Boolean {
        if (NextText.value == "返回") {
            activityFinish.value = true
        }
        index++
        for (i in titles.indices) {
            if (index == i) {
                title.value = titles[i]
                img.value = imgs[i]
                topic.value = answers[i]
            }
        }
        isTopicShow.value = true
        isNextShow.value = false
        return true
    }

}