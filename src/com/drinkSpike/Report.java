

        package com.drinkSpike;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Report {
    private  static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static int nextId=1;

    private int reportId;
    private int userId;
    private int resourceId;
    private String reason;
    private String details;
    private LocalDateTime reportDate;
    private String status;//pending,reviewed,resolved

    //constructor
    public Report(int userId,int resourceId,String reason,String details){
        this.reportId=reportId;
        this.userId=userId;
        this.resourceId=resourceId;
        this.reason=reason;
        this.details=details;
        this.reportDate=LocalDateTime.now();
        this.status="pending";
    }

    //constructor for loading from file
    public Report(int reportId,int userId,int resourceId, String reason,String details,LocalDateTime reportDate,String status){
        this.reportId=reportId;
        this.userId=userId;
        this.resourceId=resourceId;
        this.reason=reason;
        this.details=details;
        this.reportDate=reportDate;
        this.status=status;
        if(reportId >= nextId){
            nextId = reportId +1;
        }
    }

    public int getReportId(){
        return this.reportId;
    }
    public int getUserId(){
        return this.userId;
    }
    public int getResourceId(){
        return this.resourceId;
    }
    public String getReason(){
        return this.reason;
    }
    public String getDetails(){
        return this.details;
    }
    public LocalDateTime getReportDate(){
        return this.reportDate;
    }
    public void setStatus(){
        this.status=status;
    }

    //methods
    public void resolve(){
        this.status = "resolved";
        System.out.println("Report resolved");
    }
    public void markReviewed(){
        this.status="reviewed";
        System.out.println("Report marked as reviewed");
    }

    //file handling methods
    public String toFileString(){
        return reportId +"|"+userId+"|"+resourceId+"|"+reason+"|"+details+"|"+reportDate.format(FORMATTER)+"|"+status;
    }
    public  static Report fromFileString(String line){
        try{
            String[] parts = line.split("\\|");
            LocalDateTime reportDate = LocalDateTime.parse(parts[5],FORMATTER);
            return new Report(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]),Integer.parseInt(parts[2]),parts[3],parts[4],reportDate,parts[6]);
        }catch (Exception e){
            return null;
        }
    }

    public void displayInfo(){
        System.out.println("===Report #"+ reportId+" ===");
        System.out.println("User Id : "+userId);
        System.out.println("Resource Id : "+resourceId);
        System.out.println("Reason : "+reason);
        System.out.println("Details : "+details);
        System.out.println("Reported on : "+reportDate.format(FORMATTER));
        System.out.println("Status : "+status);
    }

    @Override
    public String toString(){
        return "Report #"+ reportId+" | Resource #"+ resourceId+ " | Reason : "+reason+" | Status : "+status;
    }
}

