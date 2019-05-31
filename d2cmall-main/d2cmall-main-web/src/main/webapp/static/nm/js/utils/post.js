$(function () {
    if (window.addEventListener) {
        window.addEventListener('message', receiveMsg, false);
    } else {
        window.attachEvent('message', receiveMsg);
    }

    function receiveMsg(event) {
        if (event.origin.indexOf('localhost:8089') != -1 && event.data.from === 'vueapp') {
            $('html,body').css({
                height: event.data.height + 'px',
                overflow: 'auto',
                '-webkit-overflow-scrolling': 'touch',
            });
        } else {
            return;
        }
    }
})