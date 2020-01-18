package com.oranle.es.module.ui.innovation;

import android.app.Activity;
import android.content.res.Resources;

import androidx.lifecycle.MutableLiveData;

import com.oranle.es.R;
import com.oranle.es.module.base.BaseViewModel;

public class DialecticalViewModel extends BaseViewModel {
    private Activity activity;
    public MutableLiveData<String> title = new MutableLiveData<>();
    public MutableLiveData<String> topic = new MutableLiveData<>();
    public MutableLiveData<String> NextText = new MutableLiveData<>("Next");
    public MutableLiveData<Boolean> isTopicShow = new MutableLiveData<>(true);
    public MutableLiveData<Boolean> isNextShow = new MutableLiveData<>(false);
    public String[] arrar_title;
    public String[] arrar_answer;
    private int index = 0;

    public DialecticalViewModel(Activity activity) {
        this.activity = activity;
        Resources resources = activity.getResources();
        getArrayData(resources);

        title.setValue(arrar_title[0]);
        topic.setValue(arrar_answer[0]);
    }


    public void isShow() {
        isTopicShow.setValue(false);
        isNextShow.setValue(true);
        if (index == 25) {
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
        arrar_title = new String[]{resources.getString(R.string.dialectical_one), resources.getString(R.string.dialectical_two),
                resources.getString(R.string.dialectical_three), resources.getString(R.string.dialectical_four), resources.getString(R.string.dialectical_five),
                resources.getString(R.string.dialectical_six), resources.getString(R.string.dialectical_seven), resources.getString(R.string.dialectical_eight),
                resources.getString(R.string.dialectical_nine), resources.getString(R.string.dialectical_ten), resources.getString(R.string.dialectical_eleven),
                resources.getString(R.string.dialectical_twelve), resources.getString(R.string.dialectical_thirteen), resources.getString(R.string.dialectical_fourteen),
                resources.getString(R.string.dialectical_fifteen), resources.getString(R.string.dialectical_sixteen), resources.getString(R.string.dialectical_seventeen),
                resources.getString(R.string.dialectical_eighteen), resources.getString(R.string.dialectical_nineteen), resources.getString(R.string.dialectical_twenty),
                resources.getString(R.string.dialectical_21st), resources.getString(R.string.dialectical_22st), resources.getString(R.string.dialectical_23st),
                resources.getString(R.string.dialectical_24st), resources.getString(R.string.dialectical_25st), resources.getString(R.string.dialectical_26st),
        };
        arrar_answer = new String[]{resources.getString(R.string.dialectical_one_answer), resources.getString(R.string.dialectical_two_answer),
                resources.getString(R.string.dialectical_three_answer), resources.getString(R.string.dialectical_four_answer), resources.getString(R.string.dialectical_five_answer),
                resources.getString(R.string.dialectical_six_answer), resources.getString(R.string.dialectical_seven_answer), resources.getString(R.string.dialectical_eight_answer),
                resources.getString(R.string.dialectical_nine_answer), resources.getString(R.string.dialectical_ten_answer), resources.getString(R.string.dialectical_eleven_answer),
                resources.getString(R.string.dialectical_twelve_answer), resources.getString(R.string.dialectical_thirteen_answer), resources.getString(R.string.dialectical_fourteen_answer),
                resources.getString(R.string.dialectical_fifteen_answer), resources.getString(R.string.dialectical_sixteen_answer), resources.getString(R.string.dialectical_seventeen_answer),
                resources.getString(R.string.dialectical_eighteen_answer), resources.getString(R.string.dialectical_nineteen_answer), resources.getString(R.string.dialectical_twenty_answer),
                resources.getString(R.string.dialectical_21st_answer), resources.getString(R.string.dialectical_22st_answer), resources.getString(R.string.dialectical_23st_answer),
                resources.getString(R.string.dialectical_24st_answer), resources.getString(R.string.dialectical_25st_answer), resources.getString(R.string.dialectical_26st_answer),
        };
    }
}
