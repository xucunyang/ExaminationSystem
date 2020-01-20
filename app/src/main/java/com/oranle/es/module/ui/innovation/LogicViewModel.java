package com.oranle.es.module.ui.innovation;

import android.app.Activity;
import android.content.res.Resources;

import androidx.lifecycle.MutableLiveData;

import com.oranle.es.R;
import com.oranle.es.module.base.BaseViewModel;

public class LogicViewModel extends BaseViewModel {
    private Activity activity;
    public MutableLiveData<String> title = new MutableLiveData<>();
    public MutableLiveData<String> topic = new MutableLiveData<>();
    public MutableLiveData<String> NextText = new MutableLiveData<>("Next");
    public MutableLiveData<Boolean> isTopicShow = new MutableLiveData<>(true);
    public MutableLiveData<Boolean> isNextShow = new MutableLiveData<>(false);
    public String[] arrar_title;
    public String[] arrar_answer;
    private int index = 0;

    public LogicViewModel(Activity activity) {
        this.activity = activity;
        Resources resources = activity.getResources();
        getArrayData(resources);

        title.setValue(arrar_title[0]);
        topic.setValue(arrar_answer[0]);
    }


    public void isShow() {
        isTopicShow.setValue(false);
        isNextShow.setValue(true);
        if (index == 12) {
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
        arrar_title = new String[]{resources.getString(R.string.warm_logic_one), resources.getString(R.string.warm_logic_two),
                resources.getString(R.string.warm_logic_three), resources.getString(R.string.warm_logic_four), resources.getString(R.string.warm_logic_five),
                resources.getString(R.string.warm_logic_six), resources.getString(R.string.warm_logic_seven), resources.getString(R.string.warm_logic_eight),
                resources.getString(R.string.warm_logic_nine), resources.getString(R.string.warm_logic_ten), resources.getString(R.string.warm_logic_eleven),
                resources.getString(R.string.warm_logic_twelve), resources.getString(R.string.warm_logic_thirteen)
        };

        arrar_answer = new String[]{resources.getString(R.string.warm_logic_one_answer), resources.getString(R.string.warm_logic_two_answer),
                resources.getString(R.string.warm_logic_three_answer), resources.getString(R.string.warm_logic_four_answer), resources.getString(R.string.warm_logic_five_answer),
                resources.getString(R.string.warm_logic_six_answer), resources.getString(R.string.warm_logic_seven_answer), resources.getString(R.string.warm_logic_eight_answer),
                resources.getString(R.string.warm_logic_nine_answer), resources.getString(R.string.warm_logic_ten_answer), resources.getString(R.string.warm_logic_eleven_answer),
                resources.getString(R.string.warm_logic_twelve_answer), resources.getString(R.string.warm_logic_thirteen_answer)
        };
    }
}
