package studyon.app.layer.base.dto;

import lombok.*;

import java.util.List;


/**
 * 페이징 요청/응답 처리를 하는 DTO
 * @version 1.0
 * @author kcw97
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Page {

    public static Integer calculateReturnPage(Integer afterRdateDataCount, Integer pageSize) {
        return Math.max(1, (int) Math.ceil((double) afterRdateDataCount / pageSize));
    }


    @Data
    @AllArgsConstructor
    public static class Request {

        private Integer page;
        private Integer size;

        // 기본 생성자
        public Request() {
            this.page = 0;
            this.size = 8;
        }

        public void setPage(Integer page) {
            this.page = page - 1;
        }

        public Integer getStartPage() {
            return page * size;
        }

    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response<T> {

        private List<T> data;
        private int currentPage;    // 현재 페이지
        private int groupSize;      // 그룹당 페이지 개수
        private int pageSize;        // 한 페이지 당 가져올 행 개수
        private int dataCount;      // 데이터의 총 개수 (같은 조건으로 조회된 총 데이터의 수)

        // DTO 내부에서 계산되는 값
        private int totalPage;            // 총 페이지 수
        private int currentGroup;           // 현재 그룹 번호
        private boolean hasNextGroup;       // 다음 그룹 존재 여부
        private boolean hasPreviousGroup;   // 이전 그룹 존재 여부
        private int nextGroupPage;          // 다음 그룹 시작 페이지
        private int previousGroupPage;      // 이전 그룹 시작 페이지
        private boolean isStartPage;        // 첫 페이지 여부
        private boolean isEndPage;          // 마지막 페이지 여부
        private int currentGroupStartPage;  // 현재 그룹의 시작 페이지 번호
        private int currentGroupEndPage;    // 현재 그룹의 끝 페이지 번호


        private Response(List<T> data, int currentPage, int groupSize, int pageSize, int dataCount) {
            this.data = data;
            this.currentPage = currentPage + 1;
            this.groupSize = groupSize;
            this.pageSize = pageSize;
            this.dataCount = dataCount;
            calculateGroup();
        }

        public static <T> Response<T> create(List<T> data, int currentPage, int pageSize, int dataCount) {
            return create(data, currentPage, 5, pageSize, dataCount);
        }

        public static <T> Response<T> create(List<T> data, int currentPage, int groupSize, int pageSize, int dataCount) {
            return new Response<>(data, currentPage, groupSize, pageSize, dataCount);
        }

        /**
         * 현재 페이지 기준, 페이지 그룹 계산
         * 페이지와 그룹은 1부터 시작
         */
        public void calculateGroup() {
            // 총 페이지 계산
            this.totalPage = (int) Math.ceil((double) dataCount / pageSize);
            if (this.totalPage == 0) this.totalPage = 1;

            // currentPage는 생성자에서 이미 +1 보정한 상태
            if (this.currentPage <= 1) this.currentPage = 1;
            if (this.currentPage > this.totalPage) this.currentPage = this.totalPage;

            // 현재 그룹 계산 (this.currentPage 사용)
            this.currentGroup = (int) Math.ceil((double) this.currentPage / groupSize);

            // 다음 그룹 존재 여부 및 페이지
            this.hasNextGroup = (currentGroup * groupSize) < totalPage;
            this.nextGroupPage = hasNextGroup ? (currentGroup * groupSize + 1) : totalPage;

            // 이전 그룹 존재 여부 및 페이지
            this.hasPreviousGroup = currentGroup > 1;
            this.previousGroupPage = hasPreviousGroup ? ((currentGroup - 1) * groupSize) : 1;

            // 페이지 시작/끝 여부 계산
            this.isStartPage = this.currentPage == 1;
            this.isEndPage = this.currentPage == totalPage;

            // 현재 그룹에서 시작 및 끝 페이지
            this.currentGroupStartPage = (currentGroup - 1) * groupSize + 1;
            this.currentGroupEndPage = Math.min(currentGroup * groupSize, totalPage);
        }
    }
}
