let url = "http://localhost:8080"

function init(){
    const form = document.querySelector("#login");
    form.addEventListener("submit", submit);
}

async function submit(e){
    e.preventDefault();
    const username = document.querySelector("#username").value;
    const password = document.querySelector("#password").value;

    const data = {
        username: username,
        password: password
    }

    login(data).then(response => {
        if (response.status === 200) location.href = "../views/chat.html";
        if( response.status!== 200) {
            document.querySelector("#error").textContent = "Wrong username or password";
        }
    })
}

async function login(data){
    try {
        return await fetch(url + "/authenticate", {
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
    } catch {
        document.querySelector("#error").textContent = "Internal server error";
    }
}

init()