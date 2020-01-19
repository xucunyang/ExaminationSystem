package com.oranle.es.module.ui.innovation;

import android.app.Activity;
import android.content.res.Resources;

import androidx.lifecycle.MutableLiveData;

import com.oranle.es.R;
import com.oranle.es.module.base.BaseViewModel;

public class LogicThinkViewModel extends BaseViewModel {

    private Activity activity;
    public MutableLiveData<String> title = new MutableLiveData<>();
    public MutableLiveData<String> topic = new MutableLiveData<>();
    public MutableLiveData<String> NextText = new MutableLiveData<>("Next");
    public MutableLiveData<Boolean> isTopicShow = new MutableLiveData<>(true);
    public MutableLiveData<Boolean> isNextShow = new MutableLiveData<>(false);
    public String[] arrar_title;
    public String[] arrar_answer;
    private int index = 0;

    public LogicThinkViewModel(Activity activity) {
        this.activity = activity;
        Resources resources = activity.getResources();
        getArrayData(resources);

        title.setValue(arrar_title[0]);
        topic.setValue(arrar_answer[0]);
    }


    public void isShow() {
        isTopicShow.setValue(false);
        isNextShow.setValue(true);
        if (index == 21) {
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
        arrar_title = new String[]{resources.getString(R.string.logicThink_one), resources.getString(R.string.logicThink_two),
                resources.getString(R.string.logicThink_three), resources.getString(R.string.logicThink_four), resources.getString(R.string.logicThink_five),
                resources.getString(R.string.logicThink_six), resources.getString(R.string.logicThink_seven), resources.getString(R.string.logicThink_eight),
                resources.getString(R.string.logicThink_nine), resources.getString(R.string.logicThink_ten), resources.getString(R.string.logicThink_eleven),
                resources.getString(R.string.logicThink_twelve), resources.getString(R.string.logicThink_thirteen), resources.getString(R.string.logicThink_fourteen),
                resources.getString(R.string.logicThink_fifteen), resources.getString(R.string.logicThink_sixteen), resources.getString(R.string.logicThink_seventeen),
                resources.getString(R.string.logicThink_eighteen), resources.getString(R.string.logicThink_nineteen), resources.getString(R.string.logicThink_twenty),
                resources.getString(R.string.logicThink_21st), resources.getString(R.string.logicThink_22st)
        };
        arrar_answer = new String[]{resources.getString(R.string.logicThink_one_answer), resources.getString(R.string.logicThink_two_answer),
                resources.getString(R.string.logicThink_three_answer), resources.getString(R.string.logicThink_four_answer), resources.getString(R.string.logicThink_five_answer),
                resources.getString(R.string.logicThink_six_answer), resources.getString(R.string.logicThink_seven_answer), resources.getString(R.string.logicThink_eight_answer),
                resources.getString(R.string.logicThink_nine_answer), resources.getString(R.string.logicThink_ten_answer), resources.getString(R.string.logicThink_eleven_answer),
                resources.getString(R.string.logicThink_twelve_answer), resources.getString(R.string.logicThink_thirteen_answer), resources.getString(R.string.logicThink_fourteen_answer),
                resources.getString(R.string.logicThink_fifteen_answer), resources.getString(R.string.logicThink_sixteen_answer), resources.getString(R.string.logicThink_seventeen_answer),
                resources.getString(R.string.logicThink_eighteen_answer), resources.getString(R.string.logicThink_nineteen_answer), resources.getString(R.string.logicThink_twenty_answer),
                resources.getString(R.string.logicThink_21st_answer), resources.getString(R.string.logicThink_22st_answer)
        };
    }
}
