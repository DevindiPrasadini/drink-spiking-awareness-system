

        package com.drinkSpike;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.lang.String;

public class Resource {
    protected static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    protected static int nextId = 1;

    protected int resourceId;
    protected String title;
    protected String description;
    protected String sourceLink;
    protected String category;
    protected String type;
    protected int addedBy;
    protected LocalDateTime addedDate;
    protected String status;

    //constructor for new resource
    public Resource(String title,String description,String sourceLink,String category,String type,int addedBy,LocalDateTime addedDate,String status){
        this.resourceId=nextId++;
        this.title=title;
        this.description=description;
        this.sourceLink =sourceLink;
        this.category=category;
        this.type=type;
        this.addedBy=addedBy;
        this.addedDate=addedDate != null ? addedDate : LocalDateTime.now();
        this.status=status;

    }
    //constructor for loading from file
    public Resource(int resourceId, String title, String description, String sourceLink, String category, String  type, int addedBy, LocalDateTime addedDate, String status){
        this.resourceId=resourceId;
        this.title=title;
        this.description=description;
        this.sourceLink =sourceLink;
        this.category=category;
        this.type=type;
        this.addedBy=addedBy;
        this.addedDate=addedDate != null ? addedDate : LocalDateTime.now();
        this.status=status;
        if (resourceId >= nextId){
            nextId= resourceId+1;
        }
    }

    public int getResourceId(){
        return this.resourceId;
    }
    public String getTitle(){
        return this.title;
    }
    public String getDescription(){
        return this.description;
    }
    public String getSourceLink(){
        return this.sourceLink;
    }

    public String  getCategory() {
        return this.category;
    }
    public String getType(){
        return this.type;
    }
    public int getAddedBy(){
        return this.addedBy;
    }
    public String getStatus(){
        return this.status;
    }

    public void setResourceId(int resourceId){
        this.resourceId=resourceId;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public void setDescription(String description){
        this.description=description;
    }
    public void setLink(String link){
        this.sourceLink =link;
    }
    public void setCategory(String category){
        this.category=category;
    }
    public void setType(String type){
        this.type=type;
    }
    public void setAddedBy(int addedBy){
        this.addedBy=addedBy;
    }
    public void setStatus(String status){
        this.status=status;
    }

    //file handling methods
    public String toFileString(){
        return resourceId + "|" +title+"|"+description+"|"+sourceLink+"|"+category+"|"+type+"|"+addedBy+"|"+addedDate.format(FORMATTER)+"|"+status;
    }
    public static Resource fromFileString(String line){
        try{
            String[] parts= line.split("\\|");
            LocalDateTime addedDate = LocalDateTime.parse(parts[7], FORMATTER);
            return new Resource(Integer.parseInt(parts[0]),parts[1],parts[2],parts[3],parts[4],parts[5],Integer.parseInt(parts[6]),addedDate,parts[8]);
        }catch(Exception e){
            return null;
        }
    }

    //diplay method
    public void displayInfo(){
        System.out.println("Resource ID : "+resourceId);
        System.out.println("Title : "+title);
        System.out.println("Category : "+category);
        System.out.println("Type : "+type);
        System.out.println("Description : "+description);
        System.out.println("Source : "+sourceLink);
        System.out.println("Added By : "+addedBy);
        System.out.println("Added on : "+addedDate.format(FORMATTER));
        System.out.println("Status : "+status);

    }
    @Override
    public String toString(){
        return "Resource #"+ resourceId + " | "+ title+ " | "+category;
    }

}

