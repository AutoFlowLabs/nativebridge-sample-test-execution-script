# NativeBridge.io - Sample Test Execution Scripts

This repository contains comprehensive sample test execution scripts for [NativeBridge.io](https://nativebridge.io), demonstrating various automation frameworks and approaches for mobile application testing.

## 📁 Repository Structure

```
nativebridge-sample-test-execution-scripts/
├── appium/
│   ├── java/
│   │   ├── v1/          # Appium 1.x compatible scripts
│   │   └── v2/          # Appium 2.x compatible scripts
│   └── python/          # Python-based Appium scripts
│       ├── v1/          # Appium 1.x compatible scripts
│       └── v2/          # Appium 2.x compatible scripts
└── maestro/             # Maestro YAML test scripts
    └── *.yaml           # Direct YAML test scripts (no language/framework segregation)
```

## 🚀 Overview

This repository is organized to showcase different automation approaches across multiple dimensions:

- **Automation Tools**: Appium (Java & Python) and Maestro
- **Appium Versions**: v1.x and v2.x compatibility
- **Testing Frameworks**: Multiple framework implementations per language
- **Test Applications**: Multiple sample apps (TestApp1, TestApp2, TestApp3)

---

## 📱 Appium Test Scripts

### Java Implementation

The Java Appium scripts are organized by Appium version and demonstrate different testing frameworks:

#### 🔧 Appium v1 (`appium/java/v1/`)

Compatible with Appium 1.22.3 + Java Client 8.3.0

**Available Scripts:**
- `TestApp1.java` - Plain Java implementation for TestApp1
- `TestApp2.java` - Plain Java implementation for TestApp2  
- `TestApp3.java` - Plain Java implementation for TestApp3

**Framework:** Bare-bone Java (no testing framework)
- Direct main method execution
- Basic Appium WebDriver setup
- Manual assertions and test flow control

#### 🆕 Appium v2 (`appium/java/v2/`)

Compatible with Appium 2.x + Java Client 9.x

**Available Scripts:**

| Test App | Plain Java | JUnit 5 | TestNG |
|----------|------------|---------|--------|
| TestApp1 | `TestApp1.java` | `TestApp1JUnit.java` | `TestApp1TestNG.java` |
| TestApp2 | `TestApp2.java` | `TestApp2JUnit.java` | `TestApp2TestNG.java` |

**Frameworks Available:**

1. **Plain Java** - Bare-bone implementation
   - Direct main method execution
   - UiAutomator2Options configuration
   - AppiumBy locator strategies

2. **JUnit 5** - Modern JUnit framework
   - `@Test`, `@BeforeAll`, `@AfterAll` annotations
   - Test lifecycle management
   - Assertions with JUnit assertions

3. **TestNG** - Comprehensive testing framework
   - `@Test`, `@BeforeClass`, `@AfterClass` annotations
   - Advanced test configuration
   - Parallel execution support

### Python Implementation

#### 🔧 Appium v1 (`appium/python/v1/`)

Compatible with Appium 1.22.3 + Python Client

**Available Frameworks:**
- **Plain Python** - Basic Python implementation
- **Pytest** - Modern Python testing framework
- **Unittest** - Standard Python testing framework

#### 🆕 Appium v2 (`appium/python/v2/`)

Compatible with Appium 2.x + Python Client

**Available Frameworks:**
- **Plain Python** - Basic Python implementation
- **Pytest** - Modern Python testing framework  
- **Unittest** - Standard Python testing framework

---

## 🎭 Maestro Test Scripts

The `maestro/` directory contains YAML-based test scripts for the Maestro testing framework.

**Key Features:**
- **No Language Segregation** - Direct YAML scripts
- **No Framework Variants** - Single YAML approach per test
- **Simple Structure** - Straightforward test definitions
- **Cross-Platform** - Works on iOS and Android

**Script Organization:**
```
maestro/
├── TestApp1.yaml
├── TestApp2.yaml
├── TestApp3.yaml
└── ...
```

**Example Structure:**
```yaml
appId: com.example.testapp1
---
- launchApp
- tapOn: "Login Button"
- inputText: "username@example.com"
- assertVisible: "Welcome Screen"
```

---

## 🏃‍♂️ Running the Tests

### Prerequisites

#### For Appium Tests:
- **Appium Server** (v1.22.3 or v2.x depending on script version)
- **Android SDK** / **iOS Development Tools**
- **Java 8+** (for Java scripts) or **Python 3.7+** (for Python scripts)
- **Device/Emulator** configured and accessible

#### For Maestro Tests:
- **Maestro CLI** installed
- **Device/Emulator** configured and accessible

### Execution Examples

#### Java - Plain Implementation
```bash
# Compile and run (Appium v1)
cd appium/java/v1/
javac -cp "path/to/appium-java-client.jar:path/to/selenium.jar" TestApp1.java
java -cp ".:path/to/appium-java-client.jar:path/to/selenium.jar" TestApp1
```

#### Java - JUnit 5
```bash
# Run with Maven/Gradle (Appium v2)
cd appium/java/v2/
mvn test -Dtest=TestApp1JUnit
```

#### Java - TestNG
```bash
# Run with Maven/Gradle (Appium v2)
cd appium/java/v2/
mvn test -Dtest=TestApp1TestNG
```

#### Python - Pytest
```bash
# Run with pytest (Appium v2)
cd appium/python/v2/
pytest TestApp1_pytest.py -v
```

#### Maestro
```bash
# Run Maestro test
cd maestro/
maestro test TestApp1.yaml
```

---

## 🎯 Test Coverage

Each test script covers essential mobile app testing scenarios:

### Common Test Scenarios:
- **App Launch** - Application startup and initialization
- **Element Interaction** - Buttons, text fields, checkboxes
- **Text Input** - Form filling and validation
- **Gestures** - Tap, swipe, scroll, pinch
- **Navigation** - Screen transitions and deep linking
- **Assertions** - UI element validation and state verification

### Test Applications:
- **TestApp1** - Basic UI interaction testing
- **TestApp2** - Form handling and navigation
- **TestApp3** - Advanced gestures and complex workflows

---

## 📋 Framework Comparison

| Aspect | Plain Java/Python | JUnit/Pytest | TestNG/Unittest |
|--------|-------------------|---------------|-----------------|
| **Setup Complexity** | Low | Medium | Medium |
| **Test Organization** | Basic | Good | Excellent |
| **Reporting** | Manual | Built-in | Advanced |
| **Parallel Execution** | Manual | Limited | Full Support |
| **Annotations** | None | Basic | Comprehensive |
| **Best For** | Quick prototyping | Standard testing | Enterprise testing |

---

## 🔧 Configuration

### Appium Capabilities

#### Version 1.x (Legacy)
```java
DesiredCapabilities caps = new DesiredCapabilities();
caps.setCapability("platformName", "Android");
caps.setCapability("deviceName", "emulator-5554");
caps.setCapability("appPackage", "com.example.testapp");
caps.setCapability("appActivity", ".MainActivity");
```

#### Version 2.x (Modern)
```java
UiAutomator2Options options = new UiAutomator2Options()
    .setPlatformName("Android")
    .setDeviceName("emulator-5554")
    .setAppPackage("com.example.testapp")
    .setAppActivity(".MainActivity");
```

### Maestro Configuration
```yaml
appId: com.example.testapp
tags:
  - smoke
  - regression
env:
  USERNAME: test@example.com
  PASSWORD: testpassword
```

---

## 🤝 Contributing

When adding new test scripts:

1. **Follow Naming Convention**: `TestApp[N][Framework].extension`
2. **Maintain Structure**: Preserve the version/framework organization
3. **Add Documentation**: Include inline comments and setup instructions
4. **Test Compatibility**: Ensure scripts work with specified versions
5. **Update README**: Add new scripts to the documentation

---

## 📚 Additional Resources

- [NativeBridge.io Documentation](https://nativebridge.io/docs)
- [Appium Documentation](https://appium.io/docs/)
- [Maestro Documentation](https://maestro.mobile.dev/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [TestNG Documentation](https://testng.org/doc/)

---

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## 🆘 Support

For questions and support:
- **Issues**: Create an issue in this repository
- **Documentation**: Visit [NativeBridge.io](https://nativebridge.io)
- **Community**: Join our testing community discussions

---

*Happy Testing! 🚀*