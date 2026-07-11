

        package com.drinkSpike;

import java.time.LocalDateTime;
import java.lang.String;

public class ResearchResource extends Resource {
    private String authors;
    private String publicationDate;
    private String journalName;

    //constructor for new rresearch resource
    public ResearchResource(String title, String description, String sourceLink, String category, String type, int addedBy, String status, String authors, String publicationDate, String journalName) {
        super(title, description, sourceLink, category, type, addedBy,LocalDateTime.now(),status);
        this.authors = authors;
        this.publicationDate = publicationDate;
        this.journalName = journalName;
    }

    //constructor for loading from file
    public ResearchResource(int resourceId, String title, String description, String sourceLink, String category, String type, int addedBy, LocalDateTime addedDate, String status, String authors, String publicationDate, String journalName) {
        super(resourceId, title, description, sourceLink, category, type,addedBy, addedDate, status);
        this.authors = authors;
        this.publicationDate =publicationDate;
        this.journalName = journalName;
    }

    public String getAuthors() {
        return this.authors;
    }

    public String getPublicationDate() {
        return this.publicationDate;
    }

    public String getJournalName() {
        return this.journalName;
    }


    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setJournalName(String journalName) {
        this.journalName = journalName;
    }

    //method to display citation
    public void displayCitation() {
        System.out.println("===Citation===");
        System.out.println(authors + " (" + publicationDate + "). " + title + ". " + journalName);
    }

    //override display method
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Authors : " + authors);
        System.out.println("Publication Date : " + publicationDate);
        System.out.println("Journal : " + journalName);
    }
    //file handling override
    @Override
    public String toFileString(){
        return  super.toFileString() + "|" +authors+"|"+publicationDate+"|"+journalName;
    }
    public static ResearchResource fromFileString(String line){
        try{
            String[] parts = line.split("\\|");
            LocalDateTime addedDate = LocalDateTime.parse(parts[7],FORMATTER);
            return new ResearchResource(Integer.parseInt(parts[0]),parts[1],parts[2],parts[3],parts[4],parts[5],Integer.parseInt(parts[6]),addedDate,parts[8],parts[9],parts[10],parts[11]);
        }catch(Exception e){
            return null;
        }
    }
    @Override
    public String toString(){
        return  "[RESEARCH] "+super.toString() + " | Authors : "+authors+ " | Journal : "+journalName;
    }


}









