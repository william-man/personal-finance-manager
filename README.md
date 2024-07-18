# Personal Finance Manager

## Overview

The Personal Finance Manager is a Java-based application that helps users track their personal transactions. The application provides functionalities to add income and expense entries, categorize transactions, and visualise data using charts.

## Features

- Add income and expense entries
- Categorize transactions
- Visualise data using charts

## Installation

### Prerequisites

- Java Development Kit (JDK) 14 or later
- Maven

### Building the Project

1. Clone the repository:

   ```sh
   git clone https://github.com/your-username/personal-finance-manager.git
   cd personal-finance-manager
   ```

<br>

2. Build the project using Maven:<br>
   `mvn clean package`

### Running the Application

If you're on windows, you can simply run the executable file in the root directory. Otherwise:

1. Navigate to the target directory:<br>

   ```
   cd target
   ```

2. Run the application:<br>
   ```
   java -jar personal-finance-1.0-SNAPSHOT.jar
   ```

## Usage

A mock csv file can be loaded `./src/mock` folder to see a demo of the application.

### Adding a Transaction

1. Enter the transaction details in the provided fields.
2. Click the "Submit" button to add the transaction to the list.

### Removing a Transaction

1. Enter the transaction ID in the "Remove Transaction" section.
2. Click the "Submit" button to remove the transaction from the list.

### Setting the Balance

1. Enter the initial balance in the "Enter Balance" field.
2. Click the "Set balance" button to update the balance.

### Saving and Loading Data

- Click the "Save" button to save the transaction data to a CSV file.
- Click the "Load" button to load transaction data from a CSV file.

## Development

### Running Tests

To run the tests, use the following command:

```sh
mvn test
```
