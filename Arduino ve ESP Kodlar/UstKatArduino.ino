#include <Servo.h>
#include <AFMotor.h>
Servo firlaticiServo;               // Define any servo name
Servo melihServo;               // Define any servo name
const byte numChars = 32;
char receivedChars[numChars]; // an array to store the received data
boolean yeniKomut = false;

void setup() {
  firlaticiServo.attach(9);// select servo pin(9 or 10)
  melihServo.attach(10);          // Define the servo signal pin
  firlaticiServo.write(0);
  melihServo.write(10);          // Define the servo signal pin
  Serial.begin(115200);
}

void loop() {
  komutuAl();
  komutuIsle();
}

void urunGonder(String kullaniciAdi) {
  if (kullaniciAdi == "melih"){
    melihServo.write(50);
  }
    
  delay(100);
  firlaticiServo.write(80);
  delay(300);
  firlaticiServo.write(0);

}
void servoPasifEt(String kullanici) {
  if (kullanici == "melih")
    melihServo.write(0);
}

void servoAktifEt(String kullanici) {
  if (kullanici == "melih")
    melihServo.write(180);
}

void komutuIsle() {
  if (yeniKomut == true) {
    String komut = String(receivedChars);
    komut.trim();
    Serial.println(komut);
    if (komut == "urunGonder_furkan") {
      urunGonder("furkan");
    }
    else if (komut == "urunGonder_melih") {
      urunGonder("melih");
    }
    else if (komut == "servoPasifEt_melih") {
      servoPasifEt("melih");
    }
    yeniKomut = false;
  }
}

void komutuAl() {
  static byte ndx = 0;
  char endMarker = '\n';
  char rc;

  while (Serial.available() > 0 && yeniKomut == false) {
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
      yeniKomut = true;
    }
  }
}

