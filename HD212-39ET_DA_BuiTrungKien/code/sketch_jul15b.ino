#include <Wire.h>
#include "MAX30100_PulseOximeter.h"
#include <SimpleKalmanFilter.h>
#include <Adafruit_GFX.h>
#include <Adafruit_SSD1306.h>
#include<ESP8266WiFi.h>
#include<FirebaseArduino.h>
#include<Adafruit_Sensor.h>
#include<Adafruit_ADXL345_U.h>
#include<string.h>

#define max_delay     5100
#define fall_delay     10
#define FIREBASE_HOST "data-station-c589b-default-rtdb.firebaseio.com" 
#define FIREBASE_AUTH ""   
#define WIFI_SSID "Kien"  
#define WIFI_PASSWORD "23061999"
int status_fall = 0;
int warning = 15;
PulseOximeter pox;
SimpleKalmanFilter filter(2,2,0.001);
Adafruit_SSD1306 display(128, 64, &Wire, -1);
Adafruit_ADXL345_Unified fall = Adafruit_ADXL345_Unified();
float c_past = 0;
float c_present = 0;
int count = 0;

uint32_t detect_time = 0;
uint32_t max_pre = 0;
uint32_t fall_pre = 0;
int bpm,spo2;
char BPM[5], SPO2[5];

void setup() {
  Serial.begin(115200);
  
  pinMode(warning,OUTPUT);
  
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  
  fall.begin();
  pox.begin();
  //pox.setIRLedCurrent(MAX30100_LED_CURR_7_6MA);
  pox.setOnBeatDetectedCallback(onBeatDetected);
  display.begin(SSD1306_SWITCHCAPVCC, 0x3C);
  display.clearDisplay();
  
}
void onBeatDetected(){}

void loop() {
 
  pox.update();
  if (millis() - max_pre > max_delay) { 
    bpm = pox.getHeartRate();
    spo2 = pox.getSpO2();
    
    bpm = filter.updateEstimate(bpm);
    bpm = filter.updateEstimate(bpm);
    
    pox.shutdown();
    
    if(spo2 > 90){
      display_data_max30100(bpm,spo2);
      itoa(bpm,BPM,10);
      itoa(spo2,SPO2,10);
      Firebase.setString("custom/122313756/Oxy", SPO2);
      Firebase.setString("custom/122313756/Heartbeat", BPM);
    }
    pox.resume();
    max_pre = millis();
    }
    if(millis() - fall_pre > fall_delay){
      fall_detect();
      fall_pre = millis();
    }
}
void display_data_max30100(int bpm, int spo2) {
  display.clearDisplay();
  display.setTextSize(1);
  display.setTextColor(WHITE);
  display.setCursor(0, 0);
  display.print("Name: ");
  display.setCursor(33,0 );
  display.println("Bui Trung Kien");
  display.setCursor(0, 10);
  display.print("Phone: ");
  display.setCursor(37, 10);
  display.println("0869397417");
  display.setCursor(0, 20);
  display.print("BPM ");
  display.print(bpm);
  display.setCursor(64, 20);
  display.print("Spo2% ");
  display.println(spo2);
  display.display();
}
void fall_detect(){
  pox.shutdown();
  sensors_event_t event;
  fall.getEvent(&event);
  float d = sqrt(event.acceleration.x*event.acceleration.x+event.acceleration.y*event.acceleration.y+event.acceleration.z*event.acceleration.z);
  Serial.println(d);
  if(d < 2.5){
    status_fall = 1;
    Firebase.setString("custom/122313756/Fall","1" );
    digitalWrite(warning,HIGH);
    detect_time = millis();
  }
  if(status_fall == 1 && millis() % 100 == 0 ){
    c_present = event.acceleration.x;
    float fall_recheck = abs(c_present-c_past);
    if(fall_recheck >1 && count > 1){
      Firebase.setString("custom/122313756/Fall","0");
      digitalWrite(warning,LOW);
    }
    c_past = c_present;
  count = count + 1;
  }
  pox.resume();
}
