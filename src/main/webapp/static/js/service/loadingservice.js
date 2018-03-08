var zionLoadingService = function () {
    var config = {
            effect: 'roundBounce',
            text: 'Waiting...',
            bg: 'rgba(0,0,0,0.6)',
            color: '#FFF',
            maxSize: 30,
            source: 'img.svg',
            textPos: 'horizontal',
            fontSize: '18px'
        },
        show = function (element, message) {
            if (message){
                config.text = message;
            }
            if (!element) {
                jQuery('.zion-content').waitMe(config);
            }else {
                jQuery(element).waitMe(config);
            }
        },
        stop = function (element) {
            if (!element) {
                jQuery('.zion-content').waitMe('hide');
            }else {
                jQuery(element).waitMe('hide');
            }
        };

    return {
        show: show,
        stop: stop
    }

}