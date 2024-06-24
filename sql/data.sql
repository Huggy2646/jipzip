-- 테스트 데이터 

-- 회원 정보 저장
-- ssafy, admin => pw:1234 
insert into `jipzip`.`users` (id, pw, pkey, joindate)
values 	('ssafy', '310c4a1b75e6c6aeea48b585de0d1c', '-106 -73 -85 -48 -1 -4 2 75 -77 83 -127 79 -59 -116 -46 -85', now()), 
		('admin', '8d02dcff7f4438880af4d3adef012a', '-15 5 104 -122 17 63 -114 5 -20 72 13 -19 17 -86 50 -87', now());
        
insert into `jipzip`.`userinfo` (id, role, name, adress, email, mobile_number)
values ('ssafy', '일반회원', '김싸피', '부울경시 싸피동 11구', 'ssafy@ssafy.com', '010-1234-5678'),
		('admin', '관리자', '관리자', '부울경시 싸피동 11구', 'ssafy@ssafy.com', '010-1234-5678');

-- 관심 지역 저장
insert into `jipzip`.`interestedarea` (id, dongCode)
values ('ssafy', '1111010500'),
		('admin', '2611012200'),
        ('ssafy', '2611012200'),
        ('ssafy', '2714010500');