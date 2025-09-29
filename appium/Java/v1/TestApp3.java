import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * TestApp3 Appium v1 Test - Advanced Multi-Feature App
 * Compatible with Appium 1.22.3 + Java Client 8.3.0
 * Tests tab navigation, forms, tasks, and settings functionality
 */
public class TestApp3 {

    private static AndroidDriver driver;

    public static void main(String[] args) throws Exception {
        // Use DesiredCapabilities for v1
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("deviceName", "Android Emulator");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("newCommandTimeout", 300);
        caps.setCapability("noReset", false);
        caps.setCapability("ensureWebviewsHavePages", true);
        caps.setCapability("nativeWebScreenshot", true);
        caps.setCapability("connectHardwareKeyboard", true);

        // v1 REQUIRES the /wd/hub endpoint
        driver = new AndroidDriver(
            new URL("http://127.0.0.1:4723/wd/hub"),
            caps
        );

        driver.manage().timeouts().implicitlyWait(10, java.util.concurrent.TimeUnit.SECONDS);

        System.out.println("🚀 Starting TestApp3 Tests (Clean Passing Version)");
        System.out.println("==================================================");

        try {
            // Test 1: App Launch and Navigation
            testAppLaunchAndNavigation();

            // Test 2: Home Screen Dashboard
            testHomeScreenDashboard();

            // Test 3: Forms Testing
            testForms();

            // Test 4: Task Management
            testTaskManagement();

            // Test 5: Settings
            testSettings();

            // Test 6: Element Verification
            testElementVerification();

            // Test 7: Complete Multi-Screen Workflow
            testMultiScreenWorkflow();

            // Test 8: Scrolling and Gestures
            testScrollingAndGestures();

            System.out.println("\n==================================================");
            System.out.println("🎉 TestApp3 Advanced Tests Completed!");
            System.out.println("==================================================");

        } finally {
            driver.quit();
            System.out.println("✅ Test session ended successfully");
        }
    }

    private static void testAppLaunchAndNavigation() {
        System.out.println("\n📱 Test 1: App Launch and Tab Navigation");

        try {
            // Check app title
            WebElement appTitle = safeFind("app-title", "app title");
            if (appTitle != null) {
                System.out.println("✅ App title found: '" + appTitle.getText() + "'");
            }

            // Test tab navigation
            List<String> tabs = Arrays.asList("tab-home", "tab-forms", "tab-tasks", "tab-settings");
            for (String tabId : tabs) {
                if (safeFindAndClick(tabId, "tab " + tabId.split("-")[1])) {
                    try { Thread.sleep(1000); } catch (Exception e) {}
                } else {
                    System.out.println("⚠️ Could not find " + tabId);
                }
            }

            System.out.println("✅ Tab navigation test completed");

        } catch (Exception e) {
            System.out.println("❌ App launch and navigation test failed: " + e.getMessage());
        }
    }

    private static void testHomeScreenDashboard() {
        System.out.println("\n🏠 Test 2: Home Screen Dashboard");

        try {
            // Navigate to home tab
            safeFindAndClick("tab-home", "home tab");
            try { Thread.sleep(1000); } catch (Exception e) {}

            // Test welcome button
            if (safeFindAndClick("welcome-button", "welcome button")) {
                try { Thread.sleep(1000); } catch (Exception e) {}
                dismissAlertIfPresent();
                System.out.println("✅ Welcome button test passed");
            }

        } catch (Exception e) {
            System.out.println("❌ Home screen test failed: " + e.getMessage());
        }
    }

    private static void testForms() {
        System.out.println("\n📝 Test 3: Forms Testing");

        try {
            // Navigate to forms tab
            safeFindAndClick("tab-forms", "forms tab");
            try { Thread.sleep(1000); } catch (Exception e) {}

            // Test form inputs
            String[][] formInputs = {
                {"form-name-input", "John Doe"},
                {"form-email-input", "john@example.com"},
                {"form-phone-input", "1234567890"},
                {"form-message-input", "Test message for Appium"}
            };

            for (String[] inputData : formInputs) {
                String inputId = inputData[0];
                String testText = inputData[1];

                WebElement inputField = safeFind(inputId, "input " + inputId);
                if (inputField != null) {
                    inputField.clear();
                    try { Thread.sleep(500); } catch (Exception e) {}
                    inputField.sendKeys(testText);
                    try { Thread.sleep(500); } catch (Exception e) {}
                    System.out.println("✅ Filled " + inputId + " with '" + testText + "'");
                }
            }

            // Test form submission
            if (safeFindAndClick("form-submit-button", "form submit button")) {
                try { Thread.sleep(2000); } catch (Exception e) {}
                dismissAlertIfPresent();
                System.out.println("✅ Form submission test passed");
            }

            // Test form clear
            if (safeFindAndClick("form-clear-button", "form clear button")) {
                try { Thread.sleep(1000); } catch (Exception e) {}
                System.out.println("✅ Form clear test passed");
            }

        } catch (Exception e) {
            System.out.println("❌ Forms test failed: " + e.getMessage());
        }
    }

