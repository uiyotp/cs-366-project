//function to switch tabs
function openPage(pageName) {
    var tabContent = document.getElementsByClassName("tabContent");
    for (var i = 0; i < tabContent.length; i++) {
        tabContent[i].style.display = "none";
    }
    document.getElementById(pageName).style.display = "block";
}