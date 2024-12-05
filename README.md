# **Trip Planner**

Trip Planner is a mobile application designed to make trip planning easy and collaborative. The app enables users to create trips, invite collaborators, and manage trip details efficiently. It includes a robust backend and a user-friendly frontend for seamless functionality.

---

## **Features**

### **Frontend**
- **Authentication**: Login and Signup using JWT tokens.
- **Trip Management**:
  - Create trips with a title, description, start, and end dates.
  - View trip details, including the creator and collaborators.
  - Invite collaborators via email.
- **Collaborator Management**:
  - Displays collaborator names instead of user IDs.
- **Google Maps Integration**: (Planned for future updates).

### **Backend**
- Built with **Node.js** and **Express.js**.
- MongoDB for database management.
- Core features include:
  - User authentication with JWT.
  - Trip creation, updating, and fetching.
  - Adding collaborators by email.

---

## **Screenshots**

| Login Screen               | Trip List                  | Trip Details                |
|----------------------------|----------------------------|-----------------------------|
| ![Login Screen][Screenshot_20241205-230601_Trip Planner](https://github.com/user-attachments/assets/7ccef495-3350-4a5f-9bf4-6e1d0a787265)
 | ![Trip List](screenshots/trip_list.png) | ![Trip Details](screenshots/trip_details.png) |

---

## **Tech Stack**

### **Frontend**
- **Kotlin** with **Jetpack Compose**.
- **Dagger Hilt** for Dependency Injection.
- **Retrofit** for API calls.
- **Jetpack DataStore** for local token storage.

### **Backend**
- **Node.js** with **Express.js**.
- **MongoDB** with **Mongoose**.
- **JWT** for secure authentication.
- **Bcrypt.js** for password hashing.

---

## **Challenges Faced**

1. **Frontend and Backend Communication**:
   - **Problem**: Android blocks HTTP requests by default.
   - **Solution**: Configured `android:usesCleartextTraffic="true"` in `AndroidManifest.xml`.

2. **Collaborator Names**:
   - **Problem**: Needed to fetch collaborator names instead of user IDs.
   - **Solution**: Added a backend endpoint to retrieve user details by ID.

3. **Token Management**:
   - **Problem**: Securely managing JWT tokens on the frontend.
   - **Solution**: Used Jetpack DataStore to store tokens securely.

4. **Debugging Integration Issues**:
   - **Problem**: Mismatch between frontend requests and backend responses.
   - **Solution**: Used consistent data models and tested all APIs with Postman.

---
