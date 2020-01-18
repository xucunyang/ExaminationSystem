package com.oranle.es.module.ui.innovation;

import android.app.Activity;
import android.content.res.Resources;

import androidx.lifecycle.MutableLiveData;

import com.oranle.es.R;
import com.oranle.es.module.base.BaseViewModel;

public class AnalogyViewModel extends BaseViewModel {
    private Activity activity;
    public MutableLiveData<String> title = new MutableLiveData<>();
    public MutableLiveData<String> topic = new MutableLiveData<>();
    public MutableLiveData<String> NextText = new MutableLiveData<>("Next");
    public MutableLiveData<Boolean> isTopicShow = new MutableLiveData<>(true);
    public MutableLiveData<Boolean> isNextShow = new MutableLiveData<>(false);
    public String[] arrar_title;
    public String[] arrar_answer;
    private int index = 0;

    public AnalogyViewModel(Activity activity) {
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
        arrar_title = new String[]{resources.getString(R.string.analogy_one), resources.getString(R.string.analogy_two),
                resources.getString(R.string.analogy_three), resources.getString(R.string.analogy_four), resources.getString(R.string.analogy_five),
                resources.getString(R.string.analogy_six), resources.getString(R.string.analogy_seven), resources.getString(R.string.analogy_eight),
                resources.getString(R.string.analogy_nine), resources.getString(R.string.analogy_ten), resources.getString(R.string.analogy_eleven),
                resources.getString(R.string.analogy_twelve), resources.getString(R.string.analogy_thirteen), resources.getString(R.string.analogy_fourteen),
                resources.getString(R.string.analogy_fifteen), resources.getString(R.string.analogy_sixteen), resources.getString(R.string.analogy_seventeen),
                resources.getString(R.string.analogy_eighteen), resources.getString(R.string.analogy_nineteen), resources.getString(R.string.analogy_twenty),
                resources.getString(R.string.analogy_21st), resources.getString(R.string.analogy_22st), resources.getString(R.string.analogy_23st),
                resources.getString(R.string.analogy_24st), resources.getString(R.string.analogy_25st), resources.getString(R.string.analogy_26st),
                resources.getString(R.string.analogy_27st), resources.getString(R.string.analogy_28st), resources.getString(R.string.analogy_29st),
                resources.getString(R.string.analogy_30st)
        };
        arrar_answer = new String[]{resources.getString(R.string.analogy_one_answer), resources.getString(R.string.analogy_two_answer),
                resources.getString(R.string.analogy_three_answer), resources.getString(R.string.analogy_four_answer), resources.getString(R.string.analogy_five_answer),
                resources.getString(R.string.analogy_six_answer), resources.getString(R.string.analogy_seven_answer), resources.getString(R.string.analogy_eight_answer),
                resources.getString(R.string.analogy_nine_answer), resources.getString(R.string.analogy_ten_answer), resources.getString(R.string.analogy_eleven_answer),
                resources.getString(R.string.analogy_twelve_answer), resources.getString(R.string.analogy_thirteen_answer), resources.getString(R.string.analogy_fourteen_answer),
                resources.getString(R.string.analogy_fifteen_answer), resources.getString(R.string.analogy_sixteen_answer), resources.getString(R.string.analogy_seventeen_answer),
                resources.getString(R.string.analogy_eighteen_answer), resources.getString(R.string.analogy_nineteen_answer), resources.getString(R.string.analogy_twenty_answer),
                resources.getString(R.string.analogy_21st_answer), resources.getString(R.string.analogy_22st_answer), resources.getString(R.string.analogy_23st_answer),
                resources.getString(R.string.analogy_24st_answer), resources.getString(R.string.analogy_25st_answer), resources.getString(R.string.analogy_26st_answer),
                resources.getString(R.string.analogy_27st_answer), resources.getString(R.string.analogy_28st_answer), resources.getString(R.string.analogy_29st_answer),
                resources.getString(R.string.analogy_30st_answer)
        };
    }
}
