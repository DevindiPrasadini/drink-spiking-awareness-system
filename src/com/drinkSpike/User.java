

package com.drinkSpike;

public class User {
    private static int nextId = 1;
    private int userId;
    private String name;
    private String email;
    private String password;
    private String role;
    private String status;

    //constructor for new user
    public User(String name,String email,String password,String role,String status){
        this.userId=nextId++;
        this.name=name;
        this.email=email;
        this.password=password;
        this.role=role;
        this.status=status;
    }
    //cunstructor for loading from file
    public User(int userId, String name, String email,String password, String role, String status){
        this.userId=userId;
        this.name = name;
        this.email=email;
        this.password=password;
        this.role=role;
        this.status=status;
        if (userId>= nextId){
            nextId = userId+1;
        }
    }

    //get method
    public int getUserId(){
        return this.userId;
    }
    public String getName(){
        return this.name;
    }
    public String getEmail(){
        return this.email;
    }
    public String getPassword(){
        return this.password;
    }
    public String getRole(){
        return this.role;
    }
    public String getStatus(){
        return this.status;
    }
    //set method
    public void  setUserId(int userId){
        this.userId=userId;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public void setRole(String role){
        this.role=role;
    }
    public void status(String status){
        this.status=status;
    }
    //methods
    public void register(String email,String password){
        System.out.println("successfully registered");
    }
    public void login(String email,String password){
        System.out.println("successfully logged");

    }
    public void getSavedResources(){

    }
    //file handling methods
    public String toFileString(){
        return userId + "|" + name + "|" + email + "|" + password + "|" + role + "|" + status;
    }
    public static User fromFileString(String line){
        try {
            String[] parts = line.split("\\|");
            return new User(Integer.parseInt(parts[0]), parts[1], parts[2],parts[3],parts[4],parts[5]);
        }catch (Exception e){
            return null;
        }
    }
    @Override
    public String toString(){
        return "User ID: "+userId+" | Name: "+name+" | Email: "+email+" | Role: "+role+" | Status: "+status;
    }
}

class PublicUser extends User{
    private String privacyLevel;

    //constructor for new publicUser
    public PublicUser(String name,String email,String password,String role,String status,String privacyLevel){
        super(name,email,password,role,status);
        this.privacyLevel=privacyLevel;
    }

    //constructor for loading from file
    public PublicUser(int userId, String name,String email,String password,String role,String status){
        super(userId,name,email,password,role,status);
        this.privacyLevel="public";
    }

    //constructor for loading with privacyLevel
    public PublicUser(int userId, String name,String email,String password,String role,String status,String privacyLevel){
        super(userId,name,email,password,role,status);
        this.privacyLevel=privacyLevel;
    }
    public String getPrivacyLevel() {
        return this.privacyLevel;
    }
    public void setPrivacyLevel(){
        this.privacyLevel=privacyLevel;
    }
    //methods
    public void submitExp(){
        System.out.println("experience submitted successfully");
    }
    public void saveResources(){
        System.out.println("resource saved successfully");
    }
    public void report(String content){
        System.out.println("Report submitted: "+content);
    }
    public void viewStats(){
        System.out.println("View statistics");

    }
}
class Admin extends User{
    private int moderatorId;

    //constructor for new admin
    public Admin(String name,String email,String password,String role,String status,int moderatorId){
        super(name,email,password,role,status);
        this.moderatorId=moderatorId;
    }
    //constructor for loading from admin file
    public Admin(int userId, String name, String email, String password,String role, String status, int moderatorId){
        super(userId,name,email,password,role,status);
        this.moderatorId=moderatorId;
    }
    public int getModerateId(){
        return this.moderatorId;
    }
    public void setModerateId(int moderatorId){
        this.moderatorId=moderatorId;
    }

    //methods
    public void addResources(){
        System.out.println("Resource added successfully");
    }
    public void editResources(){
        System.out.println("Resource edited successfully");
    }
    public void deleteResources(){
        System.out.println("Resource deleted successfully");
    }
    public void moderateSubm(){
        System.out.println("Submission moderated successfully");
    }
    public void viewReport(){
        System.out.println("Viewing report");
    }
    public void systemMonitor(){
        System.out.println("Monitoring system health");
    }

}

