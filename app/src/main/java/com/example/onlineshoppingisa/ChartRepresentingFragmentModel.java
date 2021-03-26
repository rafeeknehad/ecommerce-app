package com.example.onlineshoppingisa;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshoppingisa.models.FlowChart;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ChartRepresentingFragmentModel extends AndroidViewModel {

    private MutableLiveData<List<FlowChart>> liveData;
    private List<FlowChart> dataList;

    public ChartRepresentingFragmentModel(@NonNull Application application) {
        super(application);
        liveData = new MutableLiveData<>();
        dataList = new ArrayList<>();
    }

    public LiveData<List<FlowChart>> getAllChartOfUserOrder() {
        FirebaseFirestore.getInstance()
                .collection("FlowChart")
                .document(MainActivity.currentUser.getUserAuthId())
                .collection("Orders")
                .get()
                .addOnCompleteListener(task -> {
                    if (task .getResult()!= null) {
                        for (QueryDocumentSnapshot documentReference : task.getResult()) {
                            FlowChart flowChart = documentReference.toObject(FlowChart.class);
                            flowChart.setId(documentReference.getId());
                            dataList.add(flowChart);
                        }
                        liveData.setValue(dataList);
                    }
                });
        return liveData;
    }
}
