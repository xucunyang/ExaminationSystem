package com.oranle.es.module.ui.innovation;

import android.app.Activity;
import android.content.res.Resources;

import androidx.lifecycle.MutableLiveData;

import com.oranle.es.R;
import com.oranle.es.module.base.BaseViewModel;

public class ReverseViewModel extends BaseViewModel {

    private Activity activity;
    public MutableLiveData<String> title = new MutableLiveData<>();
    public MutableLiveData<String> topic = new MutableLiveData<>();
    public MutableLiveData<String> NextText = new MutableLiveData<>("Next");
    public MutableLiveData<Boolean> isTopicShow = new MutableLiveData<>(true);
    public MutableLiveData<Boolean> isNextShow = new MutableLiveData<>(false);
    public String[] arrar_title;
    public String[] arrar_answer;
    private int index = 0;

    public ReverseViewModel(Activity activity) {
        this.activity = activity;
        Resources resources = activity.getResources();
        getArrayData(resources);

        title.setValue(arrar_title[0]);
        topic.setValue(arrar_answer[0]);
    }


    public void isShow() {
        isTopicShow.setValue(false);
        isNextShow.setValue(true);
        if (index == 27) {
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
        arrar_title = new String[]{resources.getString(R.string.reverse_one), resources.getString(R.string.reverse_two),
                resources.getString(R.string.reverse_three), resources.getString(R.string.reverse_four), resources.getString(R.string.reverse_five),
                resources.getString(R.string.reverse_six), resources.getString(R.string.reverse_seven), resources.getString(R.string.reverse_eight),
                resources.getString(R.string.reverse_nine), resources.getString(R.string.reverse_ten), resources.getString(R.string.reverse_eleven),
                resources.getString(R.string.reverse_twelve), resources.getString(R.string.reverse_thirteen), resources.getString(R.string.reverse_fourteen),
                resources.getString(R.string.reverse_fifteen), resources.getString(R.string.reverse_sixteen), resources.getString(R.string.reverse_seventeen),
                resources.getString(R.string.reverse_eighteen), resources.getString(R.string.reverse_nineteen), resources.getString(R.string.reverse_twenty),
                resources.getString(R.string.reverse_21st), resources.getString(R.string.reverse_22st), resources.getString(R.string.reverse_23st),
                resources.getString(R.string.reverse_24st), resources.getString(R.string.reverse_25st), resources.getString(R.string.reverse_26st),
                resources.getString(R.string.reverse_27st), resources.getString(R.string.reverse_28st)
        };
        arrar_answer = new String[]{resources.getString(R.string.reverse_one_answer), resources.getString(R.string.reverse_two_answer),
                resources.getString(R.string.reverse_three_answer), resources.getString(R.string.reverse_four_answer), resources.getString(R.string.reverse_five_answer),
                resources.getString(R.string.reverse_six_answer), resources.getString(R.string.reverse_seven_answer), resources.getString(R.string.reverse_eight_answer),
                resources.getString(R.string.reverse_nine_answer), resources.getString(R.string.reverse_ten_answer), resources.getString(R.string.reverse_eleven_answer),
                resources.getString(R.string.reverse_twelve_answer), resources.getString(R.string.reverse_thirteen_answer), resources.getString(R.string.reverse_fourteen_answer),
                resources.getString(R.string.reverse_fifteen_answer), resources.getString(R.string.reverse_sixteen_answer), resources.getString(R.string.reverse_seventeen_answer),
                resources.getString(R.string.reverse_eighteen_answer), resources.getString(R.string.reverse_nineteen_answer), resources.getString(R.string.reverse_twenty_answer),
                resources.getString(R.string.reverse_21st_answer), resources.getString(R.string.reverse_22st_answer), resources.getString(R.string.reverse_23st_answer),
                resources.getString(R.string.reverse_24st_answer), resources.getString(R.string.reverse_25st_answer), resources.getString(R.string.reverse_26st_answer),
                resources.getString(R.string.reverse_27st_answer), resources.getString(R.string.reverse_28st_answer)
        };
    }
}
