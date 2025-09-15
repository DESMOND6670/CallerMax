# 🚀 Simple GitHub Upload Guide

## 📋 **Step 1: Create GitHub Repository**

1. **Go to** https://github.com
2. **Click "New repository"** (green button)
3. **Repository name:** `CallerMax`
4. **Make it PUBLIC** (required for free Actions)
5. **Click "Create repository"**

## 📤 **Step 2: Upload Files**

### **Method 1: Drag & Drop (Easiest)**

1. **Go to your new repository**
2. **Click "uploading an existing file"**
3. **Drag and drop these files/folders:**
   - `gradlew` and `gradlew.bat`
   - `build.gradle` (root level)
   - `settings.gradle`
   - `gradle.properties`
   - `app/` folder (entire folder)
   - `gradle/` folder (entire folder)
   - `.github/` folder (entire folder)

4. **Add commit message:** "Add CallerMax app files"
5. **Click "Commit changes"**

### **Method 2: Using Git (If you have it installed)**

```bash
git init
git add .
git commit -m "Add CallerMax app"
git remote add origin https://github.com/YOUR_USERNAME/CallerMax.git
git push -u origin main
```

## 🔨 **Step 3: Build APK**

1. **Go to "Actions" tab** in your repository
2. **Click "Build CallerMax APK"**
3. **Click "Run workflow"** (green button)
4. **Wait 5-10 minutes** for build to complete
5. **Click on the completed workflow run**
6. **Scroll down to "Artifacts"**
7. **Download "callerMax-debug-apk"**

## 📱 **Step 4: Install on Phone**

1. **Transfer APK** to your Android device
2. **Enable "Unknown Sources"** in Settings → Security
3. **Tap APK** to install
4. **Grant permissions** when asked

## ✅ **That's It!**

- **Total time:** 15 minutes
- **Cost:** $0
- **RAM usage:** 0 (builds in cloud)

## 🆘 **Need Help?**

**Build failed?** Check the Actions log for error messages
**Can't find APK?** Look in the "Artifacts" section
**Permission issues?** Make sure "Unknown Sources" is enabled
