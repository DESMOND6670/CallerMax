# CallerMax - Cold Calling Android App

A powerful Android application for automated cold calling that calls a list of phone numbers sequentially.

## Features

- **Automated Calling**: Calls numbers from a list one by one automatically
- **Natural Call End**: Waits for calls to end naturally before calling the next number
- **Number Management**: Add, remove, and clear phone numbers from the call list
- **Real-time Status**: Shows current call status and progress
- **Call Tracking**: Displays number of calls made
- **Easy Controls**: Simple start/stop interface

## How to Use

1. **Add Numbers**: Enter phone numbers in the text field and tap "Add"
2. **Start Calling**: Tap "Start Calling" to begin the automated calling process
3. **Natural Flow**: The app calls each number and waits for the call to end naturally
4. **Monitor Progress**: Watch the status updates and call count
5. **Stop Anytime**: Tap "Stop Calling" to halt the process
6. **Clear List**: Use "Clear Number List" to remove all numbers

## Permissions Required

- `CALL_PHONE`: To make phone calls
- `READ_PHONE_STATE`: To monitor call states
- `READ_CONTACTS`: To access contact information
- `MANAGE_OWN_CALLS`: To manage call operations
- `FOREGROUND_SERVICE`: To run the calling service in background

## Technical Details

- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)
- **Language**: Kotlin
- **Architecture**: MVVM with LiveData
- **Service**: Foreground service for continuous calling

## Important Notes

⚠️ **Legal Compliance**: Ensure you comply with local telemarketing laws and regulations before using this app.

⚠️ **Call Duration**: The app waits for calls to end naturally - no automatic hangup timer.

⚠️ **Permissions**: The app requires several sensitive permissions. Make sure to grant them for proper functionality.

## Installation

1. Clone or download this project
2. Open in Android Studio
3. Build and install on your Android device
4. Grant the required permissions when prompted

## How It Works

The app monitors call states and automatically proceeds to the next number when:
- The current call ends naturally (person hangs up)
- The call goes to voicemail and ends
- The call is rejected or fails

No manual intervention needed - the app handles the entire calling sequence automatically.

## Disclaimer

This app is for educational and legitimate business purposes only. Users are responsible for complying with all applicable laws and regulations regarding automated calling and telemarketing.
