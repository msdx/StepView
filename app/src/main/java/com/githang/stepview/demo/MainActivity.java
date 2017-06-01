package com.githang.stepview.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.githang.stepview.StepView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private StepView mStepView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStepView = (StepView) findViewById(R.id.step_view);
        List<String> steps = Arrays.asList(new String[]{"输入手机", "验证手机", "设置密码", "注册成功"});
        mStepView.setSteps(steps);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.next) {
            int nextStep = mStepView.getCurrentStep() + 1;
            if (nextStep > mStepView.getStepCount()) {
                nextStep = 1;
            }
            mStepView.selectedStep(nextStep);
        }
    }
}
