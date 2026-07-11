

        package com.drinkSpike;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final String USERS_FILE = "users.txt";
    private static final String RESOURCES_FILE = "resources.txt";
    private static final String AWARENESS_RESOURCES_FILE = "awareness_resources.txt";
    private static final String RESEARCH_RESOURCES_FILE = "research_resources.txt";
    private static final String EXPERIENCES_FILE = "experiences.txt";
    private static final String REPORTS_FILE = "reports.txt";
    private static final String SAVED_RESOURCES_FILE = "saved_resources.txt";

    private static User currentUser = null;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeFiles();

        while (true) {
            if (currentUser == null) {
                showMainMenu();
            } else if (currentUser.getRole().equals("admin")) {
                showAdminDashboard();
            } else {
                showPublicDashboard();
            }
        }
    }

    private static void initializeFiles() {
        try {
            new File(USERS_FILE).createNewFile();
            new File(RESOURCES_FILE).createNewFile();
            new File(AWARENESS_RESOURCES_FILE).createNewFile();
            new File(RESEARCH_RESOURCES_FILE).createNewFile();
            new File(EXPERIENCES_FILE).createNewFile();
            new File(REPORTS_FILE).createNewFile();
            new File(SAVED_RESOURCES_FILE).createNewFile();

            List<User> users = loadUsers();
            if (users.isEmpty()) {
                Admin defaultAdmin = new Admin("System Admin", "admin@system.com", "admin1234", "admin", "active", 1);
                saveUser(defaultAdmin);
                System.out.println("Default admin created - Email: admin@system.com, password: admin1234");
            }
        } catch (IOException e) {
            System.out.println("Error initializing files: " + e.getMessage());
        }
    }

    private static void showMainMenu() {
        System.out.println("\n----------------------------------------------------------------------------");
        System.out.println("PROTECT YOUR GLASS - Drink Spiking Awareness System  ");
        System.out.println("\n----------------------------------------------------------------------------");

        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. View Resources(No login required)");
        System.out.println("4. Exit");
        System.out.println("Choose option: ");

        int choice = getIntInput();
        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                register();
                break;
            case 3:
                viewAllResources();
                break;
            case 4:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    private static void showPublicDashboard() {
        System.out.println("\n----------------------------------------------------------------------------");
        System.out.println("                   PUBLIC DASHBOARD                     ");
        System.out.println("\n----------------------------------------------------------------------------");

        System.out.println("Welcome, " + currentUser.getName() + "!");
        System.out.println("1. View All Resources");
        System.out.println("2. View Awareness Resources");
        System.out.println("3. View Research Resources");
        System.out.println("4. Search Resources");
        System.out.println("5. Filter Resources by Category");
        System.out.println("6. Save Resources to Favourites");
        System.out.println("7. View Saved Resources");
        System.out.println("8. Submit Experience");
        System.out.println("9. Report Resource");
        System.out.println("10. Logout");
        System.out.println("Choose option: ");

        int choice = getIntInput();
        switch (choice) {
            case 1:
                viewAllResources();
                break;
            case 2:
                viewAwarenessResources();
                break;
            case 3:
                viewResearchResources();
                break;
            case 4:
                searchResources();
                break;
            case 5:
                filterResourcesByCategory();
                break;
            case 6:
                saveResource();
                break;
            case 7:
                viewSavedResources();
                break;
            case 8:
                submitExperience();
                break;
            case 9:
                reportResource();
                break;
            case 10:
                logout();
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    private static void showAdminDashboard() {
        System.out.println("\n=== ADMIN DASHBOARD ===");
        System.out.println("Welcome Admin, " + currentUser.getName() + "!");

        System.out.println("1. View All Resources");
        System.out.println("2. Add Awareness Resources");
        System.out.println("3. Add Research Resources");
        System.out.println("4. Edit Resources");
        System.out.println("5. Delete Resources");
        System.out.println("6. View Pending Experiences");
        System.out.println("7. Approve Experience");
        System.out.println("8. Reject Experience");
        System.out.println("9. View All Users");
        System.out.println("10. Make User Admin");
        System.out.println("11. View Reports");
        System.out.println("12. Logout");
        System.out.println("Choose option: ");

        int choice = getIntInput();
        switch (choice) {
            case 1:
                viewAllResources();
                break;
            case 2:
                addAwarenessResource();
                break;
            case 3:
                addResearchResource();
                break;
            case 4:
                editResource();
                break;
            case 5:
                deleteResource();
                break;
            case 6:
                viewPendingExperience();
                break;
            case 7:
                approveExperience();
                break;
            case 8:
                rejectExperience();
                break;
            case 9:
                viewAllUsers();
                break;
            case 10:
                makeUserAdmin();
                break;
            case 11:
                viewReports();
                break;
            case 12:
                logout();
                break;
            default:
                System.out.println("Invalid option");
        }
    }

    //user management
    private static void register() {
        System.out.println("\n===Register New Account===");
        System.out.println("Enter name : ");
        String name = scanner.nextLine();
        System.out.println("Enter email : ");
        String email = scanner.nextLine();
        System.out.println("Enter password : ");
        String password = scanner.nextLine();

        List<User> users = loadUsers();

        boolean emailExists = users.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
        if (emailExists) {
            System.out.println("Email already registered!");
            return;
        }

        PublicUser newUser = new PublicUser(name, email, password, "public_user", "active", "public");
        saveUser(newUser);
        System.out.println("Registration successfull! Please Login.");
    }

    private static void login() {
        System.out.println("\n===Login===");
        System.out.println("Enter email : ");
        String email = scanner.nextLine();
        System.out.println("Enter password : ");
        String password = scanner.nextLine();

        List<User> users = loadUsers();

        Optional<User> userOpt = users.stream().filter(u -> u.getEmail().equalsIgnoreCase(email) && u.getPassword().equals(password)).findFirst();

        if (userOpt.isPresent()) {
            currentUser = userOpt.get();
            System.out.println("Login successful! Welcome " + currentUser.getName());
            System.out.println("Role : " + currentUser.getRole());
        } else {
            System.out.println("Invalid email or password !");
        }
    }

    private static void logout() {
        currentUser = null;
        System.out.println("Logged out successfully !");
    }

    private static void viewAllUsers() {
        if (!currentUser.getRole().equals("admin")) {
            System.out.println("Access denied ! Admin only.");
            return;
        }

        List<User> users = loadUsers();
        if (users.isEmpty()){
            System.out.println("\n=== All Users ===");
            System.out.println("No users found!");
            return;
        }

        System.out.println("\n=== All Users ===");
        for (User user : users){
            System.out.println("User ID: "+user.getUserId());
            System.out.println("Name: "+user.getName());
            System.out.println("Email: "+user.getEmail());
            System.out.println("Role: "+user.getRole());
            System.out.println("Status: "+user.getStatus());
            System.out.println();
        }
        System.out.println("Total users: "+users.size());
    }

    private static void makeUserAdmin() {
        if (!currentUser.getRole().equals("admin")) {
            System.out.println("Access denied ! Admin only.");
            return;
        }

        System.out.println("Enter email of user to make admin: ");
        String email = scanner.nextLine();

        List<User> users = loadUsers();
        boolean found = false;

        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                user.setRole("admin");
                found = true;
                break;
            }
        }
        if (found) {
            saveAllUsers(users);
            System.out.println("User promoted to admin successfully !");
        } else {
            System.out.println("User not found !");
        }
    }

    //resource management
    private static void viewAllResources() {
        System.out.println("\n=== AWARENESS RESOURCES ===");
        List<AwarenessResource> awarenessResources = loadAwarenessResources();
        for (AwarenessResource r : awarenessResources) {
            if (r.getStatus().equals("approved")) {
                System.out.println(r);
                System.out.println("---");
            }
        }
        System.out.println("\n=== RESEARCH RESOURCES ===");
        List<ResearchResource> researchResources = loadResearchResources();
        for (ResearchResource r : researchResources) {
            if (r.getStatus().equals("approved")) {
                System.out.println(r);
                System.out.println("---");
            }
        }
    }

    private static void viewAwarenessResources() {
        List<AwarenessResource> resources = loadAwarenessResources();
        System.out.println("\n=== Awareness Resources ===");
        for (AwarenessResource r : resources) {
            if (r.getStatus().equals("approved")) {
                r.displayInfo();
                System.out.println("---");
            }
        }
    }

    private static void viewResearchResources() {
        List<ResearchResource> resources = loadResearchResources();
        System.out.println("\n=== Research Resources ===");
        for (ResearchResource r : resources) {
            if (r.getStatus().equals("approved")) {
                r.displayInfo();
                System.out.println("---");
            }
        }
    }

    private static void searchResources() {
        System.out.println("Enter search keyword: ");
        String keyword = scanner.nextLine().toLowerCase();

        List<AwarenessResource> awarenessResources = loadAwarenessResources();
        List<ResearchResource> researchResources = loadResearchResources();
        System.out.println("\n=== Search Results ===");

        for (AwarenessResource r : awarenessResources) {
            if (r.getTitle().toLowerCase().contains(keyword) && r.getStatus().equals("approved")) {
                System.out.println(r);
            }
        }

        for (ResearchResource r : researchResources) {
            if (r.getTitle().toLowerCase().contains(keyword) && r.getStatus().equals("approved")) {
                System.out.println(r);
            }
        }
    }

    private static void addAwarenessResource() {
        if (!currentUser.getRole().equals("admin")) {
            System.out.println("Access denied! Admin only.");
            return;
        }
        System.out.println("\n=== Add Awareness Resource ===");
        System.out.print("Enter title : ");
        String title = scanner.nextLine();
        System.out.print("Enter description : ");
        String description = scanner.nextLine();
        System.out.print("Enter source link : ");
        String link = scanner.nextLine();
        System.out.print("Enter category (Safety/Community/Emergency/Planning/Eduvation/Legal): ");
        String category = scanner.nextLine();
        System.out.print("Enter type (Article/Video/Guide): ");
        String type = scanner.nextLine();
        System.out.println("Enter target audience: ");
        String audience = scanner.nextLine();
        System.out.println("Enter prevention tips : ");
        String tips = scanner.nextLine();
        System.out.println("Enter emergency contacts : ");
        String contacts = scanner.nextLine();

        AwarenessResource newResource = new AwarenessResource(title, description, link, category, type, currentUser.getUserId(), "approved", audience, tips, contacts, false);
        saveAwarenessResource(newResource);
        System.out.println("Awareness resource added successfully!");
    }

    private static void addResearchResource() {
        if (!currentUser.getRole().equals("admin")) {
            System.out.println("Access denied! Admin only.");
            return;
        }
        System.out.println("\n=== Add Research Resource ===");
        System.out.print("Enter title : ");
        String title = scanner.nextLine();
        System.out.print("Enter description : ");
        String description = scanner.nextLine();
        System.out.print("Enter source link : ");
        String link = scanner.nextLine();
        System.out.print("Enter category (Research/Study/Report): ");
        String category = scanner.nextLine();
        System.out.print("Enter type (Journal Article/Research Paper/Report): ");
        String type = scanner.nextLine();
        System.out.println("Enter authors: ");
        String authors = scanner.nextLine();
        System.out.println("Enter publication date : ");
        String pubDate = scanner.nextLine();
        System.out.println("Enter journal name : ");
        String journal = scanner.nextLine();

        ResearchResource newResource = new ResearchResource(title, description, link, category, type, currentUser.getUserId(), "approved", authors, pubDate, journal);
        saveResearchResource(newResource);
        System.out.println("Research resource added successfully!");
    }

    private static void editResource() {
        if (!currentUser.getRole().equals("admin")) {
            System.out.println("Access denied! Admin only.");
            return;
        }

        System.out.println("Enter resource type(1-Awareness, 2-Research): ");
        int resourceType = getIntInput();
        System.out.println("Enter resource ID: ");
        int id = getIntInput();

        if (resourceType == 1) {
            List<AwarenessResource> resources = loadAwarenessResources();
            for (AwarenessResource r : resources) {
                if (r.getResourceId() == id) {
                    System.out.println("Enter new title (current: " + r.getTitle() + "): ");
                    String title = scanner.nextLine();
                    if (!title.isEmpty()) r.setTitle(title);
                    System.out.println("Enter new Description: ");
                    String desc = scanner.nextLine();
                    if (!desc.isEmpty()) r.setDescription(desc);
                    saveAllAwarenessResources(resources);
                    System.out.println("Resource updated !");
                    return;
                }
            }
        } else if (resourceType == 2) {
            List<ResearchResource> resources = loadResearchResources();
            for (ResearchResource r : resources) {
                if (r.getResourceId() == id) {
                    System.out.println("Enter new title (current: " + r.getTitle() + "): ");
                    String title = scanner.nextLine();
                    if (!title.isEmpty()) r.setTitle(title);
                    System.out.println("Enter new description: ");
                    String desc = scanner.nextLine();
                    if (!desc.isEmpty()) r.setDescription(desc);
                    saveAllResearchResources(resources);
                    System.out.println("Resource updated !");
                    return;
                }
            }
        }
        System.out.println("Resource not found!");
    }

    private static void deleteResource() {
        if (!currentUser.getRole().equals("admin")) {
            System.out.println("Access denied! Admin only.");
            return;
        }
        System.out.println("Enter resources type (1-Awareness, 2-Research): ");
        int resourceType = getIntInput();
        System.out.println("Enter resources ID to delete: ");
        int id = getIntInput();

        if (resourceType == 1) {
            List<AwarenessResource> resources = loadAwarenessResources();
            boolean removed = resources.removeIf(r -> r.getResourceId() == id);
            if (removed) {
                saveAllAwarenessResources(resources);
                System.out.println("Resource deleted!");
            } else {
                System.out.println("Resources not found!");
            }
        } else if ( resourceType==2){
            List<ResearchResource> resources = loadResearchResources();
            boolean removed = resources.removeIf(r -> r.getResourceId() == id);

            if (removed) {
                saveAllResearchResources(resources);
                System.out.println("Resources deleted!");
            }else {
                System.out.println("Resource not found!");
            }
        }
    }

    private static void saveResource() {
        if (currentUser == null) {
            System.out.println("Please login to save resources.");
            return;
        }
        System.out.println("Enter resource type (1-Awareness, 2-Research): ");
        int resourceType = getIntInput();
        System.out.println("Enter resource ID to save: ");
        int id = getIntInput();
        SavedResource saved = new SavedResource(currentUser.getUserId(), id, "Saved on " + LocalDateTime.now());
        try (FileWriter fw = new FileWriter(SAVED_RESOURCES_FILE, true); PrintWriter pw = new PrintWriter(fw)) {
            pw.println(saved.toFileString());
            System.out.println("Resource saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving resources: " + e.getMessage());
        }
    }

    private static void viewSavedResources() {
        if (currentUser == null) {
            System.out.println("Please login to view saved resources.");
            return;
        }
        List<SavedResource> savedResources = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(SAVED_RESOURCES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                SavedResource sr = SavedResource.fromFileString(line);
                if (sr != null && sr.getUserId() == currentUser.getUserId()) {
                    savedResources.add(sr);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading saved resources: " + e.getMessage());
        }

        if (savedResources.isEmpty()) {
            System.out.println("No saved resources found.");
        } else {
            System.out.println("\n=== Your Saved Resources ===");
            for (SavedResource sr : savedResources) {
                System.out.println(sr);
            }
        }
    }

    //experience management
    private static void submitExperience() {
        if (currentUser == null) {
            System.out.println("Please login to submit an experience.");
            return;
        }
        System.out.println("\n=== Submit Your Experience ===");

        System.out.print("Enter title: ");
        String title = scanner.nextLine();

        System.out.print("Describe your experience: ");
        String description = scanner.nextLine();

        System.out.print("Post anonymously? (true/false): ");
        boolean anonymous = scanner.nextBoolean();
        scanner.nextLine();  // for new line


        Experience exp = new Experience(currentUser.getUserId(), title, description,"pending", anonymous,LocalDateTime.now(),null,null);
        try (PrintWriter pw = new PrintWriter(new FileWriter(EXPERIENCES_FILE, true))) {
            pw.println(exp.toFileString());
            System.out.println("Experience submitted successfully! Waiting for admin moderation.");
        } catch (IOException e) {
            System.out.println("Error saving experience: " + e.getMessage());
        }
    }
    /*public static int generateExperienceId(){
        List<Experience> experiences = loadExperiences();
        int maxId = 0;

        for (Experience e:experiences){
            if (e.getExperienceId() > maxId){
                maxId = e.getExperienceId();
            }
        }
        return maxId+1;
    }*/

    private static void viewPendingExperience() {
        if (!currentUser.getRole().equals("admin")){
            System.out.println("Access denied! Admin only");
            return;
        }
        List<Experience> experiences = loadExperiences();

        //filter only pending experiences
        List<Experience> pendingExperiences = new ArrayList<>();
        for (Experience e :experiences){
            if ("pending".equalsIgnoreCase(e.getStatus())){
                pendingExperiences.add(e);
            }
        }

        if (pendingExperiences.isEmpty()){
            System.out.println("No pending experiences!");
            return;
        }
        System.out.println("\n===Pending Experiences ===");
        System.out.println("Total pending experiences: "+pendingExperiences.size());
        System.out.println();

        for (Experience e :pendingExperiences){
            //get user details for display
            User submitter = findUserById(e.getUserId());

            System.out.println("Experience ID: "+e.getExperienceId());
            System.out.println("Title: "+e.getTitle());
            System.out.println("Description: "+e.getDescription());
            if (e.isAnonymous()){
                System.out.println("Submitted by: ANONYMOUS");
            }else {
                System.out.println("Submitted by: "+(submitter != null ? submitter.getName() : "Unknown"));
            }
            System.out.println("Date: "+(e.getSubmissionDate() != null ? e.getSubmissionDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : "N/A"));
            System.out.println("Status: "+e.getStatus());

        }
    }

    /*private static void displayExperienceDetails(Experience e){
        //get user details
        User submitter = findUserById(e.getUserId());
        System.out.println("EXPERIENCE #"+String.format("%-4d",e.getExperienceId()));

        //show submitter info
        if (e.isAnonymous()){
            System.out.println("Submitted by : ANONYMOUS");
        }else {
            String userName = (submitter != null)? submitter.getName() : "Unknown User";
            System.out.println("Submitted by: "+String.format("%-47s",userName));
            System.out.println("Email: "+String.format("%-47s",submitter != null ? submitter.getEmail() : "N/A"));
        }

        //title
        System.out.println("Title: "+e.getTitle());
        //description
        System.out.println("Description: "+e.getDescription());
        //submission date
        System.out.println("Submitted: "+e.getSubmissionDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        //status
        System.out.println("Status: "+e.getStatus());
    }*/

    private static void approveExperience() {
        if (!currentUser.getRole().equals("admin")){
            System.out.println("Access denied! Admin only");
            return;
        }
        System.out.println("Enter experience ID to approve: ");
        int id = getIntInput();

        List<Experience> experiences = loadExperiences();
        boolean found = false;

        for (int i=0; i< experiences.size(); i++) {
            Experience e = experiences.get(i);
            if (e.getExperienceId() == id && e.getStatus().equals("pending")) {
                e.approve(currentUser.getUserId());
                found = true;
                break;
            }
        }
        if (found) {
            saveAllExperiences(experiences);
            System.out.println("Experience approved!");
        } else {
            System.out.println("Experience not found or already moderated!");
        }
    }

    private static void rejectExperience() {
        if (!currentUser.getRole().equals("admin")){
            System.out.println("Access denied! Admin only.");
            return;
        }
        System.out.println("Enter experience ID to reject: ");
        int id = getIntInput();

        List<Experience> experiences = loadExperiences();
        boolean found = false;

        for (int i=0; i< experiences.size(); i++) {
            Experience e= experiences.get(i);
            if (e.getExperienceId() == id && e.getStatus().equals("pending")) {
                e.reject(currentUser.getUserId());
                found = true;
                break;
            }
        }
        if (found) {
            saveAllExperiences(experiences);
            System.out.println("Experience rejected!");
        } else {
            System.out.println("Experience not found or already moderated!");
        }
    }

    //report management
    private static void reportResource(){
        if (currentUser == null){
            System.out.println("Please login to submit an experience.");
            return;
        }
        System.out.println("Enter resource ID to report: ");
        int resourceId = getIntInput();
        System.out.println("Reason for reporting: ");
        String reason = scanner.nextLine();
        System.out.println("Additional details: ");
        String details = scanner.nextLine();

        Report report = new Report(currentUser.getUserId(),resourceId,reason,details);
        try (FileWriter fw = new FileWriter(REPORTS_FILE, true); PrintWriter pw = new PrintWriter(fw)) {
            pw.println(report.toFileString());
            System.out.println("Report submitted successfully! Waiting for admin moderation.");
        } catch (IOException e) {
            System.out.println("Error saving report: " + e.getMessage());
        }
    }

    private static void viewReports(){
        if (!currentUser.getRole().equals("admin")){
            System.out.println("Access denied! Admin only.");
            return;
        }
        List<Report> reports = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(REPORTS_FILE))){
            String  line;
            while ((line = reader.readLine())!= null){
                Report report = Report.fromFileString(line);
                if(report != null){
                    reports.add(report);
                }
            }
        }catch (IOException e){
            System.out.println("Error loading reports: "+e.getMessage());
        }

        if (reports.isEmpty()){
            System.out.println("No reports found.");
        }else{
            System.out.println("\n=== All Reports ===");
            for (Report r: reports){
                r.displayInfo();
                System.out.println("---");
            }
        }
    }

    //file handling methods
    private static List<User> loadUsers(){
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))){
            String line;
            int lineCount = 0;
            while ((line = reader.readLine()) != null){
                lineCount ++;
                String[] parts = line.split("\\|");
                if (parts.length >= 6) {
                    int userId = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String email = parts[2];
                    String password = parts[3];
                    String role = parts[4];
                    String status = parts[5];

                    if (role.equals("admin")) {
                        users.add(new Admin(userId, name, email, password, role, status, userId));
                    } else {
                        users.add(new PublicUser(userId, name, email, password, role, status, "public"));
                    }
                }

            }
        }catch (IOException e){
            System.out.println("Error loading users: "+e.getMessage());
        }
        return users;
    }
    private static void saveUser(User user){
        try (FileWriter fw = new FileWriter(USERS_FILE, true);PrintWriter pw = new PrintWriter(fw)){
            pw.println(user.toFileString());
        }catch (IOException e){
            System.out.println("Error saving user: "+e.getMessage());
        }
    }
    private static void saveAllUsers(List<User> users){
        try( PrintWriter pw = new PrintWriter(new FileWriter(USERS_FILE))){
            for (User user : users){
                pw.println(user.toFileString());
            }
        }catch (IOException e){
            System.out.println("Error saving users: "+e.getMessage());
        }
    }
    private static User findUserById(int userId){
        List<User> users = loadUsers();
        for (User user : users){
            if (user.getUserId()== userId){
                return user;
            }
        }
        return null;
    }
    private static List<AwarenessResource> loadAwarenessResources(){
        List<AwarenessResource> resources = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(AWARENESS_RESOURCES_FILE))){
            String line;
            while ((line = reader.readLine()) != null){
                AwarenessResource r = AwarenessResource.fromFileString(line);
                if (r != null) resources.add(r);
            }
        }catch (IOException e){
            //file might not exist
        }
        return resources;
    }
    private static void saveAwarenessResource(AwarenessResource resource){
        try (FileWriter fw = new FileWriter(AWARENESS_RESOURCES_FILE, true);
             PrintWriter pw = new PrintWriter(fw)){
            pw.println(resource.toFileString());
        }catch (IOException e){
            System.out.println("Error saving resource: "+e.getMessage());
        }
    }
    private static void saveAllAwarenessResources(List<AwarenessResource> resources){
        try (PrintWriter pw = new PrintWriter(new FileWriter(AWARENESS_RESOURCES_FILE))){
            for (AwarenessResource r: resources){
                pw.println(r.toFileString());
            }
        }catch (IOException e){
            System.out.println("Error saving resources: "+e.getMessage());
        }
    }
    private static List<ResearchResource> loadResearchResources(){
        List<ResearchResource> resources = new ArrayList<>();
        try ( BufferedReader reader = new BufferedReader(new FileReader(RESEARCH_RESOURCES_FILE))){
            String line;
            while ((line = reader.readLine()) != null){
                ResearchResource r = ResearchResource.fromFileString(line);
                if ( r != null) resources.add(r);
            }
        }catch (IOException e){
            //file might not exist
        }
        return resources;
    }

    private static void saveResearchResource(ResearchResource resource){
        try (FileWriter fw = new FileWriter(RESEARCH_RESOURCES_FILE,true);PrintWriter pw = new PrintWriter(fw)){
            pw.println(resource.toFileString());
        }catch (IOException e){
            System.out.println("Error saving resources: "+e.getMessage());
        }
    }

    private static void saveAllResearchResources(List<ResearchResource> resources){
        try (PrintWriter pw = new PrintWriter(new FileWriter(RESEARCH_RESOURCES_FILE))){
            for (ResearchResource r: resources){
                pw.println(r.toFileString());
            }
        }catch (IOException e){
            System.out.println("Error saving resources: "+e.getMessage());
        }
    }

    private static List<Experience> loadExperiences(){
        List<Experience> experiences = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(EXPERIENCES_FILE))){
            String line;
            while ((line = reader.readLine()) != null){
                Experience e = Experience.fromFileString(line);
                if (e != null) experiences.add(e);
            }
        }catch (IOException e){
            //file might not exist
        }
        return experiences;
    }

    private static void saveAllExperiences(List<Experience> experiences){
        try (PrintWriter pw = new PrintWriter(new FileWriter(EXPERIENCES_FILE))){
            for (Experience e: experiences){
                pw.println(e.toFileString());
            }
        }catch (IOException e){
            System.out.println("Error saving experiences: "+e.getMessage());
        }
    }

    private static  int getIntInput(){
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            }catch (NumberFormatException e){
                System.out.println("Please enter a valid number: ");
            }
        }
    }

    private static void filterResourcesByCategory(){
        System.out.println("\n=== Filter Resources by Category ===");
        System.out.println("Available Categories:");
        System.out.println("1. Safety");
        System.out.println("2. Community");
        System.out.println("3. Emergency");
        System.out.println("4. Planning");
        System.out.println("5. Education");
        System.out.println("6. Legal");
        System.out.println("7. Research");
        System.out.println("Choose category: ");

        int categoryChoice = getIntInput();
        String category = "";

        switch (categoryChoice){
            case 1: category = "Safety";break;
            case 2: category = "Community";break;
            case 3: category = "Emergency"; break;
            case 4: category = "Planning";break;
            case 5: category = "Education"; break;
            case 6: category = "Legal";break;
            case 7: category = "Research";break;
            default:
                System.out.println("Invalid category");
                return;
        }
        System.out.println("\n=== "+category +" Resources ===");

        //filter awareness resources
        List<AwarenessResource> awarenessResources = loadAwarenessResources();
        boolean found = false;

        for (AwarenessResource r : awarenessResources){
            if (r.getCategory().equalsIgnoreCase(category) && r.getStatus().equals("approved")){
                r.displayInfo();
                System.out.println("---");
                found = true;
            }
        }

        //filter research resources
        List<ResearchResource> researchResources= loadResearchResources();

        for (ResearchResource r : researchResources){
            if (r.getCategory().equalsIgnoreCase(category) && r.getStatus().equals("approved")){
                r.displayInfo();
                System.out.println("---");
                found = true;
            }
        }
        if (!found){
            System.out.println("No resources found in category: "+category);
        }
    }
}

