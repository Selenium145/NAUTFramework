To push an existing repository from the command line
----------------------------------------------------
git remote add origin https://github.com/GitTestUser2021/NAUTFramework.git
git branch -M main
git push -u origin main

********************************
Pre-requisites
********************************
1.Install java jdk 11 or above version and configure JAVA_HOME environment variable
2.Install maven and configure MAVEN_HOME environment variable

********************************
Steps to run
********************************
1.Clone the project from NAUT Automation GIT Respository
2.Checkout Master branch
3.Edit 'RunMode' column in Standard test data sheet and updated preferred Tag name in CucumberRunner class  
4.Run the execution with the following command:>mvn test -PTestNG -Dbrowsers=<Browserser or API or Mobile> -DthreadCount=<Number of browser Type> -DthreadCountDP=<Number of browser Instance>
5.HTML Extent report will be generated under:<project folder>NAUTFramework\target\Spark\ExtentSpark.html
6.PDF Extent report will be generated under:<project folder>NAUTFramework\target\PdfReport\ExtentPdf.pdf
7.BDD Cucumber report report will be generated under:<project folder>NAUTFramework\target\cucumber-report-html

********************************
Cucumber Execution Commands
********************************
Sequential Execution:
---------------------
UI:
Syntax:
mvn test -PTestNG -Dbrowsers=<browserName> -DthreadCount=<Default=1(NA for Sequential execution)> -DthreadCountDP=Default=1(NA for Sequential execution)

Example:
mvn test -PTestNG -Dbrowsers=chrome -DthreadCount=1 -DthreadCountDP=1

API:
Syntax:
mvn test -PTestNG -Dbrowsers=API -DthreadCount=<Default=1(NA for API)> -DthreadCountDP=Default=1(NA for API)

Example:
mvn test -PTestNG -Dbrowsers=API -DthreadCount=1 -DthreadCountDP=1

UI & API:
Syntax:
mvn test -PTestNG -Dbrowsers=<browserName> -DthreadCount=<Default=1(NA for Sequential execution)> -DthreadCountDP=Default=1(NA for Sequential execution)

Example:
mvn test -PTestNG -Dbrowsers=chrome -DthreadCount=1 -DthreadCountDP=1

UI cross browser:
Syntax:
mvn test -PTestNG -Dbrowsers=<browserName> -DthreadCount=<Default=1(NA for Sequential execution)> -DthreadCountDP=Default=1(NA for Sequential execution)

Example:
mvn test -PTestNG -Dbrowsers=chrome,firefox,edge -DthreadCount=1 -DthreadCountDP=1

*********************************

Parallel Execution:
-------------------
UI:
Syntax:
mvn test -PTestNG -Dbrowsers=<browserName> -DthreadCount=<Type of browser instance needs to run parallel> -DthreadCountDP=<Number of instance needs to for specific browser>

Example:
mvn test -PTestNG -Dbrowsers=Chrome -DthreadCount=1 -DthreadCountDP=3

Note: Above command will open 3 Chrome instance and run selected scrips parallelly

UI:Cross Browser
Syntax:
mvn test -PTestNG -Dbrowsers=<browserName> -DthreadCount=<Type of browser instance needs to run parallel> -DthreadCountDP=<Number of instance needs to for specific browser>

Example:
mvn test -PTestNG -Dbrowsers=chrome,firefox -DthreadCount=2 -DthreadCountDP=2

Note: 
Above command will open 4 instacne parallelly(2 for Chrome & 2 for Firefox) and each take one scenario for execution.
It will generate two test results (1 for Chrome & 1 for Firefox) in single Extent report



**********************************
TestNG Execution Commands:
**********************************

TDD compatibility yet to be added
 

  
