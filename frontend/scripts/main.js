function configureButton() {
    const buttons = document.querySelectorAll(".button");
    buttons.forEach(btn => {
        btn.addEventListener("mouseenter", e => {
            e.target.style.backgroundColor = "gray";
        })
    })
    buttons.forEach(btn => {
        btn.addEventListener("mouseleave", e => {
            e.target.style.backgroundColor = "darkgray";
        })
    })
    buttons.forEach(btn => {
        btn.addEventListener("click", e => {
            e.target.style.backgroundColor = "black";
        })
    })
}



function init(){
    configureButton();
}

init()
