# Todo App
투두 앱 REST API 서버    

> ### 개발 환경
>     Kotlin / Spring Boot(3.2.0) / Spring Data JPA / PostgreSQL

## 요구사항
- 할 일을 작성합니다.
- 선택한 할 일을 조회합니다.
  - 연관된 댓글 목록을 함께 조회합니다.
- 할 일 목록을 조회합니다.
  - 작성일 기준 내림차순/오름차순으로 정렬합니다.
  - 작성자를 기준으로 필터합니다.
- 선택한 할 일을 수정합니다.
- 선택한 할 일을 삭제합니다.
- 할 일을 완료합니다.
- 할 일에 댓글을 작성합니다. 작성자 이름과 비밀번호를 함께 받습니다.
- 선택한 댓글을 수정합니다.
- 선택한 댓글을 삭제합니다.
- 댓글 수정, 삭제 시 작성자 이름과 비밀번호가 일치해야 합니다.
- 할 일 작성, 수정 시 제목이 1자 이상 200자 이내, 내용이 1자 이상 1000자 이하여야 합니다.

## ERD
![ERD](https://github.com/adorecamus/todo-app/assets/115597692/fa9e3271-dee0-41e2-a473-cc36fd4de14a)

## API 명세서
Link: [SwaggerHub](https://app.swaggerhub.com/apis-docs/YuminChoi/To-do/2.0.0)
