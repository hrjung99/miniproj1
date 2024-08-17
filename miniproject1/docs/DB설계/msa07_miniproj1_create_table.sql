-- 회원 관리
-- 회원 테이블 생성
CREATE TABLE TB_USER (
user_num NUMBER, PRIMARY KEY,NOT NULL,
user_id	VARCHAR2(20), NOT NULL,
user_pass VARCHAR2(20), NOT NULL,
user_name VARCHAR2(12), NOT NULL,
user_phone VARCHAR2(20), NOT NULL,
user_addr VARCHAR2(50), NOT NULL,
user_sex VARCHAR2(2), NOT NULL,
user_role VARCHAR2(10), DEFAULT 'user', NOT NULL,
user_delete_YN VARCHAR2(2), DEFAULT 'N', NOT NULL,
user_login_recent DATE, DEFAULT SYSDATE, NOT NULL,
user_logout_recent DATE, DEFAULT SYSDATE, NOT NULL
);

-- 회원테이블 시퀸스 생성
CREATE SEQUENCE “스키마명.시퀀스명”
MAXVALUE 99999
INCREMENT BY 1
START WITH 1
NOCHACHE
NOCYCLE;

-- 회원 번호 자동증가를 위해 트리거 적용
-- 트리거 생성

-- 트리거 적용






-- 로그인 이력
-- 로그인 테이블 생성

-- 로그인 이력 번호 자동 증가


-- 로그인/로그아웃시 (= 회원 테이블의 최근 로그인/로그아웃 행이 수정되면),
-- 해당 행이 로그인 이력 테이블에 자동으로 등록되게 하는 트리거 생성 및 적용











-- 게시물
-- 게시물 테이블 생성


-- 게시물 번호 자동증가


-- 