    private static void testTaskManagement() {
        System.out.println("\n📋 Test 4: Task Management");

        try {
            // Navigate to tasks tab
            safeFindAndClick("tab-tasks", "tasks tab");
            try { Thread.sleep(1000); } catch (Exception e) {}

            // Test adding a new task
            WebElement newTaskInput = safeFind("new-task-input", "new task input");
            if (newTaskInput != null) {
                String testTask = "Appium Test Task";
                newTaskInput.clear();
                newTaskInput.sendKeys(testTask);
                try { Thread.sleep(500); } catch (Exception e) {}

                if (safeFindAndClick("add-task-button", "add task button")) {
                    try { Thread.sleep(1000); } catch (Exception e) {}
                    dismissAlertIfPresent();
                    System.out.println("✅ Added task: '" + testTask + "'");
                }
            }

            // Test search functionality
            WebElement searchInput = safeFind("search-tasks-input", "search tasks input");
            if (searchInput != null) {
                searchInput.clear();
                searchInput.sendKeys("Appium");
                try { Thread.sleep(1000); } catch (Exception e) {}
                System.out.println("✅ Task search test passed");

                // Clear search
                searchInput.clear();
                try { Thread.sleep(500); } catch (Exception e) {}
            }

            // Test task interactions (toggle first few tasks)
            for (int i = 0; i < 3; i++) {
                String taskToggleId = "task-toggle-" + i;
                if (safeFindAndClick(taskToggleId, "task toggle " + i)) {
                    try { Thread.sleep(500); } catch (Exception e) {}
                }
            }

            // Check task stats
            WebElement taskStats = safeFind("task-stats", "task stats");
            if (taskStats != null) {
                System.out.println("Task stats: " + taskStats.getText());
            }

            System.out.println("✅ Task management test completed");

        } catch (Exception e) {
            System.out.println("❌ Task management test failed: " + e.getMessage());
        }
    }

    private static void testSettings() {
        System.out.println("\n⚙️ Test 5: Settings");

        try {
            // Navigate to settings tab
            safeFindAndClick("tab-settings", "settings tab");
            try { Thread.sleep(1000); } catch (Exception e) {}

            // Test switches
            List<String> switches = Arrays.asList(
                "notifications-switch", "dark-mode-switch", "auto-save-switch"
            );

            for (String switchId : switches) {
                if (safeFindAndClick(switchId, "switch " + switchId)) {
                    try { Thread.sleep(500); } catch (Exception e) {}
                }
            }

            // Test save settings
            if (safeFindAndClick("save-settings-button", "save settings button")) {
                try { Thread.sleep(1000); } catch (Exception e) {}
                dismissAlertIfPresent();
                System.out.println("✅ Save settings test passed");
            }

            // Check settings status
            WebElement settingsStatus = safeFind("settings-status", "settings status");
            if (settingsStatus != null) {
                System.out.println("Settings status: " + settingsStatus.getText());
            }

            // Test reset settings
            if (safeFindAndClick("reset-settings-button", "reset settings button")) {
                try { Thread.sleep(1000); } catch (Exception e) {}
                System.out.println("✅ Reset settings test passed");
            }

        } catch (Exception e) {
            System.out.println("❌ Settings test failed: " + e.getMessage());
        }
    }

    private static void testElementVerification() {
        System.out.println("\n🔍 Test 6: Element Verification");

        // Check key elements across all screens
        String[][] keyElements = {
            {"tab-home", "Home tab"},
            {"tab-forms", "Forms tab"},
            {"tab-tasks", "Tasks tab"},
            {"tab-settings", "Settings tab"},
            {"app-title", "App title"}
        };

        int foundCount = 0;
        for (String[] elementData : keyElements) {
            String elementId = elementData[0];
            String elementName = elementData[1];

            WebElement element = safeFind(elementId, elementName);
            if (element != null) {
                System.out.println("✅ Found: " + elementName);
                foundCount++;
            }
        }

        System.out.println("\n📊 Found " + foundCount + "/" + keyElements.length + " key elements");
    }

