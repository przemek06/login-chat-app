let url = "http://localhost:8080"

function submit(e){
    e.preventDefault();
    const username = document.querySelector("#username").value;
    const password = document.querySelector("#password").value;

    const data = {
        username: username,
        password: password
    }

    register(data).then(response => {
        if (response.status === 200) location.href = "../views/login.html";
        if (response.status === 400) document.querySelector("#error").textContent = "Username already exists";
        if (response.status === 403) document.querySelector("#error").textContent = "You are logged in";
    })
}

async function register(data){
    try {
        const result = await fetch(url + "/register", {
            method: 'POST', // *GET, POST, PUT, DELETE, etc.
            mode: 'cors', // no-cors, *cors, same-origin
            cache: 'default', // *default, no-cache, reload, force-cache, only-if-cached
            credentials: 'include', // include, *same-origin, omit
            headers: {
                'Content-Type': 'application/json'
            },
            redirect: 'follow', // manual, *follow, error
            referrerPolicy: 'no-referrer', // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
            body: JSON.stringify(data),// body data type must match "Content-Type" header
            origin: "http://localhost:63342"
        });
        return result;
    } catch {
        document.querySelector("#error").textContent = "Internal server error";
    }
}

function init(){
    const form = document.querySelector("#register");
    form.addEventListener("submit", submit);
}

init()
