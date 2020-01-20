package com.oranle.es.module.ui.innovation;

import android.app.Activity;
import android.content.res.Resources;

import androidx.lifecycle.MutableLiveData;

import com.oranle.es.R;
import com.oranle.es.module.base.BaseViewModel;

public class MathematicalViewModel extends BaseViewModel {
    private Activity activity;
    public MutableLiveData<String> title = new MutableLiveData<>();
    public MutableLiveData<String> topic = new MutableLiveData<>();
    public MutableLiveData<String> NextText = new MutableLiveData<>("Next");
    public MutableLiveData<Boolean> isTopicShow = new MutableLiveData<>(true);
    public MutableLiveData<Boolean> isNextShow = new MutableLiveData<>(false);
    public String[] arrar_title;
    public String[] arrar_answer;
    private int index = 0;

    public MathematicalViewModel(Activity activity) {
        this.activity = activity;
        Resources resources = activity.getResources();
        getArrayData(resources);

        title.setValue(arrar_title[0]);
        topic.setValue(arrar_answer[0]);
    }


    public void isShow() {
        isTopicShow.setValue(false);
        isNextShow.setValue(true);
        if (index == 29) {
            NextText.setValue("返回");
        }
    }

    public void onNext() {
        if (NextText.getValue().equals("返回")) activity.finish();
        index++;
        for (int i = 0; i < arrar_title.length; i++) {
            if (index == i) {
                title.setValue(arrar_title[i]);
                topic.setValue(arrar_answer[i]);
            }
        }
        isTopicShow.setValue(true);
        isNextShow.setValue(false);
    }

    private void getArrayData(Resources resources) {
        arrar_title = new String[]{resources.getString(R.string.warm_mathematical_one), resources.getString(R.string.warm_mathematical_two),
                resources.getString(R.string.warm_mathematical_three), resources.getString(R.string.warm_mathematical_four), resources.getString(R.string.warm_mathematical_five),
                resources.getString(R.string.warm_mathematical_six), resources.getString(R.string.warm_mathematical_seven), resources.getString(R.string.warm_mathematical_eight),
                resources.getString(R.string.warm_mathematical_nine), resources.getString(R.string.warm_mathematical_ten), resources.getString(R.string.warm_mathematical_eleven),
                resources.getString(R.string.warm_mathematical_twelve), resources.getString(R.string.warm_mathematical_thirteen), resources.getString(R.string.warm_mathematical_fourteen),
                resources.getString(R.string.warm_mathematical_fifteen), resources.getString(R.string.warm_mathematical_sixteen), resources.getString(R.string.warm_mathematical_seventeen),
                resources.getString(R.string.warm_mathematical_eighteen), resources.getString(R.string.warm_mathematical_nineteen), resources.getString(R.string.warm_mathematical_twenty),
                resources.getString(R.string.warm_mathematical_21st), resources.getString(R.string.warm_mathematical_22st), resources.getString(R.string.warm_mathematical_23st),
                resources.getString(R.string.warm_mathematical_24st), resources.getString(R.string.warm_mathematical_25st), resources.getString(R.string.warm_mathematical_26st),
                resources.getString(R.string.warm_mathematical_27st), resources.getString(R.string.warm_mathematical_28st), resources.getString(R.string.warm_mathematical_29st),
                resources.getString(R.string.warm_mathematical_30st)
        };
        arrar_answer = new String[]{resources.getString(R.string.warm_mathematical_one_answer), resources.getString(R.string.warm_mathematical_two_answer),
                resources.getString(R.string.warm_mathematical_three_answer), resources.getString(R.string.warm_mathematical_four_answer), resources.getString(R.string.warm_mathematical_five_answer),
                resources.getString(R.string.warm_mathematical_six_answer), resources.getString(R.string.warm_mathematical_seven_answer), resources.getString(R.string.warm_mathematical_eight_answer),
                resources.getString(R.string.warm_mathematical_nine_answer), resources.getString(R.string.warm_mathematical_ten_answer), resources.getString(R.string.warm_mathematical_eleven_answer),
                resources.getString(R.string.warm_mathematical_twelve_answer), resources.getString(R.string.warm_mathematical_thirteen_answer), resources.getString(R.string.warm_mathematical_fourteen_answer),
                resources.getString(R.string.warm_mathematical_fifteen_answer), resources.getString(R.string.warm_mathematical_sixteen_answer), resources.getString(R.string.warm_mathematical_seventeen_answer),
                resources.getString(R.string.warm_mathematical_eighteen_answer), resources.getString(R.string.warm_mathematical_nineteen_answer), resources.getString(R.string.warm_mathematical_twenty_answer),
                resources.getString(R.string.warm_mathematical_21st_answer), resources.getString(R.string.warm_mathematical_22st_answer), resources.getString(R.string.warm_mathematical_23st_answer),
                resources.getString(R.string.warm_mathematical_24st_answer), resources.getString(R.string.warm_mathematical_25st_answer), resources.getString(R.string.warm_mathematical_26st_answer),
                resources.getString(R.string.warm_mathematical_27st_answer), resources.getString(R.string.warm_mathematical_28st_answer), resources.getString(R.string.warm_mathematical_29st_answer),
                resources.getString(R.string.warm_mathematical_30st_answer)
        };
    }
}
