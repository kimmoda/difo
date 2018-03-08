/**
 * Feed annotation plugin
 *
 * @method  getAnnotation - initial easypin, call showAllAnnotation and addShowAllListener function. Bind _showAnnotation to click annotation event.
 * @param {object} annotationData - annotation data info including image url, annotation position and annotation content
 *
 * @method  showAllAnnotation - loop each annotation and call _showAnnotation
 *
 * @method  _showAnnotation - calculate and display feed annotation position and annotation content position
 * @param {object} tag - jQuery type of object to store easypin marker node
 * @example tags = jQuery('.easypin-marker');
 *
 * @method  addShowAllListener - listen annotation click event to show/hide annotation content
 *
 * @method  _getCurrentStrWidth - calculate annotation content string length
 * @param {string} text - annotation content
 * @param {string} font - annotation character css font family style
 *
 * @function zionFeedAnnotation - get annotation info and initial feed annotation
 * @param {object} annotationData - annotation data info including image url, annotation position and annotation content
 *
 * @return getAnnotation - for initial annotation plugin
 * @return showAllAnnotation - for active annotation content when modal pop up, need call in modal show event
 * @return addShowAllListener - for active annotation content when modal pop up, need call in modal show event
 */


(function ($) {
    var getAnnotation = function (annotationData) {
            if (annotationData) {
                jQuery('.easypin-image').easypinShow({
                    data: filterXSS(annotationData),
                    responsive: true,
                    success: function () {
                        jQuery('.easypin').css('height', 'auto').css('width', 'auto');
                        jQuery('[data-toggle="tooltip"]').tooltip();
                        if (jQuery('.easypin').width() > 0) {
                            showAllAnnotation();
                            addShowAllListener();
                        }
                        if(isIE()) {
                            jQuery('.easypin-image').addClass('ie-display-image');
                        }
                    },
                    clicked: function (tag, data) {
                        _showAnnotation(tag);
                    }
                });

            } else {
                return false;
            }
        },
        showAllAnnotation = function () {
            var tags = jQuery('.easypin-marker');
            jQuery.each(tags, function (index, value) {
                _showAnnotation(value);
            })
        },
        _showAnnotation = function (tag) {
            var imageWidth = jQuery('.easypin').width(),
                markerHeight = jQuery('.easypin-marker')[0].offsetHeight,
                imageX = 0,
                annotationTitle = jQuery('.annotation-title', tag).text(),
                textWidth = _getCurrentStrWidth(annotationTitle, jQuery('.annotation-title', tag).css('font')) + 30,
                positionXStr = jQuery(tag).css('left'),
                positionX = parseFloat(positionXStr.substring(0, positionXStr.length - 2)),
                realX = positionX - imageX;
            jQuery('.exPopoverContainer', tag).width(textWidth);
            // put the annotation under the dot
            if (positionX - markerHeight * 0.5 > textWidth * 0.5 && positionX + markerHeight * 0.5 < imageWidth - (textWidth * 0.5)) {
                var left = -textWidth * 0.5 + 5;
                jQuery('.exPopoverContainer', tag).css({
                    'top': 15,
                    'left': left
                });
            } else {
                // put the annotation on left or right
                if (realX && realX > imageWidth * 0.5) {
                    if (textWidth > realX) {
                        textWidth = realX - 10;
                        jQuery('.exPopoverContainer', tag).width(textWidth);
                    }
                    jQuery('.exPopoverContainer', tag).css('left', -textWidth - 5);
                } else {
                    if (textWidth + realX > imageWidth * 0.9) {
                        textWidth = imageWidth - realX - 30;
                        jQuery('.exPopoverContainer', tag).width(textWidth);
                    }
                    jQuery('.exPopoverContainer', tag).css('left', 15);
                }
            }

            jQuery('.easypin-popover', tag).show();
        },
        addShowAllListener = function () {
            var showAll = true;
            jQuery('.easypin-image').unbind('click');
            jQuery('.easypin-image').on('click', function (e) {
                if (jQuery(e.target).is('div.easypin-marker')) {
                    showAll = !showAll;
                    return;
                }
                if (showAll) {
                    var tags = jQuery('.easypin-marker');
                    jQuery.each(tags, function (index, value) {
                        jQuery('.easypin-popover', value).hide();
                    });
                    showAll = false;
                } else {
                    showAllAnnotation();
                    showAll = true;
                }
            });
        },
        _getCurrentStrWidth = function (text, font) {
            var currentObj = jQuery('<span>').hide().appendTo(document.body),
                width;
            jQuery(currentObj).html(filterXSS(text)).css('font', font);
            width = currentObj.width();
            currentObj.remove();
            return width;
        };

    $.fn.zionFeedAnnotation = function () {
        return {
            getAnnotation: getAnnotation,
            showAllAnnotation: showAllAnnotation,
            addShowAllListener: addShowAllListener
        }
    }
}(jQuery));