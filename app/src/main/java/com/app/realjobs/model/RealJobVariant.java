package com.app.realjobs.model;

public class RealJobVariant {
    String id, real_jobs_id, job_details;

    public RealJobVariant(String id, String real_jobs_id, String job_details) {
        this.id = id;
        this.real_jobs_id = real_jobs_id;
        this.job_details = job_details;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReal_jobs_id() {
        return real_jobs_id;
    }

    public void setReal_jobs_id(String real_jobs_id) {
        this.real_jobs_id = real_jobs_id;
    }

    public String getJob_details() {
        return job_details;
    }

    public void setJob_details(String job_details) {
        this.job_details = job_details;
    }
}
