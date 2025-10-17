const addBtn = document.getElementById('add-lecture-btn');
const listBox = document.getElementById('lecture-list-box');
let lectureIndex = 0;

// 🔹 강의 추가 버튼 클릭 시 실행
addBtn.addEventListener('click', () => {
    lectureIndex++;

    const div = document.createElement('div');
    div.className = 'lecture-item';
    div.setAttribute('data-index', lectureIndex);

    div.innerHTML = `
      <label class="fuckinglabel">제 ${lectureIndex}강 제목:</label>
      <input type="text" class="lecture-input" name="lecture-title-${lectureIndex}" placeholder="예: ${lectureIndex}강 제목 입력" required>

      <label class="fuckinglabel">강의영상파일:</label>
      <input type="file" class="lecture-input-file" name="lecture-file-${lectureIndex}" accept="video/*" required>

      <button type="button" class="remove-lecture-btn">❌</button>
      <hr>
    `;

    listBox.appendChild(div);

    // 🔹 삭제 버튼 이벤트
    const removeBtn = div.querySelector('.remove-lecture-btn');
    removeBtn.addEventListener('click', () => {
        div.remove();
        updateLectureNumbers();
    });
});

// 🔹 삭제 후 번호 갱신
function updateLectureNumbers() {
    const items = document.querySelectorAll('.lecture-item');
    lectureIndex = 0;
    items.forEach((item, i) => {
        lectureIndex = i + 1;
        item.querySelector('label').textContent = `제 ${lectureIndex}강 제목:`;
        item.querySelector('input[type="text"]').setAttribute('name', `lecture-title-${lectureIndex}`);
        item.querySelector('input[type="file"]').setAttribute('name', `lecture-file-${lectureIndex}`);
    });
}