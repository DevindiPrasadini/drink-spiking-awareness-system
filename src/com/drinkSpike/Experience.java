
package com.drinkSpike;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.lang.Integer;

public class Experience {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static int nextId = 1;

    private int experienceId;
    private int userId;
    private String title;
    private String description;
    private boolean anonymous;
    private String status; //pending,approved,rejected
    private LocalDateTime submissionDate;
    private Integer moderatedBy;
    private LocalDateTime moderationDate;


    //constructor for new experience
    public Experience(int userId, String title, String description, String status,boolean anonymous, LocalDateTime submissionDate,Integer moderatedBy,LocalDateTime moderationDate) {
        this.experienceId = nextId++;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.status = status;
        this.anonymous = anonymous;
        this.submissionDate = submissionDate != null ? submissionDate : LocalDateTime.now();
        this.moderatedBy=moderatedBy;
        this.moderationDate =moderationDate;
    }

    //constructor for loading from file
    public Experience(int experienceId, int userId, String title, String description, String status, boolean anonymous, LocalDateTime submissionDate, Integer moderatedBy, LocalDateTime moderationDate) {
        this.experienceId = experienceId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.status = status;
        this.anonymous = anonymous;
        this.submissionDate = submissionDate;
        this.moderatedBy = moderatedBy;
        this.moderationDate = moderationDate;
        if (experienceId >= nextId) {
            nextId = experienceId + 1;
        }
    }

    public int getExperienceId() {
        return this.experienceId;
    }

