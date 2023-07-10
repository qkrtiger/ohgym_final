// function closePopup() {
//     document.getElementById('popup').classList.add('popup-hidden');
// }

function openPopup(membernum) {
    var popupWindow = window.open("image?membernum="+membernum, "Image editing", "width=800,height=800");
    //document.getElementById('popup').classList.remove('popup-hidden');
}

function openTrainer(membernum) {
    var popupWindow = window.open("mTrainer?membernum="+membernum, "Changing trainer", "width=600,height=400");
    //document.getElementById('mTrainer').classList.remove('popup-hidden');
}


function openInfo(membernum) {
    var popupWindow = window.open("memberInfo?membernum="+membernum, "Changing trainer", "width=800,height=800");
    //document.getElementById('popup').classList.remove('popup-hidden');
}