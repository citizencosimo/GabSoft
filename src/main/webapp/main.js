let toggleNavStatus = false;

let toggleNav = function() {
    // . class and # id
    let getSidebar = document.querySelector(".n-sidebar");
    let getList = document.querySelector(".n-sidebar ul");
    let getA = document.querySelectorAll(".n-sidebar ul a");

    if (toggleNavStatus == false) {
        getList.style.visibility = "visible";
        getSidebar.style.width = "272px";

        let len = getA.length;
        for (let i = 0; i < len; i++) {
            getA[i].style.opacity = "1";
        }

        toggleNavStatus = true;
    }
    else if(toggleNavStatus == true) {
        
        getSidebar.style.width = "70px";

        let len = getA.length;
        for (let i = 0; i < len; i++) {
            getA[i].style.opacity = "0";
        }

        getList.style.visibility = "hidden";

        toggleNavStatus = false;
    }
}