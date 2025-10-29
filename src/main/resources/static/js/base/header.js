
function logout(e) {

    fetch("/logout", {
        method: "post"
    })
        .then(async res => {
            const data = await res.json().catch(() => ({}));

            if (!res.ok || !data.success) {
                console.log("data =", data);
                alert(data.message || "로그아웃 실패");
                return;
            }

            if (data.success) {
                window.location.href = data.redirect || "/";
            }
        })
        .catch(err => {
            alert("요청 에러: " + err.message);
        });
}