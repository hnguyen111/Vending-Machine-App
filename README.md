# Vending Machine (Lite Snacks) App

This app has 4 types of users including customers, sellers, cashiers, and owners. A new user can create an account, or they can use the vending machine as an anonymous user.

All users can buy products and proceed to payment by cash. Other than that,
- A seller can fill/modify the item details, and obtain reports on item details and sales summaries.
- A cashier can fill/modify the available change or notes/coins available, and obtain a report on current available change and a summary of transactions.
- An owner can do all of the above, remove seller/cashier /owner user(s) from the system, and obtain a report on current users and a summary of canceled transactions.

The reports can be found locally in "app/src/main/resources/reports":
- seller can find their reports in the "seller" folder
- cashier can find their reports in the "cashier" folder
- owner can find their reports in the "owner" folder

## Usage
The app can be downloaded by cloning the project.
```
git clone https://github.sydney.edu.au/SOT2412-COMP9412-2022S2/CC_17_Fri_10_Ken_Group-04.git
```
This app has to be run on Java SE 17+ with Gradle.

At the root directory of the project folder "app", run the following commands:
```
gradle clean build
gradle run
```
Login Info, for default users:

- username: owner, password: owner123 (user type: owner)

- username: cashier, password: cashier123 (user type: cashier)

- username: seller, password: seller123 (user type: seller)

To run all the test cases, run:
```
gradle test
```
JaCoCo report can be accessed locally from:
CC_17_Fri_10_Ken_Group-04/app/build/reports/jacoco/test/html/index.html

To generate the Javadoc, run:

```
gradle javadoc
```

The javadoc can then be accessed locally from app/build/docs/javadoc/index.html.

## Contributing
To make contributions to the codebase, you can fork this repository first, and clone your forked repository to your local machine. You then can create your own branch and make changes to it. After finishing your work, push your branch to GitHub and open a pull request for review.

You have to add test cases as part of your changes.

## Code Contributors
Alannah Webster

Hantha Nguyen

Henriette Onarheim

## License
[MIT](https://choosealicense.com/licenses/mit/)

