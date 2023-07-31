//회원,트레이너 프로필 변경
function openPopup(membernum) {
    var popupWindow = window.open("image?membernum="+membernum, "Image editing", "width=800,height=500");
}

//헬스장 프로필 변경
function openGymPopup(gymnum) {
    var popupWindow = window.open("gImage?gymnum="+gymnum, "Image editing", "width=800,height=500");
}

//트레이너 변경
function openTrainer(membernum) {
    var popupWindow = window.open("mTrainer?membernum="+membernum, "Changing trainer", "width=600,height=400");
}

//개인정보수정
function openInfo(membernum) {
    var popupWindow = window.open("memberInfo?membernum="+membernum, "Changing trainer", "width=800,height=800");
}

//헬스장 프로필 변경
function opengImage(gymnum) {
    var popupWindow = window.open("gImage?gymnum="+gymnum, "gymImage editing", "width=800,height=800");
}