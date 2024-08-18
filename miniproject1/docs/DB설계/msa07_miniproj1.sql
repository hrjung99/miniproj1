-- 회원 관리
-- 회원 테이블 생성
CREATE TABLE TB_USER (
user_no NUMBER PRIMARY KEY,
user_id	VARCHAR2(20) NOT NULL,
user_pass VARCHAR2(20) NOT NULL,
user_name VARCHAR2(12) NOT NULL,
user_phone VARCHAR2(20) NOT NULL,
user_addr VARCHAR2(50) NOT NULL,
user_sex VARCHAR2(5) NOT NULL,
user_role VARCHAR2(10) DEFAULT 'user' NOT NULL,
user_delete_YN VARCHAR2(2) DEFAULT 'N' NOT NULL,
-- user_login_recent DATE DEFAULT SYSDATE NOT NULL,
-- user_logout_recent DATE DEFAULT SYSDATE NOT NULL
);

-- 잘못 생각한 옵션 수정
ALTER TABLE TB_USER 
MODIFY (user_login_recent DATE NULL);

ALTER TABLE TB_USER 
MODIFY (user_logout_recent DATE NULL);


-- 회원테이블 시퀸스 생성
CREATE SEQUENCE seq_user_no
INCREMENT BY 1
START WITH 1
NOCACHE;


-- 회원 번호 자동증가를 위해 트리거 적용
CREATE OR REPLACE TRIGGER user_no_auto_increment_trg
BEFORE INSERT ON TB_USER
FOR EACH ROW
BEGIN
    :NEW.user_no := seq_user_no.NEXTVAL;
END;

-- 테스트
INSERT INTO TB_USER (user_id, user_pass, user_name, user_phone, user_addr, user_sex) VALUES ('testuser', '1004', 'user01', '010-1234-5678', '서울특별시', '여');
INSERT INTO TB_USER (user_id, user_pass, user_name, user_phone, user_addr, user_sex) VALUES ('testuser2', '1004', 'user02', '010-1234-5678', '서울특별시', '여');
INSERT INTO TB_USER (user_id, user_pass, user_name, user_phone, user_addr, user_sex) VALUES ('testuser3', '1004', 'user03', '010-1234-5678', '서울특별시', '남');
INSERT INTO TB_USER (user_id, user_pass, user_name, user_phone, user_addr, user_sex) VALUES ('testuser4', '1004', 'user04', '010-1234-5678', '서울특별시', '여');
INSERT INTO TB_USER (user_id, user_pass, user_name, user_phone, user_addr, user_sex) VALUES ('admin', '1004', '관리자', '010-1234-5678', '서울특별시', '여');
COMMIT;
SELECT * FROM TB_USER;









-- 로그인 이력
-- 로그인 테이블 생성
CREATE TABLE TB_LOGIN_HIS (
his_no NUMBER PRIMARY KEY,
user_no	NUMBER NOT NULL,
his_login_date DATE DEFAULT SYSDATE NOT NULL,
his_logout_date DATE DEFAULT SYSDATE NOT NULL,
 CONSTRAINT fk_login_his_user_no FOREIGN KEY(user_no) REFERENCES TB_USER(user_no)
);

-- 잘못 생각한 옵션 수정 (로그인 시 행은 추가 되지만 로그아웃 데이터는 비어있을 수 밖에 없다.)
ALTER TABLE TB_LOGIN_HIS
MODIFY (his_logout_date DATE NULL);


-- 로그인 이력 번호 자동 증가
CREATE SEQUENCE seq_his_no
INCREMENT BY 1
START WITH 1
NOCACHE;


CREATE OR REPLACE TRIGGER his_no_auto_increment_trg
BEFORE INSERT ON TB_LOGIN_HIS
FOR EACH ROW
BEGIN
    :NEW.his_no := seq_his_no.NEXTVAL;
END;


-- 로그인/로그아웃시 (= 회원 테이블의 최근 로그인/로그아웃 행이 수정되면),
-- 해당 행이 로그인 이력 테이블에 자동으로 등록되게 하는 트리거 생성 및 적용
CREATE OR REPLACE TRIGGER login_his_auto_insert_trg
AFTER UPDATE OF user_login_recent ON TB_USER
FOR EACH ROW
BEGIN
    -- 로그인 이력 테이블에 새 이력 추가
    INSERT INTO TB_LOGIN_HIS (
        user_no, 
        his_login_date, 
        his_logout_date
    ) VALUES (
        :NEW.user_no, 
        :NEW.user_login_recent, 
        NULL -- 로그아웃 시간은 NULL로 설정
    );
END;


CREATE OR REPLACE TRIGGER logout_his_auto_update_trg
AFTER UPDATE OF user_logout_recent ON TB_USER
FOR EACH ROW
BEGIN
    -- TB_LOGIN_HIS 테이블에서 로그아웃 시간을 업데이트
    UPDATE TB_LOGIN_HIS
    SET his_logout_date = :NEW.user_logout_recent
    WHERE user_no = :NEW.user_no
    AND his_logout_date IS NULL;
