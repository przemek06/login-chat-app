const url = "http://localhost:8080/"
let groups = []
let socket = new SockJS(url+"stomp/");
let ws = Stomp.over(socket)
let connected=""
let last = new Date().getTime()

function sendMessage(){

    let input = document.getElementById("message-input");
    let message = input.value;

    ws.send('/app/chat/'+connected, {}, JSON.stringify({message: message}));

}

function disconnect() {
    let message_list = document.getElementById('message-list');

    socket.close()

    message_list.innerHTML=""

}


function connect(chat){
    disconnect()
    socket = new SockJS(url+"stomp/");
    ws = Stomp.over(socket)
    connected=chat
    last = new Date().getTime()
    loadMessages(connected)
    ws.connect({}, () => {
        // Subscribe to "/topic/messages". Whenever a message arrives add the text in a list-item element in the unordered list.
        ws.subscribe("/topic/messages/"+chat, payload => {
            let message_list = document.getElementById('message-list');
            let message = document.createElement('li');
            message.appendChild(document.createTextNode("> " + JSON.parse(payload.body).username + ": " +
                JSON.parse(payload.body).message));
            message_list.appendChild(message);
            let box=document.querySelector("#chatbox")
            box.scrollTop=box.scrollHeight

        });
    });
}

function configureGroups() {
    let list = document.querySelector("#chat-list");

    groups.forEach(el=>{
        let temp = document.createElement('li');
        temp.textContent=el.name
        temp.classList.add("chat-element");
        temp.setAttribute("id", el.id)
        list.appendChild(temp);
    })

    const groupNodes = document.querySelectorAll(".chat-element");

    groupNodes.forEach(el => {
        el.addEventListener("mouseover", e => {
            if(e.target.attributes.getNamedItem("id").value!==connected){
                e.target.style.backgroundColor = "lightgray"
            }
        })
    })

    groupNodes.forEach(el => {
        el.addEventListener("mouseleave", e => {
            if(e.target.attributes.getNamedItem("id").value!==connected){
                e.target.style.backgroundColor = "white"
            }
        })
    })

    groupNodes.forEach(el => {
        el.addEventListener("click", e => {
            connect(e.target.attributes.getNamedItem("id").value)
            groupNodes.forEach(el => {
                el.style.backgroundColor = "white"
            })
            e.target.style.backgroundColor="gray"
        })
    })
}

async function loadGroups(){

    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", "http://localhost:8080/groups", false)
    xmlHttp.withCredentials=true
    xmlHttp.send( null );

    groups = JSON.parse(xmlHttp.response)

    configureGroups()
}

function logout(){
    let xmlHttp = new XMLHttpRequest();

    xmlHttp.open("POST", "http://localhost:8080/logouto", false)
    xmlHttp.withCredentials=true
    xmlHttp.send( null );
    location.href="../views/login.html"
}

function checkConnection(){
    let xmlHttp = new XMLHttpRequest();
    try{
    xmlHttp.open("GET", "http://localhost:8080/connected", false)
    xmlHttp.withCredentials=true
    xmlHttp.send( null );
    } finally {
        if(xmlHttp.response!=="true"){
            location.href="../views/login.html"
        }
    }
}

function loadMessages(connected){
    let xmlHttp = new XMLHttpRequest();
    try{
        xmlHttp.open("POST", url +"messages/"+connected, false)
        xmlHttp.withCredentials=true
        xmlHttp.setRequestHeader("Content-Type" , "application/json")
        xmlHttp.setRequestHeader("Access-Control-Expose-Headers", "last")
        xmlHttp.send(JSON.stringify({date: last}) );
        if(xmlHttp.getAllResponseHeaders().indexOf("last-message")>=0){
            last = parseInt(xmlHttp.getResponseHeader("last-message"))
        }

        const messages = JSON.parse(xmlHttp.response)
        const messagesList = document.querySelector("#message-list")
        messages.sort(compareMessage)
        messages.forEach(el=>{
            let entry = document.createElement("li")
            entry.textContent="> " + el.username + ": " + el.message;
            messagesList.prepend(entry)
        })

    } catch {

    }
}

function loadMessagesOnScroll(){
    const chatbox=document.querySelector("#chatbox")
    chatbox.addEventListener("scroll", function() {
        if (chatbox.scrollTop===0) {
            loadMessages(connected)
        }
    });
}

function compareMessage(a, b){
    if(a.date>b.date) return -1;
    if(b.date>a.date) return 1;
    return 0
}

window.onunload = function (){
    disconnect()
}

window.onload = function (){
    checkConnection()
    loadGroups()
    loadMessagesOnScroll()
}



