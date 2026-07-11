

        package com.drinkSpike;

import java.time.LocalDateTime;

public class AwarenessResource extends Resource{

    private String targetAudience;
    private String preventionTips;
    private String emergencyContacts;
    private boolean isFeatured;

    //constructor for new awareness resournce
    public AwarenessResource(String title,String description,String sourceLink,String category,String type,int addedBy,String status,String targetAudience,String preventionTips,String emergencyContacts,boolean isFeatured){
        super(title,description,sourceLink,category,type,addedBy,LocalDateTime.now(),status);
        this.targetAudience=targetAudience;
        this.preventionTips=preventionTips;
        this.emergencyContacts=emergencyContacts;
        this.isFeatured=isFeatured;
    }
    //constructor for loading from file
    public AwarenessResource(int resourceId, String title, String description, String sourceLink, String category, String type, int addedBy, LocalDateTime addedDate, String status, String targetAudience, String preventionTips, String emergencyContacts, boolean isFeatured){
        super(resourceId,title,description,sourceLink,category,type,addedBy,addedDate,status);
        this.targetAudience=targetAudience;
        this.preventionTips=preventionTips;
        this.emergencyContacts=emergencyContacts;
        this.isFeatured=isFeatured;
    }

    public String getTargetAudience(){
        return this.targetAudience;
    }
    public String getPreventionTips(){
        return this.preventionTips;
    }
    public String getEmergencyContacts(){
        return this.emergencyContacts;
    }
    public boolean isFeatured(){
        return this.isFeatured;
    }


    public void setTargetAudience(String targetAudience){
        this.targetAudience=targetAudience;
    }
    public void setPreventionTips(String preventionTips){
        this.preventionTips=preventionTips;
    }
    public void setEmergencyContacts(String emergencyContacts){
        this.emergencyContacts=emergencyContacts;
    }
    public void setFeatured(boolean isFeatured){
        this.isFeatured=isFeatured;
    }

    public void displayPreventionTips(){
        System.out.println("===Prevention Tips===");
        System.out.println(preventionTips);
    }
    public void displayEmergencyContacts(){
        System.out.println("===Emergency Contacts===");
        System.out.println(emergencyContacts);
    }
    //override dispaly method
    @Override
    public void displayInfo(){
        super.displayInfo();
        System.out.println("Target Audience : "+targetAudience);
        System.out.println("Prevention Tips : "+preventionTips);
        System.out.println("Emergency Contacts : "+emergencyContacts);
        System.out.println("Featured : "+(isFeatured ? "Yes" : "No"));
    }
    //override file handling
    @Override
    public String toFileString(){
        return super.toFileString() + "|" +targetAudience+"|"+preventionTips+"|"+emergencyContacts+"|"+isFeatured;
    }
    public static AwarenessResource fromFileString(String line){
        try{
            String[] parts = line.split("\\|");
            LocalDateTime addedDate=LocalDateTime.parse(parts[7],FORMATTER);
            return new AwarenessResource(Integer.parseInt(parts[0]),parts[1],parts[2],parts[3],parts[4],parts[5],Integer.parseInt(parts[6]),addedDate,parts[8],parts[9],parts[10],parts[11],Boolean.parseBoolean(parts[12]));
        }catch(Exception e){
            return null;
        }
    }
    @Override
    public String toString(){
        return  "[Awareness] "+super.toString() + " | Audience : "+targetAudience;
    }

}


