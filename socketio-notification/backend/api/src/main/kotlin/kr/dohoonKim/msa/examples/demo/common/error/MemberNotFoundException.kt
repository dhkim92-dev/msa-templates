package kr.dohoonKim.msa.examples.demo.common.error

class MemberNotFoundException : RuntimeException() {
    val msg = "사용자가 존재하지 않습니다."
}