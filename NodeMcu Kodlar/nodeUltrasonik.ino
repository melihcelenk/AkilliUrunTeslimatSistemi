#include <FirebaseArduino.h>
#include <ESP8266WiFi.h>

#define FIREBASE_HOST "akilli-urun-teslimat-sistemi.firebaseio.com"
#define FIREBASE_AUTH "GtCYEbErRC9LCy9Yh0mvNIVVErBd1v3m1IIhyd5W"

/* Kablosuz Ağ Bilgileri */
const char*  WLAN_SSID  =    "****************************";   // "Kablosuz Ağ Adı" 
const char*  WLAN_PASSWORD = "****************************";  // "Kablosuz Ağ Şifresi"
WiFiClient client;

#define FURKANTRIGGER 5   // d1
#define FURKANECHO 4   // d2

#define MELIHECHO 0 // d3
#define MELIHTRIGGER 2 // d4

#define FURKANID 0
#define MELIHID 1

/* ESP8266 WiFi Kurulum Fonksiyonu */
void WiFi_Setup() {
  delay(10);
  Serial.println(); Serial.print(WLAN_SSID);
  Serial.print("Kablosuz Agina Baglaniyor");
  WiFi.begin(WLAN_SSID, WLAN_PASSWORD);
  // WiFi durum kontrolü
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println(); Serial.print(WLAN_SSID);
  Serial.println("Kablosuz Agina Baglandi");
  Serial.println("IP adresi: ");
  Serial.println(WiFi.localIP());
}

  int furkanDoluMuSayac;
  int furkanBosMuSayac;
  int melihDoluMuSayac;
  int melihBosMuSayac;
  bool furkanOncekiDoluMu;
  bool melihOncekiDoluMu;
  
void setup() {
  
  Serial.begin (9600);
  pinMode(FURKANTRIGGER, OUTPUT);
  pinMode(FURKANECHO, INPUT);
  pinMode(MELIHTRIGGER, OUTPUT);
  pinMode(MELIHECHO, INPUT);

  WiFi_Setup();          //Kablosuz ağ bağlantı fonksiyonu
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  furkanDoluMuSayac = 0;
  furkanBosMuSayac = 0;
  melihDoluMuSayac = 0;
  melihBosMuSayac = 0;
  
}

  
void loop() {
  
    int furkanMesafe = UltrasonikOlc(FURKANTRIGGER, FURKANECHO);
    Serial.print("Furkan:");
    Serial.println(furkanMesafe);
    if (furkanMesafe < 10) { furkanDoluMuSayac++; furkanBosMuSayac = 0; }
    else { furkanBosMuSayac++; furkanDoluMuSayac = 0;  }
    
    delay(200);
          
    int melihMesafe = UltrasonikOlc(MELIHTRIGGER, MELIHECHO);
    Serial.print("Melih:");
    Serial.println(melihMesafe);
    if (melihMesafe < 10) { melihDoluMuSayac++; melihBosMuSayac = 0; }
    else { melihBosMuSayac++; melihDoluMuSayac = 0; }
    delay(200);
  
  if(furkanDoluMuSayac >= 3 && !furkanOncekiDoluMu){
    FirebaseGonder(true,FURKANID);
    furkanDoluMuSayac = 0;
    furkanBosMuSayac = 0;
    furkanOncekiDoluMu = true;
  }
  if(furkanBosMuSayac >= 3 && furkanOncekiDoluMu){
    FirebaseGonder(false,FURKANID);
    furkanDoluMuSayac = 0;
    furkanBosMuSayac = 0;
    furkanOncekiDoluMu = false;
  }
  if(melihDoluMuSayac >= 3 && !melihOncekiDoluMu){
    FirebaseGonder(true,MELIHID);
    melihDoluMuSayac = 0;
    melihBosMuSayac = 0;
    melihOncekiDoluMu = true;
  }
  if(melihBosMuSayac >= 3 && melihOncekiDoluMu){
    FirebaseGonder(false,MELIHID);
    melihDoluMuSayac = 0;
    melihBosMuSayac = 0;
    melihOncekiDoluMu = false;
  }
  
  
}

void FirebaseGonder(bool doluMu, int kisi){
  if (kisi == FURKANID) {
    Firebase.setBool("furkan/doluMu", doluMu);
    Serial.print("\nFurkan için gönderilen veri:"); 
    Serial.println(doluMu);
  }
  else if (kisi == MELIHID) {
    Firebase.setBool("melih/doluMu", doluMu);
    Serial.print("\nMelih için gönderilen veri:"); 
    Serial.println(doluMu);
  }
}


int UltrasonikOlc(int trigger, int echo){

  long duration, distance;
  digitalWrite(trigger, LOW);  
  delayMicroseconds(2); 
  digitalWrite(trigger, HIGH);
  delayMicroseconds(10); 
  digitalWrite(trigger, LOW);
  
  duration = pulseIn(echo, HIGH);
  distance = (duration/2) / 29.1;
  
  return distance;
}
