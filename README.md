✨ SafeShe
SafeShe is the ultimate app dedicated to ensuring women's safety. With an intuitive design, it enables you to share your real-time location and alert your loved ones in emergencies with a simple tap. SafeShe provides peace of mind by effortlessly connecting you to emergency services when needed.

Screenshots 📱


Features 🔥
User Management:
Login and Registration: Quick access to the app for registered users.

Safety Measures:
Live Location Sharing: Share your live location with trusted contacts in real time.

Trusted Contacts: Add up to 10 contacts who can instantly receive your location in case of emergency.

User Notifications: Alerts trusted contacts who are also using SafeShe with a notification.

SMS Notifications: For non-users, send SMS notifications to reach them instantly.

Emergency Assistance:
Emergency Helplines: Access to a curated list of emergency numbers for fast dialing.

Safety Tips: A guide for staying safe with important tips in different scenarios.

SOS Mode:
Shake Detection: Activate SOS mode by simply shaking your phone.

Audible Alert: Trigger a loud siren to attract attention in case of distress.

Automatic Emergency Call: Instantly connects you to emergency services when in SOS mode.

Architecture 🗼
SafeShe uses Firebase for authentication, cloud storage, and messaging services. Additionally, it integrates Google Cloud to securely store sensitive data.

Build-Tool 🧰
Ensure you have Android Studio Giraffe or higher to build this project.

Getting Started 🚀
Steps to Get Started:
Clone the repository:

bash
Copy
Edit
git clone https://github.com/KhushiGVishwakarma/SafeShe.git
cd SafeShe
Open the project in Android Studio:

Go to File -> Open and select the SafeShe project.

Firebase Setup:

In Android Studio, navigate to Tools > Firebase to integrate Firebase services.

Set up Firebase Authentication:

Open the Firebase Console: https://console.firebase.google.com/.

Add Firebase Authentication by enabling Email/Password Authentication.

Disable Email link (passwordless sign-in).

Generate a Service Account Key:

In Firebase Console, go to Settings > Service accounts.

Generate a new private key and save the JSON file as service_account.json in the /res/raw/ folder.

Notification Setup:

Copy the project_id from the service_account.json and paste it in the NotificationAPI.java file.

Sync Gradle:

After adding the required Firebase files, sync the project with Gradle.

Run the App:

You're all set! Run the app on your emulator or real device.

Contact 📩
Want to get in touch? Reach out to me at:

Email: khushivishwakarma@gmail.com

Credits 🤝
Icons: Icons by icons8.com
