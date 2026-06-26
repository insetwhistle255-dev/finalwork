 POM

Encapsulate page element positioning and page operations in independent Page classes to avoid using selectors directly in test code.  
Improve code reusability: Multiple test classes can share the same Page object’s methods.  
Enhance maintainability: When the page structure changes, only the corresponding Page class needs to be modified, without altering test logic.  
Manifestation in the project:

All Page classes inherit BasePage, which provides common methods (e.g., screenshots, wait for loading) and reporting/logging helper methods.  
Each test class obtains instances of page objects such as loginPage, productsPage, and cartPage through BaseTest, and directly calls business methods.  
Example: In CartTest, call `cartPage.removeProduct(…);` instead of directly operating `page.click(…)`, making the test code closer to business semantics.

2. Data-Driven Testing (DDT)  
Current implementation:

Preliminary use of hardcoded data (e.g., directly writing username and password in LoginTest), without reading from external data sources.  
The reason is that there are few test scenarios, and the primary focus is functional verification; hardcoded data is sufficient for quick validation.  
Future expansion plan (interface already reserved):

Use JUnit 5’s `@ParameterizedTest` combined with `@CsvSource`, `@CsvFileSource`, or `@MethodSource` to read test data.  
Store data files (e.g., login-data.csv) under `src/test/resources/`, and load them via `getClass().getClassLoader().getResourceAsStream(...)` (already tested in an earlier resource validation assignment).
