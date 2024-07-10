# Socket IO 기반 Notification 시스템 

## Flow
1. 사용자가 로그인
2. 서버는 사용자에게 JWT 발급
3. 사용자는 받은 JWT 토큰을 이용해 Socket IO 서버 접속
4. 각 Socket IO 인스턴스는 자신에게 접속한 사용자 커넥션을 사용자 ID로 관로
5. 서버에서 특정 사용자에게 알람 메시지 영속화 및 알람 전송 이벤트 생성(Redis Pub/Sub)
6. Socket IO 인스턴스는 Redis 이벤트를 구독 후 알람 전송 이벤트가 발생하면 자신의 연결 목록에서
   사용자를 찾아 메시지 전송

## 실행 방법
<span style="font-size: 12px; color: green">모든 Kubernetes file의 HostPath를 수정할 것</span>
1. redis 시작 kubectl apply -f redis.yaml
2. api 서버 시작 kubectl apply -f api.yaml
3. socket.io 서버 시작 kubectl apply -f socket-io.yaml
4. next 서버 시작 kubectl apply -f next.yaml
5. nginx 서버 시작 kubectl apply -f nginx.yaml

