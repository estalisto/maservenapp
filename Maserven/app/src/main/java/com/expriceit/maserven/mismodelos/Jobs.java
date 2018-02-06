package com.expriceit.maserven.mismodelos;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Table;

/**
 * Created by stalyn on 9/1/2018.
 */

@Table(name="jobs_title")
public class Jobs extends SugarRecord {
    @Column(name="job_title")
    public String jobTitle;

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}
