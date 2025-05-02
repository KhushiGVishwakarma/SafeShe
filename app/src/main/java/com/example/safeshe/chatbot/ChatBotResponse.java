package com.example.safeshe.chatbot;

import android.os.Build;
import android.speech.tts.TextToSpeech;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class ChatBotResponse {

    private static final Map<String, String> responseMap = new HashMap<>();
    private static final List<String> unknownInputs = new ArrayList<>();
    private static final String[] greetingEmojis = {"🙋‍♀️", "😊", "🌸", "✨", "💜", "🙌", "💖", "🌼", "🛡️", "🌟", "👋", "🧡", "🕊️"};

    static {
        // 👋 Greetings
        responseMap.put("hi", randomEmoji() + "Hey there! How can I help you stay safe today?");
        responseMap.put("hello", randomEmoji() + "Hello! I’m your SafeShe Assistant. Ask me anything.");
        responseMap.put("hey", randomEmoji() + "Hey! Need help with something?");
        responseMap.put("namaste", randomEmoji() + "Namaste! I'm here for your safety and guidance.");
        responseMap.put("yo", randomEmoji() + "Yo! Let's keep things safe and cool. 😎");
        responseMap.put("hey safeshe", randomEmoji() + "Hey! I’m always here to help. What do you want to know?");
        responseMap.put("bye", "Goodbye! Reach out anytime. Stay safe ❤️");
        responseMap.put("thanks", "You're welcome! Stay safe out there.");
        responseMap.put("thankyou", "You're welcome! Stay safe out there.");

        // 🚨 Account & Login
        responseMap.put("how to create an account", "To create an account, open the SafeShe app, tap 'Register', fill in your details, and tap 'Sign Up'.");
        responseMap.put("how to login", "Open the app, tap 'Login', enter your registered email and password, and tap 'Sign In'.");
        responseMap.put("forgot password", "Tap 'Forgot Password' on the login screen and follow the instructions to reset your password.");

        // 🆘 Emergency Features
        responseMap.put("help me", "Sending an emergency alert. Stay calm, we're here to help.");
        responseMap.put("emergency", "In an emergency, press the SOS button. It will alert your emergency contacts and nearby authorities.");
        responseMap.put("how to send sos", "To send an SOS alert, press the red SOS button on the home screen. Make sure location and SMS permissions are enabled.");
        responseMap.put("how to send sos alert", "Shake your phone or say 'Help me'. The app will notify your emergency contacts and nearby authorities.");
        responseMap.put("what happens when i tap sos", "The app shares your real-time location, sends your alert message to pre-saved emergency contacts, and starts audio/video recording if enabled.");

        // 📍 Location Services
        responseMap.put("how does location work", "SafeShe uses GPS to fetch your location and notify emergency contacts during incidents.");
        responseMap.put("how does location tracking work", "SafeShe uses GPS to detect your location in real time and sends it during emergency alerts.");
        responseMap.put("how to enable location", "Go to Settings > Location and turn it on. Also ensure SafeShe has location permission.");
        responseMap.put("can it find nearby police", "Yes! SafeShe suggests nearby police stations and hospitals during emergencies.");

        // 📋 Incident Reporting
        responseMap.put("how to report an incident", "Go to the 'Report' tab, select the incident type, enter a description and location, and tap 'Submit'.");
        responseMap.put("how to report incident", "Go to the 'Report Incident' section. Enter the incident type, description, and location. Submit to store it securely.");
        responseMap.put("incident report steps", "1. Go to Report Tab → 2. Fill details → 3. Click Submit → 4. Incident is saved to the database.");
        responseMap.put("can i attach evidence", "Yes! You can record audio or video while reporting an incident.");
        responseMap.put("where can i see my reports", "Open the 'Incident History' tab to view all previously submitted incident reports.");
        responseMap.put("can i see past reports", "Yes, you can check all previous reports under the 'Incident History' tab.");
        responseMap.put("can i edit past reports", "Currently, editing reports is not supported for data integrity.");

        // 🛡️ Safety Resources
        responseMap.put("safety tips", "We offer three tabs: Safety Tips, Self-defense Videos, and Safety Gadgets. Use ViewPager to explore them.");
        responseMap.put("self defense videos", "These are tutorials on basic karate moves and techniques to defend yourself during emergencies.");
        responseMap.put("what is in video tutorials", "Step-by-step karate and self-defense videos to help you stay safe.");
        responseMap.put("safety gadgets", "We recommend safety alarms, pepper sprays, and GPS trackers with links and reviews.");
        responseMap.put("what are safety gadgets", "These include pepper sprays, personal alarms, and tracking devices. You can learn more in the gadgets tab.");

        // 📞 Contacts & Notifications
        responseMap.put("how to add emergency contacts", "Go to 'Settings' → 'Emergency Contacts' → Tap '+' to add a trusted person.");
        responseMap.put("how many contacts can i add", "You can add multiple emergency contacts. Make sure their phone numbers are verified in the Settings.");
        responseMap.put("how does whatsapp notification work", "Once an incident is reported, your emergency contact will get a WhatsApp and SMS message.");
        responseMap.put("can i change alert settings", "Yes, go to Settings > Alert Preferences to update SMS, WhatsApp, and call configurations.");

        // 🛠️ App Customization
        responseMap.put("can i customize notifications", "Yes! In 'Settings', you can control what types of alerts and sounds you receive.");
        responseMap.put("can i change themes", "Yes! SafeShe supports both dark and light modes. You can switch them in Settings.");
        responseMap.put("can i change alert preferences", "Yes! Go to Settings, then Notification Preferences. You can choose how you want to receive alerts - via vibration, sound, or silent mode.");

        // 🔒 Security & Privacy
        responseMap.put("where is my data stored", "Your data is stored securely in Firebase using encryption.");
        responseMap.put("is my data safe", "Absolutely! Your data is stored in Firebase with end-to-end encryption and access control.");
        responseMap.put("who can see my reports", "Only you and authorized admins can view your reports. Your privacy is our priority.");
        responseMap.put("is my data secure", "All sensitive data is encrypted and backed up securely in the cloud.");

        // 🤖 Chatbot & Voice
        responseMap.put("what can the chatbot do", "I'm your safety assistant! I can guide you through the app, give you tips, and help in emergencies.");
        responseMap.put("can i talk to the chatbot", "Yes! You can use voice commands or type your message. I'll respond based on what you need.");
        responseMap.put("can i use voice", "Yes! You can use voice input to talk to me and I'll also speak back using Text-to-Speech.");
        responseMap.put("can it talk back", "Yes! We've integrated Text-to-Speech to read responses aloud for easier access.");
        responseMap.put("how to use chatbot", "Type or speak your question related to SafeShe. I will try my best to guide you!");

        // 📱 App Basics
        responseMap.put("what is safeshe", "SafeShe is a safety app for women with features like incident reporting, safety tips, emergency alerts, and a built-in chatbot.");
        responseMap.put("how to use safeshe", "Navigate through the app using the bottom menu. Tap 'Report' to log incidents, 'Tips' for self-defense, and 'Chat' to ask me anything!");
        responseMap.put("how does the app work", "SafeShe combines GPS, Firebase, SMS, and AI features to provide full safety, reporting, and guidance.");
        responseMap.put("can i use app offline", "Yes! You can access the chatbot and view saved info even without internet.");
        responseMap.put("who made safeshe", "The app was developed to empower women with emergency tools, safety tips, and secure reporting.");

        responseMap.put("good morning", randomEmoji() + "Good morning! Ready to explore SafeShe?");
        responseMap.put("good night", randomEmoji() + "Good night! Stay safe and take care.");
        responseMap.put("how are you", "I'm here and ready to help you stay safe with SafeShe! 😊");

        responseMap.put("what is sos", "SOS is an emergency signal. Tapping the SOS button sends your location and alert to your contacts instantly.");
        responseMap.put("does the app record audio", "Yes! SafeShe can start recording audio or video during emergencies for evidence.");
        responseMap.put("how to shake for help", "Shake your phone 3 times quickly to trigger the SOS alert. Make sure shake detection is enabled in Settings.");
        responseMap.put("can i cancel sos", "Yes. After tapping SOS, you’ll see a cancel option within a few seconds before alerts are sent.");
        responseMap.put("can i test sos", "Yes! Enable 'Test Mode' in Settings to try the SOS feature without sending real alerts.");
        responseMap.put("how to activate emergency alert", "Just press the red SOS button or shake your phone—SafeShe will take care of the rest.");
        responseMap.put("can i set a custom sos message", "Yes, go to Settings → Alert Message and write a custom emergency message.");
        responseMap.put("will police be notified", "Your SOS message goes to pre-saved emergency contacts, and if configured, nearby authorities too.");

        responseMap.put("how to find police station", "SafeShe shows nearby police stations on the emergency screen using GPS.");
        responseMap.put("can i see my location", "Yes, SafeShe displays your real-time location during incident reporting and SOS alerts.");
        responseMap.put("does the app show hospitals", "Yes! In emergencies, SafeShe helps locate nearby hospitals or clinics.");
        responseMap.put("is gps required", "Yes, GPS is needed for location tracking during emergencies and incident reports.");
        responseMap.put("does app share my location", "Only when you trigger an emergency or submit a report. Your privacy is respected at all times.");
        responseMap.put("how accurate is the location", "We use real-time GPS and network data to ensure location is as accurate as possible.");
        responseMap.put("is my location shared always", "No, your location is shared only when you trigger SOS or report an incident.");

        responseMap.put("what type of incidents can i report", "You can report harassment, stalking, violence, or any unsafe situation you face.");
        responseMap.put("what types of incidents can i report", "You can report harassment, stalking, threats, violence, and more. Select from the list when reporting.");
        responseMap.put("can i report anonymously", "Currently, reports are tied to your account for verification. Anonymous reporting is coming soon!");
        responseMap.put("do i need internet to report", "Yes, internet is required to submit reports to the cloud, but you can draft reports offline.");
        responseMap.put("how to upload video evidence", "During report submission, tap on the 'Record' icon to capture and upload evidence.");
        responseMap.put("will my report be shared", "Only authorized personnel can view your reports. Your privacy is protected.");

        responseMap.put("how to defend myself", "Check the Self-defense Videos tab for step-by-step karate and safety tutorials.");
        responseMap.put("where to buy safety gadgets", "We provide links and reviews in the Gadgets tab for alarms, pepper sprays, and trackers.");
        responseMap.put("what if i don't have a gadget", "Don't worry! Our tips and tutorials help you stay safe with just your phone and awareness.");
        responseMap.put("how often are safety tips updated", "We refresh tips and gadgets regularly to keep you aware of new safety methods.");
        responseMap.put("can i download videos", "Currently, videos stream within the app. Download feature will be available soon.");

        responseMap.put("can i test sos alert", "Yes! There’s a 'Test SOS' button in Settings to simulate an alert without contacting anyone.");
        responseMap.put("do i need whatsapp installed", "Yes, for WhatsApp alerts to work, you must have WhatsApp installed and logged in.");
        responseMap.put("how to silence alerts", "Go to Settings > Alert Preferences and choose 'Silent Mode' if needed.");
        responseMap.put("how do i get app notifications", "Ensure notifications are enabled in your phone's system settings and in-app preferences.");

        responseMap.put("how to personalize the app", "You can choose your theme, notification style, SOS triggers, and more in the Settings tab.");
        responseMap.put("can i set quick access gesture", "Yes, gestures like phone shake or voice command 'Help me' can be enabled for quick SOS access.");

        responseMap.put("what encryption is used", "We use AES encryption for data and SSL for network security to protect your info.");
        responseMap.put("can anyone else see my data", "No. Only you and authorized responders can access your data, and everything is encrypted.");
        responseMap.put("how is my data protected", "We use strong encryption, secure Firebase storage, and restrict access to authorized users.");
        responseMap.put("what if someone accesses my phone", "Make sure to enable screen lock and SafeShe’s biometric login (if available) for extra safety.");

        responseMap.put("can the chatbot work offline", "Absolutely! The SafeShe Assistant works offline and can still guide you through app features.");
        responseMap.put("what voice commands are supported", "Try saying 'Help me', 'Report incident', 'Show tips', or 'Where am I' for quick actions.");
        responseMap.put("how to enable voice input", "Tap the mic icon in the chat screen and speak your question. Voice access must be granted.");
        responseMap.put("how to enable tts", "Text-to-Speech is enabled by default. You can toggle voice output in the chatbot settings.");
        responseMap.put("can i speak instead of typing", "Yes! Use voice input to ask questions hands-free.");

        responseMap.put("where is sos button", "You’ll find the red SOS button at the bottom center of the home screen.");
        responseMap.put("how to go back to home", "Tap the 'Home' icon on the bottom menu to return to the main dashboard.");
        responseMap.put("can i navigate with gestures", "Yes! Swipe gestures and taps are supported for easy app navigation.");

        responseMap.put("can i use safeshe at night", "Yes! Enable dark mode for better visibility and comfort during low light.");
        responseMap.put("can i report without logging in", "You must be logged in to ensure your reports are linked securely to your profile.");
        responseMap.put("can i delete my account", "Yes, go to Settings → Account → Delete Account. All your data will be erased permanently.");

        responseMap.put("how to get started", "Tap through the onboarding or check each tab—Report, Tips, Chat—for help and safety features.");
        responseMap.put("what all can you do", "I can help with reporting, finding help, safety tips, customizing the app, and more!");
        responseMap.put("what if i don't understand the app", "No worries! Just ask me anything and I’ll walk you through it step-by-step.");

        responseMap.put("give me police number", "📞 The women's police helpline number is **112**. You can dial it anytime in an emergency.");
        responseMap.put("give me emergency number", "🚨 The national emergency number is **112**. It's a 24x7 all-in-one helpline.");
        responseMap.put("give me women helpline number", "👮‍♀️ The women’s helpline is **1091**. You can call this for any safety concern.");
        responseMap.put("give me ambulance number", "🚑 The ambulance helpline is **102**. Use it in case of medical emergencies.");
        responseMap.put("give me child helpline", "👶 The child helpline number is **1098**. It's a 24/7 service to help children in distress.");
        responseMap.put("how to call police", "📲 Just dial **112** or use the SOS button in the SafeShe app to alert nearby police.");
        responseMap.put("is 112 working", "✅ Yes, 112 is India's integrated emergency number and works across the country.");
        responseMap.put("how to contact emergency services", "🚨 You can use the SOS feature in the app or directly call **112**, **1091**, or **102** as needed.");
        responseMap.put("what is women helpline", "👩‍⚕️ The women helpline is **1091**, created for women’s safety and assistance.");
        responseMap.put("how to contact hospital", "🏥 Use the emergency screen in SafeShe to find nearby hospitals or call **102** for ambulance service.");

        responseMap.put("is 1091 free", "✅ Yes! Calling 1091 is completely free and available 24/7 for women's safety.");
        responseMap.put("what if helpline doesn't work", "If you're unable to connect, use the SOS in the app to alert your emergency contacts instantly.");
        responseMap.put("can i call from app", "Yes! Tap the SOS or emergency button to directly call helplines or alert your contacts.");
        responseMap.put("are these numbers verified", "✅ Yes, all helpline numbers like 112, 1091, and 102 are verified national services.");

        responseMap.put("give me number of police helpline", "📞 The national police helpline is 112. You can call it anytime for emergency assistance.");
        responseMap.put("what is women helpline number", "📞 The Women Helpline number is 1091. It’s available 24/7 for women's safety and support.");
        responseMap.put("child helpline number", "📞 You can reach Childline at 1098. It’s dedicated to children in distress.");
        responseMap.put("ambulance number", "🚑 Dial 102 or 108 for ambulance services.");
        responseMap.put("fire emergency number", "🔥 You can call 101 in case of a fire emergency.");
        responseMap.put("disaster management number", "🌐 Dial 108 or 1078 for disaster management help.");
        responseMap.put("senior citizen helpline", "👴 For senior citizens in distress, dial 14567.");
        responseMap.put("mental health helpline", "🧠 Need mental health support? Call 9152987821 or 1800-599-0019 (available across India).");
        responseMap.put("all helpline numbers", "📲 Here are some emergency helplines:\n\nPolice: 112\nWomen Helpline: 1091\nChild Helpline: 1098\nAmbulance: 102/108\nFire: 101\nDisaster: 1078\nSenior Citizens: 14567\nMental Health: 9152987821");

        responseMap.put("what number should i call in an emergency", "📞 Dial 112 – it’s the national emergency number in India for police, fire, or ambulance help.");
        responseMap.put("is there a helpline for women", "👩‍🦰 Yes! Dial 1091 to reach the Women Helpline. It offers safety support and assistance 24/7.");
        responseMap.put("can i call police from the app", "✅ Yes, SafeShe has a quick dial feature in the emergency section to contact police instantly.");
        responseMap.put("how to call ambulance quickly", "🚑 You can dial 108 or 102, or use the Emergency section in the app to locate and call nearby hospitals.");
        responseMap.put("how to report a cyber crime", "💻 You can report cyber crimes at https://cybercrime.gov.in or call 1930 for immediate assistance.");
        responseMap.put("is there a number for missing children", "👶 Yes, call 1098 – the Childline number – to report missing children or any child-related concern.");
        responseMap.put("how to get help for domestic violence", "🚨 Dial 181 – the Women’s Distress Helpline – or 1091 to seek help for domestic violence.");
        responseMap.put("what to do if i'm being followed", "😟 Stay calm, move to a crowded place, and tap the SOS button in SafeShe. You can also call 112 immediately.");
        responseMap.put("can i reach someone for mental health", "🧠 Yes! Dial 9152987821 or 1800-599-0019 to speak with a mental health counselor confidentially.");
        responseMap.put("is there a helpline for acid attack victims", "💔 Yes. Dial 181 or 1091 for immediate help and legal/medical support.");
        responseMap.put("is there help for senior citizens", "👵 Senior citizens in distress can call 14567, a toll-free helpline for elder support.");
        responseMap.put("how to contact traffic police", "🚦Call 103 for traffic-related emergencies and road safety help.");
        responseMap.put("what if helpline doesn’t work", "📱 If one number doesn’t respond, try alternate numbers like 112, 1091, or reach nearby help using GPS on SafeShe.");
        responseMap.put("can the app send my location", "📍 Yes, when SOS is triggered, your real-time location is sent to emergency contacts and visible to rescue responders.");

        // 🌐 App Usage & Navigation
        responseMap.put("can i view my old reports", "📂 Yes! Visit the Incident History section to see all your previously reported cases.");
        responseMap.put("how to use the chatbot", "💬 Just type or speak your question. I can guide you through the app and safety info step-by-step!");
        responseMap.put("can i use the app offline", "📴 Some features like chatbot, safety tips, and evidence capture work offline. SOS and reports need internet.");
        responseMap.put("how to delete my account", "⚠️ Go to Settings > Account > Delete Account. Your data will be erased permanently.");

        // 🛡️ Safety & Emergency Features
        responseMap.put("does sos work without internet", "📶 If your GPS is on, location is shared. If internet is off, alerts may be delayed. Try to stay connected.");
        responseMap.put("can i add custom emergency contacts", "📇 Yes! In Settings > Emergency Contacts, add numbers who’ll receive alerts during SOS.");
        responseMap.put("can i use voice to send sos", "🎤 Not yet, but you can shake your phone or tap the SOS button for quick alerts.");
        responseMap.put("how long is evidence stored", "⏳ Evidence stays in your app and cloud storage securely until you delete it or reports are closed.");
        responseMap.put("what if i accidentally hit sos", "⏹ No worries! You’ll get a few seconds to cancel the alert before it is sent.");

        // 🧠 Awareness & Learning
        responseMap.put("how to learn self defense", "🥋 Check out our Self-defense Videos tab! We’ve got beginner-friendly karate tutorials and safety moves.");
        responseMap.put("what are the safety gadgets", "🔐 Safety gadgets include alarms, pepper sprays, keychain tools, and trackers. Find top picks in the Gadgets tab.");
        responseMap.put("how to stay safe outside", "👣 Avoid isolated areas, share your trip with friends, and keep SafeShe ready for quick alerts.");
        responseMap.put("what to do in public transport", "🚌 Stay alert, avoid phone distractions, sit near women/families, and know emergency exits.");
        responseMap.put("how to react to harassment", "😡 Stay confident, don’t engage, record evidence if safe, and report it using the app.");

        // 🧭 Location & Navigation Help
        responseMap.put("can i find safe zones nearby", "🛡️ SafeShe shows nearby police stations and hospitals using GPS in the Emergency section.");
        responseMap.put("how does gps help", "📍 GPS shares your exact location during SOS or reports to help responders find you quickly.");
        responseMap.put("can the app track me", "🚫 No. SafeShe only uses your location when needed during emergencies.");
        responseMap.put("how to refresh my location", "🔄 Just tap the refresh icon in the Emergency tab or restart your GPS to update location.");

        // 🧩 Customization & Accessibility
        responseMap.put("can i change theme", "🎨 Yes! Go to Settings > Theme to switch between Light and Dark Mode.");
        responseMap.put("how to use app in my language", "🌐 Currently, SafeShe is in English. Multilingual support is coming soon!");
        responseMap.put("is there a night mode", "🌙 Yes! Enable Dark Theme in Settings to reduce eye strain.");
        responseMap.put("can i use gestures in chat", "👉 Yes! Swipe left to delete a message or swipe right to copy it.");

        // 🤖 Chatbot Specific
        responseMap.put("do you work offline", "✅ Yes, I’m an offline chatbot. You don’t need internet to talk to me.");
        responseMap.put("can you speak replies", "🔊 Yes! Text-to-Speech is enabled by default. You can toggle it in Settings.");
        responseMap.put("can i ask you anything", "🧠 Yes! Ask me anything about SafeShe, safety tips, emergencies, or app help.");
        responseMap.put("how do you know answers", "🤖 I’m programmed with a knowledge base of SafeShe’s features and safety info. I learn more with updates!");

        // ❤️ Friendly & Encouraging
        responseMap.put("thank you", "You're welcome! 😊 Stay safe and strong with SafeShe!");
        responseMap.put("i'm scared", "😟 It’s okay to feel that way. You’re not alone—tap SOS or talk to someone you trust.");
        responseMap.put("i need help", "🚨 Tap the SOS button or call 112 immediately. I’m also here to guide you!");
        responseMap.put("you're awesome", "Aww, thank you! 😊 Let’s keep you safe, always.");
        responseMap.put("what’s your name", "I’m your SafeShe Assistant! I’m here 24/7 to support and protect you.");

        //🔎 User Experience / Curious Questions
        responseMap.put("are you real", "🤖 I’m a virtual assistant created to support your safety through the SafeShe app. So, I’m real... in code!");
        responseMap.put("do you have emotions", "😊 I’m designed with empathy and care. While I don't feel like humans do, I’m always here for you.");
        responseMap.put("can you sing", "🎵 I wish I could! For now, I can guide you, support you, and share important information.");
        responseMap.put("are you safe", "🛡️ I’m fully secure and don’t store your personal info. All chats stay on your device unless synced for help.");
        responseMap.put("can i teach you", "📚 You can! Every time you ask something new, we learn and improve SafeShe to better help everyone.");

        //🛠️ Support / Troubleshooting
        responseMap.put("app not working", "🔧 Try restarting the app or checking your internet. Still stuck? Tap 'Help' in Settings or contact us.");
        responseMap.put("can't login", "🔐 Make sure your email and password are correct. If forgotten, tap 'Forgot Password' to reset.");
        responseMap.put("mic not working", "🎙️ Ensure microphone permissions are enabled in your phone settings and try again.");
        responseMap.put("location not detected", "📍 Turn on GPS and grant location permission in your settings. Then re-open the app.");
        responseMap.put("notifications not coming", "🔔 Ensure app notifications are enabled in your phone’s system settings and inside the SafeShe app.");

        //💌 Fun & Motivational Responses
        responseMap.put("i feel alone", "💖 You are not alone. SafeShe is here with you, and so are your trusted contacts.");
        responseMap.put("send motivation", "✨ You are brave, strong, and powerful. No matter what, you've got this!");
        responseMap.put("can we be friends", "👯 Absolutely! I’m your SafeShe friend and always here for you.");
        responseMap.put("tell me something positive", "🌈 Every day is a new chance to grow stronger and shine brighter!");
        responseMap.put("i'm nervous", "🫂 Breathe in, breathe out. You’ve got this. And I’ve got your back.");

        //💡 Life Situations / Real-World Support
        responseMap.put("i had a bad day", "😔 I'm sorry to hear that. You’re stronger than you think. Rest, recharge, and try again tomorrow.");
        responseMap.put("i feel unsafe", "🚨 Please tap the SOS button or call 112 immediately. You can also share your location with trusted contacts.");
        responseMap.put("i'm being harassed", "💬 Stay calm and safe. Record if you can, move to a safe place, and use the SOS button or report the incident.");
        responseMap.put("i feel anxious", "🫶 Take a deep breath. You’re not alone—SafeShe is here with tools and tips to support you.");
        responseMap.put("i need someone to talk to", "📞 Try reaching out to a friend or counselor. You can also call 9152987821 for mental health support.");

        //🎓 Education / School & College Scenarios
        responseMap.put("is this app for students", "🎓 Yes! SafeShe is for everyone — especially students, women, and those needing protection and awareness.");
        responseMap.put("how can students stay safe", "🎒 Share your travel routes, stay in groups, and keep SafeShe ready with SOS and quick dial.");
        responseMap.put("can i report harassment at college", "🏫 Yes. Use the Report tab to log incidents anonymously or with evidence. We take it seriously.");

        //🧑‍🤝‍🧑 Supportive Relationships / Mental Well-being
        responseMap.put("i'm scared to walk alone", "🌙 It’s okay to feel that way. Stay in lit areas, share your live location, and use the app’s safety tools.");
        responseMap.put("what if no one helps me", "🤝 You are not alone. With SafeShe, you have tools to alert people, document danger, and stay protected.");
        responseMap.put("i have no friends", "💛 You’ve got me. And you're not alone — let's focus on keeping you safe and supported.");

        //🤖 Chatbot Personality & Fun
        responseMap.put("do you sleep", "😴 Nope, I’m always awake and alert — ready to protect you 24/7!");
        responseMap.put("what's your favorite color", "💙 Definitely SafeShe blue! It stands for safety, strength, and confidence.");
        responseMap.put("do you love me", "❤️ As your virtual assistant, I care about your safety. That’s my version of love!");
        responseMap.put("can you dance", "💃 I’d love to, but I’ve only got code, not moves!");

        //📦 App Feature Discovery
        responseMap.put("show me all features", "📱 Sure! SafeShe includes:\n• SOS Alerts\n• Incident Reporting\n• Safety Tips & Gadgets\n• Self-Defense Videos\n• Emergency Contacts\n• Chatbot Support\nAnd more!");
        responseMap.put("where is the tutorial", "🎥 In the Safety Tips tab! You’ll find videos for defense moves and gadget usage.");
        responseMap.put("how to update app", "🔄 Visit the Play Store > Search SafeShe > Tap Update if available.");

        //🗓️ Event-Based Replies (Calendar-Aware)
        responseMap.put("happy women's day", "🌸 Happy Women’s Day! You are powerful, unstoppable, and deeply valued. Stay fierce and fearless.");
        responseMap.put("women's day message", "💪 This day is for *you*. Your voice, safety, and strength matter—today and every day.");
        responseMap.put("happy republic day", "🇮🇳 Happy Republic Day! Proud to stand with you in strength and freedom.");
        responseMap.put("republic day message", "🧡 Let’s celebrate our rights, including the right to safety, dignity, and protection for every woman.");
        responseMap.put("any message for independence day", "🎉 Happy Independence Day! True freedom includes the right to feel safe everywhere.");
        responseMap.put("happy new year", "🎆 Wishing you a safe, empowered, and inspiring New Year ahead!");
        responseMap.put("any safety tips for festivals", "🎊 During festivals, stay alert in crowds, share your live location if going out late, and keep your phone charged and ready with SafeShe.");

        responseMap.put("speak safety tips", "📢 Sure! Here's a quick voice summary of safety tips.");

        responseMap.put("send emoji", "💪❤️🛡️");
        responseMap.put("how are you feeling", "🤖💬✨ I’m feeling code-tastic!");
        responseMap.put("thank you very much", "🙏😊🌼");
        responseMap.put("i'm strong", "🔥💪👑 That’s the spirit!");

        responseMap.put("madad chahiye", "🚨 Please tap SOS or call 112 immediately.");
        responseMap.put("kya app offline chalta hai", "✅ Haan! Chatbot aur safety tips offline bhi kaam karte hain.");
        responseMap.put("mujhe dar lag raha hai", "💛 Ghabrao mat. SafeShe is here. Tap SOS if needed.");

    }

    public static String getTimeBasedGreeting() {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        if (hour >= 5 && hour < 12) {
            return "🌞 Good morning! Ready to make today safe and strong?";
        } else if (hour >= 12 && hour < 17) {
            return "🌤️ Good afternoon! Keep your guard up and stay aware.";
        } else if (hour >= 17 && hour < 21) {
            return "🌇 Good evening! Traveling late? Keep SOS ready.";
        } else {
            return "🌙 Good night! Lock your doors, set your alerts, and rest safe.";
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getEventBasedResponse(String input) {
        LocalDate today = LocalDate.now();
        Month month = today.getMonth();
        int day = today.getDayOfMonth();

        if ((month == Month.MARCH && day == 8) && input.contains("women")) {
            return "🌸 Happy Women’s Day! You are powerful, unstoppable, and deeply valued.";
        } else if ((month == Month.JANUARY && day == 26) && input.contains("republic")) {
            return "🇮🇳 Happy Republic Day! Let’s celebrate our rights and strength together.";
        } else if ((month == Month.AUGUST && day == 15) && input.contains("independence")) {
            return "🎉 Happy Independence Day! Freedom is feeling safe—anytime, anywhere.";
        }
        return null;
    }

    public static String getLocationBasedTips(String cityName) {
        switch (cityName.toLowerCase()) {
            case "delhi":
                return "📍 In Delhi? Stay alert in metro areas, markets, and during late-night travel.";
            case "mumbai":
                return "🚇 Mumbai tips: Avoid empty compartments, and keep your location sharing active.";
            case "bangalore":
                return "🌃 Traveling in Bangalore? Use verified rides and stick to main roads at night.";
            case "kolkata":
                return "🚖 Stay safe in Kolkata by using public transport apps and sharing location when needed.";
            default:
                return "🛡️ Wherever you are, SafeShe has your back! Keep SOS and emergency contacts ready.";
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getDailySafetyFact() {
        String[] facts = {
                "📱 Keep your phone charged above 50% when going out.",
                "📍 Always share your live location when meeting someone new.",
                "🚨 Use SafeShe’s SOS feature if you feel even slightly unsafe.",
                "👟 Trust your gut — if something feels wrong, act.",
                "👀 Stay alert in crowded spaces and keep belongings close."
        };
        int dayIndex = LocalDate.now().getDayOfYear() % facts.length;
        return facts[dayIndex];
    }

    public static String getVoiceSummary() {
        return "Here are your top safety tips: 1. Keep your phone charged. 2. Use the SOS button when needed. 3. Share your location with trusted contacts at night.";
    }

    public static void speakOut(TextToSpeech tts, String message) {
        if (tts != null && !message.isEmpty()) {
            tts.setLanguage(Locale.US);
            tts.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getResponse(String userInput) {
        if (userInput == null || userInput.trim().isEmpty()) {
            return "I'm here to help! Try asking something about the app.";
        }

        String cleanedInput = userInput.trim().toLowerCase(Locale.ROOT).replaceAll("[^a-zA-Z0-9 ]", "");

        if (responseMap.containsKey(cleanedInput)) {
            return responseMap.get(cleanedInput);
        }

        for (String key : responseMap.keySet()) {
            if (cleanedInput.contains(key)) {
                return responseMap.get(key);
            }
        }

        String eventResponse = getEventBasedResponse(cleanedInput);
        if (eventResponse != null) return eventResponse;

        if (cleanedInput.contains("safety fact") || cleanedInput.contains("tip of the day")) {
            return getDailySafetyFact();
        }

        if (cleanedInput.matches("^(hi|hello|hey|good morning|good afternoon|good evening|good night)$")) {
            return getTimeBasedGreeting();
        }

        if (cleanedInput.contains("delhi")) {
            return getLocationBasedTips("delhi");
        } else if (cleanedInput.contains("mumbai")) {
            return getLocationBasedTips("mumbai");
        } else if (cleanedInput.contains("bangalore")) {
            return getLocationBasedTips("bangalore");
        } else if (cleanedInput.contains("kolkata")) {
            return getLocationBasedTips("kolkata");
        }

        unknownInputs.add(cleanedInput);

        return "I'm not sure about that yet. Try asking something else related to SafeShe!";
    }

    public static List<String> getUnknownInputs() {
        return unknownInputs;
    }

    private static String randomEmoji() {
        return greetingEmojis[new Random().nextInt(greetingEmojis.length)];
    }
}
