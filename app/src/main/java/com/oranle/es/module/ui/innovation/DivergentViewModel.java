package com.oranle.es.module.ui.innovation;

import android.app.Activity;
import android.content.res.Resources;

import androidx.lifecycle.MutableLiveData;

import com.oranle.es.R;
import com.oranle.es.module.base.BaseViewModel;

public class DivergentViewModel extends BaseViewModel {
    private Activity activity;
    public MutableLiveData<String> title = new MutableLiveData<>();
    public MutableLiveData<String> topic = new MutableLiveData<>();
    public MutableLiveData<String> NextText = new MutableLiveData<>("Next");
    public MutableLiveData<Boolean> isTopicShow = new MutableLiveData<>(true);
    public MutableLiveData<Boolean> isNextShow = new MutableLiveData<>(false);
    public String[] arrar_title;
    public String[] arrar_answer;
    private int index = 0;

    public DivergentViewModel(Activity activity) {
        this.activity = activity;
        Resources resources = activity.getResources();
        getArrayData(resources);

        title.setValue(arrar_title[0]);
        topic.setValue(arrar_answer[0]);
    }


    public void isShow() {
        isTopicShow.setValue(false);
        isNextShow.setValue(true);
        if (index == 19) {
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
        arrar_title = new String[]{resources.getString(R.string.warm_divergent_one), resources.getString(R.string.warm_divergent_two),
                resources.getString(R.string.warm_divergent_three), resources.getString(R.string.warm_divergent_four), resources.getString(R.string.warm_divergent_five),
                resources.getString(R.string.warm_divergent_six), resources.getString(R.string.warm_divergent_seven), resources.getString(R.string.warm_divergent_eight),
                resources.getString(R.string.warm_divergent_nine), resources.getString(R.string.warm_divergent_ten), resources.getString(R.string.warm_divergent_eleven),
                resources.getString(R.string.warm_divergent_twelve), resources.getString(R.string.warm_divergent_thirteen), resources.getString(R.string.warm_divergent_fourteen),
                resources.getString(R.string.warm_divergent_fifteen), resources.getString(R.string.warm_divergent_sixteen), resources.getString(R.string.warm_divergent_seventeen),
                resources.getString(R.string.warm_divergent_eighteen), resources.getString(R.string.warm_divergent_nineteen), resources.getString(R.string.warm_divergent_twenty)
        };

        arrar_answer = new String[]{resources.getString(R.string.warm_divergent_one_answer), resources.getString(R.string.warm_divergent_two_answer),
                resources.getString(R.string.warm_divergent_three_answer), resources.getString(R.string.warm_divergent_four_answer), resources.getString(R.string.warm_divergent_five_answer),
                resources.getString(R.string.warm_divergent_six_answer), resources.getString(R.string.warm_divergent_seven_answer), resources.getString(R.string.warm_divergent_eight_answer),
                resources.getString(R.string.warm_divergent_nine_answer), resources.getString(R.string.warm_divergent_ten_answer), resources.getString(R.string.warm_divergent_eleven_answer),
                resources.getString(R.string.warm_divergent_twelve_answer), resources.getString(R.string.warm_divergent_thirteen_answer), resources.getString(R.string.warm_divergent_fourteen_answer),
                resources.getString(R.string.warm_divergent_fifteen_answer), resources.getString(R.string.warm_divergent_sixteen_answer), resources.getString(R.string.warm_divergent_seventeen_answer),
                resources.getString(R.string.warm_divergent_eighteen_answer), resources.getString(R.string.warm_divergent_nineteen_answer), resources.getString(R.string.warm_divergent_twenty_answer)
        };
    }
}
