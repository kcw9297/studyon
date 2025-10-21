<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <%-- Spring Security CSRF Token --%>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

    <!-- 기존 CSS 로드 후 추가 -->
    <style>

        /* 내부 이미지 선택 차단 */
        .note-editable img {
            -webkit-user-drag: none;
            user-select: none;
            cursor: default;
        }

        /* 높이 조절 핸들 숨기기 */
        .note-resizebar {
            display: none !important;
        }

        /* 에디터 테두리 색상 변경 */
        .note-editor.note-frame {
            border: 2px solid #333333 !important;
        }

        /* 툴바 버튼 그룹 간격 최소화 */
        .note-toolbar .note-btn-group {
            margin-right: 2px !important;
        }

        /* 툴바 버튼 간격 최소화 */
        .note-toolbar .note-btn {
            margin: 0 !important;
            padding: 3px 6px !important;
            font-size: 13px !important;
        }

        /* 드롭다운 버튼 크기 조정 */
        .note-toolbar .dropdown-toggle {
            padding: 3px 6px !important;
        }

        /* 툴바 전체 padding 최소화 */
        .note-toolbar {
            padding: 3px !important;
            line-height: 1.2 !important;
        }

        /* body 여백 완전 제거 */
        body, html {
            margin: 0 !important;
            padding: 0 !important;
            overflow: hidden !important;
        }

        #summernote {
            margin: 0 !important;
            padding: 0 !important;
        }
    </style>

    <!-- include libraries(jQuery, bootstrap) -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

    <!-- include summernote css/js -->
    <link  href="https://cdn.jsdelivr.net/npm/summernote@0.9.1/dist/summernote.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.9.1/dist/summernote.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.9.1/dist/lang/summernote-ko-KR.js"></script>
    <script>

        // CSRF 토큰 쿠키 추출 함수
        function getCSRFToken() {
        // 현재 페이지의 meta 태그에서 읽기
            const token = $('meta[name="_csrf"]').attr('content');
            console.log('🔑 Meta 태그 토큰:', token);
            return token;
        }

        // 방향성 - 업로드 시에만 업로드 파일 반영
        // 최종적으로 업로드된 파일의 <img> 태그 추출해서 고아파일 색출 후 필요 없는파일은 삭제
        // 수정은, 기존의 본문을 불러와서 반영하고, 마찬가지로 파일의 변동에 따라 새롭게 추가된 파일 / 사라진 파일을 판별 (파일 정보로 판별)
        // 파일 테이블에 파일 유형도 첨부하여 구분할 예정, EDITOR, THUMBNAIL, 등...
        $(document).ready(function() {

            // 파일 변수
            const MAX_SIZE = 2 * 1024 * 1024; // 2MB
            const MAX_COUNT = 5;
            const ALLOW_EXTS = ['webp', 'jpg', 'jpeg', 'png', 'gif'];
            const ALLOW_TYPES = ['image/webp', 'image/jpeg', 'image/png', 'image/gif'];
            let uploadCount = 0; // 여태까지 시도한 업로드 횟수

            // 에디터 상수
            const urlParams = new URLSearchParams(window.location.search); // URL 파라미터 추출
            const width = parseInt(urlParams.get('width')) || 1000;  // 기본값 1000
            const height = parseInt(urlParams.get('height')) || 500; // 기본값 500


            $('#summernote').summernote({
                width: width,
                minWidth: width,   // 너비 고정
                maxWidth: width,
                height: height,
                minHeight: height,   // 높이 고정
                maxHeight: height,
                lang: "ko-KR",
                placeholder: "최대 2000자까지 입력 가능합니다.<br>이미지는 2MB 이하 이미지를 총 5개 까지만 업로드 할 수 있습니다.",
                toolbar: [
                    ['fontname', ['fontname']],
                    ['fontsize', ['fontsize']],
                    ['style', ['bold', 'italic', 'underline', 'strikethrough', 'clear']],
                    ['color', ['forecolor', 'color']],
                    ['table', ['table']],
                    ['para', ['ul', 'ol', 'paragraph']],
                    ['height', ['height']],
                    ['insert', ['picture', 'link']],
                ],
                fontNames: ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New', '맑은 고딕', '궁서', '굴림체', '굴림', '돋움체', '바탕체'],
                fontSizes: ['8', '9', '10', '11', '12', '14', '16', '18', '20', '22', '24', '28', '30', '36', '50', '72'],
                focus: false,  // 에디터 로딩후 포커스 여부
                tabDisable: true, // tab 비활성화 (완전 차단은 아니라, 이벤트에서도 차단 필요)
                shortcuts: false, // 브라우저 기본 키보드 이벤트(Ctrl+C, Ctrl+V 등)를 제외한 단축키 차단
                onInit: function () {

                    // tooltip 비활성화
                    $('.note-editor [data-name="ul"]').tooltip('disable');

                    // 페이지 로드 후, 부모 HTML 내의 "content" id 값과 동기화 (갱신 작업 시 유효)
                    const oldContent = $(window.parent.document).find('#content').val();
                    if (oldContent) $('#summernote').summernote('code', oldContent);

                },
                callbacks: {

                    // keydown 이벤트 처리
                    onKeydown: function (e) {

                        // Tab 입력 시 포커스 이동 방지 (기본 동작 차단)
                        if (e.key === 'Tab') {
                            e.preventDefault();
                        }
                    },


                    // 내용이 변경되는 경우, 부모 HTML 내의 "content" id 값과 동기화
                    // 에디터가 iframe 기반으로 사용되므로, 반드시 동기화 필요
                    onChange: function () {
                        console.log("변경 감지");
                        syncToParent();
                    },

                    // 붙여넣기
                    onPaste: function (e) {

                        // [1] 클립보드 데이터 (파일 복사 시, onImageUpload 이벤트에서 처리)
                        e.preventDefault();
                        const clipboardData = (e.originalEvent || e).clipboardData;

                        // [2] 클립보드에서 텍스트 추출 (텍스트 붙여넣기인 경우)
                        const bufferText = clipboardData.getData('text/plain');
                        console.log('원본 텍스트:', bufferText);

                        // [3] 공백과 탭을 HTML 엔티티로 변환
                        const escapedText = bufferText
                            .replace(/&/g, '&amp;')   // & 먼저 처리
                            .replace(/</g, '&lt;')    // < 처리
                            .replace(/>/g, '&gt;')    // > 처리
                            .replace(/ /g, '&nbsp;')  // 공백 → &nbsp;
                            .replace(/\t/g, '&nbsp;&nbsp;&nbsp;&nbsp;') // 탭 → 공백 4개
                            .replace(/\r?\n/g, '<br>'); // 줄바꿈 → <br>

                        console.log('변환된 HTML:', escapedText);

                        // [4] 삽입
                        document.execCommand('insertHTML', false, escapedText);
                    },

                    // 이미지 업로드
                    onImageUpload: function (files) {

                        // [1] 파일 개수정보 추출
                        const currentCount = $('#summernote').summernote('code').match(/<img /g)?.length || 0; // 현재까지 업로드한 파일 업로드 개수
                        const uploadCount = files.length; // 업로드 대상 파일 수

                        //[2] 파일 검증 수행
                        // 1) 동시에 업로드하는 파일이 1개를 초과하는 경우
                        if (uploadCount !== 1) {
                            alert(`이미지는 한 번에 한개만 업로드할 수 있습니다`);
                            return;
                        }

                        // 2) 업로드 시도 후 전체 이미지 개수 초과
                        if (currentCount + uploadCount > MAX_COUNT) {
                            alert("이미지는 최대 " + MAX_COUNT + "개까지만 업로드할 수 있습니다.");
                            return;
                        }

                        // 3) 개별 이미지가 크기 제한(2MB) 초과
                        const file = files[0];
                        if (file.size > MAX_SIZE) {
                            alert("2MB 이하 파일만 업로드 가능합니다.");
                            return;
                        }

                        // 4) 확장자 검증
                        const ext = file.name.split('.').pop().toLowerCase();
                        if (!ALLOW_EXTS.includes(ext) || !ALLOW_TYPES.includes(file.type)) {
                            alert(`허용되지 않은 파일 형식입니다`);
                            return;
                        }


                        // ------------- 실제 업로드 처리 -------------
                        const formData = new FormData();
                        const token = getCSRFToken();
                        console.log('🔑 CSRF 토큰:', token);
                        console.log('🍪 전체 쿠키:', document.cookie);
                        formData.append("file", file);
                        formData.append("entity", "LECTURE_QUESTION");
                        formData.append("_csrf", token); // 인증 토큰 추가

                        $.ajax({
                            url: '/test/editor/upload',
                            type: 'POST',
                            data: formData,
                            processData: false,
                            contentType: false,
                            success: function (rp) {
                                $('#summernote').summernote('insertImage', rp.data.url);
                                syncToParent();
                            },
                            error: function (xhr) {
                                log.warn(xhr.responseJSON);
                                alert(`[\${file.name}] 업로드 실패`);
                            }
                        });

                    },


                }
            });

            function syncToParent() {
                const contents = $('#summernote').summernote('code');
                $(window.parent.document).find('#content').val(contents);
            }
        })


    </script>

</head>

<body>
<textarea id="summernote" class="note-editable"></textarea>
</body>
</html>
