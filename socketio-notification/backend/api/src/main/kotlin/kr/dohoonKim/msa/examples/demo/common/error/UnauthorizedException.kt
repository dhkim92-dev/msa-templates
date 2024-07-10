package kr.dohoonKim.msa.examples.demo.common.error

class UnauthorizedException : RuntimeException() {
    val msg = "로그인 실패"
}