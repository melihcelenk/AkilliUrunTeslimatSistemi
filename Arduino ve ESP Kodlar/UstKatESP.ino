/* ESP8266 WiFi Kütüphane Dosyası */
#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>

const byte numChars = 32;
char receivedChars[numChars]; // an array to store the received data
boolean newData = false;

#define FIREBASE_HOST "akilli-urun-teslimat-sistemi.firebaseio.com"
#define FIREBASE_AUTH "GtCYEbErRC9LCy9Yh0mvNIVVErBd1v3m1IIhyd5W"

/* Kablosuz Ağ Bilgileri */
const char*  WLAN_SSID  =    "*************************************";   // "Kablosuz Ağ Adı"
const char*  WLAN_PASSWORD = "*************************************";  // "Kablosuz Ağ Şifresi"
WiFiClient client;

int toplamIstek = 0;
int hasilat = 0;

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

void setup() {
  Serial.begin(115200);  // Seri port baud rate ayarı
  WiFi_Setup();          //Kablosuz ağ bağlantı fonksiyonu
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
}

void loop() {
  for (int i = 0; i < 10; i++) {
    calis();
  }
}

void recvWithEndMarker() {
  static byte ndx = 0;
  char endMarker = '\n';
  char rc;

  while (Serial.available() > 0 && newData == false) {
    rc = Serial.read();

    if (rc != endMarker) {
      receivedChars[ndx] = rc;
      ndx++;
      if (ndx >= numChars) {
        ndx = numChars - 1;
      }
    }
    else {
      receivedChars[ndx] = '\0'; // terminate the string
      ndx = 0;
      newData = true;
    }
  }
}

void calis() {
  toplamIstek = 0;
  FirebaseObject furkan = Firebase.get("furkan");
  FirebaseObject melih = Firebase.get("melih");
  
  toplamIstek += furkan.getBool("istekVarMi");
  toplamIstek += melih.getBool("istekVarMi");
  if (toplamIstek > 0) {
    if (furkan.getBool("istekVarMi") && furkan.getInt("bakiye") > 0)
      kullaniciIcinCalis("furkan");
    else if (melih.getBool("istekVarMi") && melih.getInt("bakiye") > 0)
      kullaniciIcinCalis("melih");
  }
  else {
    delay(3000);  // 3 saniye bekle
  }

  otomatikIstekAta(furkan, "furkan");
  otomatikIstekAta(melih, "melih");
}

void urunGonder(String kullaniciAdi) {
  Serial.println("urunGonder_" + kullaniciAdi);
}

void servoPasifEt(String kullaniciAdi) {
  Serial.println("servoPasifEt_" + kullaniciAdi);
}

void servoAktifEt(String kullaniciAdi) {
  Serial.println("servoAktifEt_" + kullaniciAdi);
}

void istekVarMiAta(String kullaniciAdi, boolean deger) {
  Firebase.setBool(kullaniciAdi + "/istekVarMi", deger);
}

void bakiyeyiDusur(String kullaniciAdi, int mevcutBakiye) {
  Firebase.setInt(kullaniciAdi + "/bakiye", mevcutBakiye - 1);
  hasilat++;
}

void otomatikIstekAta(FirebaseObject fbObject, String kullaniciAdi){
  if(fbObject.getBool("otomatikAl") && !fbObject.getBool("doluMu"))
    istekVarMiAta(kullaniciAdi, true);
}

void kullaniciIcinCalis(String kullaniciAdi) {
  urunGonder(kullaniciAdi);
  delay(4000); // 4 saniye bekle
  FirebaseObject fbObject = Firebase.get(kullaniciAdi);
  if (fbObject.getBool("doluMu")) {
    if (kullaniciAdi == "melih")
      servoPasifEt(kullaniciAdi); 
    istekVarMiAta(kullaniciAdi, false);
    bakiyeyiDusur(kullaniciAdi, fbObject.getInt("bakiye"));
    toplamIstek--;
  }
}
