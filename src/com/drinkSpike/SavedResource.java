

        package com.drinkSpike;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SavedResource {
    private static final DateTimeFormatter FORMATTER =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private int userId;
    private int resourceId;
    private LocalDateTime savedDate;
    private String notes;

    //constructor
    public SavedResource(int userId,int resourceId,String notes){
        this.userId=userId;
        this.resourceId=resourceId;
        this.savedDate=LocalDateTime.now();
        this.notes=notes;
    }

    //constructor for loading from file
    public SavedResource(int userId,int resourceId,LocalDateTime savedDate,String notes){
        this.userId=userId;
        this.resourceId=resourceId;
        this.savedDate=savedDate;
        this.notes=notes;
    }

    public int getUserId(){
        return this.userId;
    }
    public int getResourceId(){
        return this.resourceId;
    }
    public  LocalDateTime getSavedDate(){
        return this.savedDate;
    }
    public String getNotes(){
        return this.notes;
    }

    public void setNotes(String notes){
        this.notes=notes;
    }

    //file handling methods
    public String toFileString(){
        return userId + "|"+resourceId+"|"+savedDate.format(FORMATTER)+"|"+notes;
    }
    public static SavedResource fromFileString(String line){
        try{
            String[] parts = line.split("\\|");
            LocalDateTime savedDate = LocalDateTime.parse(parts[2],FORMATTER);
            return new SavedResource(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]),savedDate,parts[3]);
        }catch (Exception e ){
            return null;
        }
    }

    @Override
    public String toString(){
        return "User Id : "+userId+ " | Resource Id : "+resourceId+" | Saved on : "+savedDate.format(FORMATTER)+" | Notes : "+notes;
    }
}
