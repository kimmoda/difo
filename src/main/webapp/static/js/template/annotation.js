(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['annotation'] = template({"1":function(container,depth0,helpers,partials,data) {
    var helper;

  return "                            /"
    + container.escapeExpression(((helper = (helper = helpers.location || (depth0 != null ? depth0.location : depth0)) != null ? helper : helpers.helperMissing),(typeof helper === "function" ? helper.call(depth0 != null ? depth0 : (container.nullContext || {}),{"name":"location","hash":{},"data":data}) : helper)))
    + "\r\n";
},"3":function(container,depth0,helpers,partials,data) {
    var stack1;

  return "                    <div class=\"tag\">\r\n"
    + ((stack1 = helpers.each.call(depth0 != null ? depth0 : (container.nullContext || {}),(depth0 != null ? depth0.feedTags : depth0),{"name":"each","hash":{},"fn":container.program(4, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "                    </div>\r\n";
},"4":function(container,depth0,helpers,partials,data) {
    var helper, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression;

  return "                            <span class=\"zion-feed-tag\" data-tag-name=\""
    + alias4(((helper = (helper = helpers.name || (depth0 != null ? depth0.name : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"name","hash":{},"data":data}) : helper)))
    + "\"\r\n                                  data-tag-code=\""
    + alias4(((helper = (helper = helpers.code || (depth0 != null ? depth0.code : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"code","hash":{},"data":data}) : helper)))
    + "\">#"
    + alias4(((helper = (helper = helpers.name || (depth0 != null ? depth0.name : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"name","hash":{},"data":data}) : helper)))
    + "</span>\r\n";
},"6":function(container,depth0,helpers,partials,data) {
    var stack1;

  return ((stack1 = helpers["if"].call(depth0 != null ? depth0 : (container.nullContext || {}),((stack1 = (depth0 != null ? depth0.coverImage : depth0)) != null ? stack1.url : stack1),{"name":"if","hash":{},"fn":container.program(7, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "");
},"7":function(container,depth0,helpers,partials,data) {
    var stack1, helper, alias1=container.lambda, alias2=container.escapeExpression;

  return "                            <img class=\"image img-responsive center-block pin easypin-image\" src=\""
    + alias2(alias1(((stack1 = (depth0 != null ? depth0.coverImage : depth0)) != null ? stack1.url : stack1), depth0))
    + "\"\r\n                                 alt=\""
    + alias2(((helper = (helper = helpers.title || (depth0 != null ? depth0.title : depth0)) != null ? helper : helpers.helperMissing),(typeof helper === "function" ? helper.call(depth0 != null ? depth0 : (container.nullContext || {}),{"name":"title","hash":{},"data":data}) : helper)))
    + "\"\r\n                                 easypin-id=\""
    + alias2(alias1(((stack1 = (depth0 != null ? depth0.coverImage : depth0)) != null ? stack1.publicId : stack1), depth0))
    + "\">\r\n                            <div class=\"cover-image-content\">"
    + ((stack1 = alias1(((stack1 = (depth0 != null ? depth0.coverImage : depth0)) != null ? stack1.description : stack1), depth0)) != null ? stack1 : "")
    + "</div>\r\n";
},"9":function(container,depth0,helpers,partials,data) {
    var stack1;

  return "                    <p class=\"description\">\r\n                        "
    + ((stack1 = (helpers.contentFormat || (depth0 && depth0.contentFormat) || helpers.helperMissing).call(depth0 != null ? depth0 : (container.nullContext || {}),(depth0 != null ? depth0.content : depth0),false,{"name":"contentFormat","hash":{},"data":data})) != null ? stack1 : "")
    + "\r\n                    </p>\r\n";
},"11":function(container,depth0,helpers,partials,data) {
    var stack1, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=helpers.helperMissing;

  return "                    <br>\r\n"
    + ((stack1 = (helpers.ifEqual || (depth0 && depth0.ifEqual) || alias2).call(alias1,(depth0 != null ? depth0.mediaType : depth0),"IMAGE",{"name":"ifEqual","hash":{},"fn":container.program(12, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + ((stack1 = (helpers.ifEqual || (depth0 && depth0.ifEqual) || alias2).call(alias1,(depth0 != null ? depth0.mediaType : depth0),"VIDEO",{"name":"ifEqual","hash":{},"fn":container.program(14, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + ((stack1 = (helpers.ifEqual || (depth0 && depth0.ifEqual) || alias2).call(alias1,(depth0 != null ? depth0.mediaType : depth0),"TEXT",{"name":"ifEqual","hash":{},"fn":container.program(16, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "");
},"12":function(container,depth0,helpers,partials,data) {
    var stack1, helper, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression;

  return "                        <img class=\"image img-responsive center-block pin easypin-image\" src=\""
    + alias4(((helper = (helper = helpers.url || (depth0 != null ? depth0.url : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"url","hash":{},"data":data}) : helper)))
    + "\" alt=\""
    + alias4(((helper = (helper = helpers.title || (depth0 != null ? depth0.title : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"title","hash":{},"data":data}) : helper)))
    + "\"\r\n                             easypin-id=\""
    + alias4(((helper = (helper = helpers.publicId || (depth0 != null ? depth0.publicId : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"publicId","hash":{},"data":data}) : helper)))
    + "\">\r\n                        <div class=\"media-image-content\">"
    + ((stack1 = ((helper = (helper = helpers.description || (depth0 != null ? depth0.description : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"description","hash":{},"data":data}) : helper))) != null ? stack1 : "")
    + "</div>\r\n";
},"14":function(container,depth0,helpers,partials,data) {
    var stack1, helper, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=helpers.helperMissing, alias3="function";

  return "                        <div class=\"video-container\">\r\n                            <iframe src=\""
    + container.escapeExpression(((helper = (helper = helpers.url || (depth0 != null ? depth0.url : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"url","hash":{},"data":data}) : helper)))
    + "?control=0&rel=0&showinfo=0\" allowfullscreen></iframe>\r\n                        </div>\r\n                        <div class=\"media-video-content\">"
    + ((stack1 = ((helper = (helper = helpers.description || (depth0 != null ? depth0.description : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"description","hash":{},"data":data}) : helper))) != null ? stack1 : "")
    + "</div>\r\n";
},"16":function(container,depth0,helpers,partials,data) {
    var stack1, helper;

  return "                        <div class=\"text-container\">"
    + ((stack1 = ((helper = (helper = helpers.description || (depth0 != null ? depth0.description : depth0)) != null ? helper : helpers.helperMissing),(typeof helper === "function" ? helper.call(depth0 != null ? depth0 : (container.nullContext || {}),{"name":"description","hash":{},"data":data}) : helper))) != null ? stack1 : "")
    + "</div>\r\n";
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1, helper, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression, alias5=container.lambda;

  return "<div class=\"modal-dialog instagram-style-container\" role=\"document\">\r\n    <div class=\"modal-content\">\r\n        <div class=\"modal-body\">\r\n            <div class=\"top-bar clearfix\">\r\n                <div class=\"modal-close\">\r\n                    <i class=\"fa fa-lg fa-chevron-left\" aria-hidden=\"true\" data-dismiss=\"modal\" aria-label=\"Close\"></i>\r\n                </div>\r\n                <div class=\"mobile-top-bar-title\">\r\n                    <h4 class=\"mobile-top-bar-title-content\">Post</h4>\r\n                </div>\r\n            </div>\r\n            <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\">\r\n                <span class=\"cancel-text\" aria-hidden=\"true\">&times;</span>\r\n            </button>\r\n            <div class=\"look-container\">\r\n                <div class=\"feed-title\">\r\n                    <h4 class=\"feed-title-content\">\r\n                        "
    + alias4(((helper = (helper = helpers.title || (depth0 != null ? depth0.title : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"title","hash":{},"data":data}) : helper)))
    + "\r\n                    </h4>\r\n                </div>\r\n                <div class=\"feed-publish-time\">\r\n                    <p class=\"feed-publish-time-content\">\r\n                        "
    + alias4((helpers.dateTimeFormat || (depth0 && depth0.dateTimeFormat) || alias2).call(alias1,(depth0 != null ? depth0.creationDate : depth0),{"name":"dateTimeFormat","hash":{},"data":data}))
    + "\r\n                    </p>\r\n                </div>\r\n                <div class=\"author-info-container\">\r\n                    <div class=\"user-profile-link author-avatar\">\r\n                        <img class=\"img-circle img-responsive avatar\" src=\""
    + alias4(alias5(((stack1 = (depth0 != null ? depth0.author : depth0)) != null ? stack1.avatar : stack1), depth0))
    + "\" alt=\"avatar\"/>\r\n                    </div>\r\n                    <div class=\"user-profile-link author-name-location\" title=\""
    + alias4(alias5(((stack1 = (depth0 != null ? depth0.author : depth0)) != null ? stack1.displayName : stack1), depth0))
    + "\">\r\n                        <span class=\"user-name\">By "
    + alias4(alias5(((stack1 = (depth0 != null ? depth0.author : depth0)) != null ? stack1.displayName : stack1), depth0))
    + "</span>\r\n"
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.location : depth0),{"name":"if","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "                    </div>\r\n                </div>\r\n"
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.feedTags : depth0),{"name":"if","hash":{},"fn":container.program(3, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "                <div class=\"annotation-container\">\r\n"
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.coverImage : depth0),{"name":"if","hash":{},"fn":container.program(6, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "                </div>\r\n"
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.content : depth0),{"name":"if","hash":{},"fn":container.program(9, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + ((stack1 = helpers.each.call(alias1,(depth0 != null ? depth0.mediaContent : depth0),{"name":"each","hash":{},"fn":container.program(11, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "            </div>\r\n            <div class=\"share-container clearfix\">\r\n                <div class=\"zion-modal-feed-like-container\" data-feed-like-id=\""
    + alias4(alias5((depth0 != null ? depth0.id : depth0), depth0))
    + "\"\r\n                     data-feed-like=\""
    + alias4(alias5((depth0 != null ? depth0.like : depth0), depth0))
    + "\" data-feed-like-count=\""
    + alias4(((helper = (helper = helpers.likeCount || (depth0 != null ? depth0.likeCount : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"likeCount","hash":{},"data":data}) : helper)))
    + "\"></div>\r\n                <button id=\"share-feed-button\" class=\"share-feed-btn btn btn-default\">\r\n                    <i class=\"fa fa-share-alt\" aria-hidden=\"true\"></i>\r\n                </button>\r\n                <div class=\"feed-share-container\">\r\n                    <div class=\"zion-social-share-plugin-container\" id=\"zion-social-share-container\"\r\n                         data-share-url=\""
    + alias4(((helper = (helper = helpers.shortUrl || (depth0 != null ? depth0.shortUrl : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"shortUrl","hash":{},"data":data}) : helper)))
    + "\" data-share-title=\""
    + alias4(((helper = (helper = helpers.title || (depth0 != null ? depth0.title : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"title","hash":{},"data":data}) : helper)))
    + "\"\r\n                         data-share-description=\""
    + alias4(((helper = (helper = helpers.content || (depth0 != null ? depth0.content : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"content","hash":{},"data":data}) : helper)))
    + "\" data-share-image=\""
    + alias4(alias5(((stack1 = (depth0 != null ? depth0.coverImage : depth0)) != null ? stack1.url : stack1), depth0))
    + "\"></div>\r\n                </div>\r\n            </div>\r\n            <div class=\"comment-container\">\r\n                <div class=\"comment-send-container clearfix\">\r\n                    <textarea class=\"form-control comment-input\" id=\"comments-input\"\r\n                              placeholder=\"Comment on this look\"></textarea>\r\n                    <div class=\"comment-send-button btn btn-primary pull-right\" id=\"comment-send-button\">SEND</div>\r\n                    <div class=\"zion-input-error-text zion-hide\" id=\"comment-error\">Please provide your comments less\r\n                        than 200 characters.\r\n                    </div>\r\n                </div>\r\n                <div class=\"comment-content-container\" id=\"comment-container\">\r\n                    <div id=\"comments\"></div>\r\n                    <div class=\"load-more-button-container hidden\" id=\"load-comment-more-button-container\">\r\n                        <div class=\"load-more-button btn btn-link\" id=\"load-more-button\">View all comments</div>\r\n                    </div>\r\n                </div>\r\n            </div>\r\n        </div>\r\n    </div>\r\n</div>\r\n\r\n<div style=\"display:none;\" easypin-tpl>\r\n    <popover>\r\n        <div class=\"exPopoverContainer\" shadow=\"true\">\r\n            <div class=\"popBg\">\r\n                <a href={[url]} target=\"_blank\">\r\n                    <p class=\"annotation-title\">{[title]}</p>\r\n                </a>\r\n            </div>\r\n        </div>\r\n    </popover>\r\n    <marker>\r\n        <div class=\"marker2\" style=\"height: 15px; width: 15px\">\r\n        </div>\r\n    </marker>\r\n</div>\r\n</div>";
},"useData":true});
})();