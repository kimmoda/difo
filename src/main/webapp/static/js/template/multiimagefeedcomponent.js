(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['multiimagefeedcomponent'] = template({"1":function(container,depth0,helpers,partials,data) {
    var stack1, helper, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression;

  return "        <div class=\"form-group image-preview\">\n            <div class=\"image-container\">\n                <img id=\"look-thumbnail-"
    + alias4(((helper = (helper = helpers.publicId || (depth0 != null ? depth0.publicId : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"publicId","hash":{},"data":data}) : helper)))
    + "\" easypin-id=\""
    + alias4(((helper = (helper = helpers.publicId || (depth0 != null ? depth0.publicId : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"publicId","hash":{},"data":data}) : helper)))
    + "\" class=\"look-thumbnail img-responsive\" src=\""
    + alias4(((helper = (helper = helpers.url || (depth0 != null ? depth0.url : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"url","hash":{},"data":data}) : helper)))
    + "\"\n                     alt=\"look image preview\" data-mediaId=\""
    + alias4(((helper = (helper = helpers.publicId || (depth0 != null ? depth0.publicId : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"publicId","hash":{},"data":data}) : helper)))
    + "\">\n            </div>\n        </div>\n        <div class=\"form-group margin-bottom-0 edit-area\">\n            <div class=\"tools-area zion-hide\" id=\"tools-area-"
    + alias4(((helper = (helper = helpers.publicId || (depth0 != null ? depth0.publicId : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"publicId","hash":{},"data":data}) : helper)))
    + "\">\n                <div class=\"btn btn-sm insert-Link editor-tool-btn\" data-mediaId=\""
    + alias4(((helper = (helper = helpers.publicId || (depth0 != null ? depth0.publicId : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"publicId","hash":{},"data":data}) : helper)))
    + "\" data-toggle=\"modal\"\n                     data-target=\"#insert-link\">\n                    <i class=\"fa fa-link\" aria-hidden=\"true\"></i>\n                </div>\n            </div>\n            <label for=\"feed-description\" class=\"title\">Tell us about your look <span\n                    class=\"zion-form-compulsory\">*</span></label>\n            <div contenteditable=\"true\" class=\"form-control feed-description\"\n                 id=\"feed-description-"
    + alias4(((helper = (helper = helpers.publicId || (depth0 != null ? depth0.publicId : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"publicId","hash":{},"data":data}) : helper)))
    + "\" data-mediaId=\""
    + alias4(((helper = (helper = helpers.publicId || (depth0 != null ? depth0.publicId : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"publicId","hash":{},"data":data}) : helper)))
    + "\">"
    + ((stack1 = ((helper = (helper = helpers.description || (depth0 != null ? depth0.description : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"description","hash":{},"data":data}) : helper))) != null ? stack1 : "")
    + "</div>\n\n            <div id=\"desc-error-indicator\" class=\"zion-input-error-text zion-hide\"></div>\n        </div>\n";
},"3":function(container,depth0,helpers,partials,data) {
    var stack1, helper, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression;

  return "        <div class=\"form-group video-container\">\n            <iframe src=\""
    + alias4(((helper = (helper = helpers.url || (depth0 != null ? depth0.url : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"url","hash":{},"data":data}) : helper)))
    + "\" allowfullscreen></iframe>\n        </div>\n        <div class=\"form-group margin-bottom-0 edit-area\">\n            <div class=\"tools-area zion-hide\" id=\"tools-area-"
    + alias4(((helper = (helper = helpers.publicId || (depth0 != null ? depth0.publicId : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"publicId","hash":{},"data":data}) : helper)))
    + "\">\n                <div class=\"btn btn-sm insert-Link editor-tool-btn\" data-mediaId=\""
    + alias4(((helper = (helper = helpers.publicId || (depth0 != null ? depth0.publicId : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"publicId","hash":{},"data":data}) : helper)))
    + "\" data-toggle=\"modal\"\n                     data-target=\"#insert-link\">\n                    <i class=\"fa fa-link\" aria-hidden=\"true\"></i>\n                </div>\n            </div>\n            <label for=\"feed-description\" class=\"title\">Tell us about your look <span\n                    class=\"zion-form-compulsory\">*</span></label>\n            <div contenteditable=\"true\" class=\"form-control feed-description\"\n                 id=\"feed-description-"
    + alias4(((helper = (helper = helpers.publicId || (depth0 != null ? depth0.publicId : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"publicId","hash":{},"data":data}) : helper)))
    + "\" data-mediaId=\""
    + alias4(((helper = (helper = helpers.publicId || (depth0 != null ? depth0.publicId : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"publicId","hash":{},"data":data}) : helper)))
    + "\">"
    + ((stack1 = ((helper = (helper = helpers.description || (depth0 != null ? depth0.description : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"description","hash":{},"data":data}) : helper))) != null ? stack1 : "")
    + "</div>\n\n            <div id=\"desc-error-indicator\" class=\"zion-input-error-text zion-hide\"></div>\n        </div>\n";
},"5":function(container,depth0,helpers,partials,data) {
    var stack1, helper, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression;

  return "        <div class=\"form-group margin-bottom-0 edit-area\">\n            <div class=\"tools-area zion-hide\" id=\"tools-area-"
    + alias4(((helper = (helper = helpers.publicId || (depth0 != null ? depth0.publicId : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"publicId","hash":{},"data":data}) : helper)))
    + "\">\n                <div class=\"btn btn-sm insert-Link editor-tool-btn\" data-mediaId=\""
    + alias4(((helper = (helper = helpers.publicId || (depth0 != null ? depth0.publicId : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"publicId","hash":{},"data":data}) : helper)))
    + "\" data-toggle=\"modal\"\n                     data-target=\"#insert-link\">\n                    <i class=\"fa fa-link\" aria-hidden=\"true\"></i>\n                </div>\n            </div>\n            <label for=\"feed-description\" class=\"title\">Tell us about your look <span\n                    class=\"zion-form-compulsory\">*</span></label>\n            <div contenteditable=\"true\" class=\"form-control feed-description\"\n                 id=\"feed-description-"
    + alias4(((helper = (helper = helpers.publicId || (depth0 != null ? depth0.publicId : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"publicId","hash":{},"data":data}) : helper)))
    + "\" data-mediaId=\""
    + alias4(((helper = (helper = helpers.publicId || (depth0 != null ? depth0.publicId : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"publicId","hash":{},"data":data}) : helper)))
    + "\">"
    + ((stack1 = ((helper = (helper = helpers.description || (depth0 != null ? depth0.description : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"description","hash":{},"data":data}) : helper))) != null ? stack1 : "")
    + "</div>\n\n            <div id=\"desc-error-indicator\" class=\"zion-input-error-text zion-hide\"></div>\n        </div>\n";
},"7":function(container,depth0,helpers,partials,data) {
    var helper, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression;

  return "            <label class=\"checkbox-inline\">\n                <input id=\"cover-image-"
    + alias4(((helper = (helper = helpers.publicId || (depth0 != null ? depth0.publicId : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"publicId","hash":{},"data":data}) : helper)))
    + "\" class=\"cover-image\" type=\"checkbox\" value=\"cover-image\"\n                       data-mediaId=\""
    + alias4(((helper = (helper = helpers.publicId || (depth0 != null ? depth0.publicId : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"publicId","hash":{},"data":data}) : helper)))
    + "\">Set as cover image\n            </label>\n";
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1, helper, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression;

  return "<div class=\"feed-info-container zion-form\" id=\"component-"
    + alias4(((helper = (helper = helpers.publicId || (depth0 != null ? depth0.publicId : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"publicId","hash":{},"data":data}) : helper)))
    + "\">\n"
    + ((stack1 = (helpers.ifEqual || (depth0 && depth0.ifEqual) || alias2).call(alias1,(depth0 != null ? depth0.mediaType : depth0),"IMAGE",{"name":"ifEqual","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + ((stack1 = (helpers.ifEqual || (depth0 && depth0.ifEqual) || alias2).call(alias1,(depth0 != null ? depth0.mediaType : depth0),"VIDEO",{"name":"ifEqual","hash":{},"fn":container.program(3, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + ((stack1 = (helpers.ifEqual || (depth0 && depth0.ifEqual) || alias2).call(alias1,(depth0 != null ? depth0.mediaType : depth0),"TEXT",{"name":"ifEqual","hash":{},"fn":container.program(5, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "    <div class=\"trash-icon\" data-mediaId=\""
    + alias4(((helper = (helper = helpers.publicId || (depth0 != null ? depth0.publicId : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"publicId","hash":{},"data":data}) : helper)))
    + "\">\n        <i class=\"fa fa-times\" aria-hidden=\"true\"></i>\n    </div>\n    <div class=\"action-container\">\n"
    + ((stack1 = (helpers.ifEqual || (depth0 && depth0.ifEqual) || alias2).call(alias1,(depth0 != null ? depth0.mediaType : depth0),"IMAGE",{"name":"ifEqual","hash":{},"fn":container.program(7, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "    </div>\n\n</div>\n";
},"useData":true});
})();