package com.expriceit.maserven.adaptadores;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.expriceit.maserven.mismodelos.Jobs;

import java.util.ArrayList;

/**
 * Created by stalyn on 9/1/2018.
 */

public class JobsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Jobs> jobList;

    public JobsAdapter(Context context, ArrayList<Jobs> jobList) {
        this.context = context;
        this.jobList = jobList;
    }


    @Override
    public int getCount() {
        return jobList.size();
    }

    @Override
    public Object getItem(int i) {
        return jobList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
  /*      if (view== null){
            view = View.inflate(context, R.layout.jobs_list,null);

        }
        TextView jobText = (TextView) view.findViewById(R.id.job_textView);
        Jobs jobs = jobList.get(i);
        jobText.setText(jobs.getJobTitle());
*/
        return view;
    }
}

