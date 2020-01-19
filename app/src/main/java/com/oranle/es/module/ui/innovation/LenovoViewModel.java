package com.oranle.es.module.ui.innovation;

import android.app.Activity;
import android.content.res.Resources;

import androidx.lifecycle.MutableLiveData;

import com.oranle.es.R;
import com.oranle.es.module.base.BaseViewModel;

public class LenovoViewModel extends BaseViewModel {

    private Activity activity;
    public MutableLiveData<String> title = new MutableLiveData<>();
    public MutableLiveData<String> topic = new MutableLiveData<>();
    public MutableLiveData<String> NextText = new MutableLiveData<>("Next");
    public MutableLiveData<Boolean> isTopicShow = new MutableLiveData<>(true);
    public MutableLiveData<Boolean> isNextShow = new MutableLiveData<>(false);
    public String[] arrar_title;
    public String[] arrar_answer;
    private int index = 0;

    public LenovoViewModel(Activity activity) {
        this.activity = activity;
        Resources resources = activity.getResources();
        getArrayData(resources);

        title.setValue(arrar_title[0]);
        topic.setValue(arrar_answer[0]);
    }


    public void isShow() {
        isTopicShow.setValue(false);
        isNextShow.setValue(true);
        if (index == 24) {
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
        arrar_title = new String[]{resources.getString(R.string.lenovo_one), resources.getString(R.string.lenovo_two),
                resources.getString(R.string.lenovo_three), resources.getString(R.string.lenovo_four), resources.getString(R.string.lenovo_five),
                resources.getString(R.string.lenovo_six), resources.getString(R.string.lenovo_seven), resources.getString(R.string.lenovo_eight),
                resources.getString(R.string.lenovo_nine), resources.getString(R.string.lenovo_ten), resources.getString(R.string.lenovo_eleven),
                resources.getString(R.string.lenovo_twelve), resources.getString(R.string.lenovo_thirteen), resources.getString(R.string.lenovo_fourteen),
                resources.getString(R.string.lenovo_fifteen), resources.getString(R.string.lenovo_sixteen), resources.getString(R.string.lenovo_seventeen),
                resources.getString(R.string.lenovo_eighteen), resources.getString(R.string.lenovo_nineteen), resources.getString(R.string.lenovo_twenty),
                resources.getString(R.string.lenovo_21st), resources.getString(R.string.lenovo_22st), resources.getString(R.string.lenovo_23st),
                resources.getString(R.string.lenovo_24st), resources.getString(R.string.lenovo_25st)
        };
        arrar_answer = new String[]{resources.getString(R.string.lenovo_one_answer), resources.getString(R.string.lenovo_two_answer),
                resources.getString(R.string.lenovo_three_answer), resources.getString(R.string.lenovo_four_answer), resources.getString(R.string.lenovo_five_answer),
                resources.getString(R.string.lenovo_six_answer), resources.getString(R.string.lenovo_seven_answer), resources.getString(R.string.lenovo_eight_answer),
                resources.getString(R.string.lenovo_nine_answer), resources.getString(R.string.lenovo_ten_answer), resources.getString(R.string.lenovo_eleven_answer),
                resources.getString(R.string.lenovo_twelve_answer), resources.getString(R.string.lenovo_thirteen_answer), resources.getString(R.string.lenovo_fourteen_answer),
                resources.getString(R.string.lenovo_fifteen_answer), resources.getString(R.string.lenovo_sixteen_answer), resources.getString(R.string.lenovo_seventeen_answer),
                resources.getString(R.string.lenovo_eighteen_answer), resources.getString(R.string.lenovo_nineteen_answer), resources.getString(R.string.lenovo_twenty_answer),
                resources.getString(R.string.lenovo_21st_answer), resources.getString(R.string.lenovo_22st_answer), resources.getString(R.string.lenovo_23st_answer),
                resources.getString(R.string.lenovo_24st_answer), resources.getString(R.string.lenovo_25st_answer)
        };
    }
}
