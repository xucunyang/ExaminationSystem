package com.oranle.es.module.ui.innovation;

import android.app.Activity;
import android.content.res.Resources;

import androidx.lifecycle.MutableLiveData;

import com.oranle.es.R;
import com.oranle.es.module.base.BaseViewModel;

public class DivergentThinkViewModel extends BaseViewModel {

    private Activity activity;
    public MutableLiveData<String> title = new MutableLiveData<>();
    public MutableLiveData<String> topic = new MutableLiveData<>();
    public MutableLiveData<String> NextText = new MutableLiveData<>("Next");
    public MutableLiveData<Boolean> isTopicShow = new MutableLiveData<>(true);
    public MutableLiveData<Boolean> isNextShow = new MutableLiveData<>(false);
    public String[] arrar_title;
    public String[] arrar_answer;
    private int index = 0;

    public DivergentThinkViewModel(Activity activity) {
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
        arrar_title = new String[]{resources.getString(R.string.divergentThink_one), resources.getString(R.string.divergentThink_two),
                resources.getString(R.string.divergentThink_three), resources.getString(R.string.divergentThink_four), resources.getString(R.string.divergentThink_five),
                resources.getString(R.string.divergentThink_six), resources.getString(R.string.divergentThink_seven), resources.getString(R.string.divergentThink_eight),
                resources.getString(R.string.divergentThink_nine), resources.getString(R.string.divergentThink_ten), resources.getString(R.string.divergentThink_eleven),
                resources.getString(R.string.divergentThink_twelve), resources.getString(R.string.divergentThink_thirteen), resources.getString(R.string.divergentThink_fourteen),
                resources.getString(R.string.divergentThink_fifteen), resources.getString(R.string.divergentThink_sixteen), resources.getString(R.string.divergentThink_seventeen),
                resources.getString(R.string.divergentThink_eighteen), resources.getString(R.string.divergentThink_nineteen), resources.getString(R.string.divergentThink_twenty)
        };
        arrar_answer = new String[]{resources.getString(R.string.divergentThink_one_answer), resources.getString(R.string.divergentThink_two_answer),
                resources.getString(R.string.divergentThink_three_answer), resources.getString(R.string.divergentThink_four_answer), resources.getString(R.string.divergentThink_five_answer),
                resources.getString(R.string.divergentThink_six_answer), resources.getString(R.string.divergentThink_seven_answer), resources.getString(R.string.divergentThink_eight_answer),
                resources.getString(R.string.divergentThink_nine_answer), resources.getString(R.string.divergentThink_ten_answer), resources.getString(R.string.divergentThink_eleven_answer),
                resources.getString(R.string.divergentThink_twelve_answer), resources.getString(R.string.divergentThink_thirteen_answer), resources.getString(R.string.divergentThink_fourteen_answer),
                resources.getString(R.string.divergentThink_fifteen_answer), resources.getString(R.string.divergentThink_sixteen_answer), resources.getString(R.string.divergentThink_seventeen_answer),
                resources.getString(R.string.divergentThink_eighteen_answer), resources.getString(R.string.divergentThink_nineteen_answer), resources.getString(R.string.divergentThink_twenty_answer)
        };
    }
}
