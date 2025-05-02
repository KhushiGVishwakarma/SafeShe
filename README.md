# ✨ SafeShe

SafeShe is your essential personal safety companion, designed to help women stay safe and connected at all times. With features like live location sharing, SOS alerts, emergency contacts, and safety tips, SafeShe empowers users with the tools needed for a secure environment.

---

## 📱 Screenshots

### 5.1 Main Page (Splash Screen)

![Splash Screen](screenshots/splash.png)

### 5.2 Onboarding Pages

* **5.2.1 Onboarding Page 1**
  ![Onboarding 1](screenshots/onboarding1.png)

* **5.2.2 Onboarding Page 2**
  ![Onboarding 2](screenshots/onboarding2.png)

* **5.2.3 Onboarding Page 3**
  ![Onboarding 3](screenshots/onboarding3.png)

### 5.3 Login Page

![Login](screenshots/login.png)

### 5.4 Register Page

![Register](screenshots/register.png)

### 5.5 Home Page

![Home](screenshots/home.png)

### 5.6 Navigation Drawer Page

![Drawer](screenshots/drawer.png)

### 5.7 Contact Page

![Contact](screenshots/contact.png)

### 5.8 Contact Message Page

![Contact Message](screenshots/contact_message.png)

### 5.9 Helpline Page

![Helpline](screenshots/helpline.png)

### 5.10 Safety Tips Pages

* **5.10.1 Tip Page**
  ![Tip](screenshots/tip.png)

* **5.10.2 Safety Video Page**
  ![Video](screenshots/video.png)

* **5.10.3 Safety Gadgets Page**
  ![Gadgets](screenshots/gadgets.png)

### 5.11 About Page

![About](screenshots/about.png)

### 5.12 Report Incident Page

![Report](screenshots/report.png)

### 5.13 Incident History Page

![History](screenshots/history.png)

### 5.14 Chatbot Page

![Chatbot](screenshots/chatbot.png)

### 5.15 Feedback Page

![Feedback](screenshots/feedback.png)

### 5.16 Profile Page

![Profile](screenshots/profile.png)

### 5.17 Edit Profile Page

![Edit Profile](screenshots/edit_profile.png)

### 5.18 Settings Page

![Settings](screenshots/settings.png)

---

## 🔥 Features

### 👤 User Management

* **Login and Registration**: Easy access for users.

### 🛡️ Safety Measures

* **Live Location Sharing**: Instantly share your location with trusted contacts.
* **Trusted Contacts**: Add up to 10 trusted contacts.
* **User Notifications**: Alert trusted contacts via notifications.
* **SMS Notifications**: Notify non-app users via SMS.

### 🚨 Emergency Assistance

* **Emergency Helplines**: Access important helpline numbers.
* **Safety Tips**: Learn how to stay safe through curated tips.

### 🆘 SOS Mode

* **Shake Detection**: Trigger SOS with a simple shake gesture.
* **Audible Alert**: Loud siren to draw attention.
* **Emergency Call**: Auto-call emergency services in SOS mode.

---

## 🗼 Architecture

This app uses Firebase services (Authentication, Realtime Database, Firestore).

---

## 🧰 Build Tool

To build this project, use **Android Studio Giraffe or above**.

---

## 🚀 Getting Started

1. In Android Studio: Tools > Firebase > Authentication > Authenticate using a custom authentication system.
2. Connect to Firebase.
3. Add Firebase Authentication SDK.
4. In Firebase Console > Authentication > Sign-in method:

   * Enable Email/Password.
   * Do not enable Email Link.
5. In Firebase Console > Settings > Service accounts:

   * Generate new private key.
   * Rename to `service_account.json` and place in `res/raw/`.
6. In `NotificationAPI.java`, paste your `project_id` from `service_account.json`.

You're good to go!

---

## 📩 Contact

Feel free to connect:

* **Email**: [your-email@example.com](mailto:your-email@example.com)

---

## 🤝 Credits

* [icons8.com](https://icons8.com/) for providing high-quality app icons.

---
