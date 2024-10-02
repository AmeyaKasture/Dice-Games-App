# A3: Dice Play App

An Android application offering users two gaming options, along with a separate feature showcasing details of the second game.

### a. Project Details

**Project Name**: Dice Play Mobile Application

**Participants**: Sharwin Neema (2021A7PS1442G, f20211442@goa.bits-pilani.ac.in) - Ameya Kasture (2021A7PS2058G, f20212058@goa.bits-pilani.ac.in)

### b. App Overview

The app hosts two distinct games across three interfaces. Users can either tap a dice icon, receiving 5 coins each time a 6 is rolled or participate in a game where they stake coins on specific combinations to earn rewards.

However, there are some noted restrictions:

- Extensive dice rolls can lead to overlapping text in the coin label or even go beyond the display, impairing readability.
- Excessive rolls may also surpass JAVA's maximum integer range, causing potential app glitches.
- Rapid taps on the dice icon may lead to system lag or interrupted feedback.
- Initially, the dice displays a '0' before any roll.

It's worth noting that these issues are rare and don't compromise the app's core functions.

### Known Bugs
- Occasionally, the UI does not update immediately after earning coins, requiring a manual refresh.
- Navigation between fragments may lag slightly on older devices.

## Task Completion Overview
To successfully complete the Wallet and Dice Game app, we undertook several key tasks:

1. **Implementing the Wallet ViewModel**:
   - We started with the `GamesViewModelWalletTest` to ensure the wallet's functionality passed all test cases. 
   - The `walletDie` was injected as a `@Spy` object, allowing us to monitor its interactions during tests. 
   - After implementing the initial model, we added additional test cases to verify the wallet’s behavior, ensuring it meets the expected outcomes.

2. **Connecting the WalletFragment UI**:
   - We tied the `WalletFragment` UI to the wallet model, allowing real-time updates to the displayed balance. 
   - Extensive testing was performed on the UI to ensure it interacted correctly with the underlying model and provided a smooth user experience.

3. **Enhancing the GamesFragment**:
   - We implemented the gameplay functionality by fixing the `GamePlayTest` and `MockedGamePlayTest` cases, confirming that all game mechanics worked as intended.
   - Additional test cases were created to further validate the game logic and ensure robustness.
   - We developed the UI for the `GamesFragment`, ensuring it matched the provided design specifications and connected seamlessly to the model. We wrote UI test cases concurrently to maintain quality.

4. **Adding the InfoFragment**:
   - A new `InfoFragment` was added to the navigation graph to provide users with game rules. 
   - We implemented an INFO button in the `GamesFragment` that navigates to the `InfoFragment` using `NavDirections`.

5. **Persisting Wallet Balance**:
   - We used the `SharedPreferences` API to persist the wallet balance across device rotations, app kills, and reboots. 
   - This functionality ensures that users always have access to their current balance, regardless of how they navigate the app.

6. **Accessibility Testing**:
   - We tested the app using TalkBack to assess its accessibility. Navigating the app with eyes closed presented challenges in identifying elements, emphasizing the need for clear labels and descriptions.
   - We conducted a report using the Accessibility Scanner, identifying areas for improvement in color contrast and font sizes.
   - We wrote four Espresso test cases to ensure the app's accessibility features were functioning correctly. The integration of accessibility checks into our test framework helped identify potential issues in the UI.

## Testing Approach
We employed a combination of test-driven development (TDD) and manual testing to validate the app's functionality. Test cases were written before implementing features to ensure that all aspects of the wallet and gameplay logic worked as expected. This approach was beneficial in identifying issues early in the development process.

We also used Espresso for UI testing, which allowed us to automate interactions with the app and verify the behavior of different UI components. While most tests were straightforward, some edge cases required additional attention, highlighting the importance of thorough testing.

## Time and Difficulty
- **Approximate Hours Spent**: 25 hours
- **Difficulty Rating**: 7/10

Overall, the assignment presented a good mix of challenges, particularly in integrating UI with the ViewModel and ensuring data persistence across various states.

## Acknowledgments
Special thanks to my classmates and instructors for their support and feedback throughout this project.

## Conclusion
This Wallet and Dice Game app represents a significant step in our learning journey, showcasing our ability to apply Android development concepts effectively. We look forward to further enhancing the app and addressing any outstanding issues.
