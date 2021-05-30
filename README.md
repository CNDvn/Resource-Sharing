# LAB 321Assignment

```
Type: Long Assignment
Code: J3.L.P00 16
Point: 750
Slot(s): NA
```
## Title: Resource Sharing

## Background

Build a website to share the company’s resource.

## Program Specifications

Build a website for managing resource sharing within the company. A company is divided into 3 groups of employees:
manager, leaders, and employees.

- Leaders and employees group have different role to use resources.
- The Manager has the right to accept or deny the requests of subordinate employees.
- **Student must use Servlet as Controller and follow MVC2 model.**

## Features:

This system contains the following functions:

- **Function 1: Login- 100 Points**
    o The actor enters userID and password
       ▪ The function checks if the userID with the password is in the available user list, then system grants
          the access permission.
       ▪ Otherwise, a message would appear to notify that user is not found.
    o Login function includes logout and welcome.
    o This function must integrate with verify service using Google reCAPTCHA service.
- **Function 2: Display- Search- 50 Points**
    o All users can use this function (login is required).
    o User can find the resource based on category, name and using dates.
    o List first 20 available items in the system corresponding with login user and order by item name. Some
       information is shown as item name, color, category, quantity.
    o Paging is required.
- **Function 3: Create new account- 100 points**
    o Register new user with information such as email as userID, phone, name, address, create date.
    o Create date is current date.
    o The default status of new user is New.
    o You must verify the email address of the user before changing the status of new user to Active.
- **Function 4: Booking- 10 0 points**
    o All users can use this function except manager role (login is required)
    o If the user has not authenticated, the system redirects to the login page.
    o Members can send a request to use the resource and waiting for the manager's approval.


```
o The default status of the request is New.
o The status will be changed to Accept if the manager accepts that request.
```
- **Function 5: Request process- 150 points**
    o Only manager account can use this function.
    o List first 20 requests in the system order by date. Paging is required.
    o This function is used to support search old request/available process with showing with some information
       such as date, resource name, user request, status (New, Delete, Accept).
    o User input the text that they want to find and select request’s status after that click the Search button or
       the Enter key. A list request which contain the search keyword (search by content)
    o This GUI allows to delete or approval the selected request.
       ▪ The list result must be updated after delete or approval.
       ▪ Delete action is used to update the status of request to Delete.
       ▪ Approval action is used to change the status of request to Accept.
- **Function 6: Request history- 100 points**
    o User can take over the request history: list of request order by booking date.
    o This function is used to support search old request by searching following name or request date.
    o This function is used to support delete old request in list result
       ▪ **Notes:** update the status of old request to inactive
    o Login is required by user
- **Function 7: Store database on Amazon web service- 100 points(extra)**
    o This function is used to store website data on Amazon Relational Database Service.
- **Function 7 : Login with Google account- 5 0 points (extra)**
    o User can login by using Google account.

* The above specifications are only basic information; you must perform a requirements analysis step and build the
application according to real requirements.
* You have to build your own database.
* The lecturer will explain the requirement only once on the first slot of the assignment.


