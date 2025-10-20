const totalLectures = 3;
  let completedLectures = 1; // 예: 1강 완료

  function updateProgress() {
    const progressPercent = Math.round((completedLectures / totalLectures) * 100);
    document.getElementById('progress-fill').style.width = `${progressPercent}%`;
    document.getElementById('progress-text').textContent = `${progressPercent}%`;
  }

  // 예: '봤어요 ✅' 버튼 클릭 시 진도율 증가
  document.getElementById('completeBtn')?.addEventListener('click', () => {
    if (completedLectures < totalLectures) {
      completedLectures++;
      updateProgress();
    }
  });

  // 초기 실행
  updateProgress();