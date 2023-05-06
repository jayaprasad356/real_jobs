package com.app.realjobs.model;

import java.util.List;

public class Real {
    String id,company_name,title,income,description;
    List<RealJobVariant> real_jobs_variant;

    public Real(String id, String company_name, String title, String income, String description, List<RealJobVariant> real_jobs_variant) {
        this.id = id;
        this.company_name = company_name;
        this.title = title;
        this.income = income;
        this.description = description;
        this.real_jobs_variant = real_jobs_variant;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<RealJobVariant> getReal_jobs_variant() {
        return real_jobs_variant;
    }

    public void setReal_jobs_variant(List<RealJobVariant> real_jobs_variant) {
        this.real_jobs_variant = real_jobs_variant;
    }
}
