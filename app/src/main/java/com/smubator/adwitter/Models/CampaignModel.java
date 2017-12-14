package com.smubator.adwitter.Models;

public class CampaignModel {
    private String id;
    private String user_id;
    private String category_id;
    private String camp_name;
    private String camp_description;
    private String camp_from;
    private String camp_to;
    private String camp_daily_budget;
    private String camp_max_cpc;
    private String camp_text_url;
    private String camp_media;
    private String camp_status;
    private boolean camp_active;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCamp_name() {
        return camp_name;
    }

    public void setCamp_name(String camp_name) {
        this.camp_name = camp_name;
    }

    public String getCamp_description() {
        return camp_description;
    }

    public void setCamp_description(String camp_description) {
        this.camp_description = camp_description;
    }

    public String getCamp_from() {
        return camp_from;
    }

    public void setCamp_from(String camp_from) {
        this.camp_from = camp_from;
    }

    public String getCamp_to() {
        return camp_to;
    }

    public void setCamp_to(String camp_to) {
        this.camp_to = camp_to;
    }

    public String getCamp_daily_budget() {
        return camp_daily_budget;
    }

    public void setCamp_daily_budget(String camp_daily_budget) {
        this.camp_daily_budget = camp_daily_budget;
    }

    public String getCamp_max_cpc() {
        return camp_max_cpc;
    }

    public void setCamp_max_cpc(String camp_max_cpc) {
        this.camp_max_cpc = camp_max_cpc;
    }

    public String getCamp_text_url() {
        return camp_text_url;
    }

    public void setCamp_text_url(String camp_text_url) {
        this.camp_text_url = camp_text_url;
    }

    public String getCamp_media() {
        return camp_media;
    }

    public void setCamp_media(String camp_media) {
        this.camp_media = camp_media;
    }

    public String getCamp_status() {
        return camp_status;
    }

    public void setCamp_status(String camp_status) {
        this.camp_status = camp_status;
    }

    public boolean getCamp_active() {
        return camp_active;
    }

    public void setCamp_active(boolean camp_active) {
        this.camp_active = camp_active;
    }
}
