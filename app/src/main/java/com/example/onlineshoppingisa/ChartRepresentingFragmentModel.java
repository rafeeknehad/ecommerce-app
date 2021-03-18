package com.example.onlineshoppingisa;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshoppingisa.util.FlowChartRetrofit;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ChartRepresentingFragmentModel extends AndroidViewModel {

    private static final String TAG = "ChartRepresentingFragme";
    private MutableLiveData<List<FlowChartRetrofit>> liveData;
    private List<FlowChartRetrofit> dataList;

    public ChartRepresentingFragmentModel(@NonNull Application application) {
        super(application);
        liveData = new MutableLiveData<>();
        dataList = new ArrayList<>();
    }

    public LiveData<List<FlowChartRetrofit>> getAllChartOfUserOrder() {
        Log.d(TAG, "getAllChartOfUserOrder: ////// " + MainActivity.currentUser.getUserAuthId());
        CollectionReference years = FirebaseFirestore.getInstance()
                .collection("FlowChart")
                .document(MainActivity.currentUser.getUserAuthId())
                .collection("Years");


        years.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            Log.d(TAG, "onComplete: //////// 22 " + task.getResult().size());
                            for (DocumentSnapshot snapshot : task.getResult()) {
                                Log.d(TAG, "onComplete: ////// " + snapshot.getId());
                                years.document(snapshot.getId())
                                        .collection("Months")
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful() && task.getResult() != null) {
                                                    for (DocumentSnapshot snapshot1 : task.getResult()) {
                                                        years.document(snapshot.getId())
                                                                .collection("Months")
                                                                .document(snapshot1.getId())
                                                                .collection("Orders")
                                                                .get()
                                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                        if (task.isSuccessful() && task.getResult() != null) {
                                                                            Log.d(TAG, "onComplete: ///// " + task.getResult().size());
                                                                            dataList.add(new FlowChartRetrofit(snapshot.getId(), snapshot1.getId(), String.valueOf(task.getResult().size())));
                                                                            liveData.setValue(dataList);
                                                                        }
                                                                    }
                                                                });
                                                    }
                                                }
                                            }
                                        });
                            }
                        }
                    }
                });
        return liveData;
    }
}