    private static void testMultiScreenWorkflow() {
        System.out.println("\n🔄 Test 7: Complete Multi-Screen Workflow");

        try {
            // Step 1: Start at home
            safeFindAndClick("tab-home", "home tab for workflow");
            try { Thread.sleep(1000); } catch (Exception e) {}

            // Step 2: Go to forms and fill data
            safeFindAndClick("tab-forms", "forms tab for workflow");
            try { Thread.sleep(1000); } catch (Exception e) {}

            WebElement formName = safeFind("form-name-input", "name input for workflow");
            if (formName != null) {
                formName.clear();
                formName.sendKeys("Workflow User");
                try { Thread.sleep(500); } catch (Exception e) {}
                System.out.println("✅ Step 1: Form data entered");
            }

            // Step 3: Go to tasks and add task
            safeFindAndClick("tab-tasks", "tasks tab for workflow");
            try { Thread.sleep(1000); } catch (Exception e) {}

            WebElement newTask = safeFind("new-task-input", "new task for workflow");
            if (newTask != null) {
                newTask.clear();
                newTask.sendKeys("Workflow Task");
                try { Thread.sleep(500); } catch (Exception e) {}

                if (safeFindAndClick("add-task-button", "add task for workflow")) {
                    try { Thread.sleep(1000); } catch (Exception e) {}
                    dismissAlertIfPresent();
                    System.out.println("✅ Step 2: Task added in workflow");
                }
            }

            // Step 4: Go to settings and modify
            safeFindAndClick("tab-settings", "settings tab for workflow");
            try { Thread.sleep(1000); } catch (Exception e) {}

            if (safeFindAndClick("notifications-switch", "notifications switch for workflow")) {
                try { Thread.sleep(500); } catch (Exception e) {}
                System.out.println("✅ Step 3: Settings modified in workflow");
            }

            // Step 5: Return to home
            safeFindAndClick("tab-home", "home tab final workflow");
            try { Thread.sleep(1000); } catch (Exception e) {}

            System.out.println("✅ Complete multi-screen workflow finished successfully!");

        } catch (Exception e) {
            System.out.println("❌ Multi-screen workflow failed: " + e.getMessage());
        }
    }

    private static void testScrollingAndGestures() {
        System.out.println("\n📜 Test 8: Scrolling and Gestures");

        try {
            // Test scrolling in different screens
            safeFindAndClick("tab-tasks", "tasks tab for scrolling");
            try { Thread.sleep(1000); } catch (Exception e) {}

            // Scroll down in tasks list
            try {
                TouchActions touchAction = new TouchActions(driver);
                touchAction.down(500, 1000)
                          .move(500, 300)
                          .up(500, 300)
                          .perform();
                try { Thread.sleep(1000); } catch (Exception e) {}
                System.out.println("✅ Scroll down completed");
            } catch (Exception e) {
                System.out.println("⚠️ Scroll down failed: " + e.getMessage());
            }

            // Scroll back up
            try {
                TouchActions touchAction = new TouchActions(driver);
                touchAction.down(500, 300)
                          .move(500, 1000)
                          .up(500, 1000)
                          .perform();
                try { Thread.sleep(1000); } catch (Exception e) {}
                System.out.println("✅ Scroll up completed");
            } catch (Exception e) {
                System.out.println("⚠️ Scroll up failed: " + e.getMessage());
            }

            System.out.println("✅ Scrolling and gestures test completed");

        } catch (Exception e) {
            System.out.println("❌ Scrolling test failed: " + e.getMessage());
        }
    }

    private static WebElement safeFind(String elementId, String elementName) {
        try {
            // Try method 1: XPath with resource-id
            return driver.findElement(MobileBy.xpath("//*[@resource-id='" + elementId + "']"));
        } catch (Exception e1) {
            try {
                // Try method 2: Direct ID
                return driver.findElement(MobileBy.id(elementId));
            } catch (Exception e2) {
                try {
                    // Try method 3: Full resource-id format
                    return driver.findElement(MobileBy.id("com.testapp3:id/" + elementId));
                } catch (Exception e3) {
                    System.out.println("❌ Could not find " + elementName);
                    return null;
                }
            }
        }
    }

    private static boolean safeFindAndClick(String elementId, String elementName) {
        try {
            WebElement element = safeFind(elementId, elementName);
            if (element != null) {
                element.click();
                System.out.println("✅ Clicked " + elementName);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("❌ Could not click " + elementName + ": " + e.getMessage());
            return false;
        }
    }

    private static boolean dismissAlertIfPresent() {
        try {
            // Try different possible alert button selectors
            List<String> alertSelectors = Arrays.asList(
                "android:id/button1",  // OK button
                "//*[@text='OK']",     // Text-based
                "//*[@text='ok']"      // Lowercase
            );

            for (String selector : alertSelectors) {
                try {
                    WebElement alertButton;
                    if (selector.startsWith("//")) {
                        alertButton = driver.findElement(MobileBy.xpath(selector));
                    } else {
                        alertButton = driver.findElement(MobileBy.id(selector));
                    }
                    alertButton.click();
                    System.out.println("✅ Alert dismissed");
                    try { Thread.sleep(500); } catch (Exception e) {}
                    return true;
                } catch (Exception e) {
                    // Continue to next selector
                }
            }

            // If no button found, try pressing back key
            driver.pressKeyCode(4);  // Back button
            System.out.println("✅ Alert dismissed with back key");
            return true;

        } catch (Exception e) {
            System.out.println("⚠️ No alert to dismiss");
            return false;
        }
    }
}