END;


-- 테스트
SELECT * FROM TB_USER;

-- 로그인 시 user_login_recent 업데이트 (테스트)
UPDATE TB_USER
SET user_login_recent = SYSDATE
WHERE user_id = 'testuser';

SELECT * FROM TB_LOGIN_HIS;
COMMIT;

-- 로그아웃 시 user_logout_recent 업데이트 (테스트)
UPDATE TB_USER
SET user_logout_recent = SYSDATE
WHERE user_id = 'testuser';

SELECT * FROM TB_LOGIN_HIS;
COMMIT;






DROP TABLE TB_BOARD;


-- 게시물
-- 게시물 테이블 생성
CREATE TABLE TB_BOARD (
board_no NUMBER PRIMARY KEY NOT NULL,
user_no NUMBER NOT NULL,
board_title VARCHAR2(100)NOT NULL,
board_content VARCHAR2(500) NOT NULL,
board_view_cnt NUMBER DEFAULT 0 NOT NULL,
board_reg_date DATE DEFAULT SYSDATE NOT NULL,
 CONSTRAINT fk_board_user_no FOREIGN KEY(user_no) REFERENCES TB_USER(user_no)
);


-- 게시물 번호 자동증가
CREATE SEQUENCE seq_board_no
INCREMENT BY 1
START WITH 1
NOCACHE;


-- 회원 번호 자동증가를 위해 트리거 적용
CREATE OR REPLACE TRIGGER board_no_auto_increment_trg
BEFORE INSERT ON TB_BOARD
FOR EACH ROW
BEGIN
    :NEW.board_no := seq_board_no.NEXTVAL;
END;


-- 테스트
INSERT INTO TB_BOARD (user_no, board_title, board_content) VALUES (1, '첫 번째 게시물', '이것은 첫 번째 게시물의 내용입니다.');
INSERT INTO TB_BOARD (user_no, board_title, board_content) VALUES (1, '두 번째 게시물', '이것은 두 번째 게시물의 내용입니다.');
INSERT INTO TB_BOARD (user_no, board_title, board_content) VALUES (1, '세 번째 게시물', '이것은 세 번째 게시물의 내용입니다.');
INSERT INTO TB_BOARD (user_no, board_title, board_content) VALUES (1, '네 번째 게시물', '이것은 네 번째 게시물의 내용입니다.');
INSERT INTO TB_BOARD (user_no, board_title, board_content) VALUES (1, '다섯 번째 게시물', '이것은 다섯 번째 게시물의 내용입니다.');

COMMIT;
SELECT * FROM TB_BOARD;







-- BOARD CRUD
-- list
SELECT B.BOARD_NO,
B.USER_NO, 
       U.USER_NAME AS BOARD_WRITER, 
       B.BOARD_TITLE, 
       B.BOARD_VIEW_CNT, 
       CASE 
           WHEN B.BOARD_REG_DATE >= (SYSDATE-1) THEN TO_CHAR(B.BOARD_REG_DATE, 'HH24:MI')
           ELSE TO_CHAR(B.BOARD_REG_DATE, 'YYYY-MM-DD')
       END AS BOARD_REG_DATE
FROM TB_BOARD B 
JOIN TB_USER U ON B.USER_NO = U.USER_NO
ORDER BY B.BOARD_NO ASC;

-- delete
DELETE FROM TB_BOARD WHERE board_no = '1';
-- view
SELECT B.BOARD_NO,
B.USER_NO, 
       U.USER_NAME AS BOARD_WRITER, 
       B.BOARD_TITLE, 
       B.BOARD_VIEW_CNT, B.BOARD_REG_DATE
FROM TB_BOARD B 
JOIN TB_USER U ON B.USER_NO = U.USER_NO
WHERE B.BOARD_NO = 1;
ORDER BY B.BOARD_NO ASC;


UPDATE TB_BOARD SET BOARD_VIEW_CNT = BOARD_VIEW_CNT+1
WHERE BOARD_NO = 1;
-- insert
INSERT INTO TB_BOARD (user_no, board_title, board_content) VALUES (1, '첫 번째 게시물', '이것은 첫 번째 게시물의 내용입니다.');
-- update
UPDATE TB_BOARD SET BOARD_TITLE = '게시물제목1', BOARD_CONTENT = '게시물내용1'WHERE BOARD_NO = 1;
SELECT * FROM TB_BOARD TB;
COMMIT;





-- USER CRUD
-- list
SELECT * FROM TB_USER;

-- view
-- delete
-- insert
INSERT INTO TB_USER 
(user_id, user_pass, user_name, user_phone, user_addr, user_sex) 
VALUES 
('testuser', '1004', 'user01', '010-1234-5678', '서울특별시', '여');


-- update