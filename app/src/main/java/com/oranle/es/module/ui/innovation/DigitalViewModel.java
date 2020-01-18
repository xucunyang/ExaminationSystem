package com.oranle.es.module.ui.innovation;

import android.app.Activity;
import android.content.res.Resources;

import androidx.lifecycle.MutableLiveData;

import com.oranle.es.R;
import com.oranle.es.module.base.BaseViewModel;

public class DigitalViewModel extends BaseViewModel {

    private Activity activity;
    public MutableLiveData<String> title = new MutableLiveData<>();
    public MutableLiveData<String> topic = new MutableLiveData<>();
    public MutableLiveData<String> NextText = new MutableLiveData<>("Next");
    public MutableLiveData<Boolean> isTopicShow = new MutableLiveData<>(true);
    public MutableLiveData<Boolean> isNextShow = new MutableLiveData<>(false);
    public String[] arrar_title;
    public String[] arrar_answer;
    private int index = 0;

    public DigitalViewModel(Activity activity) {
        this.activity = activity;
        Resources resources = activity.getResources();
        getArrayData(resources);

        title.setValue(arrar_title[0]);
        topic.setValue(arrar_answer[0]);
    }


    public void isShow() {
        isTopicShow.setValue(false);
        isNextShow.setValue(true);
        if (index == 28) {
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
        arrar_title = new String[]{resources.getString(R.string.digital_one), resources.getString(R.string.digital_two),
                resources.getString(R.string.digital_three), resources.getString(R.string.digital_four), resources.getString(R.string.digital_five),
                resources.getString(R.string.digital_six), resources.getString(R.string.digital_seven), resources.getString(R.string.digital_eight),
                resources.getString(R.string.digital_nine), resources.getString(R.string.digital_ten), resources.getString(R.string.digital_eleven),
                resources.getString(R.string.digital_twelve), resources.getString(R.string.digital_thirteen), resources.getString(R.string.digital_fourteen),
                resources.getString(R.string.digital_fifteen), resources.getString(R.string.digital_sixteen), resources.getString(R.string.digital_seventeen),
                resources.getString(R.string.digital_eighteen), resources.getString(R.string.digital_nineteen), resources.getString(R.string.digital_twenty),
                resources.getString(R.string.digital_21st), resources.getString(R.string.digital_22st), resources.getString(R.string.digital_23st),
                resources.getString(R.string.digital_24st), resources.getString(R.string.digital_25st), resources.getString(R.string.digital_26st),
                resources.getString(R.string.digital_27st), resources.getString(R.string.digital_28st), resources.getString(R.string.digital_29st)
        };
        arrar_answer = new String[]{resources.getString(R.string.digital_one_answer), resources.getString(R.string.digital_two_answer),
                resources.getString(R.string.digital_three_answer), resources.getString(R.string.digital_four_answer), resources.getString(R.string.digital_five_answer),
                resources.getString(R.string.digital_six_answer), resources.getString(R.string.digital_seven_answer), resources.getString(R.string.digital_eight_answer),
                resources.getString(R.string.digital_nine_answer), resources.getString(R.string.digital_ten_answer), resources.getString(R.string.digital_eleven_answer),
                resources.getString(R.string.digital_twelve_answer), resources.getString(R.string.digital_thirteen_answer), resources.getString(R.string.digital_fourteen_answer),
                resources.getString(R.string.digital_fifteen_answer), resources.getString(R.string.digital_sixteen_answer), resources.getString(R.string.digital_seventeen_answer),
                resources.getString(R.string.digital_eighteen_answer), resources.getString(R.string.digital_nineteen_answer), resources.getString(R.string.digital_twenty_answer),
                resources.getString(R.string.digital_21st_answer), resources.getString(R.string.digital_22st_answer), resources.getString(R.string.digital_23st_answer),
                resources.getString(R.string.digital_24st_answer), resources.getString(R.string.digital_25st_answer), resources.getString(R.string.digital_26st_answer),
                resources.getString(R.string.digital_27st_answer), resources.getString(R.string.digital_28st_answer), resources.getString(R.string.digital_29st_answer)
        };
    }
}
