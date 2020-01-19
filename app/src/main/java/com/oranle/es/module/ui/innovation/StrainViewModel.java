package com.oranle.es.module.ui.innovation;

import android.app.Activity;
import android.content.res.Resources;

import androidx.lifecycle.MutableLiveData;

import com.oranle.es.R;
import com.oranle.es.module.base.BaseViewModel;

public class StrainViewModel extends BaseViewModel {
    private Activity activity;
    public MutableLiveData<String> title = new MutableLiveData<>();
    public MutableLiveData<String> topic = new MutableLiveData<>();
    public MutableLiveData<String> NextText = new MutableLiveData<>("Next");
    public MutableLiveData<Boolean> isTopicShow = new MutableLiveData<>(true);
    public MutableLiveData<Boolean> isNextShow = new MutableLiveData<>(false);
    public String[] arrar_title;
    public String[] arrar_answer;
    private int index = 0;

    public StrainViewModel(Activity activity) {
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
        arrar_title = new String[]{resources.getString(R.string.Strain_one), resources.getString(R.string.Strain_two),
                resources.getString(R.string.Strain_three), resources.getString(R.string.Strain_four), resources.getString(R.string.Strain_five),
                resources.getString(R.string.Strain_six), resources.getString(R.string.Strain_seven), resources.getString(R.string.Strain_eight),
                resources.getString(R.string.Strain_nine), resources.getString(R.string.Strain_ten), resources.getString(R.string.Strain_eleven),
                resources.getString(R.string.Strain_twelve), resources.getString(R.string.Strain_thirteen), resources.getString(R.string.Strain_fourteen),
                resources.getString(R.string.Strain_fifteen), resources.getString(R.string.Strain_sixteen), resources.getString(R.string.Strain_seventeen),
                resources.getString(R.string.Strain_eighteen), resources.getString(R.string.Strain_nineteen), resources.getString(R.string.Strain_twenty),
                resources.getString(R.string.Strain_21st), resources.getString(R.string.Strain_22st), resources.getString(R.string.Strain_23st),
                resources.getString(R.string.Strain_24st), resources.getString(R.string.Strain_25st), resources.getString(R.string.Strain_26st),
                resources.getString(R.string.Strain_27st), resources.getString(R.string.Strain_28st), resources.getString(R.string.Strain_29st)
        };
        arrar_answer = new String[]{resources.getString(R.string.Strain_one_answer), resources.getString(R.string.Strain_two_answer),
                resources.getString(R.string.Strain_three_answer), resources.getString(R.string.Strain_four_answer), resources.getString(R.string.Strain_five_answer),
                resources.getString(R.string.Strain_six_answer), resources.getString(R.string.Strain_seven_answer), resources.getString(R.string.Strain_eight_answer),
                resources.getString(R.string.Strain_nine_answer), resources.getString(R.string.Strain_ten_answer), resources.getString(R.string.Strain_eleven_answer),
                resources.getString(R.string.Strain_twelve_answer), resources.getString(R.string.Strain_thirteen_answer), resources.getString(R.string.Strain_fourteen_answer),
                resources.getString(R.string.Strain_fifteen_answer), resources.getString(R.string.Strain_sixteen_answer), resources.getString(R.string.Strain_seventeen_answer),
                resources.getString(R.string.Strain_eighteen_answer), resources.getString(R.string.Strain_nineteen_answer), resources.getString(R.string.Strain_twenty_answer),
                resources.getString(R.string.Strain_21st_answer), resources.getString(R.string.Strain_22st_answer), resources.getString(R.string.Strain_23st_answer),
                resources.getString(R.string.Strain_24st_answer), resources.getString(R.string.Strain_25st_answer), resources.getString(R.string.Strain_26st_answer),
                resources.getString(R.string.Strain_27st_answer), resources.getString(R.string.Strain_28st_answer), resources.getString(R.string.Strain_29st_answer)
        };
    }
}
