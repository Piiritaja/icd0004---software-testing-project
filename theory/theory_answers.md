# Theoretical part

### 1. Which of the following activities cannot be automated
- [ ] Test execution
- [x] Exploratory testing
- [x] Discussing testability issues
- [ ] Test data generation

### 2. How do we describe a good unit test?
- [ ] Flawless, Ready, Self-healing, True, Irresistible
- [ ] Red, Green, Refactor
- [x] Fast, Repeatable, Self-validating, Timely, Isolated
- [ ] Tests should be dependent on other tests

### 3. When is it a good idea to use XPath selectors
- [x] When CSS or other selectors are not an option or would be brittle and hard to maintain
- [ ] When we need to find an element based on parent/child/sibling relationship
- [ ] When an element is located deep within the HTML (or DOM) structure
- [ ] All the above

### 4. Describe the TDD process
TDD is a software development process, where we write tests before implementation.
You should only write just enough code to pass the tests.
Then you can improve/refactor your code without changing its behaviour.
Repeat the process again for every new functionality.

### 5. Write 2 test cases or scenarios for a String Calculator application, which has a method ```calculate()``` that takes a string of two numbers separated by a comma as input, and returns the sum.
Example:
- **Given** the input "1,5" **When** the method ```calculate()``` is called **Then** I should see "6" as a result.
---
- Given the input "1.5,5.5" When the method `calculate()` is called Then I should see "7" as a result.
- Given the input "0,0" When the method `calculate()` is called Then I should see "0"  as a result.