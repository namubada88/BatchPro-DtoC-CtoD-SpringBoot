# 제목 : 스프링 배치 DB to CSV and CSV to DB
## 주요기능 : 
1. DB를 CSV 파일로 생성 및 다른 폴더로 복사
2. CSV파일을 DB로 복사하기전 테이블 초기화 후 DB로 내용 복사

## <커밋별 추가사항은 아래 확인>
## First commit
- DB를 CSV 파일로 생성 및 다른 폴더로 복사완료
- CSV 파일을 DB로 복사하기전 테이블 초기화 후 DB로 내용 복사

## Second commit
- CSV파일 생성완료 후 fin 파일 생성
- fin 파일 있을 경우 SecondJOb의 step 실행 없을 경우 step 미진행
