## Indoor Soccer Field Management Application
​
This directory contains the complete application for managing an indoor soccer field business. The purpose of this application is to provide a comprehensive and user-friendly platform for managing reservations, scheduling, customer information, and more. The application is built using Java, Spring Boot, Vue.js, and PostgreSQL.

# Purpose
The Indoor Soccer Field Management Application is designed to streamline the operations of an indoor soccer facility. It provides features such as:

1. Reservation Management: Allow customers to book field time slots online, view available times, and receive confirmations.
2. Scheduling: Manage and organize the schedule for different soccer fields, ensuring no overlaps and efficient use of the facility.
3. Customer Management: Keep track of customer information, including contact details, booking history, and preferences.
4. Payment Processing: Integrate with payment gateways to handle online payments for reservations.
5. Notifications: Send email or SMS notifications to customers about their bookings, cancellations, or any changes in the schedule.
6. User Roles: Differentiate between admin and regular user roles, providing appropriate access and permissions to each.

# Project Setup
To set up the project on your local machine, follow these steps:

1. Clone the repository to your local machine: git clone <repository-url>
2. Navigate to the project directory: cd indoor-soccer-app
3. Install the necessary dependencies for the backend: cd backend
./mvnw install
4. Set up the PostgreSQL database: cd database
./create.sh
5. Run the Spring Boot application: cd ..
./mvnw spring-boot:run
6. Install the necessary dependencies for the frontend: cd frontend
npm install
7. Start the Vue application: npm run dev
 
# Technologies Used
Backend: Java, Spring Boot, PostgreSQL
Frontend: Vue.js, Vue Router, Vuex, Axios
Build Tools: Maven, Vite
Security: JWT for authentication, Spring Security

# Features
1. Responsive Design: The application is designed to be responsive and works well on both desktop and mobile devices.
2. Real-time Updates: The application provides real-time updates for reservations and scheduling.
3. User-friendly Interface: A clean and intuitive interface that makes it easy for users to navigate and use the application.
4. Scalability: The application is designed to be scalable, allowing it to handle a large number of users and reservations.

# Contributing
If you would like to contribute to this project, please follow these steps:

1. Fork the repository.
2. Create a new branch (git checkout -b feature-branch).
3. Make your changes.
4. Commit your changes (git commit -m 'Add new feature').
5. Push to the branch (git push origin feature-branch).
6. Create a new Pull Request.

Happy coding!
