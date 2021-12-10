2021-12-09  
간단하게 redis를 스프링부트와 연동 하는 테스트입니다  
redis설치후  
config로 등록후  
사용  
2021-12-10  
aws sqs 연동  성공  
#이게 꼭있어야한다   
cloud.aws.stack.auto=false  
@SqsListener("testsqs")를 사용해서  
sqs에 접근해서 빼올 수있다  

aws sns 연동성공  
다만 전체 전송이되는데 개별 전송 되는법을 찾아봐야겠다  

mybatis연동성공   
