(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['singleLook'] = template({"1":function(container,depth0,helpers,partials,data) {
    var stack1;

  return ((stack1 = helpers["if"].call(depth0 != null ? depth0 : (container.nullContext || {}),((stack1 = (depth0 != null ? depth0.coverImage : depth0)) != null ? stack1.url : stack1),{"name":"if","hash":{},"fn":container.program(2, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "");
},"2":function(container,depth0,helpers,partials,data) {
    var stack1, alias1=container.lambda, alias2=container.escapeExpression;

  return "                    <img class=\"img-responsive pin easypin-image\" src=\""
    + alias2(alias1(((stack1 = (depth0 != null ? depth0.coverImage : depth0)) != null ? stack1.url : stack1), depth0))
    + "\" alt=\""
    + alias2(alias1((depth0 != null ? depth0.title : depth0), depth0))
    + "\"\n                         easypin-id=\""
    + alias2(alias1(((stack1 = (depth0 != null ? depth0.coverImage : depth0)) != null ? stack1.publicId : stack1), depth0))
    + "\">\n                    <div class=\"cover-image-content\">"
    + ((stack1 = alias1(((stack1 = (depth0 != null ? depth0.coverImage : depth0)) != null ? stack1.description : stack1), depth0)) != null ? stack1 : "")
    + "</div>\n";
},"4":function(container,depth0,helpers,partials,data) {
    var stack1;

  return "                <div class=\"description\">\n                    "
    + ((stack1 = (helpers.contentFormat || (depth0 && depth0.contentFormat) || helpers.helperMissing).call(depth0 != null ? depth0 : (container.nullContext || {}),(depth0 != null ? depth0.content : depth0),false,{"name":"contentFormat","hash":{},"data":data})) != null ? stack1 : "")
    + "\n                </div>\n";
},"6":function(container,depth0,helpers,partials,data) {
    var stack1, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=helpers.helperMissing;

  return "                <br>\n"
    + ((stack1 = (helpers.ifEqual || (depth0 && depth0.ifEqual) || alias2).call(alias1,(depth0 != null ? depth0.mediaType : depth0),"IMAGE",{"name":"ifEqual","hash":{},"fn":container.program(7, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + ((stack1 = (helpers.ifEqual || (depth0 && depth0.ifEqual) || alias2).call(alias1,(depth0 != null ? depth0.mediaType : depth0),"VIDEO",{"name":"ifEqual","hash":{},"fn":container.program(9, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + ((stack1 = (helpers.ifEqual || (depth0 && depth0.ifEqual) || alias2).call(alias1,(depth0 != null ? depth0.mediaType : depth0),"TEXT",{"name":"ifEqual","hash":{},"fn":container.program(11, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "");
},"7":function(container,depth0,helpers,partials,data) {
    var stack1, helper, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression;

  return "                    <img class=\"img-responsive pin easypin-image\" src=\""
    + alias4(((helper = (helper = helpers.url || (depth0 != null ? depth0.url : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"url","hash":{},"data":data}) : helper)))
    + "\" alt=\""
    + alias4(((helper = (helper = helpers.title || (depth0 != null ? depth0.title : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"title","hash":{},"data":data}) : helper)))
    + "\" easypin-id=\""
    + alias4(((helper = (helper = helpers.publicId || (depth0 != null ? depth0.publicId : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"publicId","hash":{},"data":data}) : helper)))
    + "\">\n                    <div class=\"media-image-content\">"
    + ((stack1 = ((helper = (helper = helpers.description || (depth0 != null ? depth0.description : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"description","hash":{},"data":data}) : helper))) != null ? stack1 : "")
    + "</div>\n";
},"9":function(container,depth0,helpers,partials,data) {
    var stack1, helper, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=helpers.helperMissing, alias3="function";

  return "                    <div class=\"video-container\">\n                        <iframe src=\""
    + container.escapeExpression(((helper = (helper = helpers.url || (depth0 != null ? depth0.url : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"url","hash":{},"data":data}) : helper)))
    + "?control=0&rel=0&showinfo=0\" allowfullscreen></iframe>\n                    </div>\n                    <div class=\"media-video-content\">"
    + ((stack1 = ((helper = (helper = helpers.description || (depth0 != null ? depth0.description : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"description","hash":{},"data":data}) : helper))) != null ? stack1 : "")
    + "</div>\n";
},"11":function(container,depth0,helpers,partials,data) {
    var stack1, helper;

  return "                    <div class=\"text-container\">\n                        "
    + ((stack1 = ((helper = (helper = helpers.description || (depth0 != null ? depth0.description : depth0)) != null ? helper : helpers.helperMissing),(typeof helper === "function" ? helper.call(depth0 != null ? depth0 : (container.nullContext || {}),{"name":"description","hash":{},"data":data}) : helper))) != null ? stack1 : "")
    + "\n                    </div>\n";
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1, helper, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression, alias5=container.lambda;

  return "<div class=\"row\" id=\"feed-"
    + alias4(((helper = (helper = helpers.id || (depth0 != null ? depth0.id : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"id","hash":{},"data":data}) : helper)))
    + "\">\n    <div class=\"zion-feed-header col-md-8 col-md-offset-2\">\n        <div class=\"author-info-container\" data-user-id=\""
    + alias4(alias5(((stack1 = (depth0 != null ? depth0.author : depth0)) != null ? stack1.userId : stack1), depth0))
    + "\">\n            <div class=\"feed-title\">\n                <h4 class=\"feed-title-content\">\n                    "
    + alias4(((helper = (helper = helpers.title || (depth0 != null ? depth0.title : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"title","hash":{},"data":data}) : helper)))
    + "\n                </h4>\n            </div>\n            <div class=\"feed-publish-time\">\n                <p class=\"feed-publish-time-content\">\n                    "
    + alias4((helpers.dateTimeFormat || (depth0 && depth0.dateTimeFormat) || alias2).call(alias1,(depth0 != null ? depth0.creationDate : depth0),{"name":"dateTimeFormat","hash":{},"data":data}))
    + "\n                </p>\n            </div>\n            <div class=\"author-content-container clearfix\">\n                <div class=\"author-info\">\n                    <img class=\"img-circle image-avatar img-responsive avatar\"\n                         src=\""
    + alias4(alias5(((stack1 = (depth0 != null ? depth0.author : depth0)) != null ? stack1.avatar : stack1), depth0))
    + "\" alt=\"avatar\"/>\n                    <div class=\"author-name-content\" title=\""
    + alias4(alias5(((stack1 = (depth0 != null ? depth0.author : depth0)) != null ? stack1.displayName : stack1), depth0))
    + "\">\n                        "
    + alias4(alias5(((stack1 = (depth0 != null ? depth0.author : depth0)) != null ? stack1.displayName : stack1), depth0))
    + "\n                    </div>\n                </div>\n                <button id=\"share-feed-button\" class=\"share-feed-btn btn btn-default\">\n                    <i class=\"fa fa-share-alt\" aria-hidden=\"true\"></i>\n                    <span>Share</span>\n                </button>\n                <div class=\"feed-share-container\">\n                    <div class=\"zion-social-share-plugin-container\" id=\"zion-social-share-container\"\n                         data-share-url=\""
    + alias4(((helper = (helper = helpers.shortUrl || (depth0 != null ? depth0.shortUrl : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"shortUrl","hash":{},"data":data}) : helper)))
    + "\" data-share-title=\""
    + alias4(((helper = (helper = helpers.title || (depth0 != null ? depth0.title : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"title","hash":{},"data":data}) : helper)))
    + "\" data-share-description=\""
    + alias4(((helper = (helper = helpers.content || (depth0 != null ? depth0.content : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"content","hash":{},"data":data}) : helper)))
    + "\"\n                         data-share-image=\""
    + alias4(alias5(((stack1 = (depth0 != null ? depth0.coverImage : depth0)) != null ? stack1.url : stack1), depth0))
    + "\"></div>\n                </div>\n            </div>\n        </div>\n    </div>\n    <div class=\"zion-feed-content col-md-8 col-md-offset-2\">\n        <div class=\"image annotation-container\">\n"
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.coverImage : depth0),{"name":"if","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.content : depth0),{"name":"if","hash":{},"fn":container.program(4, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + ((stack1 = helpers.each.call(alias1,(depth0 != null ? depth0.mediaContent : depth0),{"name":"each","hash":{},"fn":container.program(6, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "        </div>\n    </div>\n</div>\n\n<div style=\"display:none;\" easypin-tpl>\n    <popover>\n        <div class=\"exPopoverContainer\" shadow=\"true\">\n            <div class=\"popBg\">\n                <a href={[url]} target=\"_blank\">\n                    <p class=\"annotation-title\">{[title]}</p>\n                </a>\n            </div>\n        </div>\n    </popover>\n    <marker>\n        <div class=\"marker2\" style=\"height: 15px; width: 15px\">\n        </div>\n    </marker>\n</div>";
},"useData":true});
})();