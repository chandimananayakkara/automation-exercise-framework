# 🛒 AutomationExercise — E-Commerce Test Automation Framework

![Java](https://img.shields.io/badge/Java-17-orange)
![Selenium](https://img.shields.io/badge/Selenium-4.27.0-green)
![TestNG](https://img.shields.io/badge/TestNG-7.10.2-red)
![Maven](https://img.shields.io/badge/Maven-Build-blue)
![CI/CD](https://img.shields.io/badge/CI%2FCD-GitHub%20Actions-yellow)

> Industry-standard Selenium WebDriver test automation framework for
> [automationexercise.com](https://automationexercise.com) — an e-commerce
> clothing store. Built with **Page Object Model (POM)**, **Data-Driven
> Testing**, **ExtentReports**, and **CI/CD integration**.

---

## 🏗️ Architecture

```
                        ┌─────────────────────────────────────────────┐ 
                        │              testng.xml (Suite)             │ 
                        ├─────────────────────────────────────────────┤
                        │            Test Classes (tests/)            │
                        │    LoginTest │ SignupTest │ E2ECheckoutTest │
                        ├─────────────────────────────────────────────┤
                        │            Page Objects (pages/)            │
                        │  HomePage │ LoginPage │ ProductsPage │ ...  │
                        ├─────────────────────────────────────────────┤
                        │              Base Layer (base/)             │
                        │    BasePage │ DriverFactory │ BaseTest      │
                        ├─────────────────────────────────────────────┤
                        │            Utilities (utils/)               │
                        │  ExcelReader │ Screenshot │ Reports │ Waits │
                        ├─────────────────────────────────────────────┤
                        │          Configuration (config/)            │
                        │     config.properties │ ConfigReader        │
                        └─────────────────────────────────────────────┘
```


## 🛠️ Tech Stack

| Technology | Purpose |
|-----------|---------|
| Java 17 | Programming Language |
| Selenium WebDriver 4.27 | Browser Automation |
| TestNG 7.10 | Test Framework |
| Maven | Build & Dependency Management |
| Page Object Model | Design Pattern |
| ExtentReports 5 | HTML Test Reporting |
| Log4j2 | Logging |
| Apache POI | Excel Data Reading |
| GitHub Actions | CI/CD Pipeline |

## 🚀 How to Run

### Prerequisites
- Java JDK 17+
- Maven 3.9+
- Chrome Browser (latest)

### Run All Tests
```bash
mvn clean test
```

### Run Specific Test Suite
```bash
mvn clean test -Dsurefire.suiteXmlFiles=testng.xml
```

### Run in Headless Mode
```terminaloutput
mvn clean test -Dheadless=true
```

### Run Specific Test Class
```bash
mvn clean test -Dtest=LoginTest
```

### 📊 Test Reports
After execution, HTML reports are generated in reports/ directory. Open the .html file in any browser to view detailed results.

## 📁 Project Structure

```terminaloutput
automation-exercise-framework/
├── src/main/java/com/automationexercise/
│   ├── base/          # BasePage, DriverFactory
│   ├── pages/         # Page Object classes
│   ├── utils/         # Utilities (Excel, Screenshot, Reports)
│   └── config/        # Configuration reader
├── src/test/java/com/automationexercise/
│   └── tests/         # Test classes
├── src/main/resources/
│   ├── config.properties
│   └── log4j2.xml
├── .github/workflows/ # CI/CD pipeline
├── testng.xml         # Test suite configuration
└── pom.xml            # Maven dependencies
```

### ✅ Test Cases Covered


|#|	Test Case|	Type|
|:---|:---|:---|
|1	|Valid Login	|Smoke|
|2	|Invalid Login|	Regression|
|3	|Empty Fields Login|	Regression|
|4	|Logout	|Regression|
|5	|Register New User	|Smoke|
|6	|Register Existing Email	|Regression|
|7	|All Products Page	|Regression|
|8	|Product Detail Page	|Regression|
|9	|Search Product	|Regression|
|10	|Add Products to Cart	|Regression|
|11	|E2E: Register & Checkout	|E2E|
|12	|E2E: Login & Checkout	|E2E|
|13	|Contact Us Form	|Regression|
|14	|Subscription	|Regression|
|15	|Scroll Up/Down	|Regression|

### 🔧 Design Patterns Used

* Page Object Model (POM) — Each page = separate class
* Factory Pattern — DriverFactory for browser creation
* Singleton Pattern — ConfigReader loads properties once
* Builder Pattern — Method chaining in page objects

### 👤 Author
Chandima Nanayakkara — QA Automation Engineer

GitHub: [@chandimananayakkara] <br>
LinkedIn: [https://github.com/chandimananayakkara]