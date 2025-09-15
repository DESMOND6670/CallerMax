# 🚀 FREE GitHub APK Building Guide

This guide will help you build your CallerMax APK for **FREE** using GitHub Actions - no powerful computer needed!

## ✅ **What You Get**
- **100% FREE** APK building in the cloud
- **Automatic builds** every time you update code
- **Download APK** directly from GitHub
- **No Android SDK** needed on your computer
- **No powerful hardware** required

## 📋 **Step-by-Step Instructions**

### **Step 1: Create GitHub Account**
1. Go to [github.com](https://github.com)
2. Click "Sign up" and create a free account
3. Verify your email address

### **Step 2: Create New Repository**
1. Click the **"+"** button → **"New repository"**
2. Repository name: `CallerMax-Android`
3. Description: `Cold Calling Android App`
4. Make sure **"Public"** is selected (required for free Actions)
5. Check **"Add a README file"**
6. Click **"Create repository"**

### **Step 3: Upload Your Project**
1. Click **"uploading an existing file"**
2. Drag and drop ALL files from your CallerMax folder:
   - `app/` folder
   - `gradle/` folder
   - `build.gradle`
   - `settings.gradle`
   - `gradle.properties`
   - `gradlew` and `gradlew.bat`
   - `.github/workflows/build-apk.yml` (I created this for you)
   - All other files

3. Add commit message: `Initial CallerMax app upload`
4. Click **"Commit changes"**

### **Step 4: Trigger the Build**
1. Go to the **"Actions"** tab in your repository
2. You should see **"Build CallerMax APK"** workflow
3. Click on it, then click **"Run workflow"**
4. Click the green **"Run workflow"** button
5. Wait 5-10 minutes for the build to complete

### **Step 5: Download Your APK**
1. Go back to **"Actions"** tab
2. Click on the completed workflow run
3. Scroll down to **"Artifacts"** section
4. Download **"callerMax-debug-apk"** (recommended for testing)
5. Or download **"callerMax-release-apk"** (for production)

## 📱 **Install on Your Android Device**

1. **Transfer APK** to your Android device (via USB, email, cloud storage)
2. **Enable Unknown Sources**:
   - Go to Settings → Security → Unknown Sources (enable)
   - Or Settings → Apps → Special Access → Install Unknown Apps
3. **Tap the APK file** to install
4. **Grant permissions** when prompted

## 🔄 **Future Updates**

Whenever you want to update the app:
1. Edit files in your GitHub repository
2. Commit changes
3. GitHub automatically builds a new APK
4. Download the updated APK from Actions

## 🆓 **Why This is Free**

- **GitHub Actions**: 2,000 minutes/month FREE for public repos
- **Public Repository**: Required for free Actions
- **No Credit Card**: Not needed for free tier
- **Unlimited Builds**: As long as you stay under monthly limit

## 🛠️ **What Happens During Build**

The GitHub Actions workflow:
1. ✅ Sets up Android SDK automatically
2. ✅ Installs Java and build tools
3. ✅ Downloads all dependencies
4. ✅ Builds both Debug and Release APKs
5. ✅ Uploads APKs as downloadable artifacts

## 🚨 **Important Notes**

- **Public Repository**: Your code will be visible to everyone (this is required for free Actions)
- **APK Size**: Usually 10-50MB
- **Build Time**: 5-10 minutes per build
- **Storage**: APKs are stored for 90 days

## 🆘 **Troubleshooting**

**Build Failed?**
- Check the Actions log for error messages
- Make sure all files were uploaded correctly
- Try running the workflow again

**Can't Find APK?**
- Look in the "Artifacts" section at the bottom of the workflow run
- Make sure the build completed successfully (green checkmark)

**Permission Issues?**
- Make sure "Unknown Sources" is enabled on your device
- Try downloading the APK again

## 🎉 **You're Done!**

Once you follow these steps, you'll have:
- ✅ A working CallerMax APK
- ✅ Free cloud building setup
- ✅ Easy updates in the future
- ✅ No need for a powerful computer

**Total Cost: $0.00** 💰

Need help with any step? Just ask!
