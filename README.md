
# 📈 **Stock Simulation Application**

## **Authors and Contributors**
- **Maya Edri** - [GitHub: EdriMaya](https://github.com/EdriMaya)
- **Amvi Patel** - [GitHub: amvipat](https://github.com/amvipat)
- **Dora Gombar** - [GitHub: dorcagombar](https://github.com/dorcagombar)
- **Anh Dang Phuong** - [GitHub: phuonganhdangamy](https://github.com/phuonganhdangamy)
- **Mind Kunrattanaporn** - [GitHub: Mind-MNK](https://github.com/Mind-MNK)

---

## 🚀 **Purpose**
The Stock Simulation Application is designed to provide a realistic and interactive platform where users can:
- Learn how to trade stocks using virtual money without financial risks.
- Practice responding to market dynamics and make informed trading decisions.
- Analyze profit and loss for improved trading strategies.

The project is aimed at both beginners and enthusiasts interested in stock trading.

---

## 📚 **Table of Contents**
1. [Features](#features)
2. [Installation Instructions](#installation-instructions)
3. [Usage Guide](#usage-guide)
4. [License](#license)
5. [Feedback & Contributions](#feedback--contributions)

---

## ✨ **Features**
1. **User Account Management**:
  - Secure account creation and login with unique usernames.
  - Persistent storage of user data and transaction history.

2. **Stock Trading**:
  - Real-time stock price lookup using the Alpha Vantage API.
  - Ability to buy and sell stocks with virtual money.

3. **Portfolio Tracking**:
  - View a detailed list of owned stocks, including ticker symbols and quantities.
  - Access profit and loss statistics for individual stocks.

4. **Data Persistence**:
  - Ensures user data is saved for future sessions.

5. **Simulation Environment**:
  - Provides a risk-free platform to practice trading.

---

## 🛠️ **Installation Instructions**

### Prerequisites:
- **Java Development Kit (JDK)**: Version 11 or higher.
- **Maven**: For build and dependency management.
- **API Key**: Obtain a free API key from [Alpha Vantage](https://www.alphavantage.co/).

### Steps:
1. Clone the repository:
   ```bash
   git clone https://github.com/phuonganhdangamy/CSC207-G2.git
   cd CSC207-G2
   ```
2. Set up the API key:
  - Create a configuration file or environment variable to store your Alpha Vantage API key.

3. Build the project using Maven:
   ```bash
   mvn clean install
   ```

4. Run the application:
   ```bash
   java -jar target/stock-simulation-1.0-SNAPSHOT.jar
   ```

### Common Issues:
- Ensure Java and Maven are correctly installed and in the system PATH.
- Verify your API key is valid and not exceeding daily request limits.

---

## 📋 **Usage Guide**
1. **Start the Application**:
  - Open IntelliJ and import the project.
  - Run the `main` method in the application entry class.

2. **Log In or Create an Account**:
  - Follow prompts to log in or create a new account.
![Login Page](images\LoginView.png)
![Signup Page](images\SignupView.png)
3. **Trade Stocks**:
  - Use the menu to search for stock prices, buy or sell stocks.
  - Monitor your balance and portfolio after transactions.
![Logged In View to make transactions and track stocks](images\LoggedInView.png)
4. **Analyze Performance**:
  - Navigate to your portfolio to view profit and loss statistics.

5. **Log Out**:
  - Log out to save your data securely.

---

## 📜 **License**
This project is licensed under the **public domain**. This means:
- You are free to use, modify, distribute, and build upon this project for any purpose, commercial or non-commercial, without permission.
- The project is provided "as-is," without warranty of any kind.


---

## 💬 **Feedback & Contributions**
We welcome your feedback and contributions!

### Feedback:
- We value your feedback! Please submit your thoughts and feedback via [GitHub Issues](https://github.com/phuonganhdangamy/CSC207-G2/issues).
- Be constructive and specific. Expect a response within 7 business days.

### Contributions:
Interested in contributing? Follow these steps:
1. Fork the repository.
2. Create a new branch for your changes.
    ```bash
      git checkout -b feature/your-feature-name  
    ```
3. Submit a pull request with a clear description.

**Contribution Guidelines**:
- Follow the project's coding standards.
- Write tests for new features or bug fixes.
- Provide detailed commit messages.

---

## 👥 **User Stories**
1. Log in and log out of an account (common).

   Allows users to securely access their accounts and save data when they log out. Enables secure user access with persistent data storage.

2. Buy a stock (Dora).

   Enables users to purchase stocks using virtual money, reducing their account balance accordingly.

3. Sell a stock (Amvi).

   Allows users to sell stocks they own and increase their account balance by the current stock price.

4. See a list of owned stocks (Maya).

   Provides a detailed view of all stocks the user owns, including quantities for each ticker.

5. Search for a stock and see its current price (Amy).

   Enables users to look up real-time stock prices using the Alpha Vantage API.

6. View the profit and loss of a stock (Mind).

   Displays the profit or loss for each stock, helping users make informed trading decisions.



