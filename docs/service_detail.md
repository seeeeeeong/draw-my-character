
# 이미지 생성

## 서비스 개요

기존에 image-to-image 방식으로 기능을 구현하고자 했다.

하지만 대부분 러닝 커브가 높고 복잡한 설정으로 개인 프로젝트에 도입하기에는 어렵다고 판단하였다.

예) Stable Diffusion 등

이러한 이유로, Spring AI의 ChatGPT를 활용하여 text-to-image 기능을 통해
최대한 image-to-image 방식 처럼 구현하는 방법을 생각하였다.

## 구현 로직

## 1. 이미지 제공 및 특성 분석

- 이미지를 제공하면 그 이미지에 대한 특성을 분석한다.

이미지 요청 예시

￼![e00c7a74999e15e1527a43c17018aa36_400x400](https://github.com/user-attachments/assets/23a97e7b-e87d-4fd1-a33a-ba71011fcbe0)

특성 응답 예시

- 이 이미지는 애니메이션 스타일의 캐릭터입니다. 검은 머리와 검은 옷을 입고 있으며, 등에 칼을 메고 있고 윙크를 하고 있습니다. 캐릭터는 자신감 있는 표정을 짓고 있습니다.


## 2. 특성 분석을 통한 이미지 생성

- 위에서 분석한 특성 정보를 바탕으로, text-to-image 방식으로 이미지를 생성한다.

이미지 응답 예시

￼![img-EC16HtwUrCYjeFfabc71yRp0](https://github.com/user-attachments/assets/86cfbd25-bab2-4f63-8ff3-3e29dda83897)

## 3. 특성 저장 및 변형 요청

- 이미지의 특성을 저장한 후, 사용자 요청에 따라 이미지를 변형한다.

요청 예시

{
"characterId" : 1,
"action" : "울고 있는 모습 그려줘”,
"numberOfImages" : 1
}

이미지 응답 예시

￼![img-jN8iwHyM3MKxYydicXjnvMxS](https://github.com/user-attachments/assets/932c9134-f260-4d94-8f3e-dfc0fcead913)

