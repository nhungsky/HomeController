#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>
#define FIREBASE_HOST "familyelectroniccontrolserver.firebaseio.com" //Thay bằng địa chỉ firebase của bạn
#define FIREBASE_AUTH ""   //Không dùng xác thực nên không đổi
#define WIFI_SSID "KLTN"   //Thay wifi và mật khẩu
#define WIFI_PASSWORD "22121223"

#define DEN_TV 12// GPIO 12
#define DEN_VUON 13// GPIO 13
#define DEN_PHONG 14// GPIO 14
#define DEN_QUAT 15// GPIO 15


#define PATH_DEN_VUONG "DEN_VUON"
#define PATH_DEN_PHONG "DEN_PHONG"
#define PATH_DEN_QUAT "DEN_QUAT"
#define PATH_DEN_TV "DEN_TV"

#define PATH_DANG_HOAT_DONG "DANG_HOAT_DONG"

void setupWifi() {
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("connecting");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
  Serial.println();
  Serial.print("connected: ");
  Serial.println(WiFi.localIP());
}

void setupFirebase() {
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
}

void setupOutput() {
  pinMode(DEN_VUON, OUTPUT);
  pinMode(DEN_PHONG, OUTPUT);
  pinMode(DEN_TV, OUTPUT);
  pinMode(DEN_QUAT, OUTPUT);
}

void setup() {
  Serial.begin(9600);

  setupOutput();

  setupWifi();

  setupFirebase();

}
void loop() {
  // Module Func đọc và cập nhật trạng thái của đèn vườn
  docTrangThaiDenVuon();

  docTrangThaiDenPhong();

  docTrangThaiDenQuat();

  docTrangThaiDenTV();

  // Thông báo rằng thiết bị vẫn đang hoạt động
  capNhatDangHoatDong();
}

// Đọc trạng thái của đèn vườn từ Firebase
void capNhatDangHoatDong() {
  Firebase.setInt(PATH_DANG_HOAT_DONG, 1);
  // handle error
  if (Firebase.failed()) {
    Serial.print("setting /number failed:");
    Serial.println(Firebase.error());
    return;
  }
  delay(200);
}

// Đọc trạng thái của đèn vườn từ Firebase
void docTrangThaiDenVuon() {
  int state = Firebase.getInt(PATH_DEN_VUONG);
  // handle error
  if (Firebase.failed()) {
    Serial.print("setting /number failed:");
    Serial.println(Firebase.error());
    return;
  }
  digitalWrite(DEN_VUON, state);
  delay(10);
}

void docTrangThaiDenPhong() {
  int state = Firebase.getInt(PATH_DEN_PHONG);
  // handle error
  if (Firebase.failed()) {
    Serial.print("setting /number failed:");
    Serial.println(Firebase.error());
    return;
  }

  digitalWrite(DEN_PHONG, state);
  delay(10);
}

void docTrangThaiDenQuat() {
  int state = Firebase.getInt(PATH_DEN_QUAT);
  // handle error
  if (Firebase.failed()) {
    Serial.print("setting /number failed:");
    Serial.println(Firebase.error());
    return;
  }

  digitalWrite(DEN_QUAT, state);
  delay(10);
}

void docTrangThaiDenTV() {
  int state = Firebase.getInt(PATH_DEN_TV);
  // handle error
  if (Firebase.failed()) {
    Serial.print("setting /number failed:");
    Serial.println(Firebase.error());
    return;
  }

  digitalWrite(DEN_TV, state);
  delay(10);
}