    public int getUserId() {
        return this.userId;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getStatus() {
        return this.status;
    }

    public boolean isAnonymous() {
        return this.anonymous;
    }

    public LocalDateTime getSubmissionDate() {
        return this.submissionDate;
    }

    public Integer getModeratedBy() {
        return this.moderatedBy;
    }

    public LocalDateTime getModerationDate() {
        return this.moderationDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setModeratedBy(int moderatedBy) {
        this.moderatedBy = moderatedBy;
    }

    public void setModerationDate(LocalDateTime moderationDate) {
        this.moderationDate = moderationDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //methods
    public void approve(int adminId) {
        this.status = "approved";
        this.moderatedBy = adminId;
        this.moderationDate = LocalDateTime.now();
        System.out.println("Experience approved successfully");
    }

    public void reject(int adminId) {
        this.status = "rejected";
        this.moderatedBy = adminId;
        this.moderationDate = LocalDateTime.now();
        System.out.println("Experience rejected");
    }


    public boolean isPending() {
        return "pending".equalsIgnoreCase(status);
    }
    private static LocalDateTime parseDate(String dateStr){
        if (dateStr == null || dateStr.trim().isEmpty()){
            return null;
        }
        //clean the string first
        dateStr = dateStr.trim().replaceAll("\\s+", " ").replace("\u00A0", " ");

        //try different formats
        String[] formats = {
                "yyyy-MM-dd HH:mm:ss",
                "yyyy-MM-dd HH:mm:ss.SSS",
                "yyyy-MM-dd HH:mm",
                "yyyy-MM-dd"
        };
        for (String format :formats){
            try{
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                return LocalDateTime.parse(dateStr, formatter);
            }catch (Exception e){
                System.out.println("Error in date and time format");
            }
        }
        try {
            String clean = dateStr.replaceAll("[^0-9\\-: ]", " ").replaceAll("\\s+", " ");
            String[] parts = clean.split(" ");
            if (parts.length >= 3){
                int year = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int day = Integer.parseInt(parts[2]);
                int hour = 0, minute = 0, second = 0;
                if (parts.length >= 4) hour = Integer.parseInt(parts[3]);
                if (parts.length >= 5) minute = Integer.parseInt(parts[4]);
                if (parts.length >= 6) second = Integer.parseInt(parts[5]);
                return  LocalDateTime.of(year,month,day,hour,minute,second);
            }
        }catch (Exception e) {
            System.out.println("Could not parse: "+dateStr);
        }
        return LocalDateTime.now();
    }

    //file handling methods
    public String toFileString() {
        //clean description and title
        String cleanTitle = title != null ? title.replace("\n"," ").replace("\r"," ") : "";
        String cleanDescription = description != null ? description.replace("\n"," ").replace("\r"," ") : "";


        String subDate = (submissionDate != null) ? submissionDate.format(FORMATTER) : "";

        String modBy = (moderatedBy != null) ? String.valueOf(moderatedBy) : "";

        String modDate = (moderationDate != null) ? moderationDate.format(FORMATTER) : "";

        return experienceId + "|" + userId + "|"+ cleanTitle +"|"+cleanDescription +"|"+status +"|"+anonymous+"|"+subDate+"|"+modBy+"|"+modDate;
/*
        //handle submission date - should not be null
        String submissionDateStr = "";
        if (submissionDate != null){
            submissionDateStr = submissionDate.format(FORMATTER);
        }else{
            submissionDateStr = LocalDateTime.now().format(FORMATTER);
        }

        //handle moderation date - can be null
        String moderationDateStr = "";
        if (moderationDate != null ){
            moderationDateStr = moderationDate.format(FORMATTER);
        }

        //handle moderated by - can be null
        String moderatedByStr = "";
        if(moderatedBy != null){
            moderatedByStr = String.valueOf(moderatedBy);
        }
        return experienceId + "|" + userId +"|"+cleanTitle+"|"+cleanDescription+"|"+status+"|"+anonymous+"|"+submissionDateStr+"|"+moderatedByStr + "|"+ moderationDateStr;

        return experienceId + "|"+userId+"|"+title+"|"+description+"|"+"|"+status+"|"+anonymous+"|"+
                (submissionDate != null ? submissionDate.format(FORMATTER): "")+
"|"+(moderatedBy != null ? moderatedBy: "null")+"|"+(moderationDate != null ? moderationDate: "null");  */
    }

    public static Experience fromFileString(String line) {
        try {
            if (line == null || line.trim().isEmpty()) {
                //System.out.println("DEBUG: Empty line");
                return null;
            }
            String[] parts = line.split("\\|");
            // System.out.println("DEBUG: Found "+parts.length +" parts");

            /*for (int i=0; i< parts.length;i++){
                System.out.println("DEBUG: Not enough parts");
            }*/

            if (parts.length < 7){
                //System.out.println("DEBUG: Not enough parts");
                return null;
            }

            int expId = Integer.parseInt(parts[0].trim());
            int userId = Integer.parseInt(parts[1].trim());
            String title = parts[2];
            String desc = parts[3];
            String status = parts[4].trim();
            boolean anonymous = Boolean.parseBoolean(parts[5].trim());

            LocalDateTime subDate;
            String  dateStr = parts[6].trim();
            if (dateStr.isEmpty()){
                System.out.println("Date is empty");
                subDate = LocalDateTime.now();
            }else {
                try {
                    subDate = LocalDateTime.parse(dateStr, FORMATTER);
                    //System.out.println("Successfully parsed");
                }catch (Exception e){
                    System.out.println("date parse error");
                    subDate = LocalDateTime.now();
                }
            }
            Integer modBy = null;
            if (parts.length > 7 && parts[7] != null && !parts[7].trim().isEmpty() && !parts[7].trim().equals("null")){
                try{
                    modBy = Integer.parseInt(parts[7].trim());
                }catch (Exception e){
                    //ignore
                }

            }


            LocalDateTime modDate = null;
            if (parts.length > 8 && parts[8] != null && !parts[8].trim().isEmpty() && !parts[8].trim().equals("null")){
                try {
                    modDate = LocalDateTime.parse(parts[8].trim(), FORMATTER);
                } catch (Exception e){
                    //ignore
                }
            }
            //System.out.println("DEBUG: successfully parsed");
            return new Experience(expId,userId, title,desc,status,anonymous,subDate,modBy,modDate);
        }catch (Exception e){
            System.out.println("DEBUG: Error parsing experience: "+e.getMessage());
            return null;
        }
        /*try {
            //skip empty lines
            if (line == null || line.trim().isEmpty()){
                return null;
            }

            String[] parts = line.split("\\|");

            if(parts.length < 7){
                System.out.println("Invalid experience line!");
                return null;
            }
            int expId=Integer.parseInt(parts[0].trim());//experienceId
            int uId = Integer.parseInt(parts[1].trim());//userId
            String t = parts[2];//title
            String desc = parts[3];//description
            String stat =parts[4].trim();//status
            boolean anon = Boolean.parseBoolean(parts[5].trim());//anonymous
            String  dateStr = parts[6].trim();
            dateStr = dateStr.replaceAll("\\s+", " ");
            dateStr = dateStr.replace("\u00A0", " ");

            LocalDateTime subDate = LocalDateTime.parse(dateStr, FORMATTER);
            Integer modBy= null;//moderatedBy
            if (parts.length > 7 && parts[7] != null && !parts[7].isEmpty() && !parts[7].equals("null")){
                modBy = Integer.parseInt(parts[7]);
            }
            LocalDateTime modDate=null;//moderatedDate
            if (parts.length > 8 && parts[8] != null && !parts[8].isEmpty() && !parts[8].equals("null")){
                String modDateStr = parts[8].trim().replaceAll("\\s+", " ");
                modDate = LocalDateTime.parse(modDateStr, FORMATTER);
            }
           return new Experience(expId,uId,t,desc,stat,anon,subDate,modBy,modDate);

        } catch (Exception e) {
            System.out.println("Error passing experience: "+e.getMessage());
            return null;
        }*/
    }


    /*private int generateId(){
        int lastId = 0;

        try(BufferedReader br = new BufferedReader(new FileReader("experiences.txt"))){
            String line;
            while ((line = br.readLine())!=null){
                    String[] parts = line.split("\\|");
                    if (parts.length > 0){
                        lastId = Integer.parseInt(parts[0]);
                    }
            }
        }catch (IIOException e){
            System.out.println("Error in generate Id in experience submission");
        }
        return  lastId+1;
    }*/

    public void displayInfo(User user) {
        System.out.println("===Experience #" + experienceId + "===");
        if (anonymous) {
            System.out.println("Submitted by : Anonymous");
        } else {
            System.out.println("Submitted by : " + user.getName());
        }
        System.out.println("Title : " + title);
        System.out.println("Description : " + description);
        System.out.println("Status : " + status);
        System.out.println("Submitted : " + submissionDate.format(FORMATTER));
    }
    @Override
    public String toString(){
        return "Experience #"+experienceId+" | "+title+" | Status : "+status;
    }
}
