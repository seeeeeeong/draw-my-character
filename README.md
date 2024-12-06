<div align="center">
  <br>
  <h2> AI Service </h2>
  <h1> OpenAI의 ChatGPT를 활용하여 이미지 기반 캐릭터 생성 및 변형 서비스를 제공합니다.</h1>
</div>
<br>

- [프로젝트 소개](#프로젝트-소개)
    * [프로젝트 기능](#프로젝트-기능)
- [기술 스택](#기술-스택)
- [ERD](#erd)
- [프로젝트 wiki](#프로젝트-wiki)
- [패키지 구조](#패키지-구조)


## 프로젝트 소개

'AI Service'는 OpenAI의 ChatGPT를 활용하여 이미지 기반 캐릭터 생성 및 변형 서비스를 제공합니다.

### 프로젝트 기능

[서비스 세부 기능](/docs/service_detail.md)

## 기술 스택

- Java 21
- Gradle 
- Spring Boot 3.3.5
- Spring Data JPA
- Spring Security
- QueryDSL 
- PostgreSQL
- SpringAI
- DALL-E 3

## ERD

![ERD](https://github.com/user-attachments/assets/a4fd6249-89f7-4535-bae8-9a87d4bedb81)

## 프로젝트 wiki

프로젝트를 경험하면서 알게된 지식, 경험을 정리한 위키입니다.

[프로젝트 위키](https://few-monkey-6ee.notion.site/154a674672d98052a540f4563108db49?v=154a674672d981cab258000ca4218a60)

## 패키지 구조

[패키지 구조 설명](/docs/package_structure.md)

## 이미지 생성 플로우

이미지 생성 시, Open AI의 DALL-E 모델을 통해서 이미지를 생성 및 변형하는 과정을 포함하고 있어 여러 단계로 나누어져 있습니다.

![이미지 생성 플로우](https://github.com/user-attachments/assets/6e7d23ed-a07d-47a4-911e-d64c52ed7d84)


