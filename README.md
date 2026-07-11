# SID = 2517660
# Protect Your Glass – Drink Spiking Awareness System
## Project Overview
The Drink Spiking Awareness System is a java-based console application designed to educate the public about drink spiking prevention and provide a platform for sharing experiences. The system combines educational resources, user participation, and administrative control to create a safe and informative environment.

## User roles
### Public user
-	View awareness and research resources
-	Search and filter resources by category
-	Save favorite resources
-	Submit personal experiences (anonymously or publicly)
-	Report inappropriate content

### Administrator
-	All public user capabilities
-	Add, edit, and delete resources
-	Approve or reject user-submitted experiences
-	Manage user roles (promote to admin)
-	View system reports

## Features
### Experience submission
-	Share personal drink spiking experiences
-	Anonymous posting option
-	Admin moderation workflow
-	Consent verification

### File-based storage
All data is stored in plain text files:
-	‘users.txt’ - User accounts
-	‘awareness_resources.txt’ - Awareness content
-	 ‘research_resources.txt’ - Research content
-	‘experiences.txt’ - User experiences
-	‘reports.txt’ - Content reports
-	‘saved_resources.txt’ - User bookmarks

## System requirements
-	Java 11 or higher
-	Command line / Terminal access
-	100MB free disk space

## Project structure
Com/drinkSpike/
├── Main.java  # Main application entry point
├── User.java  # User, PublicUser, Admin classes
├── Resource.java  # Base resource class
├── AwarenessResource.java  # Awareness resources
├── ResearchResource.java  # Research resources
├── Experience.java  # Experience management
├── Report.java  # Report handling
├── SavedResources.java  # Saved resources tracking
└── experience.txt  # Data storage files

## Installing and setup
### 1. Create project folder and package structure
DrinkSpikingSystem/
└── com/
      └── drinkSpike/
### 2. Copy all .java files into com/drinkSpike/ folder
### 3. Compile
 
javac  com/drinkSpike/*.java
### 4. Run
java com.drinkSpike.Main

## Default admin account
On first run, the system automatically creates an admin account:
Field	Value
Email	Admin@system.com
Password	admin1234
Role	admin

## How to use
### For public users
1.	Register an account (Option 2 from main menu)
2.	Login with credentials
3.	View resources – Browse awareness and research materials
4.	Search resources – Find specific content by keywords
5.	Filter by category – Narrow down resources for later
6.	Save favorites – Bookmark resources for later
7.	Submit experiences – Share story (pending admin approval)
8.	Report content – Flag inappropriate resources
 ### For administrators
1.	Login with admin credentials
2.	Add resources – Create new awareness or research content
3.	Manage users – Promote regular users to admin
4.	View reports – Review user-reported content

## Sample data
The system includes pre-loaded sample resources:
### Awareness resources
-	Complete guide to drink spiking prevention
-	Community action against drink spiking
### Research resources
-	Prevalence and patterns of drink spiking in urban nightlife

>**NOTE: These are sample resources for testing. Resources can be moderated through Admin dashboard.

## File formats
### users.txt format
userId | name | email | password | role | status
### experiences.txt format
experienceId | userId | title | description | status | anonymous | submissionDate | moderatedBy | moderationDate
### awareness_resource.txt
resourced | title | description | sourceLink | category | type | addedBy | addedDate | status | targetAudience |  prventionTips | emergencyContacts | isFeatured

## Menu navigation
## Min menu
1.	Login
2.	Register
3.	View resources
4.	Exit

## Public dashboard
1.	View All Resources
2.	View Awarness Resources
3.	View Research Resources
4.	Search Resources
5.	Filter Resources By Category
6.	Save Resources To Favorites
7.	View Saved Resources
8.	Submit Experience
9.	Report Experience
10.	Logout

## Admin dashboard
1.	View All Resources
2.	Add Awareness Resources
3.	Add Research Resources
4.	Edit Resources
5.	Delete Resources
6.	View Pending Experiences
7.	Approve Experiences
8.	Reject Experiences
9.	View All Users
10.	Make User Admin
11.	View Reports
12.	Logout

## Troubleshooting
### Common issues
Issue	Solution
“Email already registered” 	Use a different email address
“Access denied! Admin only”	Login with admin credentials
Experiences not showing	Check experiences.txt file format
Date parsing errors	Ensure date format: yyyy-MM-dd HH:mm:ss

### File location
Data files are created in the same directory as the application. If files are not being created, check write permissions.

## Class documentation
Class	Description
User	Base user class with login/registration
PublicUser	Regular user with privacy setting
Admin	Administrator with moderation privileges
Resource	Base resource class
AwarenessResource	Educational content with prevention tips
ResearchResource	Academic papers and studies
Experience	User-submitted stories
Report	User content reports
SavedResource	User bookmarks

## Security features
•	Password-based authentication
•	Role-based access control
•	Anonymous experience submission
•	Content moderation before publication
•	User reporting system

## Author
Student ID: 2517660
Team: Glass Guard
 
