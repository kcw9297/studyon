<%@ page contentType="text/html;charset=UTF-8" %>
<div class="category">
    <a href="/mypage/lecture_management?subject=all" class="subject">전체</a>
    <a href="/mypage/lecture_management?subject=korean" class="subject">국어</a>
    <a href="/mypage/lecture_management?subject=math" class="subject">수학</a>
    <a href="/mypage/lecture_management?subject=english" class="subject">영어</a>
    <a href="/mypage/lecture_management?subject=social" class="subject">사회탐구</a>
    <a href="/mypage/lecture_management?subject=science" class="subject">과학탐구</a>
</div>


<style>
    .category {
        display: flex;
        margin-bottom: 20px;
    }
    .subject {
        padding: 8px 15px;
        border: 1px solid #ddd;
        border-radius: 8px;
        color: #333;
        text-decoration: none;
        transition: 0.2s;
    }
    .subject:hover {
        background-color: #f8f9fa;
    }
    .subject.active {
        background-color: #fff7e6;
        border-color: #ffb000;
        color: #ffb000;
    }
</style>

<script>
    document.addEventListener("DOMContentLoaded", () => {
        const currentSubject = new URLSearchParams(window.location.search).get("subject") || "all";
        document.querySelectorAll(".subject").forEach(a => {
            const subject = new URL(a.href, window.location.origin).searchParams.get("subject");
            if (subject === currentSubject) {
                a.classList.add("active");
            }
        });
    });
</script>

