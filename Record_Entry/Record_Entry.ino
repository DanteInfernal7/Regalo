#include <Adafruit_Fingerprint.h>
#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>
#include <ArduinoJson.h>
#include <ezTime.h>
#include <WiFiUdp.h>
#include <Wire.h> 
#include <LiquidCrystal_I2C.h>

// Set these to run example.
#define FIREBASE_HOST "attendance-management-sy-3b5fd.firebaseio.com"
#define FIREBASE_AUTH "jRIMungXL6P0CwkXRfvGWFJHlERWkgtUzqBsZYM3"
#define WIFI_SSID "Skynet"
#define WIFI_PASSWORD "6461726b736f756c73"

SoftwareSerial mySerial(12, 14);
LiquidCrystal_I2C lcd(0x27,16,2);

Adafruit_Fingerprint finger = Adafruit_Fingerprint(&mySerial);

Timezone myLocalTime;

unsigned int localPort = 2390;
String date;
String present = "Present";
String inputname;
String n;

//______________________________________________________________________________________________________________Declarations
void setup() {
  Serial.begin(9600);

  lcd.init();
  lcd.backlight();
//______________________________________________________________________________________________________________LCD Display
  // connect to wifi.
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Connecting");
  lcd.setCursor(0,0);
  lcd.print("Connecting");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);  
  }
  Serial.println();
  Serial.print("Connected: ");
  lcd.clear();
  lcd.setCursor(0,0); 
  lcd.print("Connected");

  
  Serial.println(WiFi.localIP());

  waitForSync();
//______________________________________________________________________________________________________________Wifi
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
//______________________________________________________________________________________________________________Firebase
  myLocalTime.setLocation(F("IN"));
  date = String(myLocalTime.dateTime("d-m-Y"));
  waitForSync();
  delay(2000);
//______________________________________________________________________________________________________________Time
  while (!Serial);  // For Yun/Leo/Micro/Zero/...
  delay(100);
  Serial.println("\n\nAdafruit finger detect test");

  // set the data rate for the sensor serial port
  finger.begin(57600);
  delay(5);
  if (finger.verifyPassword()) {
    Serial.println("Found fingerprint sensor!");
  } else {
    Serial.println("Did not find fingerprint sensor :(");
    while (1) { delay(1); }
  }

  finger.getTemplateCount();
  Serial.print("Sensor contains "); Serial.print(finger.templateCount); Serial.println(" templates");
  Serial.println("Waiting for valid finger...");
  lcd.clear();
  lcd.setCursor(0,0);
  lcd.print("Place finger");
  lcd.setCursor(0,1);
  lcd.print("on scanner");
}


void loop() {
  getFingerprintIDez();
  
  delay(1000);
}
//______________________________________________________________________________________________________________Fingerprint Scanner
uint8_t getFingerprintID() {
  uint8_t p = finger.getImage();
  switch (p) {
    case FINGERPRINT_OK:
      Serial.println("Image taken");
      lcd.clear();
      lcd.setCursor(0,0);
      lcd.print("Image taken");
      break;
    case FINGERPRINT_NOFINGER:
      Serial.println("No finger detected");
      return p;
    case FINGERPRINT_PACKETRECIEVEERR:
      Serial.println("Communication error");
      return p;
    case FINGERPRINT_IMAGEFAIL:
      Serial.println("Imaging error");
      return p;
    default:
      Serial.println("Unknown error");
      return p;
  }

  // OK success!

  p = finger.image2Tz();
  switch (p) {
    case FINGERPRINT_OK:
      Serial.println("Image converted");
      break;
    case FINGERPRINT_IMAGEMESS:
      Serial.println("Image too messy");
      return p;
    case FINGERPRINT_PACKETRECIEVEERR:
      Serial.println("Communication error");
      return p;
    case FINGERPRINT_FEATUREFAIL:
      Serial.println("Could not find fingerprint features");
      return p;
    case FINGERPRINT_INVALIDIMAGE:
      Serial.println("Could not find fingerprint features");
      return p;
    default:
      Serial.println("Unknown error");
      return p;
  }
  
  // OK converted!
  p = finger.fingerFastSearch();
  if (p == FINGERPRINT_OK) {
    Serial.println("Found a print match!");
    lcd.clear();
    lcd.setCursor(0,0);
    lcd.print("Found a match!");
  } else if (p == FINGERPRINT_PACKETRECIEVEERR) {
    Serial.println("Communication error");
    return p;
  } else if (p == FINGERPRINT_NOTFOUND) {
    Serial.println("Did not find a match");
    return p;
  } else {
    Serial.println("Unknown error");
    return p;
  }   
  
  // found a match!
  Serial.print("Found ID #"); Serial.print(finger.fingerID); 
  Serial.print(" with confidence of "); Serial.println(finger.confidence);
  String fingerrec = String(finger.fingerID);
  enterRecord(fingerrec);

  return finger.fingerID;
}

// returns -1 if failed, otherwise returns ID #
int getFingerprintIDez() {
  uint8_t p = finger.getImage();
  if (p != FINGERPRINT_OK)  return -1;

  p = finger.image2Tz();
  if (p != FINGERPRINT_OK)  return -1;

  p = finger.fingerFastSearch();
  if (p != FINGERPRINT_OK)  return -1;
  
  // found a match!
  Serial.print("Found ID #"); Serial.print(finger.fingerID); 
  Serial.print(" with confidence of "); Serial.println(finger.confidence);
  String fingerrec = String(finger.fingerID);
  enterRecord(fingerrec);
  
  return finger.fingerID; 
}

//______________________________________________________________________________________________________________Scanning

void enterRecord(String n){
  String path = ("Employees/"+n+"/name");
  inputname=Firebase.getString(path);
  String Input = (inputname+date);
  Serial.println(Input);
  lcd.clear();
  lcd.setCursor(0,0);
  lcd.print("Hello!");
  lcd.setCursor(0,1);
  lcd.print(inputname);
  delay(5000);
  lcd.clear();
  lcd.setCursor(0,0);
  lcd.print("Place finger");
  lcd.setCursor(0,1);
  lcd.print("on scanner");
  String fireenterdate ("Attendance/"+Input+"/attendanceAttend");
  String fireenterattend ("Attendance/"+Input+"/attendanceName");
  Firebase.setString(fireenterdate, date);
  Firebase.setString(fireenterattend, "Present");
  
  delay(200);
}
//______________________________________________________________________________________________________________Record Entry
