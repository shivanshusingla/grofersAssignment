## Grofers Assignment

This is **Maven** project based on page-object Model (or **POM**). This whole project is written in **Java** programming language. For test case results, I use **testng**. For Reporting, I use ExtendReports for test cases results.

*I use Maven because it is easy to import a maven project and maven dependencies are easily available on web*

---

##Prerequisites to Run

1. Eclipse or any other IDE which supports java
2. JDK Installation
3. Maven Installation
4. Provide JAVA_HOME path in environment variables
5. Provide M2_HOME path in environment variables for maven
6. Install testng in eclipse
7. GIT

---

## How To Run through Maven

1. Clone from Repository [Clone a repository](https://github.com/shivanshusingla/grofersAssignment) in local directory.
2. Open eclipse
3. Navigate to File > import.
4. After 3rd step > Maven > Existing Maven Projects > Provide path of Root Directory(POM.xml must be checked) > Finish.
5. After importing the maven project, expand and right click on **pom.xml** then click on Run As > Maven clean
6. Wait for some time.
7. After Build Success message in console, right click on **pom.xml** again then click on Run As > Maven install.
8. Project starts running

---

## How To Run through TestNG

1. Import maven project as like in above steps
2. After importing the maven project, expand and right click on **cucumber-testng.xml** then click on Run As > TestNG Suite
3. Project starts running

---

*Note - First time the project should be run through testng.xml to generate test reports & Java version must be 1.8 to run this project*


