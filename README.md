# SMART DOORMAN

![image](https://user-images.githubusercontent.com/38436013/106386526-bdb9df80-6418-11eb-9dc2-bc89e80e51ee.png)

2020.11.20 ~ 2020.12.24

삼성 멀티캠퍼스 [융복합 프로제트형 클라우드 서비스 개발] 과정 - 3차 융복합 프로젝트

팀명 : 멀캠유치원문지기

## 제안 배경

1. CCTV를 설치해 모니터링 기능을 강화함으로써 원격지에서 언제든지 스트리밍으로 해당 장소의 상황을 확인할 수 있도록 하는 시스템

2. 외출 직전에 날씨, 마스크 착용, 전원 상태 모두를 문 앞에서 확인할 수 있는 시스템

   => 안면 인식 기반 스마트 도어맨 서비스(외출 케어 시스템, 보안)

   

##  기능 소개

![image](https://user-images.githubusercontent.com/38436013/106386167-08d2f300-6417-11eb-9106-c06a93ed960d.png)

![image](https://user-images.githubusercontent.com/38436013/106386187-1a1bff80-6417-11eb-9930-58fb58f940b4.png)



## CLOUD 

![image](https://user-images.githubusercontent.com/38436013/106386699-99aace00-6419-11eb-80ce-43b2bc52d00d.png)

#### user application(smartdoorman)과 디스플레이 어플리케이션 두가지를 설계

##### smartdoorman application

- user application에서는 amplify와 cognito를 통해 사용자 인증을 하고 얼굴을 등록하며 소켓통신을 통해 안면 영상을 전송

- 람다함수와 api gw를 통해 서버리스로 구성했으며, 다이나모디비에서 데이터를 읽어와 방문자 기록 확인

  

##### **디스플레이 어플리케이션**

- 디스플레이 어플리케이션에서는 날씨, 미세먼지, 택배 데이터를 받아오는 람다를 구현하여 데이터를 다이나모디비에 저장
- AI로부터 마스크 착용 유무 판별 모델을 받아와 파이어베이스를 통해 디스플레이 어플리케이션과 연동



- **lambda + apigateway ?** 
  - 서버리스로 구성하는데 있어 개발과 배포가 비교적 쉽고, 함수 호출 빈도가 낮아 비용절감을 위해 사용

- **iotcore ?**
  - 디바이스간 통신과 aws와의 연동을 위해 사용

- **amplify ?**
  - 개발에 있어 CI/CD를 보완하고자 했으며, 사용자 인증 기능과 api 기능 적용을 위해 사용

- **cognito?**
  - amplify를 통해 손쉽게 로그인 인증을 하기 위해 사용

- **dynamodb ?**
  - 데이터 관계(조인) 설정이 없고, 빠른 데이터 출력 속도를 위해 사용

## AI

![image](https://user-images.githubusercontent.com/38436013/106387796-bf86a180-641e-11eb-9f35-eb46c89f1053.png)

-  실시간으로 판단하여 결과를 출력을 목표로 함
- 모델을 라즈베리파이에서 직접 구동하기때문에 데이터를 모델의 용량과 파라미터의 수 줄이는 것을 목표로 함
- 평균적으로 146mb와 3800만개의 파라미터를 가지는 pretrained 모델들중에서 용량이 14mb이고 파라미터 수가 약 3백50만들개인 모바일넷브이2를 선택

## IoT

#### IoT 센서 구성

![image](https://user-images.githubusercontent.com/38436013/106387807-cc0afa00-641e-11eb-9b95-bfe9b6ddcb37.png)

#### IoT 통신 구성

![image](https://user-images.githubusercontent.com/38436013/106387814-d75e2580-641e-11eb-97f7-a2360de345a2.png)

## 시연 

#### 얼굴 인식과 문 개폐 기능 

- 주인, 외부인, 범죄자 구분
- 방문자 기록 확인 및 알림 기능

![image](https://user-images.githubusercontent.com/38436013/106388294-2dcc6380-6421-11eb-94c1-0236b3ce2580.png)

#### 문 안쪽 디스플레이 기능

- 마스크 착용 확인

- 날씨 미세먼지 택배 정보

- 충격 및 진동 감지 기능

  ![image](https://user-images.githubusercontent.com/38436013/106388309-3755cb80-6421-11eb-88cc-92d558e8a617.png)

## 역할

![image](https://user-images.githubusercontent.com/38436013/106388199-cb736300-6420-11eb-9a3f-b84c3ef78380.png)



## 개선할 점

![image](https://user-images.githubusercontent.com/38436013/106388409-bc40e500-6421-11eb-974d-a3415d319e38.png)
